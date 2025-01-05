package be.kdg.integration5.playerstatistics.ports.out;

import be.kdg.integration5.common.domain.PlayerStatistics;

import java.util.List;

public interface PlayerStatisticsLoadPort {


    public List<PlayerStatistics> loadPlayerStatistics();
}
