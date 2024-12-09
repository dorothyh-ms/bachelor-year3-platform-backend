package be.kdg.integration5.adapters.out.db.entities;

import be.kdg.integration5.domain.PlayerMatchOutcome;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog="statistics", name="player_matches")
public class PlayerMatchJpaEntity {
    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="match_id")
    private MatchJpaEntity match;


    @Column(name="number_of_turns")
    private int numberOfTurns;

    @Column(name="score")
    private double score;


    @Column(name="outcome")
    @Enumerated(EnumType.STRING)
    private PlayerMatchOutcome outcome;

    @ManyToOne
    @JoinColumn(name="player_id")
    private BoardGamePlayerJpaEntity player;
}
