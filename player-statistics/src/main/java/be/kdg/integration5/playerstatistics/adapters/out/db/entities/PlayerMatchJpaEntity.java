package be.kdg.integration5.playerstatistics.adapters.out.db.entities;

import be.kdg.integration5.common.domain.MatchOutcome;
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
    private MatchOutcome outcome;

    @ManyToOne
    @JoinColumn(name="player_id")
    private PlayerProfileJpaEntity player;

    public PlayerMatchJpaEntity() {
    }

    public PlayerMatchJpaEntity(UUID id, MatchJpaEntity match, int numberOfTurns, double score, MatchOutcome outcome, PlayerProfileJpaEntity player) {
        this.id = id;
        this.match = match;
        this.numberOfTurns = numberOfTurns;
        this.score = score;
        this.outcome = outcome;
        this.player = player;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public MatchJpaEntity getMatch() {
        return match;
    }

    public void setMatch(MatchJpaEntity match) {
        this.match = match;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public MatchOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(MatchOutcome outcome) {
        this.outcome = outcome;
    }

    public PlayerProfileJpaEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerProfileJpaEntity player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "PlayerMatchJpaEntity{" +
                "id=" + id +
                ", match=" + match +
                ", numberOfTurns=" + numberOfTurns +
                ", score=" + score +
                ", outcome=" + outcome +
                ", player=" + player +
                '}';
    }
}
