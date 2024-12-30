package be.kdg.integration5.adapters.out.db.repositories;

import be.kdg.integration5.adapters.out.db.entities.PlayerProfileJpaEntity;
import be.kdg.integration5.common.domain.PlayerGameClassification;
import be.kdg.integration5.common.domain.PlayerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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


    @Query(value = """
    SELECT
        pwlr.player_id AS playerId,
        pwlr.game_id AS gameId,
        pwlr.game_name AS gameName,
        pwlr.total_wins,
        pwlr.total_losses,
        pwlr.total_matches,
        pwlr.total_seconds_played,
        CASE
            WHEN (pwlr.total_wins + pwlr.total_losses > 5) AND pwlr.win_loss_ratio >= 4.0 THEN 'Legend'
            WHEN (pwlr.total_wins + pwlr.total_losses > 5) AND pwlr.win_loss_ratio >= 2.0 THEN 'Elite'
            WHEN (pwlr.total_wins + pwlr.total_losses > 3) AND pwlr.win_loss_ratio >= 1.0 THEN 'Competitor'
            ELSE 'Beginner'
        END AS classification
        
    FROM (
        SELECT
            pwl.player_id,
            pwl.game_id,
            pwl.game_name,
            pwl.total_wins,
            pwl.total_losses,
            pwl.total_matches,
            pwl.total_seconds_played,
            CASE
                WHEN pwl.total_losses = 0 THEN NULL
                ELSE ROUND(pwl.total_wins / pwl.total_losses, 2)
            END AS win_loss_ratio
        FROM (
            SELECT
                pm.player_id,
                m.game_id,
                bg.game_name,
                SUM(CASE WHEN pm.outcome = 'WIN' THEN 1 ELSE 0 END) AS total_wins,
                SUM(CASE WHEN pm.outcome = 'LOSS' THEN 1 ELSE 0 END) AS total_losses,
                COUNT(pm.match_id) AS total_matches,
                SUM(TIMESTAMPDIFF(SECOND, m.start_time, m.end_time)) AS total_seconds_played
            FROM statistics.player_matches pm
            JOIN statistics.matches m ON pm.match_id = m.id
            JOIN statistics.games bg ON m.game_id = bg.game_id
            WHERE pm.player_id = :userId
            GROUP BY pm.player_id, m.game_id, bg.game_name
        ) pwl
    ) pwlr
    WHERE pwlr.win_loss_ratio IS NOT NULL
""", nativeQuery = true)
    List<Object[]> loadPlayerClassifications(@Param("userId") String userId);

}
