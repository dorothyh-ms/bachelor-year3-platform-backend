package be.kdg.integration5.platform.adapters.out.db.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(catalog="platform", name = "Friendship")
public class FriendshipJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerJpaEntity player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", nullable = false)
    private PlayerJpaEntity friend;

    public FriendshipJpaEntity() {}

    public FriendshipJpaEntity(PlayerJpaEntity player, PlayerJpaEntity friend) {
        this.player = player;
        this.friend = friend;
    }

    public Long getId() {
        return id;
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
}
