package be.kdg.integration5.platform.adapters.in.web.dtos;

import be.kdg.integration5.common.domain.GameGenre;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class FavoriteDto {
    private UUID favoriteId;
    private UUID gameId;
    private String gameName;
    private String genre;
    private String image;
    private String description;
    private BigDecimal price;
    private String difficultyLevel;
    private String url;
    private LocalDateTime createdAt;

    public FavoriteDto(UUID favoriteId, UUID gameId, String gameName, String genre, String image,
                       String description, BigDecimal price, String difficultyLevel, String url,
                       LocalDateTime createdAt) {
        this.favoriteId = favoriteId;
        this.gameId = gameId;
        this.gameName = gameName;
        this.genre = genre;
        this.image = image;
        this.description = description;
        this.price = price;
        this.difficultyLevel = difficultyLevel;
        this.url = url;
        this.createdAt = createdAt;
    }

    public UUID getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(UUID favoriteId) {
        this.favoriteId = favoriteId;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

