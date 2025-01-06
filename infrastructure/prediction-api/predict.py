import pandas as pd
from datetime import datetime, timedelta
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import os

# TODO: load model
# Import joblib to load the model
import joblib

# Load the trained model
model_path = "time_spent_predictor.pkl"
if not os.path.exists(model_path):
    raise FileNotFoundError(f"Model file '{model_path}' not found. Ensure the model is trained and available.")
model = joblib.load(model_path)


# FastAPI Integration
app = FastAPI()
@app.get("/")
async def home():
    return "hi from predict.py!"


class PredictPlayerGameEngagementRequest(BaseModel):
    username: str  # Changed to 'username'
    game_name: str

@app.post("/predict-engagement")
def predict_minutes(request: PredictPlayerGameEngagementRequest):
    username = request.username  # Use 'username' instead of 'player_id'
    game_name = request.game_name
    historical_data = pd.read_csv("data/historical_data.csv", index_col=False)

    if historical_data[historical_data['game_name'].str.lower() == game_name.lower()].empty:
        raise HTTPException(status_code=404, detail=f"Game '{game_name}' not found.")

    # Check if the username exists in players data
    players = pd.read_csv("data/players.csv")
    player_row = players.loc[players['username'] == username]  # Match username instead of player_id

    if player_row.empty:
        raise HTTPException(status_code=404, detail=f"Username '{username}' not found.")

    player_id = player_row['id'].iloc[0]  # Get the player_id based on the username

    # Check if the combination of player_id and game_name exists
    filtered_row = historical_data[
        (historical_data['player_id'] == player_id) & 
        (historical_data['game_name'].str.lower() == game_name.lower())
    ].reset_index()
    player_has_played_game = not filtered_row.empty
    
    

    # Generate predictions for the next 7 days
    predictions = []
    for i in range(7):
        date = datetime.today() + timedelta(days=i)
        if player_has_played_game:
            # Proceed with the usual logic if both player_id and game_name exist
            print("FILTERED ROW", filtered_row['player_id'][0])

            player_gender = player_row['gender'].iloc[0]
            player_birth_date = pd.to_datetime(player_row['birth_date'].iloc[0])

            player_age = datetime.today().year - player_birth_date.year
            avg_time_spent = filtered_row.average_match_duration[0]
            total_time_spent = filtered_row.total_time_spent_playing[0]
            matches_played = filtered_row.number_of_matches_played[0]
            
            # Create input for the future prediction
            input_data = {
                'day_of_week': [date.weekday()],
                'is_weekend': [1 if date.weekday() in [5, 6] else 0],
                'avg_time_spent': [avg_time_spent],  
                'total_time_spent': [total_time_spent],  
                'matches_played': [matches_played],  
                'age': [player_age],  
                'gender_FEMALE': [1] if player_gender.lower() == 'female' else 0,  # Gender encoding
                'gender_MALE': [1] if player_gender.lower() == 'male' else 0   # Gender encoding
            }

            input_df = pd.DataFrame(input_data)
            predicted_time = model.predict(input_df)[0]
            print(f"Predicted time spent for player {username} on boardGame {game_name} for {date}: {predicted_time:.2f} minutes")
            
            # Store the result for each day
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