package be.kdg.integration5.platform.adapters.out.db.entities;

import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Player;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog="platform", name="lobbies")
public class LobbyJpaEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "player_id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    private GameJpaEntity game;

    @ManyToOne
    private PlayerJpaEntity initiatingPlayer;

    private LocalDateTime dateCreated;

    public LobbyJpaEntity() {
    }


    public LobbyJpaEntity(UUID id, GameJpaEntity game, PlayerJpaEntity initiatingPlayer, LocalDateTime dateCreated) {
        this.id = id;
        this.game = game;
        this.initiatingPlayer = initiatingPlayer;
        this.dateCreated = dateCreated;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GameJpaEntity getGame() {
        return game;
    }

    public void setGame(GameJpaEntity game) {
        this.game = game;
    }

    public PlayerJpaEntity getInitiatingPlayer() {
        return initiatingPlayer;
    }

    public void setInitiatingPlayer(PlayerJpaEntity initiatingPlayer) {
        this.initiatingPlayer = initiatingPlayer;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
