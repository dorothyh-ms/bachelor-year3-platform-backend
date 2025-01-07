package be.kdg.integration5.platform.adapters.out.db.repositories;

import be.kdg.integration5.platform.adapters.out.db.entities.PlayerAchievementJpaEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerAchievementRepository extends JpaRepository<PlayerAchievementJpaEntity, UUID> {

    Optional<PlayerAchievementJpaEntity> findFirstByAchievement_Game_IdAndAchievement_IdAndPlayer_PlayerId(UUID gameId,
                                                                                                           UUID achievementId,
                                                                                                           UUID playerId);

    List<PlayerAchievementJpaEntity> findAllByPlayer_PlayerId(UUID playerId);
}
