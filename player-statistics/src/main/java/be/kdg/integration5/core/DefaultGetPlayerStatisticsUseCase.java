package be.kdg.integration5.core;

import be.kdg.integration5.common.domain.PlayerStatistics;
import be.kdg.integration5.ports.in.GetPlayerStatisticsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import be.kdg.integration5.ports.out.PlayerStatisticsLoadPort;
import java.util.List;

@Service
public class DefaultGetPlayerStatisticsUseCase implements GetPlayerStatisticsUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGetPlayerStatisticsUseCase.class);

    private final PlayerStatisticsLoadPort playerStatisticsLoadPort;

    public DefaultGetPlayerStatisticsUseCase(PlayerStatisticsLoadPort playerStatisticsLoadPort) {
        this.playerStatisticsLoadPort = playerStatisticsLoadPort;
    }

    @Override
    public List<PlayerStatistics> getPlayerStatistics() {
        LOGGER.info("DefaultGetPlayerStatisticsUseCase is running loadPlayerStatistics");
        return playerStatisticsLoadPort.loadPlayerStatistics();
    }
}
