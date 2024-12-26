package be.kdg.integration5.core;

import be.kdg.integration5.ports.in.GetPlayerChurnUseCase;
import be.kdg.integration5.ports.out.PlayerChurnPredictionLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultGetPlayerChurnPredictionUseCase implements GetPlayerChurnUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGetPlayerChurnPredictionUseCase.class);
    private final PlayerChurnPredictionLoadPort playerChurnPredictionLoadPort;

    public DefaultGetPlayerChurnPredictionUseCase(PlayerChurnPredictionLoadPort playerChurnPredictionLoadPort) {
        this.playerChurnPredictionLoadPort = playerChurnPredictionLoadPort;
    }


    @Override
    public double getPlayerChurn(String playerId, String date) {
        LOGGER.info("Executing churn prediction for player ID: {} and date: {}", playerId, date);
        return playerChurnPredictionLoadPort.loadPlayerChurnPrediction(playerId, date);
    }
}
