package be.kdg.integration5.platform.adapters.out.db.entities;


import be.kdg.integration5.common.domain.GameDifficulty;
import be.kdg.integration5.common.domain.GameGenre;
import be.kdg.integration5.platform.domain.SubmissionState;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog="platform", name="gamesSubmission")
public class GameSubmissionJpaEntity{
    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "game_id", nullable = false, updatable = false)
    private UUID gameId;

    @Column(name = "game_name", nullable = false, length = 100, unique = true)
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

    @Column(name="image_url")
    private String image;

    @Column(name="url")
    private String url;

    @Enumerated(value = EnumType.ORDINAL)
    private SubmissionState submissionState;
    private UUID createdBy;

    private String rulesName;

    public GameSubmissionJpaEntity() {
        // Default constructor for JPA
    }

    public GameSubmissionJpaEntity(UUID id, String name, GameGenre genre, GameDifficulty difficultyLevel, BigDecimal price, String description, String image, String url, SubmissionState submissionState, UUID createdBy, String rulesName) {
        this.gameId = id;
        this.gameName = name;
        this.genre = genre;
        this.difficultyLevel = difficultyLevel;
        this.price = price;
        this.description = description;
        this.image = image;
        this.url = url;
        this.submissionState = submissionState;
        this.createdBy = createdBy;
        this.rulesName = rulesName;
    }

    public SubmissionState getSubmissionState() {
        return submissionState;
    }

    public void setSubmissionState(SubmissionState submissionState) {
        this.submissionState = submissionState;
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

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public String getRulesName() {
        return rulesName;
    }

    public void setRulesName(String rulesName) {
        this.rulesName = rulesName;
    }

    @Override
    public String toString() {
        return "GameSubmissionJpaEntity{" +
                "gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                ", genre=" + genre +
                ", difficultyLevel=" + difficultyLevel +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", submissionState=" + submissionState +
                ", createdBy=" + createdBy +
                ", rulesName='" + rulesName + '\'' +
                '}';
    }
}
