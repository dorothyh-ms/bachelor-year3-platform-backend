package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.in.FriendServiceUseCase;
import be.kdg.integration5.platform.ports.out.FriendLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultFriendUseCase implements FriendServiceUseCase {

    private final FriendLoadPort friendLoadPort;

    public DefaultFriendUseCase(FriendLoadPort friendLoadPort) {
        this.friendLoadPort = friendLoadPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Player> getFriends(UUID playerId) {
        return friendLoadPort.loadFriendsByPlayerId(playerId);
    }

    @Override
    @Transactional
    public boolean addFriend(UUID playerId, UUID friendId) {
        try {
            friendLoadPort.saveFriendship(playerId, friendId);
            return true;
        } catch (Exception e) {
            // Optionally log the error for debugging
            return false;
        }
    }
}
