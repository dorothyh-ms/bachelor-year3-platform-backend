package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Player;

import java.util.List;
import java.util.UUID;

public interface FriendServiceUseCase {
    /**
     * Retrieves the list of friends for a given player.
     *
     * @param playerId The UUID of the player.
     * @return A list of Player entities representing the player's friends.
     */
    List<Player> getFriends(UUID playerId);

    /**
     * Adds a friend for a given player.
     *
     * @param playerId The UUID of the player.
     * @param friendId The UUID of the friend to add.
     * @return A boolean indicating if the operation was successful.
     */
    boolean addFriend(UUID playerId, UUID friendId);
}
