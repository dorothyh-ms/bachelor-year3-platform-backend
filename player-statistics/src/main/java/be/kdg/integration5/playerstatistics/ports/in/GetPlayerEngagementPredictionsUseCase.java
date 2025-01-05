package be.kdg.integration5.playerstatistics.ports.in;


import be.kdg.integration5.playerstatistics.domain.PlayerEngagementPredictions;

public interface GetPlayerEngagementPredictionsUseCase {

        PlayerEngagementPredictions getPlayerEngagementPredictions(String username, String gameName);
}


