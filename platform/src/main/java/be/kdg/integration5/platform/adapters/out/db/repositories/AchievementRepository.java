package be.kdg.integration5.platform.adapters.out.db.repositories;

import be.kdg.integration5.platform.adapters.out.db.entities.AchievementJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AchievementRepository extends JpaRepository<AchievementJpaEntity, UUID> {

    public Optional<AchievementJpaEntity> findByNameAndAndGame_Id(String name, UUID id);
}
