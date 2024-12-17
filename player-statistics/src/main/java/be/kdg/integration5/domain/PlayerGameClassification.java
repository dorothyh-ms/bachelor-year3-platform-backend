package be.kdg.integration5.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class PlayerGameClassification {
    private UUID playerId;
    private UUID gameId;
    private String gameName;
    private int totalWins;
    private int totalLosses;
    private String classification;

    public PlayerGameClassification(String playerId, String gameId, String gameName, BigDecimal totalWins, BigDecimal totalLosses, String classification) {
        this.playerId = UUID.fromString(playerId);
        this.gameId = UUID.fromString(gameId);
        this.gameName = gameName;
        this.totalWins = totalWins.intValue();
        this.totalLosses = totalLosses.intValue();
        this.classification = classification;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }



    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }



    public String getClassification() {
        return classification;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(int totalLosses) {
        this.totalLosses = totalLosses;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public String toString() {
        return "PlayerGameClassification{" +
                "playerId=" + playerId +
                ", gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                ", totalWins=" + totalWins +
                ", totalLosses=" + totalLosses +
                ", classification='" + classification + '\'' +
                '}';
    }
}
