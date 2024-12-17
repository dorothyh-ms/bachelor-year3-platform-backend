package be.kdg.integration5.core;

import be.kdg.integration5.domain.PlayerGameClassification;
import be.kdg.integration5.ports.in.GetPlayerClassificationsUseCase;
import be.kdg.integration5.ports.out.PlayerGameClassificationLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class DefaultGetPlayerClassificationsUseCase implements GetPlayerClassificationsUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGetPlayerClassificationsUseCase.class);

    private final PlayerGameClassificationLoadPort playerGameClassificationLoadPort;

    public DefaultGetPlayerClassificationsUseCase(PlayerGameClassificationLoadPort playerGameClassificationLoadPort) {
        this.playerGameClassificationLoadPort = playerGameClassificationLoadPort;
    }

    @Override
    public List<PlayerGameClassification> getPlayerGameClassifications(UUID userId) {
        LOGGER.info("DefaultGetPlayerClassificationsUseCase is running getPlayerGameClassifications with id {}", userId);
        return playerGameClassificationLoadPort.loadPlayerGameClassifications(userId);
    }
}
