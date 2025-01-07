package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.PlayerAchievement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerAchievementLoadPort {
    public Optional<PlayerAchievement> loadByGameIdAchievementIdAndPlayerId(UUID gameId, UUID achievementId, UUID playerId);

    public List<PlayerAchievement> loadPlayerAchievementsById(UUID id);
}
