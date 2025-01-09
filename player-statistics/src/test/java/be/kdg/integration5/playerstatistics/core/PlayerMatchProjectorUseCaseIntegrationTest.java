package be.kdg.integration5.playerstatistics.core;

import be.kdg.integration5.common.domain.MatchOutcome;
import be.kdg.integration5.playerstatistics.adapters.out.db.entities.*;
import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.*;
import be.kdg.integration5.playerstatistics.domain.MatchStatus;

import be.kdg.integration5.playerstatistics.ports.in.PlayerMatchProjector;
import be.kdg.integration5.playerstatistics.ports.in.RecordEndMatchUseCase;
import be.kdg.integration5.playerstatistics.ports.in.commands.ProjectPlayerMatchCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static be.kdg.integration5.playerstatistics.core.TestValues.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerMatchProjectorUseCaseIntegrationTest extends AbstractDatabaseTest{
    @Autowired
    private BoardGameRepository boardGameRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayerProfileRepository playerRepository;

    @Autowired
    private PlayerMatchRepository playerMatchRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private LocationRepository locationRepository;



    @Autowired
    private PlayerMatchProjector sut;


    @BeforeEach
    public void setup(){
        // ARRANGE
        CountryJpaEntity country = new CountryJpaEntity(UUID.randomUUID(), COUNTRY_NAME);


        country =countryRepository.save(country);
        LocationJpaEntity location = new LocationJpaEntity(UUID.randomUUID(), CITY_NAME, country);
        location = locationRepository.save(location);
        BoardGameJpaEntity gameJpa=  new BoardGameJpaEntity(GAME_ID, GAME_NAME, GAME_GENRE, GAME_DIFFICULTY, GAME_PRICE, GAME_DESCRIPTION);
        boardGameRepository.save(gameJpa);
        PlayerProfileJpaEntity playerProfileJpa1 = new PlayerProfileJpaEntity(PLAYER1_ID,
                PLAYER_USERNAME,
                PLAYER_FIRSTNAME,
                PLAYER_LASTNAME,
                PLAYER_BIRTHDATE,
                PLAYER_GENDER,
                location

        );
        playerRepository.save(playerProfileJpa1);
        PlayerProfileJpaEntity playerProfileJpa2 = new PlayerProfileJpaEntity(PLAYER2_ID,
                PLAYER_USERNAME,
                PLAYER_FIRSTNAME,
                PLAYER_LASTNAME,
                PLAYER_BIRTHDATE,
                PLAYER_GENDER,
                location
        );
        playerRepository.save(playerProfileJpa2);

        MatchJpaEntity matchJpa = matchRepository.save(new MatchJpaEntity(MATCH_ID, gameJpa, LocalDateTime.now(), null, MatchStatus.IN_PROGRESS));
        playerMatchRepository.save(new PlayerMatchJpaEntity(PLAYER_MATCH_ID1, matchJpa, PLAYER_MATCH1_TURNS, PLAYER_MATCH1_SCORE, MatchOutcome.IN_PROGRESS, playerProfileJpa1));
        playerMatchRepository.save(new PlayerMatchJpaEntity(PLAYER_MATCH_ID2, matchJpa, PLAYER_MATCH1_TURNS, PLAYER_MATCH2_SCORE, MatchOutcome.IN_PROGRESS, playerProfileJpa2));
    }

    @Test
    void addTurnToExistingPlayerMatchShouldIncreaseNumberOfTurnsBy1AndUpdateScore(){
        //ACT
        int pointsEarned = 2;
        sut.projectPlayerMatch(new ProjectPlayerMatchCommand(MATCH_ID, PLAYER1_ID, LocalDateTime.now(), pointsEarned));

        //ASSERT
        Optional<PlayerMatchJpaEntity> playerMatchJpaEntityOptional = playerMatchRepository.findById(PLAYER_MATCH_ID1);

        assertEquals(playerMatchJpaEntityOptional.isPresent(), Boolean.TRUE);
        PlayerMatchJpaEntity playerMatchJpaEntity = playerMatchJpaEntityOptional.get();

        assertEquals(PLAYER_MATCH1_TURNS+1, playerMatchJpaEntity.getNumberOfTurns());
        assertEquals(PLAYER_MATCH1_SCORE+pointsEarned, playerMatchJpaEntity.getScore());
    }

}
