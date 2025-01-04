package be.kdg.integration5.adapters.in.web;

import be.kdg.integration5.core.DefaultGetPlayerEngagementPredictionsPredictionUseCase;
import be.kdg.integration5.domain.PlayerEngagementPredictions;
import be.kdg.integration5.ports.in.GetPlayerEngagementPredictionsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
            @RequestParam("game_id") UUID gameId) {
            LOGGER.info("Received request for churn prediction for player: {} and game id: {}", username, gameId);


             PlayerEngagementPredictions predictions = getPlayerEngagementPredictionsUseCase.getPlayerEngagementPredictions(username, gameId);
             return new ResponseEntity<>(predictions, HttpStatusCode.valueOf(200));

        }
    }


