package be.kdg.integration5.predictions.adapters.in;


import be.kdg.integration5.predictions.domain.PlayerEngagementPredictions;
import be.kdg.integration5.predictions.ports.in.GetPlayerEngagementPredictionsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/engagement")
public class EngagementPredictionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EngagementPredictionController.class);

    private final GetPlayerEngagementPredictionsUseCase getPlayerEngagementPredictionsUseCase;



    public EngagementPredictionController(GetPlayerEngagementPredictionsUseCase getPlayerEngagementPredictionsUseCase) {
        this.getPlayerEngagementPredictionsUseCase = getPlayerEngagementPredictionsUseCase;
    }

    @GetMapping
    public ResponseEntity<PlayerEngagementPredictionsDto> getPlayerChurnPrediction(
            @RequestParam("username") String username,
            @RequestParam("game_name") String gameName) {
            LOGGER.info("Received request for engagement prediction for player: {} and game: {}", username, gameName);


             PlayerEngagementPredictions predictions = getPlayerEngagementPredictionsUseCase.getPlayerEngagementPredictions(username, gameName);
             return new ResponseEntity<>(
                     new PlayerEngagementPredictionsDto(
                             predictions.getUsername(),
                             predictions.getGameName(),
                             predictions.getPredictions().stream().map(pred -> new PredictionDto(
                                     pred.date(),
                                     pred.predictedMinutes()

                             )).toList()
                     ), HttpStatusCode.valueOf(200));

        }
    }


