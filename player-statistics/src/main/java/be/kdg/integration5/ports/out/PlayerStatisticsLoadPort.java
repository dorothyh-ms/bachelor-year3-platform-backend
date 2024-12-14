package be.kdg.integration5.ports.out;

import be.kdg.integration5.common.domain.PlayerStatistics;

import java.util.List;
import java.util.UUID;

public interface PlayerStatisticsLoadPort {


    public List<PlayerStatistics> loadPlayerStatistics();
}
