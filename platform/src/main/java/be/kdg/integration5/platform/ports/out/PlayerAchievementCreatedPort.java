package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.PlayerAchievement;

public interface PlayerAchievementCreatedPort {
    public PlayerAchievement playerAchievementCreated(PlayerAchievement playerAchievement);
}
