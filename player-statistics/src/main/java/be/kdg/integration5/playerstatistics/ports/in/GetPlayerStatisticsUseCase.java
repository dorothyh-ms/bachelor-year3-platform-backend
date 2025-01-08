package be.kdg.integration5.playerstatistics.ports.in;

import be.kdg.integration5.common.domain.PlayerStatistics;

import java.util.List;

public interface GetPlayerStatisticsUseCase {
    public List<PlayerStatistics> getPlayerStatistics();
}
