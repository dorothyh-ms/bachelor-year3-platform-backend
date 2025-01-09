package be.kdg.integration5.playerstatistics.core;

import be.kdg.integration5.common.domain.MatchOutcome;
import be.kdg.integration5.playerstatistics.adapters.out.db.entities.*;
import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.*;
import be.kdg.integration5.playerstatistics.domain.MatchStatus;
import be.kdg.integration5.playerstatistics.ports.in.RecordNewMatchCommand;
import be.kdg.integration5.playerstatistics.ports.in.RecordNewMatchUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static be.kdg.integration5.playerstatistics.core.TestValues.*;
import static org.junit.jupiter.api.Assertions.*;


class RecordNewMatchUseCaseIntegrationTest extends AbstractDatabaseTest{

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
    private RecordNewMatchUseCase sut;

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
    }

    @Test
    public void recordNewMatchShouldCreateMatchAndPlayerMatchForBothPlayers(){
        // ASSERT
        assertFalse(matchRepository.existsById(MATCH_ID));
        assertFalse(playerMatchRepository.existsByMatch_IdAndPlayer_Id(MATCH_ID, PLAYER1_ID));
        assertFalse(playerMatchRepository.existsByMatch_IdAndPlayer_Id(MATCH_ID, PLAYER2_ID));

        // ACT
        sut.createMatch(new RecordNewMatchCommand(MATCH_ID, PLAYER1_ID, PLAYER2_ID, GAME_NAME, MATCH_START_TIME));
        final Optional<MatchJpaEntity> matchJpaEntityOptional = matchRepository.findById(MATCH_ID);
        assertEquals(matchJpaEntityOptional.isPresent(), Boolean.TRUE);
        MatchJpaEntity matchJpa = matchJpaEntityOptional.get();
        assertEquals(MATCH_ID, matchJpa.getId());
        assertEquals(MatchStatus.IN_PROGRESS, matchJpa.getStatus()); // new match should be in progress
        assertEquals(MATCH_START_TIME.truncatedTo(ChronoUnit.MILLIS), matchJpa.getStartTime().truncatedTo(ChronoUnit.MILLIS));
        assertNull(matchJpa.getEndTime()); // match should not have end time yet

        final Optional<PlayerMatchJpaEntity> player1MatchJpaEntityOptional = playerMatchRepository.findFirstByMatch_IdAndPlayer_Id(MATCH_ID, PLAYER1_ID);
        assertEquals(player1MatchJpaEntityOptional.isPresent(), Boolean.TRUE);
        PlayerMatchJpaEntity playerMatchJpaEntity1 = player1MatchJpaEntityOptional.get();
        assertEquals(PLAYER1_ID, playerMatchJpaEntity1.getPlayer().getId());
        assertEquals(MatchOutcome.IN_PROGRESS, playerMatchJpaEntity1.getOutcome());
        assertEquals(0, playerMatchJpaEntity1.getNumberOfTurns()); // should be initialized with 0 points
        assertEquals(0, playerMatchJpaEntity1.getScore()); // should be initialized with 0 turns

        final Optional<PlayerMatchJpaEntity> player2MatchJpaEntityOptional = playerMatchRepository.findFirstByMatch_IdAndPlayer_Id(MATCH_ID, PLAYER2_ID);
        assertEquals(player2MatchJpaEntityOptional.isPresent(), Boolean.TRUE);
        PlayerMatchJpaEntity playerMatchJpaEntity2 = player1MatchJpaEntityOptional.get();
        assertEquals(PLAYER1_ID, playerMatchJpaEntity2.getPlayer().getId());
        assertEquals(MatchOutcome.IN_PROGRESS, playerMatchJpaEntity2.getOutcome());
        assertEquals(0, playerMatchJpaEntity2.getNumberOfTurns()); // should be initialized with 0 points
        assertEquals(0, playerMatchJpaEntity2.getScore()); // should be initialized with 0 turns
    }

    @Test
    public void shouldNotCreateNewMatchForNonExistentGame(){
        // ASSERT
        String NON_EXISTENT_GAME_NAME = UUID.randomUUID().toString();
        UUID NEW_MATCH_ID = UUID.randomUUID();

        // ACT
        sut.createMatch(new RecordNewMatchCommand(NEW_MATCH_ID, PLAYER1_ID, PLAYER2_ID, NON_EXISTENT_GAME_NAME, MATCH_START_TIME));

        assertFalse(matchRepository.existsById(NEW_MATCH_ID));
        assertFalse(playerMatchRepository.existsByMatch_IdAndPlayer_Id(NEW_MATCH_ID, PLAYER1_ID));
        assertFalse(playerMatchRepository.existsByMatch_IdAndPlayer_Id(NEW_MATCH_ID, PLAYER2_ID));

    }


}