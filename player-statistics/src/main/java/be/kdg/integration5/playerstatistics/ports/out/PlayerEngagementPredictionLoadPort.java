package be.kdg.integration5.playerstatistics.ports.out;

import be.kdg.integration5.playerstatistics.domain.PlayerEngagementPredictions;

public interface PlayerEngagementPredictionLoadPort {
     PlayerEngagementPredictions loadPlayerEngagementPredictions(String username, String gameName);
}
