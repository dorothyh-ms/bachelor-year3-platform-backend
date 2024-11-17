package be.kdg.integration5.platform.adapters.out.db.entities;

import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.LobbyStatus;
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
    @Column(name = "lobby_id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "game_id", updatable = false, nullable = false)
    private GameJpaEntity game;

    @ManyToOne
    @JoinColumn(name = "initiating_player_id", updatable = false, nullable = false)
    private PlayerJpaEntity initiatingPlayer;

    private LocalDateTime dateCreated;

    @Enumerated(value = EnumType.STRING)
    private LobbyStatus lobbyStatus;

    @ManyToOne
    @JoinColumn(name = "joined_player_id", nullable = true)
    private PlayerJpaEntity joinedPlayer;

    public LobbyJpaEntity() {
    }


    public LobbyJpaEntity(UUID id, GameJpaEntity game, PlayerJpaEntity initiatingPlayer, LocalDateTime dateCreated, LobbyStatus lobbyStatus) {
        this.id = id;
        this.game = game;
        this.initiatingPlayer = initiatingPlayer;
        this.dateCreated = dateCreated;
        this.lobbyStatus = lobbyStatus;
    }

    public LobbyJpaEntity(UUID id, GameJpaEntity game, PlayerJpaEntity initiatingPlayer, LocalDateTime dateCreated, LobbyStatus lobbyStatus, PlayerJpaEntity joinedPlayer) {
        this.id = id;
        this.game = game;
        this.initiatingPlayer = initiatingPlayer;
        this.dateCreated = dateCreated;
        this.lobbyStatus = lobbyStatus;
        this.joinedPlayer = joinedPlayer;
    }

    public LobbyStatus getLobbyStatus() {
        return lobbyStatus;
    }

    public void setLobbyStatus(LobbyStatus lobbyStatus) {
        this.lobbyStatus = lobbyStatus;
    }

    public PlayerJpaEntity getJoinedPlayer() {
        return joinedPlayer;
    }

    public void setJoinedPlayer(PlayerJpaEntity joinedPlayer) {
        this.joinedPlayer = joinedPlayer;
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
