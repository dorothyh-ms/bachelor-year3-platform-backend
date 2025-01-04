package be.kdg.integration5.recommender.ports.in;


import be.kdg.integration5.recommender.domain.Game;

import java.util.List;
import java.util.UUID;

public interface RecommendGamesForPlayerUseCase {

    public List<Game> recommendGamesForPlayer(UUID playerId);
}
