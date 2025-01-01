package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.InviteJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.LobbyJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.repositories.GameRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.InviteRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.LobbyRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.PlayerRepository;
import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.InviteStatus;
import be.kdg.integration5.platform.domain.LobbyStatus;
import be.kdg.integration5.platform.ports.in.PlayerCreatesInviteUseCase;
import be.kdg.integration5.platform.ports.out.InviteUpdatePort;
import be.kdg.integration5.platform.ports.out.LobbyJoinedPort;
import be.kdg.integration5.platform.ports.out.LobbyLoadPort;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static be.kdg.integration5.platform.core.TestValues.*;
import static be.kdg.integration5.platform.core.TestValues.INVITE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerCreatesInviteIntegrationTest extends AbstractDatabaseTest{

    @Autowired
    private PlayerCreatesInviteUseCase playerCreatesInviteUseCase;

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

        LobbyJpaEntity lobbyJpa = new LobbyJpaEntity(LOBBY_ID, gameJpaEntity,player1Jpa, LocalDateTime.now(), LobbyStatus.OPEN);
        lobbyRepository.save(lobbyJpa);

    }

    @Test
    public void inviteShouldBeCreated() {
        // ACT
        Invite invite = playerCreatesInviteUseCase.createInvite(PLAYER1_ID, PLAYER2_ID, LOBBY_ID);

        // ASSERT
        final Optional<InviteJpaEntity> inviteJpaOptional = inviteRepository.findById(invite.getId());
        assertEquals(inviteJpaOptional.isPresent(), Boolean.TRUE);
        InviteJpaEntity inviteJpa = inviteJpaOptional.get();
        assertEquals(inviteJpa.getInviteStatus(), InviteStatus.OPEN);
    }
}
