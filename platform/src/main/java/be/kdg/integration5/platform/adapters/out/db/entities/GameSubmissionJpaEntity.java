package be.kdg.integration5.platform.adapters.out.db.entities;


import be.kdg.integration5.common.domain.GameDifficulty;
import be.kdg.integration5.common.domain.GameGenre;
import be.kdg.integration5.platform.domain.SubmissionState;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

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

    @Enumerated(value = EnumType.STRING)
    private SubmissionState submissionState;

    public GameSubmissionJpaEntity() {
        // Default constructor for JPA
    }

    public GameSubmissionJpaEntity(UUID id, String name, GameGenre genre, GameDifficulty difficultyLevel, BigDecimal price, String description, String image, String url, SubmissionState submissionState) {
        this.gameId = id;
        this.gameName = name;
        this.genre = genre;
        this.difficultyLevel = difficultyLevel;
        this.price = price;
        this.description = description;
        this.image = image;
        this.url = url;
        this.submissionState = submissionState;
    }

    public SubmissionState getSubmissionState() {
        return submissionState;
    }

    public void setSubmissionState(SubmissionState submissionState) {
        this.submissionState = submissionState;
    }
}
