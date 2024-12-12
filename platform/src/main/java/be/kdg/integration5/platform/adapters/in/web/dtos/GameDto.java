package be.kdg.integration5.platform.adapters.in.web.dtos;

import be.kdg.integration5.common.domain.GameDifficulty;
import be.kdg.integration5.common.domain.GameGenre;

import java.math.BigDecimal;
import java.util.UUID;

public class GameDto {
    private UUID id;

    private String name;

    private GameGenre genre;

    private GameDifficulty difficultyLevel;


    private BigDecimal price;

    private String description;


    private String image;

    private String url;

    public GameDto(UUID id, String name, GameGenre genre, GameDifficulty difficultyLevel, BigDecimal price, String description, String image, String url) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.difficultyLevel = difficultyLevel;
        this.price = price;
        this.description = description;
        this.image = image;
        this.url = url;
    }

    public GameDto() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
