package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.in.web.MatchController;
import be.kdg.integration5.platform.domain.Match;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.exceptions.PlayerNotFoundException;
import be.kdg.integration5.platform.ports.in.GetAvailableMatchesUseCase;
import be.kdg.integration5.platform.ports.out.MatchLoadPort;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultGetAvailableMatchesUseCase implements GetAvailableMatchesUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGetAvailableMatchesUseCase.class);
    private final MatchLoadPort matchLoadPort;

    public DefaultGetAvailableMatchesUseCase(MatchLoadPort matchLoadPort) {
        this.matchLoadPort = matchLoadPort;
    }

    @Override
    public List<Match> getUnfinishedMatchesOfPlayer(UUID playerId) {
        LOGGER.info("DefaultGetAvailableMatchesUseCase is running getUnfinishedMatchesOfPlayer");
        return matchLoadPort.loadUnfinishedMatchesOfPlayer(playerId);
    }
}
