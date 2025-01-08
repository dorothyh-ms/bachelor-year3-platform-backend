package be.kdg.integration5.platform.adapters.out.db.entities;


import be.kdg.integration5.platform.domain.MatchStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog="platform", name="matches")
public class PlatformMatchJpaEntity {
    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "player1_id", updatable = false, nullable = false)
    private PlayerJpaEntity player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", updatable = false, nullable = false)
    private PlayerJpaEntity player2;


    @ManyToOne
    @JoinColumn(name = "game_id", updatable = false, nullable = false)
    private GameJpaEntity gameJpaEntity;

    private String url;

    private LocalDateTime dateCreated;

    @Enumerated(value = EnumType.STRING)
    private MatchStatus status;

    public PlatformMatchJpaEntity() {
    }

    public PlatformMatchJpaEntity(UUID id, PlayerJpaEntity player1, PlayerJpaEntity player2, GameJpaEntity gameJpaEntity, String url, LocalDateTime dateCreated, MatchStatus status) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.gameJpaEntity = gameJpaEntity;
        this.url = url;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PlayerJpaEntity getPlayer1() {
        return player1;
    }

    public void setPlayer1(PlayerJpaEntity player1) {
        this.player1 = player1;
    }

    public PlayerJpaEntity getPlayer2() {
        return player2;
    }

    public void setPlayer2(PlayerJpaEntity player2) {
        this.player2 = player2;
    }

    public GameJpaEntity getGameJpaEntity() {
        return gameJpaEntity;
    }

    public void setGameJpaEntity(GameJpaEntity gameJpaEntity) {
        this.gameJpaEntity = gameJpaEntity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }
}
