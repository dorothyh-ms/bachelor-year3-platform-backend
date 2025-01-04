package be.kdg.integration5.ports.out;

import be.kdg.integration5.domain.PlayerEngagementPredictions;

import java.util.UUID;

public interface PlayerEngagementPredictionLoadPort {
     PlayerEngagementPredictions loadPlayerEngagementPredictions(String username, UUID gameId);
}
