import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report
from sklearn.linear_model import LogisticRegression
from sklearn.preprocessing import StandardScaler, OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline
import joblib

# Load Data
player_matches = pd.read_csv('data/player_matches.csv')
matches = pd.read_csv('data/matches.csv')
players = pd.read_csv('data/players.csv')
games = pd.read_csv('data/games.csv')

# Preprocess Matches Data
# Merge player_matches and matches datasets on match_id
merged = player_matches.merge(matches, left_on='match_id', right_on='id', suffixes=('_player', '_match'))
merged = merged.merge(games, how="left", on="game_id")

# Calculate match duration
merged['start_time'] = pd.to_datetime(merged['start_time'])
merged['end_time'] = pd.to_datetime(merged['end_time'])
merged['match_duration'] = (merged['end_time'] - merged['start_time']).dt.total_seconds() / 60  # duration in minutes

# Add features
merged['day_of_week'] = merged['start_time'].dt.dayofweek  # 0=Monday, 6=Sunday
merged['is_weekend'] = merged['day_of_week'].isin([5, 6]).astype(int)
merged['date'] = merged['start_time'].dt.date

# Aggregate features per player
player_aggregates = merged.groupby('player_id').agg(
    avg_time_spent=('match_duration', 'mean'),
    total_time_spent=('match_duration', 'sum'),
    matches_played=('match_id', 'count'),
    last_played_date=('date', 'max')
).reset_index()

# Add churn target
cutoff_date = pd.Timestamp.now()
player_aggregates['last_played_date'] = pd.to_datetime(player_aggregates['last_played_date'])
player_aggregates['churned'] = (cutoff_date - player_aggregates['last_played_date']).dt.days > 30

# Merge with player demographic data
players['birth_date'] = pd.to_datetime(players['birth_date'])
players['age'] = (pd.Timestamp.now() - players['birth_date']).dt.days // 365  # Age in years
players = players[['id', 'age', 'gender']]
players.rename(columns={'id': 'player_id'}, inplace=True)

player_aggregates = player_aggregates.merge(players, on='player_id', how='left')

# One-hot encode gender
gender_encoded = pd.get_dummies(player_aggregates['gender'], prefix='gender', dtype=int)
player_aggregates = pd.concat([player_aggregates, gender_encoded], axis=1)

# Features and target
features = ['avg_time_spent', 'total_time_spent', 'matches_played', 'age', 'gender_FEMALE', 'gender_MALE']
target = 'churned'

X = player_aggregates[features]
y = player_aggregates[target]

# Split data
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Standardize features
scaler = StandardScaler()
model = LogisticRegression(random_state=42)

# Pipeline
pipeline = Pipeline(steps=[
    ('scaler', scaler),
    ('model', model)
])

# Train the model
pipeline.fit(X_train, y_train)

# Predict and evaluate
y_pred = pipeline.predict(X_test)
accuracy = accuracy_score(y_test, y_pred)
print(f"Accuracy: {accuracy:.2f}")
print(classification_report(y_test, y_pred))

# Save the model
joblib.dump(pipeline, 'player_churn_predictor.pkl')


