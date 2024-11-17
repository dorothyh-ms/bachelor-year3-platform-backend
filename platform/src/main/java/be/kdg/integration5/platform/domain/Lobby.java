package be.kdg.integration5.platform.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Lobby {


    private UUID id;
    private Game game;
    private Player initiatingPlayer;
    private Player joinedPlayer;
    private LobbyStatus status;
    private LocalDateTime dateCreated;


    public Lobby() {
    }

    public Lobby(UUID id, Game game, Player initiatingPlayer) {
        this.id = id;
        this.game = game;
        this.initiatingPlayer = initiatingPlayer;
        this.status = LobbyStatus.OPEN;
        this.dateCreated = LocalDateTime.now();
    }

    public Lobby(UUID id, Game game, Player initiatingPlayer, Player joinedPlayer, LobbyStatus status, LocalDateTime dateCreated) {
        this.id = id;
        this.game = game;
        this.initiatingPlayer = initiatingPlayer;
        this.joinedPlayer = joinedPlayer;
        this.status = status;
        this.dateCreated = dateCreated;
    }

    public void joinPlayer(Player player) {
        joinedPlayer = player;
        setStatus(LobbyStatus.CLOSED);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getInitiatingPlayer() {
        return initiatingPlayer;
    }

    public void setInitiatingPlayer(Player initiatingPlayer) {
        this.initiatingPlayer = initiatingPlayer;
    }

    public Player getJoinedPlayer() {
        return joinedPlayer;
    }

    public void setJoinedPlayer(Player joinedPlayer) {
        this.joinedPlayer = joinedPlayer;
    }

    public LobbyStatus getStatus() {
        return status;
    }

    public void setStatus(LobbyStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
