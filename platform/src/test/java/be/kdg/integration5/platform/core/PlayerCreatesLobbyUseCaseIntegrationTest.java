package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.LobbyJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.repositories.GameRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.InviteRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.LobbyRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.PlayerRepository;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.exceptions.GameNotFoundException;
import be.kdg.integration5.platform.exceptions.PlayerNotFoundException;
import be.kdg.integration5.platform.ports.in.PlayerCreatesLobbyUseCase;
import be.kdg.integration5.platform.ports.in.commands.CreateLobbyCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static be.kdg.integration5.platform.core.TestValues.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerCreatesLobbyUseCaseIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private PlayerCreatesLobbyUseCase playerCreatesLobbyUseCase;
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
        PlayerJpaEntity player1Jpa = new PlayerJpaEntity(PLAYER1_ID, PLAYER1_USERNAME);
        player1Jpa = playerRepository.save(player1Jpa);
        PlayerJpaEntity player2Jpa = new PlayerJpaEntity(PLAYER2_ID, PLAYER2_USERNAME);
        player2Jpa = playerRepository.save(player2Jpa);

        GameJpaEntity gameJpaEntity = new GameJpaEntity(GAME_ID, GAME_NAME, GENRE, DIFFICULTY, GAME_PRICE, GAME_DESCRIPTION, GAME_IMAGE, GAME_URL);
        gameJpaEntity = gameRepository.save(gameJpaEntity);

    }

    @Test
    public void lobbyShouldBeCreated() {
        // ACT
        Lobby lobby = playerCreatesLobbyUseCase.createLobby(new CreateLobbyCommand(PLAYER1_ID, GAME_ID));

        // ASSERT
        final Optional<LobbyJpaEntity> lobbyJpaEntityOptional = lobbyRepository.findById(lobby.getId());
        assertEquals(lobbyJpaEntityOptional.isPresent(), Boolean.TRUE);
        LobbyJpaEntity lobbyJpa = lobbyJpaEntityOptional.get();
        assertEquals(lobbyJpa.getInitiatingPlayer().getPlayerId(), PLAYER1_ID);
        assertNull(lobbyJpa.getJoinedPlayer());
        assertEquals(lobbyJpa.getGame().getId(), GAME_ID);
    }

    @Test void createLobbyShouldFailForNonExistentGame() {
        // ASSERT
        assertThrows(GameNotFoundException.class, () -> {
            // ACT
            playerCreatesLobbyUseCase.createLobby(new CreateLobbyCommand(PLAYER1_ID, UUID.randomUUID()));
        });
    }


    @Test void createLobbyShouldFailForNonExistentPlayer() {
        // ASSERT
        assertThrows(PlayerNotFoundException.class, () -> {
            // ACT
            playerCreatesLobbyUseCase.createLobby(new CreateLobbyCommand(UUID.randomUUID(), GAME_ID));
        });
    }
}
