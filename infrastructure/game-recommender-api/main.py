from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List
import pandas as pd
import mysql.connector
from sklearn.preprocessing import StandardScaler
from sklearn.neighbors import NearestNeighbors
import os

# # Database connection
db = mysql.connector.connect(
    host=os.getenv('MYSQL_HOST', 'localhost'),
    user=os.getenv('MYSQL_USER', 'user'),
    password=os.getenv('MYSQL_PASSWORD', 'password')
)


# Game Recommender Class
class GameRecommender:
    def recommend_unplayed_games(self, player_id, games_scores):
        sorted_games = games_scores.sort_values()
        return [
            game
            for game in sorted_games.index
            if self.player_raw_playtimes.loc[player_id, game] == 0
        ]

    def preprocess_player_match_statistics(self, player_match_statistics_df):
        scaler = StandardScaler()
        df_scaled = scaler.fit_transform(player_match_statistics_df.to_numpy())
        df = pd.DataFrame(df_scaled, index=player_match_statistics_df.index, columns=player_match_statistics_df.columns)
        return df

    def __init__(self, player_match_statistics_df, games_df):
        raw_playtimes = player_match_statistics_df.pivot_table(
            index="player_id",
            columns="game_id",
            values="duration_played",
            aggfunc="sum"
        ).fillna(0)
        self.player_raw_playtimes = raw_playtimes
        self.player_vectors = self.preprocess_player_match_statistics(raw_playtimes)
        self.games=games_df

    def get_all_recommendations(self, player_id):
        if player_id not in self.player_vectors.index:
            raise ValueError(f"Player ID {player_id} not found in data.")
        model = NearestNeighbors(n_neighbors=3, metric='cosine')
        model.fit(self.player_vectors)
        distances, knn = model.kneighbors(self.player_vectors)
        knn = pd.DataFrame(knn, index=self.player_vectors.index).apply(lambda x: self.player_vectors.index[x])
        sim = pd.DataFrame(1 - distances, index=self.player_vectors.index)
        neighbors = knn.loc[player_id, 1:]
        similarities = sim.loc[player_id, 1:]
        similarities.index = self.player_vectors.loc[neighbors].index
        games_scores = pd.Series(
            self.player_vectors.loc[neighbors]
            .mul(similarities, axis='index')
            .sum(axis='index')
            / similarities.sum(),
            name='recommendation'
        )
        unplayed_game_ids = self.recommend_unplayed_games(player_id, games_scores)
        game_recommendations = []
        for id in unplayed_game_ids:
            print("id", id)
            print(self.games.shape)
            name =self.games.loc[self.games['game_id'] == id, 'game_name'].iloc[0]
            difficulty =self.games.loc[self.games['game_id'] == id, 'difficulty_level'].iloc[0]
            description =self.games.loc[self.games['game_id'] == id, 'description'].iloc[0]
            print("name", type(name))
            game_recommendations.append({
                "id" : id,
                "name" : name,
                "difficulty": difficulty,
                "description": description
            })
        print(game_recommendations)
        return game_recommendations


# FastAPI app
app = FastAPI()
@app.get("/")
async def home():
    return "hi!"

@app.get("/player-game-recommendations/{userId}")
async def get_recommendations(userId: str):
    try:
        # Load data from the database
        player_match_stats = pd.read_sql('''
        SELECT p.id as player_id, game_id, username, gender,
               (SELECT TIMESTAMPDIFF(YEAR, p.birth_date, CURDATE())) AS age,
               c.name, outcome,
               TIMESTAMPDIFF(SECOND, start_time, end_time) as duration_played
        FROM statistics.player_matches pm
        JOIN statistics.matches m on pm.match_id=m.id
        JOIN statistics.players p on pm.player_id=p.id
        JOIN statistics.locations l on l.id=p.location_id
        JOIN statistics.countries c on l.country_id=c.id
        ''', con=db)

        games = pd.read_sql("""
                            SELECT game_id, game_name, difficulty_level, description FROM statistics.games
                            """, con=db)


        # Initialize recommender
        recommender = GameRecommender(player_match_stats, games)

        recommendations = recommender.get_all_recommendations(userId)
        return recommendations
    except ValueError as e:
        raise HTTPException(status_code=404, detail=str(e))
    except Exception as e:
        raise HTTPException(status_code=500, detail="An error occurred while processing recommendations.")


