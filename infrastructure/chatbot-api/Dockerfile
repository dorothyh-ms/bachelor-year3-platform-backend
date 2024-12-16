# Use an official Python runtime as a parent image
FROM python:3.11-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install system dependencies
RUN apt-get update && apt-get install -y \
    build-essential \
    libpq-dev \
    curl \
    && curl -sSL https://ollama.com/install.sh | bash \
    && rm -rf /var/lib/apt/lists/*

#RUN ollama pull mistral:latest
#RUN ollama pull nomic-embed-text:latest

# Install any needed packages specified in requirements.txt
RUN pip install --no-cache-dir --upgrade pip
RUN pip install --no-cache-dir -r requirements.txt

RUN chmod +x entrypoint.sh \
    && ./entrypoint.sh

# Expose the port that FastAPI will run on
EXPOSE 8000

#ENTRYPOINT ["/app/entrypoint.sh"]

# Command to run the application using Uvicorn server
CMD ["uvicorn", "rag:app", "--host", "0.0.0.0", "--port", "8000"]
