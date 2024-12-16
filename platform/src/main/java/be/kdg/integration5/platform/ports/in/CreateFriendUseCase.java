package be.kdg.integration5.platform.ports.in;

import java.util.UUID;

/**
 * Use case for adding a friend for a given player.
 */
public interface CreateFriendUseCase {
    /**
     * Adds a friend for a given player.
     *
     * @param playerId The UUID of the player.
     * @param friendId The UUID of the friend to add.
     * @return A boolean indicating if the operation was successful.
     */
    boolean addFriend(UUID playerId, UUID friendId);
}
