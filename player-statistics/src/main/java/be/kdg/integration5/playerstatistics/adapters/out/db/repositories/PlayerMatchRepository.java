package be.kdg.integration5.playerstatistics.adapters.out.db.repositories;

import be.kdg.integration5.playerstatistics.adapters.out.db.entities.PlayerMatchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerMatchRepository extends JpaRepository< PlayerMatchJpaEntity, UUID> {
    public Optional<PlayerMatchJpaEntity> findFirstByMatch_IdAndPlayer_Id(UUID matchId, UUID playerId);

    public List<PlayerMatchJpaEntity> findByMatch_Id(UUID matchId);

    boolean existsByMatch_IdAndPlayer_Id(UUID matchId, UUID player1Id);
}
