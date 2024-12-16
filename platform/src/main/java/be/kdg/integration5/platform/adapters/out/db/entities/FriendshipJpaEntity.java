package be.kdg.integration5.platform.adapters.out.db.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(catalog = "platform", name = "friendship")
public class FriendshipJpaEntity {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "friendship_id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerJpaEntity player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", nullable = false)
    private PlayerJpaEntity friend;

    // Default constructor for JPA
    public FriendshipJpaEntity() {}

    // Constructor with all fields
    public FriendshipJpaEntity(UUID id, PlayerJpaEntity player, PlayerJpaEntity friend) {
        this.id = id;
        this.player = player;
        this.friend = friend;
    }

    // Constructor with auto-generated UUID
    public FriendshipJpaEntity(PlayerJpaEntity player, PlayerJpaEntity friend) {
        this.id = UUID.randomUUID(); // Automatically generate UUID
        this.player = player;
        this.friend = friend;
    }

    // Getters and Setters
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

    public PlayerJpaEntity getFriend() {
        return friend;
    }

    public void setFriend(PlayerJpaEntity friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        return "FriendshipJpaEntity{" +
                "id=" + id +
                ", player=" + player +
                ", friend=" + friend +
                '}';
    }
}
