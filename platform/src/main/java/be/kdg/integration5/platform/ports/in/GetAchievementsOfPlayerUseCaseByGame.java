package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.PlayerAchievement;

import java.util.List;
import java.util.UUID;

public interface GetAchievementsOfPlayerUseCaseByGame {
    List<PlayerAchievement> getPlayerAchievementsByGame(UUID playerId, UUID gameId);
}
