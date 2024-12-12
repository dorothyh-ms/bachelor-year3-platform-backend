package be.kdg.integration5.core;

import be.kdg.integration5.domain.Game;
import be.kdg.integration5.ports.in.RecommendGamesForPlayerUseCase;
import be.kdg.integration5.ports.out.GameRecommendationLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultRecommendGamesForPlayer implements RecommendGamesForPlayerUseCase {

    private final GameRecommendationLoadPort gameRecommendationLoadPort;

    public DefaultRecommendGamesForPlayer(GameRecommendationLoadPort gameRecommendationLoadPort) {
        this.gameRecommendationLoadPort = gameRecommendationLoadPort;
    }

    @Override
    public List<Game> recommendGamesForPlayer(UUID playerId) {
        return gameRecommendationLoadPort.recommendGamesForUser(playerId);
    }
}
