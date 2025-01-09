package be.kdg.integration5.predictions.core;



import be.kdg.integration5.predictions.domain.PlayerEngagementPredictions;
import be.kdg.integration5.predictions.exceptions.GameOrPlayerNotFound;
import be.kdg.integration5.predictions.ports.in.GetPlayerEngagementPredictionsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PredictEngagementTest extends AbstractPredictionApiTest {

    @Autowired
    GetPlayerEngagementPredictionsUseCase getPlayerEngagementPredictionsUseCase;
    @Test
    void shouldReturnEngagementPredictionsFor7DaysForExistingPlayerAndExistingGAme() {

        //ACT
        PlayerEngagementPredictions predictions = getPlayerEngagementPredictionsUseCase.getPlayerEngagementPredictions(TestValues.EXISTING_USERNAME, TestValues.EXISTING_GAME_NAME);

        //ASSERT
        assertNotNull(predictions);
        assertEquals(TestValues.EXISTING_USERNAME, predictions.getUsername());
        assertEquals(TestValues.EXISTING_GAME_NAME, predictions.getGameName());
        assertEquals(7, predictions.getPredictions().size());
    }

    @Test
    void shouldFailForNonExistentPlayer() {

        //ASSERT
        assertThrows(GameOrPlayerNotFound.class, () -> {

            //ACT
            getPlayerEngagementPredictionsUseCase.getPlayerEngagementPredictions(TestValues.NONEXISTENT_USERNAME, TestValues.EXISTING_GAME_NAME);
        });

    }

    @Test
    void shouldFailForNonExistentGame() {

        //ASSERT
        assertThrows(GameOrPlayerNotFound.class, () -> {

            //ACT
            getPlayerEngagementPredictionsUseCase.getPlayerEngagementPredictions(TestValues.EXISTING_USERNAME, TestValues.NONEXISTENT_GAME_NAME);
        });

    }

}