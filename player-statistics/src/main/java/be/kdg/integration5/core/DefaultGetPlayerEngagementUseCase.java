package be.kdg.integration5.core;

import be.kdg.integration5.ports.in.GetPlayerEngagementPredictionUseCase;
import be.kdg.integration5.ports.out.PlayerEngagementLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultGetPlayerEngagementUseCase implements GetPlayerEngagementPredictionUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGetPlayerEngagementUseCase.class);
    private final PlayerEngagementLoadPort playerEngagementLoadPort;

    public DefaultGetPlayerEngagementUseCase(PlayerEngagementLoadPort playerEngagementLoadPort) {
        this.playerEngagementLoadPort = playerEngagementLoadPort;
    }


    @Override
    public double getPlayerEngagement(String playerId, String date) {
        LOGGER.info("Executing engagement prediction for player ID: {} and date: {}", playerId, date);
        return playerEngagementLoadPort.loadPlayerEngagementPrediction(playerId, date);
    }
}
