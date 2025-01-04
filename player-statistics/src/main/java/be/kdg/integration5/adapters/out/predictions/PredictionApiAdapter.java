package be.kdg.integration5.adapters.out.predictions;

import be.kdg.integration5.adapters.out.predictions.feign.PlayerEngagementPredictionRequest;
import be.kdg.integration5.adapters.out.predictions.feign.PlayerEngagementPredictionsResponse;
import be.kdg.integration5.adapters.out.predictions.feign.PredictionFeignClient;
import be.kdg.integration5.domain.PlayerEngagementPredictions;
import be.kdg.integration5.domain.Prediction;
import be.kdg.integration5.ports.out.PlayerEngagementPredictionLoadPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class PredictionApiAdapter implements PlayerEngagementPredictionLoadPort {

    private final PredictionFeignClient predictionFeignClient;


    @Autowired
    public PredictionApiAdapter(PredictionFeignClient predictionFeignClient) {
        this.predictionFeignClient = predictionFeignClient;
    }

    @Override
    public PlayerEngagementPredictions loadPlayerEngagementPredictions(String username, UUID gameId) {
        PlayerEngagementPredictionRequest request = new PlayerEngagementPredictionRequest();
        request.setUsername(username);
        request.setGame_id(gameId.toString());

        PlayerEngagementPredictionsResponse response = predictionFeignClient.getPredictions(request);

        List<Prediction> predictions = response.getPredictions().stream()
                .map(predictionResponse -> new Prediction(
                        LocalDate.parse(predictionResponse.getDate()),
                        (int) predictionResponse.getPredicted_minutes()))
                .toList();

        PlayerEngagementPredictions playerEngagementPredictions = new PlayerEngagementPredictions();
        playerEngagementPredictions.setUsername(response.getUsername());
        playerEngagementPredictions.setGame_id(response.getGame_id());
        playerEngagementPredictions.setPredictions(predictions);

        return playerEngagementPredictions;
    }
}
