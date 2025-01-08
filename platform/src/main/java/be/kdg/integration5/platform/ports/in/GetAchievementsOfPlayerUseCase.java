package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.PlayerAchievement;

import java.util.List;
import java.util.UUID;

public interface GetAchievementsOfPlayerUseCase {
    List<PlayerAchievement> getPlayerAchievements(UUID playerId);
}
