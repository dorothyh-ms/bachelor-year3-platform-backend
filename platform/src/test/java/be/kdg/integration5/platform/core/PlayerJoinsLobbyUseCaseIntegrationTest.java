package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.LobbyJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.repositories.*;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.domain.LobbyStatus;
import be.kdg.integration5.platform.exceptions.PlayerNotAdmittedToLobbyException;
import be.kdg.integration5.platform.ports.in.PlayerJoinsLobbyUseCase;
import be.kdg.integration5.platform.ports.in.commands.JoinLobbyCommand;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static be.kdg.integration5.platform.core.TestValues.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlayerJoinsLobbyUseCaseIntegrationTest extends AbstractDatabaseTest{

    @Autowired
    private PlayerJoinsLobbyUseCase playerJoinsLobbyUseCase;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private LobbyRepository lobbyRepository;




    @BeforeEach
    public void setup(){


        // ARRANGE
        PlayerJpaEntity player1Jpa = new PlayerJpaEntity(PLAYER1_ID, PLAYER1_USERNAME);
        player1Jpa = playerRepository.save(player1Jpa);
        PlayerJpaEntity player2Jpa = new PlayerJpaEntity(PLAYER2_ID, PLAYER2_USERNAME);
        player2Jpa = playerRepository.save(player2Jpa);

        GameJpaEntity gameJpaEntity = new GameJpaEntity(GAME_ID, GAME_NAME, GENRE, DIFFICULTY, GAME_PRICE, GAME_DESCRIPTION, GAME_IMAGE, GAME_URL);
        gameJpaEntity = gameRepository.save(gameJpaEntity);

        LobbyJpaEntity openLobbyJpa = new LobbyJpaEntity(OPEN_LOBBY_ID, gameJpaEntity, player1Jpa, LocalDateTime.now(), LobbyStatus.OPEN);
        lobbyRepository.save(openLobbyJpa);

        LobbyJpaEntity closedLobbyJpa = new LobbyJpaEntity(CLOSED_LOBBY_ID, gameJpaEntity, player1Jpa, LocalDateTime.now(), LobbyStatus.CLOSED, player2Jpa, UUID.randomUUID());
        lobbyRepository.save(closedLobbyJpa);

    }

    @Test
    public void playerShouldJoinLobby() {
        // ACT
        Lobby lobby = playerJoinsLobbyUseCase.joinLobby(new JoinLobbyCommand(PLAYER2_ID, OPEN_LOBBY_ID));

        // ASSERT
        Optional<LobbyJpaEntity> lobbyJpaEntityOptional = lobbyRepository.findById(OPEN_LOBBY_ID);
        assertTrue(lobbyJpaEntityOptional.isPresent());
        LobbyJpaEntity lobbyJpa = lobbyJpaEntityOptional.get();
        assertEquals(lobbyJpa.getInitiatingPlayer().getPlayerId(), PLAYER1_ID);
        assertEquals(lobbyJpa.getJoinedPlayer().getPlayerId(), PLAYER2_ID);
        assertEquals(lobbyJpa.getId(), OPEN_LOBBY_ID);
        assertEquals(lobbyJpa.getGame().getId(), GAME_ID);
        assertEquals(lobbyJpa.getLobbyStatus(), LobbyStatus.CLOSED);
    }

    @Test
    public void joinLobbyShouldFailForInitiatingPlayer() {
        assertThrows(PlayerNotAdmittedToLobbyException.class, () -> {
            playerJoinsLobbyUseCase.joinLobby(new JoinLobbyCommand(PLAYER1_ID, OPEN_LOBBY_ID));
        });
    }


    @Test
    public void joinLobbyShouldFailForClosedLobby() {
        assertThrows(PlayerNotAdmittedToLobbyException.class, () -> {
            playerJoinsLobbyUseCase.joinLobby(new JoinLobbyCommand(PLAYER2_ID, CLOSED_LOBBY_ID));
        });
    }


}