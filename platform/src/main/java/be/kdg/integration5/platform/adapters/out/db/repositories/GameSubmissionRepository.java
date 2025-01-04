package be.kdg.integration5.platform.adapters.out.db.repositories;

import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.GameSubmissionJpaEntity;
import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.domain.SubmissionState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameSubmissionRepository extends JpaRepository<GameSubmissionJpaEntity, UUID> {
    List<GameSubmissionJpaEntity> getAllBySubmissionStateIs(SubmissionState submissionState);
}
