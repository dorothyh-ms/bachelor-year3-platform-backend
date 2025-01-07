### train_churn_model.py ###
import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report
from lightgbm import LGBMClassifier
import datetime
import joblib

# Load Data
print("Loading dataset...")
player_matches = pd.read_csv('data/player_matches.csv')
matches = pd.read_csv('data/matches.csv')
players = pd.read_csv('data/players.csv')
print("Loading completed.")

# Preprocess Matches Data
print("Merging columns...")
merged = player_matches.merge(matches, left_on='match_id', right_on='id', suffixes=('_player', '_match'))

# Calculate match activity data
merged['start_time'] = pd.to_datetime(merged['start_time'])
merged['end_time'] = pd.to_datetime(merged['end_time'])
merged['match_duration'] = (merged['end_time'] - merged['start_time']).dt.total_seconds() / 60  # duration in minutes
merged['date'] = merged['start_time'].dt.date

# Aggregate features per player
player_aggregates = merged.groupby('player_id').agg(
    total_time_spent=('match_duration', 'sum'),
    matches_played=('match_id', 'count'),
    last_active_date=('date', 'max')
).reset_index()

# Merge Player Data
players['birth_date'] = pd.to_datetime(players['birth_date'])
players['age'] = (datetime.datetime.now() - players['birth_date']).dt.days // 365  # Calculate age
players = players[['id', 'age', 'gender']]
players.rename(columns={'id': 'player_id'}, inplace=True)
player_data = player_aggregates.merge(players, on='player_id', how='left')

# Calculate churn target
# Assuming churn levels based on inactivity
player_data['last_active_date'] = pd.to_datetime(player_data['last_active_date'])
days_inactive = (datetime.datetime.now() - player_data['last_active_date']).dt.days
player_data['churn_level'] = pd.cut(
    days_inactive,
    bins=[-1, 30, 90, np.inf],  # Define ranges for low, medium, high churn
    labels=['low', 'medium', 'high']
)

# One-hot encode gender
gender_encoded = pd.get_dummies(player_data['gender'], prefix='gender', dtype=int)
player_data = pd.concat([player_data, gender_encoded], axis=1)

# Prepare features and target
features = ['total_time_spent', 'matches_played', 'age', 'gender_FEMALE', 'gender_MALE']
target = 'churn_level'

X = player_data[features]
y = player_data[target]

# Split data into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Train the model
print("Training the churn prediction model...")
model = LGBMClassifier(random_state=42)
model.fit(X_train, y_train)

# Evaluate the model
print("Evaluating the model...")
y_pred = model.predict(X_test)
print("Accuracy:", accuracy_score(y_test, y_pred))
print("Classification Report:\n", classification_report(y_test, y_pred))

# Save the model
joblib.dump(model, 'churn_predictor.pkl')
print("Model saved as 'churn_predictor.pkl'.")

# Save aggregated data as .pkl
player_data.drop(columns=['last_active_date'], inplace=True)  # Drop non-predictive columns
with open("player_churn_data.pkl", "wb") as f:
    joblib.dump(player_data, f)
print("Aggregated player data saved as 'player_churn_data.pkl'.")

print("Training complete.")
