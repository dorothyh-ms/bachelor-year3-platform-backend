package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.exceptions.AddSelfAsFriendException;
import be.kdg.integration5.platform.exceptions.AlreadyFriendsException;
import be.kdg.integration5.platform.ports.in.CreateFriendUseCase;
import be.kdg.integration5.platform.ports.in.commands.AddFriendCommand;
import be.kdg.integration5.platform.ports.out.FriendLoadPort;
import be.kdg.integration5.platform.ports.out.FriendSavePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DefaultCreateFriendUseCase implements CreateFriendUseCase {

    private final FriendLoadPort friendLoadPort;
    private final FriendSavePort friendSavePort;

    public DefaultCreateFriendUseCase(FriendLoadPort friendLoadPort, FriendSavePort friendSavePort) {
        this.friendLoadPort = friendLoadPort;
        this.friendSavePort = friendSavePort;
    }

    @Override
    @Transactional
    public boolean addFriend(AddFriendCommand addFriendCommand) {
        UUID playerId = addFriendCommand.playerId();
        UUID friendId = addFriendCommand.friendId();
        // Validate inputs
        if (playerId == null || friendId == null) {
            throw new IllegalArgumentException("Player ID and Friend ID cannot be null.");
        }

        // Check if the player is trying to add themselves
        if (playerId.equals(friendId)) {
            throw new AddSelfAsFriendException("A player cannot add themselves as a friend.");
        }

        // Check if the players are already friends
        boolean alreadyFriends = friendLoadPort.arePlayersFriends(playerId, friendId);
        if (alreadyFriends) {
            throw new AlreadyFriendsException("The players are already friends.");
        }

        // Save the friendship
        friendSavePort.saveFriendship(playerId, friendId);
        return true;
    }
}
