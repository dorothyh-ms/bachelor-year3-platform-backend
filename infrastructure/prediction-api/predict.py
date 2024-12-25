import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor
from sklearn.preprocessing import LabelEncoder
from datetime import datetime
from fastapi import FastAPI
from pydantic import BaseModel
import os

# Load data from environment-defined paths
DATA_PATH = os.getenv("DATA_PATH", "./data")

countries = pd.read_csv(os.path.join(DATA_PATH, "countries.csv"))
locations = pd.read_csv(os.path.join(DATA_PATH, "locations.csv"))
players = pd.read_csv(os.path.join(DATA_PATH, "players.csv"))
games = pd.read_csv(os.path.join(DATA_PATH, "games.csv"))
matches = pd.read_csv(os.path.join(DATA_PATH, "matches.csv"))
player_matches = pd.read_csv(os.path.join(DATA_PATH, "player_matches.csv"))

# Step 1: Merge Data into a Single Dataset
# Merge locations with countries
locations = locations.merge(countries, left_on='country_id', right_on='id', suffixes=('_location', '_country'))
locations = locations.drop(columns=['id_country'])

# Merge players with locations
players = players.merge(locations, left_on='location_id', right_on='id_location', how='left')

# Merge player_matches with players
player_data = player_matches.merge(players, left_on='player_id', right_on='id', suffixes=('_match', '_player'))

# Merge matches with games
matches = matches.merge(games, left_on='game_id', right_on='game_id')

# Merge all into final dataset
final_df = player_data.merge(matches, left_on='match_id', right_on='id', suffixes=('_player_match', '_game'))

# Step 2: Feature Engineering
# Convert start and end times to datetime
final_df['start_time'] = pd.to_datetime(final_df['start_time'])
final_df['end_time'] = pd.to_datetime(final_df['end_time'])

# Calculate match duration in minutes
final_df['match_duration'] = (final_df['end_time'] - final_df['start_time']).dt.total_seconds() / 60

# Create player-level aggregated features
player_features = final_df.groupby(['player_id', final_df['start_time'].dt.date]).agg({
    'match_duration': 'sum',  # Total match duration per day
    'game_name': 'nunique',  # Number of unique games played
    'difficulty_level': lambda x: x.mode()[0] if not x.mode().empty else 'BEGINNER',
    'match_id': 'count'  # Total matches played
}).reset_index()

player_features.columns = ['player_id', 'date', 'total_minutes_played', 'unique_games_played', 'most_played_difficulty',
                           'total_matches_played']

# Add demographic data from players
player_features = player_features.merge(players[['id', 'birth_date', 'gender']], left_on='player_id', right_on='id')
player_features = player_features.drop(columns=['id'])

# Feature: Age
player_features['birth_date'] = pd.to_datetime(player_features['birth_date'])
player_features['age'] = (pd.Timestamp.now() - player_features['birth_date']).dt.days // 365
player_features = player_features.drop(columns=['birth_date'])

# Encode categorical variables
le_gender = LabelEncoder()
player_features['gender'] = le_gender.fit_transform(player_features['gender'])

le_difficulty = LabelEncoder()
player_features['most_played_difficulty'] = le_difficulty.fit_transform(player_features['most_played_difficulty'])

# Step 3: Define Features and Target
X = player_features[
    ['player_id', 'date', 'unique_games_played', 'most_played_difficulty', 'total_matches_played', 'age', 'gender']]
y = player_features['total_minutes_played']

# Convert date to ordinal for modeling purposes
X = X.copy()
X.loc[:, 'date'] = pd.to_datetime(X['date']).apply(lambda x: x.toordinal())

# Step 4: Train-Test Split
X_train, X_test, y_train, y_test = train_test_split(X.drop(columns=['player_id']), y, test_size=0.2, random_state=42)

# Step 5: Train the Regression Model
model = RandomForestRegressor(n_estimators=100, random_state=42)
print("Training the model...")
model.fit(X_train, y_train)  # Ensure the model is fitted
print("Model training completed.")
feature_names = X_train.columns.tolist()

# FastAPI Integration
app = FastAPI()

class PredictionRequest(BaseModel):
    player_id: str
    date: str

@app.post("/predict")
def predict_minutes(request: PredictionRequest):
    player_id = request.player_id
    input_date = request.date

    # Filter data for the specified player
    player_row = players[players['id'] == player_id]
    if player_row.empty:
        return {"error": f"Player ID {player_id} not found."}

    # Generate features for prediction
    age = (datetime.now() - pd.to_datetime(player_row['birth_date'].iloc[0])).days // 365
    gender = le_gender.transform([player_row['gender'].iloc[0]])[0]
    most_played_difficulty = 1  # Default placeholder value

    # Create input data with the exact feature names and order
    input_data = pd.DataFrame([[0, most_played_difficulty, 0, age, gender, pd.to_datetime(input_date).toordinal()]],
                              columns=feature_names)

    # Predict total minutes played
    predicted_minutes = model.predict(input_data)[0]
    return {"player_id": player_id, "date": input_date, "predicted_minutes": round(predicted_minutes, 2)}


