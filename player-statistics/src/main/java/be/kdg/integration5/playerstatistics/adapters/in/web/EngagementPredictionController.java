package be.kdg.integration5.playerstatistics.adapters.in.web;

import be.kdg.integration5.playerstatistics.core.DefaultGetPlayerEngagementPredictionsPredictionUseCase;
import be.kdg.integration5.playerstatistics.domain.PlayerEngagementPredictions;
import be.kdg.integration5.playerstatistics.ports.in.GetPlayerEngagementPredictionsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/engagement")
public class EngagementPredictionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EngagementPredictionController.class);

    private final GetPlayerEngagementPredictionsUseCase getPlayerEngagementPredictionsUseCase;



    public EngagementPredictionController(DefaultGetPlayerEngagementPredictionsPredictionUseCase getPlayerEngagementPredictionsUseCase) {
        this.getPlayerEngagementPredictionsUseCase = getPlayerEngagementPredictionsUseCase;
    }

    @GetMapping()
    public ResponseEntity<PlayerEngagementPredictions> getPlayerChurnPrediction(
            @RequestParam("username") String username,
            @RequestParam("game_name") String gameName) {
            LOGGER.info("Received request for engagement prediction for player: {} and game: {}", username, gameName);


             PlayerEngagementPredictions predictions = getPlayerEngagementPredictionsUseCase.getPlayerEngagementPredictions(username, gameName);
             return new ResponseEntity<>(predictions, HttpStatusCode.valueOf(200));

        }
    }


