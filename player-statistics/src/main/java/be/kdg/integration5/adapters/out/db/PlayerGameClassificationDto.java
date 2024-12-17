package be.kdg.integration5.adapters.out.db;

import java.math.BigDecimal;
import java.util.UUID;

public class PlayerGameClassificationDto {
    private String playerId;
    private String gameId;
    private String gameName;
    private BigDecimal totalWins;
    private BigDecimal totalLosses;
    private String classification;

    public PlayerGameClassificationDto(String playerId, String gameId, String gameName, BigDecimal totalWins, BigDecimal totalLosses, String classification) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.gameName = gameName;
        this.totalWins = totalWins;
        this.totalLosses = totalLosses;
        this.classification = classification;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public BigDecimal getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(BigDecimal totalWins) {
        this.totalWins = totalWins;
    }

    public BigDecimal getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(BigDecimal totalLosses) {
        this.totalLosses = totalLosses;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
