package be.kdg.integration5.playerstatistics.domain;

import be.kdg.integration5.common.domain.GameDifficulty;
import be.kdg.integration5.common.domain.GameGenre;

import java.math.BigDecimal;
import java.util.UUID;

public class BoardGame {

    private UUID gameId;


    private String gameName;

    private GameGenre genre;


    private GameDifficulty difficultyLevel;


    private BigDecimal price;

    private String description;

    public BoardGame(UUID gameId, String gameName, GameGenre genre, GameDifficulty difficultyLevel, BigDecimal price, String description) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.genre = genre;
        this.difficultyLevel = difficultyLevel;
        this.price = price;
        this.description = description;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public GameGenre getGenre() {
        return genre;
    }

    public void setGenre(GameGenre genre) {
        this.genre = genre;
    }

    public GameDifficulty getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(GameDifficulty difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BoardGame{" +
                "gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                ", genre=" + genre +
                ", difficultyLevel=" + difficultyLevel +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
