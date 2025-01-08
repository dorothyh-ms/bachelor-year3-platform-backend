package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Achievement;
import be.kdg.integration5.platform.domain.PlayerAchievement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AchievementLoadPort {
    public Optional<Achievement> loadAchievementByGameIdAndAchievementName(UUID gameId, String achievementName);
}
