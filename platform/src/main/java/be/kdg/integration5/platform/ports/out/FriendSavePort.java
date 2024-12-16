package be.kdg.integration5.platform.ports.out;

import java.util.UUID;

public interface FriendSavePort {
    /**
     * Saves a friendship between two players.
     *
     * @param playerId The UUID of the player.
     * @param friendId The UUID of the friend.
     */
    void saveFriendship(UUID playerId, UUID friendId);
}
