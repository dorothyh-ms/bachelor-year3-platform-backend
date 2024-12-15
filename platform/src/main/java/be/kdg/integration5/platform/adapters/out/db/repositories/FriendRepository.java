package be.kdg.integration5.platform.adapters.out.db.repositories;

import be.kdg.integration5.platform.adapters.out.db.entities.FriendshipJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<FriendshipJpaEntity, UUID> {
    /**
     * Finds all friendships where the given player is the source.
     *
     * @param player The player entity.
     * @return A list of FriendshipJpaEntity objects.
     */
    List<FriendshipJpaEntity> findByPlayer(PlayerJpaEntity player);

    /**
     * Checks if a friendship exists between two players.
     *
     * @param player The source player entity.
     * @param friend The target player entity.
     * @return True if the friendship exists, otherwise false.
     */
    boolean existsByPlayerAndFriend(PlayerJpaEntity player, PlayerJpaEntity friend);
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FriendshipJpaEntity f " +
            "WHERE (f.player = :player AND f.friend = :friend) OR (f.player = :friend AND f.friend = :player)")
    boolean arePlayersFriends(@Param("player") PlayerJpaEntity player, @Param("friend") PlayerJpaEntity friend);
}

