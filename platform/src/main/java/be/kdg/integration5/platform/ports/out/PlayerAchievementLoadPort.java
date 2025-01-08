package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.domain.PlayerAchievement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerAchievementLoadPort {
    public Optional<PlayerAchievement> loadByGameIdAchievementIdAndPlayerId(UUID gameId, UUID achievementId, UUID playerId);

    public List<PlayerAchievement> loadPlayerAchievementsById(UUID id);

    List<PlayerAchievement> loadPlayerAchievementsByIdAndGame(PlayerJpaEntity player, GameJpaEntity game);
}
