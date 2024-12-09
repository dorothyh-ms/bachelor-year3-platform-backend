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
    game: str

def setup():
    print("Pulling models...")
    print(subprocess.run(["/app/entrypoint.sh"], shell=True))

# Static file paths (you can change these based on your file location)
RULES_PATHS = [os.path.join(RULES_FOLDER, f) for f in os.listdir(RULES_FOLDER) if f.endswith(".txt")]

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

# Load text files from paths
def load_texts_from_path(txt_paths):
    docs = []
    for txt_path in txt_paths:
        if os.path.exists(txt_path):
            print(f"Loading file: {txt_path}")  # Debugging line
            with open(txt_path, 'r', encoding='utf-8') as file:
                text = file.read()

            # Generate a unique document ID (e.g., based on the file name)
            doc_id = os.path.basename(txt_path)  # Using the file name as the ID
            documents = [Document(page_content=text, metadata={"source": txt_path, "id": doc_id})]
            docs.extend(documents)

    print(f"Total documents loaded: {len(docs)}")  # Debugging line
    return docs


# Process input to get the answer with conversation history
def process_input(user_id: UUID, question: str, game: str):
    # Load documents and initialize the vectorstore
    docs_list = load_texts_from_path(RULES_PATHS)
    initialize_vectorstore(docs_list)
    retriever = vectorstore.as_retriever()
    print(game)
    # Retrieve previous conversation history
    if user_id not in conversation_history:
        conversation_history[user_id] = []

    # Append the new question to the conversation history
    conversation_history[user_id].append(f"User: {question}")

    # Combine conversation history with the documents retrieved
    context = "\n".join(conversation_history[user_id])  # Full conversation as context

    after_rag_template = """Answer the question based only on the following context, the conversation history, and the content of the file {game}_rules.txt only if the question is based on the game:
    Context: {context}
    Game: {game}
    Question: {question}
    
    If the question is not related to any game kindly decline answering
    """

    after_rag_prompt = ChatPromptTemplate.from_template(after_rag_template)
    after_rag_chain = (
            {"context": retriever, "question": RunnablePassthrough(), "game": RunnablePassthrough()}
            | after_rag_prompt
            | model_local
            | StrOutputParser()
    )
    answer = after_rag_chain.invoke(question)

    # Append the model's answer to the conversation history
    conversation_history[user_id].append(f"Model: {answer}")

    return answer

# Query documents endpoint (Updated path: /question)
@app.post("/question/")
async def question(request: QueryRequest):
    try:
        user_id = request.user_id  # Retrieve user_id (UUID) from the request
        answer = process_input(user_id, request.question, request.game)
        return {"answer": answer}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"An error occurred: {e}")


@app.post("/add-rules/")
async def add_rules_file(file: UploadFile = File(...)):
    try:
        # Save the uploaded file to the rules folder
        file_path = os.path.join(RULES_FOLDER, file.filename)

        with open(file_path, "wb") as f:
            f.write(await file.read())

        # Reload documents and reinitialize the vectorstore with new documents
        docs_list = load_texts_from_path(RULES_PATHS)
        initialize_vectorstore(docs_list)

        return {"message": f"File '{file.filename}' uploaded and vectorstore updated successfully."}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"An error occurred: {e}")


