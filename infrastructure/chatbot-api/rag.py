from pydantic import BaseModel
from uuid import UUID
import os
from langchain.docstore.document import Document
import chromadb
from langchain_community.vectorstores import Chroma
from langchain_ollama import OllamaEmbeddings
from langchain_ollama import OllamaLLM
from langchain_core.runnables import RunnablePassthrough
from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import ChatPromptTemplate
from langchain.text_splitter import CharacterTextSplitter
import subprocess
from fastapi import FastAPI, UploadFile, File, HTTPException
import json

# Initialize FastAPI app
app = FastAPI()
# Path to the folder where rule files are stored
RULES_FOLDER = "./rules"
# Ensure the folder exists
os.makedirs(RULES_FOLDER, exist_ok=True)


# Define request model
class QueryRequest(BaseModel):
    user_id: UUID  # Expect UUID for the user ID
    question: str
    boardGame: str

def setup():
    print("Pulling models...")
    print(subprocess.run(["/app/entrypoint.sh"], shell=True))

# Static file paths (you can change these based on your file location)
RULES_PATHS = [os.path.join(RULES_FOLDER, f) for f in os.listdir(RULES_FOLDER) if f.endswith(".json")]

# Initialize Chroma client
def get_chroma_client():
    return chromadb.PersistentClient(path="/app/chroma_db")

# Global variables
model_local = OllamaLLM(model="mistral")
chroma_client = get_chroma_client()
vectorstore = None
setup()

# Store conversation history (session-based, typically would be stored in a DB for real applications)
conversation_history = {}

# Initialize vectorstore
def initialize_vectorstore(docs_list):
    global vectorstore
    text_splitter = CharacterTextSplitter.from_tiktoken_encoder(chunk_size=7500, chunk_overlap=100)
    doc_splits = text_splitter.split_documents(docs_list)

    if vectorstore is None:
        vectorstore = Chroma.from_documents(
            documents=doc_splits,
            collection_name="rag-chroma",
            embedding=OllamaEmbeddings(model='nomic-embed-text'),
            client=chroma_client
        )


def load_json_from_path(paths):
    docs = []
    for path in paths:
        if os.path.exists(path):
            with open(path, 'r', encoding='utf-8') as f:
                json_data = json.load(f)

            # Process the JSON file and flatten it into a string
            docs.extend(process_json_data(json_data, path))
    return docs


def process_json_data(json_data, source_path, parent_key=""):
    docs = []

    # Iterate through the JSON data
    for key, value in json_data.items():
        current_key = f"{parent_key}.{key}" if parent_key else key

        # If the value is a dictionary, recurse into it
        if isinstance(value, dict):
            docs.extend(process_json_data(value, source_path, current_key))

        # If the value is a list, handle accordingly
        elif isinstance(value, list):
            content = f"{current_key}:\n"
            content += "\n".join([str(item) for item in value])  # Join list items with newlines
            docs.append(Document(page_content=content, metadata={"source": source_path, "section": current_key}))

        # Otherwise, it's a simple value (string, number, etc.)
        else:
            content = f"{current_key}: {value}"
            docs.append(Document(page_content=content, metadata={"source": source_path, "section": current_key}))

    return docs


# Process input to get the answer with conversation history
def process_input(user_id: UUID, question: str, boardGame: str):
    # Load documents and initialize the vectorstore
    docs_list = load_json_from_path(RULES_PATHS)
    initialize_vectorstore(docs_list)
    retriever = vectorstore.as_retriever()
    print(boardGame)
    # Retrieve previous conversation history
    if user_id not in conversation_history:
        conversation_history[user_id] = []

    # Append the new question to the conversation history
    conversation_history[user_id].append(f"User: {question}")

    # Combine conversation history with the documents retrieved
    # In process_input, use structured JSON data if needed:
    context = "\n".join([doc.page_content for doc in retriever.get_relevant_documents(question)])
    # Full conversation as context

    if boardGame != "BanditGames":
        validation_template = f"""Is this question: {question} related to {boardGame} and the information in {boardGame}_rules.json? answer in 1 word: true or false"""

        validation_prompt = ChatPromptTemplate.from_template(validation_template)
        validation_chain = (
                {"context": retriever, "question": RunnablePassthrough(), "boardGame": RunnablePassthrough()}
                | validation_prompt
                | model_local
                | StrOutputParser()
        )
        answer = validation_chain.invoke(question)
        print("answer: " + answer)
    else:
        validation_template = f"""answer in 1 word only: true or false: Based on the content of the file {boardGame}_rules.json, determine if it contains any instructions related to the question {question}? """

        validation_prompt = ChatPromptTemplate.from_template(validation_template)
        validation_chain = (
                {"context": retriever, "question": RunnablePassthrough(), "boardGame": RunnablePassthrough()}
                | validation_prompt
                | model_local
                | StrOutputParser()
        )
        answer = validation_chain.invoke(question)
        print("answer: " + answer)

    if answer.split(' ')[0].strip() in "True":
        print("true, answer question")
        after_rag_template = f"""Only answer if the question is related to {boardGame}
        Answer the question based only on the following context, the conversation history, and the content of the file {boardGame}_rules.json only if the question is based on the boardGame do not mention the document in your answer:
        Context: {context}
        Game: {boardGame}
        Question: {question}
        """

        after_rag_prompt = ChatPromptTemplate.from_template(after_rag_template)
        after_rag_chain = (
                {"context": retriever, "question": RunnablePassthrough(), "boardGame": RunnablePassthrough()}
                | after_rag_prompt
                | model_local
                | StrOutputParser()
        )
        answer = after_rag_chain.invoke(question)

        # Append the model's answer to the conversation history
        conversation_history[user_id].append(f"Model: {answer}")

        return answer
    else:
        answer = f"I'm sorry, I can only answer questions about {boardGame}."
        return answer


# Query documents endpoint (Updated path: /question)
@app.post("/question/")
async def question(request: QueryRequest):
    try:
        user_id = request.user_id  # Retrieve user_id (UUID) from the request
        answer = process_input(user_id, request.question, request.boardGame)
        return {"answer": answer}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"An error occurred: {e}")


@app.post("/add-rules/")
async def add_rules_file(file: UploadFile = File(...)):
    try:
        # Validate JSON file extension
        if not file.filename.endswith('.json'):
            raise HTTPException(status_code=400, detail="Only .json files are allowed.")

        # Save the uploaded JSON file
        file_path = os.path.join(RULES_FOLDER, file.filename)

        with open(file_path, "wb") as f:
            f.write(await file.read())

        # Validate JSON content
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                json.load(f)  # Check for valid JSON
        except json.JSONDecodeError:
            os.remove(file_path)  # Remove invalid file
            raise HTTPException(status_code=400, detail="Invalid JSON file format.")

        # Reload documents and reinitialize the vectorstore with new documents
        docs_list = load_json_from_path(RULES_PATHS)
        initialize_vectorstore(docs_list)

        return {"message": f"File '{file.filename}' uploaded and vectorstore updated successfully."}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"An error occurred: {e}")

