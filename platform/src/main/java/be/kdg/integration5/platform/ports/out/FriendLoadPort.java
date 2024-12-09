package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Player;

import java.util.List;
import java.util.UUID;

public interface FriendLoadPort {
    /**
     * Loads the list of friends for a given player ID.
     *
     * @param playerId The UUID of the player.
     * @return A list of Player entities.
     */
    List<Player> loadFriendsByPlayerId(UUID playerId);

    /**
     * Saves a friendship between two players.
     *
     * @param playerId The UUID of the player.
     * @param friendId The UUID of the friend.
     */
    void saveFriendship(UUID playerId, UUID friendId);
}
