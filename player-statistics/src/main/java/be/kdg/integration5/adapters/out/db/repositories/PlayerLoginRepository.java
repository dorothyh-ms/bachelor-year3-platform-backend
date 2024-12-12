package be.kdg.integration5.adapters.out.db.repositories;

import be.kdg.integration5.adapters.out.db.entities.PlayerLoginJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerLoginRepository extends JpaRepository<PlayerLoginJpaEntity, UUID> {
}
