package be.kdg.integration5.platform.adapters.out.db.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "favorites", catalog = "platform")
public class FavoriteJpaEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerJpaEntity player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", nullable = false)
    private GameJpaEntity game;

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    public FavoriteJpaEntity() {}


    public FavoriteJpaEntity(PlayerJpaEntity player, GameJpaEntity game) {
        this.id = UUID.randomUUID();
        this.player = player;
        this.game = game;
    }


    public FavoriteJpaEntity(PlayerJpaEntity player, GameJpaEntity game, Timestamp createdAt) {
        this.id = UUID.randomUUID();
        this.player = player;
        this.game = game;
        this.createdAt = LocalDateTime.now();;
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

    public GameJpaEntity getGame() {
        return game;
    }

    public void setGame(GameJpaEntity game) {
        this.game = game;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public String toString() {
        return "FavoriteJpaEntity{" +
                "id=" + id +
                ", player=" + player +
                ", game=" + game +
                ", createdAt=" + createdAt +
                '}';
    }
}
