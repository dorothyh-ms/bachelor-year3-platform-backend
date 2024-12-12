package be.kdg.integration5.adapters.out.db.entities;


import be.kdg.integration5.domain.MatchStatus;
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
}
