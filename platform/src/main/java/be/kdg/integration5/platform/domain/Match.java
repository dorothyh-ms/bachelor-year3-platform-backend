package be.kdg.integration5.platform.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Match {

    private UUID id;
    private Player player1;
    private Player player2;
    private Game game;
    private String url;
    private MatchStatus status;
    private LocalDateTime dateCreated;

    public Match(UUID id, Player player1, Player player2, Game game, String url, MatchStatus status, LocalDateTime dateCreated) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.game = game;
        this.url = url;
        this.status = status;
        this.dateCreated = dateCreated;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void end(){
        this.status = MatchStatus.COMPLETED;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", game=" + game +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
