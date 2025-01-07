package be.kdg.integration5.platform.adapters.out.db.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "achievements", catalog = "platform")
public class AchievementJpaEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;



    @ManyToOne
    @JoinColumn(name = "game_id", updatable = false, nullable = false)
    private GameJpaEntity game;

    private String name;

    private String description;

    public AchievementJpaEntity() {
    }

    public AchievementJpaEntity(UUID id,  GameJpaEntity game, String name, String description) {
        this.id = id;

        this.game = game;
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }



    public GameJpaEntity getGame() {
        return game;
    }

    public void setGame(GameJpaEntity game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
