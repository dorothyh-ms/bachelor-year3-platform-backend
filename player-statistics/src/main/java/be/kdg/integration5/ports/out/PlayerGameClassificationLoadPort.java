package be.kdg.integration5.ports.out;

import be.kdg.integration5.domain.PlayerGameClassification;

import java.util.List;
import java.util.UUID;

public interface PlayerGameClassificationLoadPort {
    public List<PlayerGameClassification> loadPlayerGameClassifications(UUID userId);
}
