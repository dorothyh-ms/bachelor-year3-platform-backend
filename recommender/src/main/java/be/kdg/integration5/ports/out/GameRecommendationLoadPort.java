package be.kdg.integration5.ports.out;

import be.kdg.integration5.domain.Game;

import java.util.List;
import java.util.UUID;

public interface GameRecommendationLoadPort {
    public List<Game> recommendGamesForUser(UUID userId);

    public List<Game> recommendSimilarGames(UUID gameId);
}
