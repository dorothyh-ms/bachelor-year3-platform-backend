package be.kdg.integration5.recommender.core;

import be.kdg.integration5.recommender.domain.Game;
import be.kdg.integration5.recommender.exceptions.PlayerNotFoundException;
import be.kdg.integration5.recommender.ports.in.RecommendGamesForPlayerUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static be.kdg.integration5.recommender.core.TestValues.EXISITING_PLAYER_ID;
import static be.kdg.integration5.recommender.core.TestValues.NONEXISTENT_PLAYER_ID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DefaultRecommendGamesForPlayerTest extends AbstractGameRecommenderTest {


    @Autowired
    private RecommendGamesForPlayerUseCase recommendGamesForPlayerUseCase;



    @Test
    void gameRecommendationServiceShouldReturnRecommendationsForPlayer() {
        // ACT

        List<Game> games = recommendGamesForPlayerUseCase.recommendGamesForPlayer(EXISITING_PLAYER_ID);

        assertNotNull(games);
        assertNotEquals(games.size(), 0);
    }

    @Test
    void gameRecommendationServiceShouldReturnPlayerNotFoundExceptionForNonExistentPlayer(){
        assertThrows(PlayerNotFoundException.class, () -> {
            recommendGamesForPlayerUseCase.recommendGamesForPlayer(NONEXISTENT_PLAYER_ID);
        });



    }

}