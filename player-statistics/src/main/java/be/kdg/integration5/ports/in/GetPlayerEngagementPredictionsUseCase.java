package be.kdg.integration5.ports.in;


import be.kdg.integration5.domain.PlayerEngagementPredictions;

import java.util.UUID;

public interface GetPlayerEngagementPredictionsUseCase {

        PlayerEngagementPredictions getPlayerEngagementPredictions(String username, String gameName);
}


