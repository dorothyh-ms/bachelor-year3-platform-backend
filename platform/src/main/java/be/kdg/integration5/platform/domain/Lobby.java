package be.kdg.integration5.platform.domain;



import java.time.LocalDateTime;
import java.util.UUID;

public class Lobby {


    private UUID id;
    private Game game;
    private Player initiatingPlayer;
    private Player joinedPlayer;
    private LobbyStatus status;
    private LocalDateTime dateCreated;
    private UUID matchId;


    public Lobby() {
    }

    public Lobby(UUID id, Game game, Player initiatingPlayer) {
        this.id = id;
        this.game = game;
        this.initiatingPlayer = initiatingPlayer;
        this.status = LobbyStatus.OPEN;
        this.dateCreated = LocalDateTime.now();
    }

    public Lobby(UUID id, Game game, Player initiatingPlayer, Player joinedPlayer, LobbyStatus status, LocalDateTime dateCreated, UUID matchId) {
        this.id = id;
        this.game = game;
        this.initiatingPlayer = initiatingPlayer;
        this.joinedPlayer = joinedPlayer;
        this.status = status;
        this.dateCreated = dateCreated;
        this.matchId = matchId;
    }

    public boolean isClosed(){
        return this.status.equals(LobbyStatus.CLOSED);
    }


    public boolean admitPlayer(Player player) {
        if ( this.isClosed()){
            return false;
        }
        if (player.equals(initiatingPlayer)){
            return false;
        }
        this.joinedPlayer = player;
        setStatus(LobbyStatus.CLOSED);
        this.matchId = UUID.randomUUID();
        return true;
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

    public UUID getMatchId() {
        return matchId;
    }

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }

    @Override
    public String toString() {
        return "Lobby{" +
                "id=" + id +
                ", game=" + game +
                ", initiatingPlayer=" + initiatingPlayer +
                ", joinedPlayer=" + joinedPlayer +
                ", status=" + status +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
