package be.kdg.integration5.playerstatistics.adapters.out.db.entities;


import be.kdg.integration5.playerstatistics.domain.MatchStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog="statistics", name="matches")
public class MatchJpaEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="game_id")
    private BoardGameJpaEntity game;


    @Column(name="start_time")
    private LocalDateTime startTime;


    @Column(name="end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private MatchStatus status;

    public MatchJpaEntity() {
    }

    public MatchJpaEntity(UUID id, BoardGameJpaEntity game, LocalDateTime startTime, LocalDateTime endTime, MatchStatus status) {
        this.id = id;
        this.game = game;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BoardGameJpaEntity getGame() {
        return game;
    }

    public void setGame(BoardGameJpaEntity game) {
        this.game = game;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }
}
