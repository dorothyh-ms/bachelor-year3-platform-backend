package be.kdg.integration5.predictions.ports.in;


import be.kdg.integration5.predictions.domain.PlayerEngagementPredictions;

public interface GetPlayerEngagementPredictionsUseCase {

        PlayerEngagementPredictions getPlayerEngagementPredictions(String username, String gameName);
}


