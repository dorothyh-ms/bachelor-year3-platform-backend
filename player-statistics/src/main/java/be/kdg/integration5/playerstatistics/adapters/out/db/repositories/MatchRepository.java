package be.kdg.integration5.playerstatistics.adapters.out.db.repositories;

import be.kdg.integration5.playerstatistics.adapters.out.db.entities.MatchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MatchRepository extends JpaRepository< MatchJpaEntity, UUID> {
}
