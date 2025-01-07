import pandas as pd
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import os
import joblib

# Load the trained model
model_path = "churn_predictor.pkl"
if not os.path.exists(model_path):
    raise FileNotFoundError(f"Model file '{model_path}' not found. Ensure the model is trained and available.")
model = joblib.load(model_path)

# Load the aggregated data
data_path = "player_churn_data.pkl"
if not os.path.exists(data_path):
    raise FileNotFoundError(f"Data file '{data_path}' not found. Ensure the aggregated data is saved and available.")
with open(data_path, "rb") as f:
    player_churn_data = joblib.load(f)

# FastAPI Integration
app = FastAPI()

@app.get("/")
async def home():
    return "Welcome to the Player Churn Prediction API!"

class ChurnPredictionRequest(BaseModel):
    username: str

@app.post("/predict-churn")
def predict_churn(request: ChurnPredictionRequest):
    username = request.username
    players = pd.read_csv("data/players.csv")

    # Check if the username exists
    player_row = players.loc[players['username'] == username]
    if player_row.empty:
        raise HTTPException(status_code=404, detail=f"Username '{username}' not found.")

    player_id = player_row['id'].iloc[0]  # Get player_id
    player_data = player_churn_data[player_churn_data['player_id'] == player_id]
    if player_data.empty:
        raise HTTPException(status_code=404, detail=f"No historical data found for username '{username}'.")

    # Prepare input for prediction
    input_data = player_data[['total_time_spent', 'matches_played', 'age', 'gender_FEMALE', 'gender_MALE']].iloc[0]
    input_df = pd.DataFrame([input_data])

    # Predict churn level (low, medium, high)
    churn_prediction = model.predict(input_df)[0]

    # Return result
    return {
        "username": username,
        "player_id": player_id,
        "churn": churn_prediction,
        "message": f"The player's churn level is predicted to be '{churn_prediction}'."
    }
