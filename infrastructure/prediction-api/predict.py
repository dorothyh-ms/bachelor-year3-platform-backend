import pandas as pd
from datetime import datetime, timedelta
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import os
import joblib

# Load the trained models
engagement_model_path = "time_spent_predictor.pkl"
churn_model_path = "churn_predictor.pkl"

if not os.path.exists(engagement_model_path):
    raise FileNotFoundError(f"Engagement model file '{engagement_model_path}' not found. Ensure the model is trained and available.")
engagement_model = joblib.load(engagement_model_path)

if not os.path.exists(churn_model_path):
    raise FileNotFoundError(f"Churn model file '{churn_model_path}' not found. Ensure the model is trained and available.")
churn_model = joblib.load(churn_model_path)

# Load aggregated data for churn prediction
churn_data_path = "player_churn_data.pkl"
if not os.path.exists(churn_data_path):
    raise FileNotFoundError(f"Churn data file '{churn_data_path}' not found. Ensure the aggregated data is saved and available.")
with open(churn_data_path, "rb") as f:
    player_churn_data = joblib.load(f)

# FastAPI integration
app = FastAPI()

@app.get("/")
async def home():
    return "Welcome to the Player Engagement and Churn Prediction API!"

# Request model for engagement prediction
class PredictPlayerGameEngagementRequest(BaseModel):
    username: str
    game_name: str

# Request model for churn prediction
class ChurnPredictionRequest(BaseModel):
    username: str

@app.post("/predict-engagement")
def predict_minutes(request: PredictPlayerGameEngagementRequest):
    username = request.username
    game_name = request.game_name
    historical_data = pd.read_csv("data/historical_data.csv", index_col=False)

    if historical_data[historical_data['game_name'].str.lower() == game_name.lower()].empty:
        raise HTTPException(status_code=404, detail=f"Game '{game_name}' not found.")

    players = pd.read_csv("data/players.csv")
    player_row = players.loc[players['username'] == username]

    if player_row.empty:
        raise HTTPException(status_code=404, detail=f"Username '{username}' not found.")

    player_id = player_row['id'].iloc[0]
    filtered_row = historical_data[
        (historical_data['player_id'] == player_id) &
        (historical_data['game_name'].str.lower() == game_name.lower())
        ].reset_index()
    player_has_played_game = not filtered_row.empty

    predictions = []
    for i in range(7):
        date = datetime.today() + timedelta(days=i)
        if player_has_played_game:
            player_gender = player_row['gender'].iloc[0]
            player_birth_date = pd.to_datetime(player_row['birth_date'].iloc[0])
            player_age = datetime.today().year - player_birth_date.year
            avg_time_spent = filtered_row.average_match_duration[0]
            total_time_spent = filtered_row.total_time_spent_playing[0]
            matches_played = filtered_row.number_of_matches_played[0]

            input_data = {
                'day_of_week': [date.weekday()],
                'is_weekend': [1 if date.weekday() in [5, 6] else 0],
                'avg_time_spent': [avg_time_spent],
                'total_time_spent': [total_time_spent],
                'matches_played': [matches_played],
                'age': [player_age],
                'gender_FEMALE': [1] if player_gender.lower() == 'female' else 0,
                'gender_MALE': [1] if player_gender.lower() == 'male' else 0
            }
            input_df = pd.DataFrame(input_data)
            predicted_time = engagement_model.predict(input_df)[0]
            predictions.append({
                "date": date.strftime("%Y-%m-%d"),
                "predicted_minutes": round(predicted_time, 2)
            })
        else:
            predictions.append({
                "date": date.strftime("%Y-%m-%d"),
                "predicted_minutes": 0
            })

    return {
        "username": username,
        "game_name": game_name,
        "predictions": predictions
    }

@app.get("/predict-churn")
def predict_churn(username: str):
    players = pd.read_csv("data/players.csv")

    player_row = players.loc[players['username'] == username]
    if player_row.empty:
        raise HTTPException(status_code=404, detail=f"Username '{username}' not found.")

    player_id = player_row['id'].iloc[0]
    player_data = player_churn_data[player_churn_data['player_id'] == player_id]
    if player_data.empty:
        raise HTTPException(status_code=404, detail=f"No historical data found for username '{username}'.")

    input_data = player_data[['total_time_spent', 'matches_played', 'age', 'gender_FEMALE', 'gender_MALE']].iloc[0]
    input_df = pd.DataFrame([input_data])

    churn_prediction = churn_model.predict(input_df)[0]

    return {
        "username": username,
        "player_id": player_id,
        "churn": churn_prediction,
        "prediction": f"The player's churn level is predicted to be '{churn_prediction}'."
    }
