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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static be.kdg.integration5.platform.core.TestValues.*;
import static be.kdg.integration5.platform.core.TestValues.INVITE_ID;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerCreatesLobbyIntegrationTest {

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
}
