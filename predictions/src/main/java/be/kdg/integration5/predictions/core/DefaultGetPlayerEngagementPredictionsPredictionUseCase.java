package be.kdg.integration5.predictions.core;


import be.kdg.integration5.predictions.domain.PlayerEngagementPredictions;
import be.kdg.integration5.predictions.exceptions.GameOrPlayerNotFound;
import be.kdg.integration5.predictions.exceptions.PredictionServiceNotAvailable;
import be.kdg.integration5.predictions.ports.in.GetPlayerEngagementPredictionsUseCase;
import be.kdg.integration5.predictions.ports.out.PlayerEngagementPredictionLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class DefaultGetPlayerEngagementPredictionsPredictionUseCase implements GetPlayerEngagementPredictionsUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGetPlayerEngagementPredictionsPredictionUseCase.class);

    private final PlayerEngagementPredictionLoadPort playerEngagementPredictionLoadPort;


    public DefaultGetPlayerEngagementPredictionsPredictionUseCase(PlayerEngagementPredictionLoadPort playerEngagementPredictionLoadPort) {
        this.playerEngagementPredictionLoadPort = playerEngagementPredictionLoadPort;
    }

    @Override
    public PlayerEngagementPredictions getPlayerEngagementPredictions(String username, String gameName) {

        try {
            PlayerEngagementPredictions predictions = playerEngagementPredictionLoadPort.loadPlayerEngagementPredictions(username, gameName);
            LOGGER.info("DefaultGetPlayerEngagementPredictionsPredictionUseCase is returning predictions {}", predictions);
            if (predictions == null) {
                throw new GameOrPlayerNotFound(String.format("Player with username %s or game with game name %s does not exist", username, gameName));
            }
            return predictions;
        }
        catch (GameOrPlayerNotFound e) {
            // Re-throw specific exceptions
            throw e;
        }
        catch (Exception e) {
            throw new PredictionServiceNotAvailable("Prediction service is not available");
        }
    }

}
