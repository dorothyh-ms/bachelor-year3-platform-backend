package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.out.db.entities.FriendshipJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.repositories.FriendRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.PlayerRepository;
import be.kdg.integration5.platform.exceptions.AlreadyFriendsException;
import be.kdg.integration5.platform.ports.in.CreateFriendUseCase;
import be.kdg.integration5.platform.ports.in.commands.AddFriendCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static be.kdg.integration5.platform.core.TestValues.*;
import static be.kdg.integration5.platform.core.TestValues.PLAYER2_USERNAME;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateFriendUseCaseIntegrationTest extends AbstractDatabaseTest{

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private CreateFriendUseCase createFriendUseCase;

    private PlayerJpaEntity player1JpaEntity;
    private PlayerJpaEntity player2JpaEntity;

    @BeforeEach
    public void setup(){
        friendRepository.deleteAll();
        playerRepository.deleteAll();

        // ARRANGE
        player1JpaEntity = new PlayerJpaEntity(PLAYER1_ID, PLAYER1_USERNAME);
        player1JpaEntity = playerRepository.save(player1JpaEntity);
        player2JpaEntity = new PlayerJpaEntity(PLAYER2_ID, PLAYER2_USERNAME);
        player2JpaEntity = playerRepository.save(player2JpaEntity);
    }

    @Test
    public void addPlayerAsFriendShouldSucceed(){
        // ASSERT
        assertFalse(friendRepository.arePlayersFriends(player1JpaEntity, player2JpaEntity));

        // ACT
        boolean friendAdded = createFriendUseCase.addFriend(new AddFriendCommand(PLAYER1_ID, PLAYER2_ID));

        // ASSERT
        assertTrue(friendAdded);
        assertTrue(friendRepository.arePlayersFriends(player1JpaEntity, player2JpaEntity));
    }

    @Test
    public void addPlayerAsFriendShouldFailWhenPlayerIsAlreadyFriend(){
        // ARRANGE
        friendRepository.save(new FriendshipJpaEntity(UUID.randomUUID(), player1JpaEntity, player2JpaEntity));

        // ASSERT
        assertThrows(AlreadyFriendsException.class, () -> {
            // ACT
            createFriendUseCase.addFriend(new AddFriendCommand(PLAYER1_ID, PLAYER2_ID));
        });
    }

    @Test
    public void friendshipShouldExistRegardlessOfPlayerIdOrder() {

        // ACT
        createFriendUseCase.addFriend(new AddFriendCommand(PLAYER2_ID, PLAYER1_ID));

        // ASSERT
        assertTrue(friendRepository.arePlayersFriends(player1JpaEntity, player2JpaEntity));
        assertTrue(friendRepository.arePlayersFriends(player2JpaEntity, player1JpaEntity));
    }
}