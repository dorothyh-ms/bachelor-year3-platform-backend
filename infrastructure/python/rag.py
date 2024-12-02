from fastapi import FastAPI, HTTPException
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
from langchain_community.embeddings import OpenAIEmbeddings

# Initialize FastAPI app
app = FastAPI()

# Define request model
class QueryRequest(BaseModel):
    user_id: UUID  # Expect UUID for the user ID
    question: str

# Static file paths (you can change these based on your file location)
TXT_PATHS = [os.path.join("txt", f) for f in os.listdir("txt") if f.endswith(".txt")]

# Initialize Chroma client
def get_chroma_client():
    return chromadb.PersistentClient(path="../chroma_db")

# Global variables
model_local = OllamaLLM(model="mistral")
chroma_client = get_chroma_client()
vectorstore = None

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
            with open(txt_path, 'r', encoding='utf-8') as file:
                text = file.read()
            documents = [Document(page_content=text, metadata={"source": txt_path})]
            docs.extend(documents)
    return docs

# Process input to get the answer with conversation history
def process_input(user_id: UUID, question: str):
    # Load documents and initialize the vectorstore
    docs_list = load_texts_from_path(TXT_PATHS)
    initialize_vectorstore(docs_list)
    retriever = vectorstore.as_retriever()

    # Retrieve previous conversation history
    if user_id not in conversation_history:
        conversation_history[user_id] = []

    # Append the new question to the conversation history
    conversation_history[user_id].append(f"User: {question}")

    # Combine conversation history with the documents retrieved
    context = "\n".join(conversation_history[user_id])  # Full conversation as context

    after_rag_template = """Answer the question based only on the following context and conversation history:
    {context}
    Question: {question}
    """
    after_rag_prompt = ChatPromptTemplate.from_template(after_rag_template)
    after_rag_chain = (
            {"context": retriever, "question": RunnablePassthrough()}
            | after_rag_prompt
            | model_local
            | StrOutputParser()
    )
    answer = after_rag_chain.invoke(question)

    # Append the model's answer to the conversation history
    conversation_history[user_id].append(f"Model: {answer}")
    print(context)

    return answer

# Query documents endpoint (Updated path: /question)
@app.post("/question/")
async def question(request: QueryRequest):
    try:
        user_id = request.user_id  # Retrieve user_id (UUID) from the request
        answer = process_input(user_id, request.question)
        return {"answer": answer}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"An error occurred: {e}")
