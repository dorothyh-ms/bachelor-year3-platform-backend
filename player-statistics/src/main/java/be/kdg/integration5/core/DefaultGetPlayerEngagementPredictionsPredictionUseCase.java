package be.kdg.integration5.core;

import be.kdg.integration5.domain.PlayerEngagementPredictions;
import be.kdg.integration5.ports.in.GetPlayerEngagementPredictionsUseCase;
import be.kdg.integration5.ports.out.PlayerEngagementPredictionLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class DefaultGetPlayerEngagementPredictionsPredictionUseCase implements GetPlayerEngagementPredictionsUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGetPlayerEngagementPredictionsPredictionUseCase.class);

    private final PlayerEngagementPredictionLoadPort playerEngagementPredictionLoadPort;


    public DefaultGetPlayerEngagementPredictionsPredictionUseCase(PlayerEngagementPredictionLoadPort playerEngagementPredictionLoadPort) {
        this.playerEngagementPredictionLoadPort = playerEngagementPredictionLoadPort;
    }

    @Override
    public PlayerEngagementPredictions getPlayerEngagementPredictions(String username, UUID gameId){
        playerEngagementPredictionLoadPort.loadPlayerEngagementPredictions(username, gameId);
        return null;
    }


}
