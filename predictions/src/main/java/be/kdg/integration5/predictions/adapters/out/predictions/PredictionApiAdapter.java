package be.kdg.integration5.predictions.adapters.out.predictions;

import be.kdg.integration5.predictions.adapters.out.predictions.feign.PlayerEngagementPredictionRequest;
import be.kdg.integration5.predictions.adapters.out.predictions.feign.PlayerEngagementPredictionsResponse;
import be.kdg.integration5.predictions.adapters.out.predictions.feign.PredictionFeignClient;

import be.kdg.integration5.predictions.domain.PlayerEngagementPredictions;
import be.kdg.integration5.predictions.domain.Prediction;
import be.kdg.integration5.predictions.exceptions.PredictionServiceNotAvailable;
import be.kdg.integration5.predictions.ports.out.PlayerEngagementPredictionLoadPort;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PredictionApiAdapter implements PlayerEngagementPredictionLoadPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionApiAdapter.class);


    private final PredictionFeignClient predictionFeignClient;


    @Autowired
    public PredictionApiAdapter(PredictionFeignClient predictionFeignClient) {
        this.predictionFeignClient = predictionFeignClient;
    }

    @Override
    public PlayerEngagementPredictions loadPlayerEngagementPredictions(String username, String gameName) {
        LOGGER.info("PredictionApiAdapter is running loadPlayerEngagementPredictions with {}, {}", username, gameName);
        PlayerEngagementPredictionRequest request = new PlayerEngagementPredictionRequest();
        request.setUsername(username);
        request.setGame_name(gameName);
        try {
            PlayerEngagementPredictionsResponse response = predictionFeignClient.getPredictions(request);

            List<Prediction> predictions = response.getPredictions().stream()
                    .map(predictionResponse -> new Prediction(
                            LocalDate.parse(predictionResponse.getDate()),
                            predictionResponse.getPredicted_minutes()))
                    .toList();

            PlayerEngagementPredictions playerEngagementPredictions = new PlayerEngagementPredictions();
            playerEngagementPredictions.setUsername(response.getUsername());
            playerEngagementPredictions.setGameName(response.getGame_name());
            playerEngagementPredictions.setPredictions(predictions);

            return playerEngagementPredictions;

        } catch (FeignException.NotFound e) {
            LOGGER.info("user: {}, game: {} not found", username, gameName, e);
            return null;
        }
        catch (FeignException e) {
            LOGGER.info("Failed to fetch predictions for user: {}, game: {}", username, gameName, e);
            throw new FeignClientErrorException(e.getMessage());
        }
    }
}
