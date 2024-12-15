package be.kdg.integration5.adapters.out.db.repositories;

import be.kdg.integration5.adapters.out.db.entities.PlayerProfileJpaEntity;
import be.kdg.integration5.common.domain.PlayerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PlayerProfileRepository extends JpaRepository<PlayerProfileJpaEntity, UUID> {
    @Query(value = "SELECT new be.kdg.integration5.common.domain.PlayerStatistics(" +
            "pm.player.userName, p.birthDate, l.city, c.name, g.gameName , " +
            "SUM(TIMESTAMPDIFF(SECOND, m.startTime, m.endTime)), " +
            "SUM(CASE WHEN pm.outcome = 'WIN' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN pm.outcome = 'LOSS' THEN 1 ELSE 0 END) ) " +
            "FROM PlayerMatchJpaEntity pm " +
            "INNER JOIN pm.match m " +
            "INNER JOIN m.game g " +
            "INNER JOIN pm.player p " +
            "INNER JOIN p.location l " +
            "INNER JOIN l.country c " +
            "GROUP BY pm.player.userName, p.birthDate, p.gender, l.city, c.name, g.gameName")
    List<PlayerStatistics> findPlayerStatistics();
}
