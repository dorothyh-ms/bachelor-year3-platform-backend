package be.kdg.integration5.ports.in;

import be.kdg.integration5.common.domain.PlayerGameClassification;

import java.util.List;
import java.util.UUID;

public interface GetPlayerClassificationsUseCase {
    public List<PlayerGameClassification> getPlayerGameClassifications(UUID userId);
}
