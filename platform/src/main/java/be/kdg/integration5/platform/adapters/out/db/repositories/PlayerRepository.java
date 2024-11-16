package be.kdg.integration5.platform.adapters.out.db.repositories;

import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<PlayerJpaEntity, UUID> {
    List<PlayerJpaEntity> findPlayerJpaEntitiesByUsernameContainingIgnoreCase(String username);
}
