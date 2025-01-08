package be.kdg.integration5.playerstatistics.adapters.out.db.entities;


import be.kdg.integration5.common.domain.GameDifficulty;
import be.kdg.integration5.common.domain.GameGenre;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog="statistics", name="games")
public class BoardGameJpaEntity {
    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "game_id", nullable = false, updatable = false)
    private UUID gameId;

    @Column(name = "game_name", nullable = false, length = 100)
    private String gameName;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private GameGenre genre;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level")
    private GameDifficulty difficultyLevel;


    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public BoardGameJpaEntity() {
    }

    public BoardGameJpaEntity(UUID gameId, String gameName, GameGenre genre, GameDifficulty difficultyLevel, BigDecimal price, String description) {
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
}
