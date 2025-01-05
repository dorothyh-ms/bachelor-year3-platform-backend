package be.kdg.integration5.playerstatistics.adapters.out.db.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog="statistics", name="player_logins")
public class PlayerLoginJpaEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name="player_id")
    private PlayerProfileJpaEntity player;


    @Column(name="datetime")
    private LocalDateTime datetime;

    public PlayerLoginJpaEntity() {
    }

    public PlayerLoginJpaEntity(UUID id, PlayerProfileJpaEntity player, LocalDateTime datetime) {
        this.id = id;
        this.player = player;
        this.datetime = datetime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PlayerProfileJpaEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerProfileJpaEntity player) {
        this.player = player;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
