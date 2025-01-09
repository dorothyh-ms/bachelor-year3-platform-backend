package be.kdg.integration5.predictions.ports.out;


import be.kdg.integration5.predictions.domain.PlayerEngagementPredictions;

public interface PlayerEngagementPredictionLoadPort {
     PlayerEngagementPredictions loadPlayerEngagementPredictions(String username, String gameName);
}
