package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Player;

import java.util.List;
import java.util.UUID;

/**
 * Use case for retrieving the list of friends for a given player.
 */
public interface GetFriendsUseCase {
    /**
     * Retrieves the list of friends for a given player.
     *
     * @param playerId The UUID of the player.
     * @return A list of Player entities representing the player's friends.
     */
    List<Player> getFriends(UUID playerId);
}
