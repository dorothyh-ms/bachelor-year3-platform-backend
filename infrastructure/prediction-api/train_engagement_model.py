import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_absolute_error, mean_squared_error
from lightgbm import LGBMRegressor
import datetime

# Load Data
player_matches = pd.read_csv('data/player_matches.csv')
matches = pd.read_csv('data/matches.csv')
players = pd.read_csv('data/players.csv')

# Preprocess Matches Data
# Merge player_matches and matches datasets on match_id
merged = player_matches.merge(matches, left_on='match_id', right_on='id', suffixes=('_player', '_match'))

# Calculate match duration and player time spent (target variable)
merged['start_time'] = pd.to_datetime(merged['start_time'])
merged['end_time'] = pd.to_datetime(merged['end_time'])
merged['match_duration'] = (merged['end_time'] - merged['start_time']).dt.total_seconds() / 60  # duration in minutes

# Extract relevant features
merged['day_of_week'] = merged['start_time'].dt.dayofweek  # 0=Monday, 6=Sunday
merged['is_weekend'] = merged['day_of_week'].isin([5, 6]).astype(int)
merged['date'] = merged['start_time'].dt.date

# Aggregate features
player_game_aggregates = merged.groupby(['player_id', 'game_id']).agg(
    avg_time_spent=('match_duration', 'mean'),
    total_time_spent=('match_duration', 'sum'),
    matches_played=('match_id', 'count')
).reset_index()

game_aggregates = merged.groupby('game_id').agg(
    game_avg_time=('match_duration', 'mean'),
    game_total_matches=('match_id', 'count')
).reset_index()

# Merge aggregates back
merged = merged.merge(player_game_aggregates, on=['player_id', 'game_id'], how='left')
merged = merged.merge(game_aggregates, on='game_id', how='left')

# Merge Player Data
players['birth_date'] = pd.to_datetime(players['birth_date'])
players['age'] = (datetime.datetime.now() - players['birth_date']).dt.days // 365  # Calculate age
players = players[['id', 'age', 'gender']]
players.rename(columns={'id': 'player_id'}, inplace=True)
merged = merged.merge(players, on='player_id', how='left')

# One-hot encode gender
gender_encoded = pd.get_dummies(merged['gender'], prefix='gender', dtype = int)
merged = pd.concat([merged, gender_encoded], axis=1)

# Prepare training data
features = ['day_of_week', 'is_weekend', 'avg_time_spent', 'total_time_spent', 'matches_played', 'age', 'gender_FEMALE', 'gender_MALE']
merged['target'] = merged.groupby(['player_id', 'game_id', 'date'])['match_duration'].transform('sum')

# Drop duplicate records for player_id, game_id, and date to create unique inputs
unique_data = merged[['player_id', 'game_id', 'date', 'day_of_week', 'is_weekend', 'avg_time_spent', 'total_time_spent', 'matches_played', 'game_avg_time', 'game_total_matches', 'age', 'gender_FEMALE', 'gender_MALE', 'target']].drop_duplicates()

# Split data into train/test sets based on time
split_date = unique_data['date'].quantile(0.8)  # 80% train, 20% test based on date
train_data = unique_data[unique_data['date'] <= split_date]
test_data = unique_data[unique_data['date'] > split_date]

X_train = train_data[features]
y_train = train_data['target']
X_test = test_data[features]
y_test = test_data['target']

train_records_index = X_train.index

# Train the model
model = LGBMRegressor(random_state=42)
model.fit(X_train, y_train)

# Make predictions
y_pred = model.predict(X_test)

# Evaluate the model
mae = mean_absolute_error(y_test, y_pred)
rmse = np.sqrt(mean_squared_error(y_test, y_pred))
print(f"Mean Absolute Error: {mae:.2f} minutes")
print(f"Root Mean Squared Error: {rmse:.2f} minutes")

# Save the model for deployment
import joblib
joblib.dump(model, 'time_spent_predictor.pkl')


agg_stats = merged.loc[train_records_index,].groupby(['player_id', 'game_id']).agg(
    average_match_duration=('match_duration', 'mean'),
    total_time_spent_playing=('match_duration', 'sum'),
    number_of_matches_played=('match_id', 'count'),
).reset_index()
agg_stats.to_csv("data/historical_data.csv", index=False)


