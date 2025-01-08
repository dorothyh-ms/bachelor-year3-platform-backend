package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.InviteJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.LobbyJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.repositories.GameRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.InviteRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.LobbyRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.PlayerRepository;
import be.kdg.integration5.platform.domain.InviteStatus;
import be.kdg.integration5.platform.domain.LobbyStatus;
import be.kdg.integration5.platform.exceptions.InvalidInviteUserException;
import be.kdg.integration5.platform.ports.in.PlayerAcceptsInviteUseCase;
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
public class PlayerAcceptsInviteUseCaseIntegrationTest extends AbstractDatabaseTest{

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private LobbyRepository lobbyRepository;



    @Autowired
    private PlayerAcceptsInviteUseCase playerAcceptsInviteUseCase;



    @BeforeEach
    public void setup(){
        // ARRANGE
        PlayerJpaEntity player1Jpa = new PlayerJpaEntity(PLAYER1_ID, PLAYER1_USERNAME);
        player1Jpa = playerRepository.save(player1Jpa);
        PlayerJpaEntity player2Jpa = new PlayerJpaEntity(PLAYER2_ID, PLAYER2_USERNAME);
        player2Jpa = playerRepository.save(player2Jpa);

        GameJpaEntity gameJpaEntity = new GameJpaEntity(GAME_ID, GAME_NAME, GENRE, DIFFICULTY, GAME_PRICE, GAME_DESCRIPTION, GAME_IMAGE, GAME_URL);
        gameJpaEntity = gameRepository.save(gameJpaEntity);

        LobbyJpaEntity lobbyJpa = new LobbyJpaEntity(OPEN_LOBBY_ID, gameJpaEntity,player1Jpa, LocalDateTime.now(), LobbyStatus.OPEN);
        lobbyRepository.save(lobbyJpa);

        InviteJpaEntity inviteJpa = new InviteJpaEntity(INVITE_ID, player1Jpa, player2Jpa, lobbyJpa, InviteStatus.OPEN);
        inviteJpa = inviteRepository.save(inviteJpa);
    }



   @Test
    public void inviteShouldBeAccepted() {
       // ACT
       playerAcceptsInviteUseCase.playerAnswersInvite(INVITE_ID, PLAYER2_ID, "ACCEPT");

       // ASSERT
       final Optional<InviteJpaEntity> inviteJpaOptional = inviteRepository.findById(INVITE_ID);
       assertEquals(inviteJpaOptional.isPresent(), Boolean.TRUE);
       InviteJpaEntity inviteJpa = inviteJpaOptional.get();
       assertEquals(inviteJpa.getInviteStatus(), InviteStatus.ACCEPTED);
       assertNotNull(inviteJpa.getLobby().getMatchId());
    }

    @Test
    public void acceptInviteShouldFailForWrongRecipientAndInviteShouldNotHaveAcceptedStatus() {

        UUID nonExistentUserId = UUID.randomUUID();
        // ASSERT
        assertThrows(InvalidInviteUserException.class, () ->
                // ACT
                { playerAcceptsInviteUseCase.playerAnswersInvite(INVITE_ID, nonExistentUserId, "ACCEPT");}
        );


        final Optional<InviteJpaEntity> inviteJpaOptional = inviteRepository.findById(INVITE_ID);
        InviteJpaEntity inviteJpa = inviteJpaOptional.get();
        // ASSERT
        assertEquals(inviteJpa.getInviteStatus(), InviteStatus.OPEN);
    }


}
