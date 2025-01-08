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

# Merging Player Data
players['birth_date'] = pd.to_datetime(players['birth_date'])
players['age'] = (datetime.datetime.now() - players['birth_date']).dt.days // 365  # Calculate age
players = players[['id', 'age', 'gender']]
players.rename(columns={'id': 'player_id'}, inplace=True)
player_data = player_aggregates.merge(players, on='player_id', how='left')

# Calculate churn target
# Analyze inactivity distribution to define thresholds
print("Analyzing inactivity data to define churn thresholds...")
player_data['last_active_date'] = pd.to_datetime(player_data['last_active_date'])
days_inactive = (datetime.datetime.now() - player_data['last_active_date']).dt.days

# Determine thresholds using quantiles
low_churn_threshold = days_inactive.quantile(0.33)  # 33rd percentile
medium_churn_threshold = days_inactive.quantile(0.66)  # 66th percentile

print(f"Low churn threshold: {low_churn_threshold} days")
print(f"Medium churn threshold: {medium_churn_threshold} days")

player_data['churn_level'] = pd.cut(
    days_inactive,
    bins=[-1, low_churn_threshold, medium_churn_threshold, np.inf],  # Define ranges for low, medium, high churn
    labels=['low', 'medium', 'high']
)

# Justification for thresholds:
# - Low churn: Players inactive for up to the 33rd percentile of days_inactive.
# - Medium churn: Players inactive between the 33rd and 66th percentile.
# - High churn: Players inactive longer than the 66th percentile.

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

# Saving the model
joblib.dump(model, 'churn_predictor.pkl')


# Save aggregated data as .pkl for deployment
player_data.drop(columns=['last_active_date'], inplace=True)  # Drop non-predictive columns
with open("player_churn_data.pkl", "wb") as f:
    joblib.dump(player_data, f)

# Export aggregated data to CSV
player_data.to_csv('player_statistics_churn.csv', index=False)
print("Player statistics exported as 'player_statistics_churn.csv'.")


