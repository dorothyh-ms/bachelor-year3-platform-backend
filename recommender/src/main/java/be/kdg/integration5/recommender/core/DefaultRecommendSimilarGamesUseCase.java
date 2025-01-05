package be.kdg.integration5.recommender.core;

import be.kdg.integration5.recommender.domain.Game;
import be.kdg.integration5.recommender.ports.in.RecommendSimilarGamesUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultRecommendSimilarGamesUseCase implements RecommendSimilarGamesUseCase {

    @Override
    public List<Game> recommendSimilarGamesUseCase(UUID gameId) {
        return List.of();
    }
}
