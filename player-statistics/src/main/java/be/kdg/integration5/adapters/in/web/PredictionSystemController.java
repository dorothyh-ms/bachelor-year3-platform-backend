package be.kdg.integration5.adapters.in.web;

import be.kdg.integration5.ports.in.GetPlayerChurnUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/predict")
public class PredictionSystemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionSystemController.class);

    private final GetPlayerChurnUseCase getPlayerChurnUseCase;

    public PredictionSystemController(GetPlayerChurnUseCase getPlayerChurnUseCase) {
        this.getPlayerChurnUseCase = getPlayerChurnUseCase;
    }

    @PostMapping()
    public ResponseEntity<Double> getPlayerChurnPrediction(
            @RequestParam("playerId") String playerId,
            @RequestParam("date") String date) {
        LOGGER.info("Received request for churn prediction for player ID: {} and date: {}", playerId, date);


            double prediction = getPlayerChurnUseCase.getPlayerChurn(playerId, date);
            return ResponseEntity.ok(prediction);

        }
    }


