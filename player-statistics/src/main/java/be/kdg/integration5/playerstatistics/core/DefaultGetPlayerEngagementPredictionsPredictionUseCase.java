package be.kdg.integration5.playerstatistics.core;

import be.kdg.integration5.playerstatistics.domain.PlayerEngagementPredictions;
import be.kdg.integration5.playerstatistics.ports.in.GetPlayerEngagementPredictionsUseCase;
import be.kdg.integration5.playerstatistics.ports.out.PlayerEngagementPredictionLoadPort;
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
    public PlayerEngagementPredictions getPlayerEngagementPredictions(String username, String gameName){
        PlayerEngagementPredictions predictions = playerEngagementPredictionLoadPort.loadPlayerEngagementPredictions(username, gameName);
        LOGGER.info("DefaultGetPlayerEngagementPredictionsPredictionUseCase is returning predictions {}", predictions);
        return predictions;
    }


}
