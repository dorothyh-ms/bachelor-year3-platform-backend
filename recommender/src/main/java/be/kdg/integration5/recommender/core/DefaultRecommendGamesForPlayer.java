package be.kdg.integration5.recommender.core;

import be.kdg.integration5.recommender.domain.Game;
import be.kdg.integration5.recommender.exceptions.PlayerNotFoundException;
import be.kdg.integration5.recommender.ports.in.RecommendGamesForPlayerUseCase;
import be.kdg.integration5.recommender.ports.out.GameRecommendationLoadPort;
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
        List<Game> games =  gameRecommendationLoadPort.recommendGamesForUser(playerId);
        if (games.isEmpty()){
            throw new PlayerNotFoundException(String.format("Player with ID %s does not exist in the game recommender", playerId));
        };
        return games;
    }
}
