package be.kdg.integration5.platform.adapters.out.db.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "player_achievements", catalog = "platform")
public class PlayerAchievementJpaEntity {
    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "player_id", updatable = false, nullable = false)
    private PlayerJpaEntity player;

    @ManyToOne
    @JoinColumn(name = "achievement_id", updatable = false, nullable = false)
    private AchievementJpaEntity achievement;

    public PlayerAchievementJpaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PlayerJpaEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerJpaEntity player) {
        this.player = player;
    }

    public AchievementJpaEntity getAchievement() {
        return achievement;
    }

    public void setAchievement(AchievementJpaEntity achievementJpaEntity) {
        this.achievement = achievementJpaEntity;
    }

    public PlayerAchievementJpaEntity(UUID id, PlayerJpaEntity player, AchievementJpaEntity achievement) {
        this.id = id;
        this.player = player;
        this.achievement = achievement;
    }
}
