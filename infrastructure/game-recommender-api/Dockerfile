# Step 1: Use an official Python runtime as a parent image
FROM python:3.11-slim

# Step 2: Set environment variables to ensure the output of Python is not buffered
ENV PYTHONUNBUFFERED 1

# Step 3: Set the working directory inside the container
WORKDIR /app

# Step 4: Copy the current directory contents into the container at /app
COPY . /app/

# Step 5: Install the dependencies
RUN pip install --upgrade pip
RUN pip install -r requirements.txt

# Step 6: Expose the port that FastAPI will run on
EXPOSE 8000
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]

