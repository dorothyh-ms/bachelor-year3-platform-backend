package be.kdg.integration5.playerstatistics.adapters.out.db.repositories;

import be.kdg.integration5.playerstatistics.adapters.out.db.entities.BoardGameJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BoardGameRepository extends JpaRepository<BoardGameJpaEntity, UUID> {
    Optional<BoardGameJpaEntity> findFirstByGameNameEqualsIgnoreCase(String name);
}
