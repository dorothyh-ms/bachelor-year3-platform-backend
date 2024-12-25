# Use an official Python image
FROM python:3.11

# Set environment variables
ENV DATA_PATH=/app/data

# Set the working directory
WORKDIR /app

# Copy requirements file and install dependencies
COPY requirements.txt .
RUN pip install --upgrade pip
RUN pip install -r requirements.txt

# Copy the Python script and data directory into the container
COPY predict.py .
COPY data/ ./data/

# Expose port 8000 for FastAPI
EXPOSE 8000

# Command to run the FastAPI app
# CMD ["uvicorn", "predict:app", "--host", "0.0.0.0", "--port", "8000"]
