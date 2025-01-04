# Use an official Python image as the base
FROM python:3.10-slim

# Set the working directory
WORKDIR /app

# Copy all necessary files to the container
COPY . /app

# Copy requirements file and install dependencies
COPY requirements.txt .
RUN pip install --upgrade pip
# Install system dependencies required by LightGBM
RUN apt-get update && apt-get install -y libgomp1 && rm -rf /var/lib/apt/lists/*

RUN pip install -r requirements.txt


# Run the training script to generate the model
RUN python train_engagement_model.py

# Expose the port for FastAPI
EXPOSE 8000

# Command to start FastAPI
CMD ["uvicorn", "predict:app", "--host", "0.0.0.0", "--port", "8000"]
