package be.kdg.integration5.platform.adapters.out.db.repositories;

import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<GameJpaEntity, UUID> {

    public Optional<GameJpaEntity> findFirstByGameNameEqualsIgnoreCase(String name);
}
