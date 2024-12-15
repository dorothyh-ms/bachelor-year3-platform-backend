package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.in.GetFriendsUseCase;
import be.kdg.integration5.platform.ports.out.FriendLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultGetFriendsUseCase implements GetFriendsUseCase {

    private final FriendLoadPort friendLoadPort;

    public DefaultGetFriendsUseCase(FriendLoadPort friendLoadPort) {
        this.friendLoadPort = friendLoadPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Player> getFriends(UUID playerId) {
        return friendLoadPort.loadFriendsByPlayerId(playerId);
    }
}
