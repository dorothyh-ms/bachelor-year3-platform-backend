package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Achievement;

public interface AchievementCreatedPort {
    public Achievement achievementCreated(Achievement achievement);
}
