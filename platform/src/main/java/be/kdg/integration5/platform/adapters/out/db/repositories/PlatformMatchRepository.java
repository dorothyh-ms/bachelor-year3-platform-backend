package be.kdg.integration5.platform.adapters.out.db.repositories;

import be.kdg.integration5.platform.adapters.out.db.entities.PlatformMatchJpaEntity;
import be.kdg.integration5.platform.domain.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PlatformMatchRepository extends JpaRepository<PlatformMatchJpaEntity, UUID> {



    @Query("SELECT m FROM PlatformMatchJpaEntity m WHERE (m.player1.playerId = :playerId OR m.player2.playerId = :playerId) AND m.status = :status")
    List<PlatformMatchJpaEntity> findAllByPlayerIdAndStatus(UUID playerId, MatchStatus status);
}
