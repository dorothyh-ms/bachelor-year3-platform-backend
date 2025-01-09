package be.kdg.integration5.playerstatistics.core;

import be.kdg.integration5.common.domain.MatchOutcome;
import be.kdg.integration5.common.domain.PlayerMatchOutcome;
import be.kdg.integration5.playerstatistics.adapters.out.db.entities.*;
import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.*;
import be.kdg.integration5.playerstatistics.domain.MatchStatus;
import be.kdg.integration5.playerstatistics.ports.in.RecordEndMatchUseCase;
import be.kdg.integration5.playerstatistics.ports.in.RecordMatchEndCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static be.kdg.integration5.playerstatistics.core.TestValues.*;
import static be.kdg.integration5.playerstatistics.core.TestValues.PLAYER_GENDER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RecordEndMatchUseCaseIntegrationTest extends AbstractDatabaseTest{

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
    private RecordEndMatchUseCase sut;

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
        playerMatchRepository.save(new PlayerMatchJpaEntity(UUID.randomUUID(), matchJpa, 1, 1, MatchOutcome.IN_PROGRESS, playerProfileJpa1));
        playerMatchRepository.save(new PlayerMatchJpaEntity(UUID.randomUUID(), matchJpa, 1, 1, MatchOutcome.IN_PROGRESS, playerProfileJpa2));
    }

    @Test
    public void recordEndMatchShouldChangeMatchStatusAndSaveMatchOutcomesForPlayers(){
        //ACT
        LocalDateTime END_TIME = LocalDateTime.now();
        sut.endMatch(new RecordMatchEndCommand(
                MATCH_ID,
                END_TIME,
                List.of(
                        new PlayerMatchOutcome(PLAYER1_ID, MatchOutcome.WIN),
                        new PlayerMatchOutcome(PLAYER2_ID, MatchOutcome.LOSS)
                )
        ));


        //ASSERT
        final Optional<MatchJpaEntity> matchJpaEntityOptional = matchRepository.findById(MATCH_ID);
        assertEquals(matchJpaEntityOptional.isPresent(), Boolean.TRUE);
        MatchJpaEntity matchJpa = matchJpaEntityOptional.get();
        assertEquals(MATCH_ID, matchJpa.getId());
        assertEquals(MatchStatus.COMPLETED, matchJpa.getStatus()); // new match should be ended
        assertEquals(END_TIME.truncatedTo(ChronoUnit.MILLIS), matchJpa.getEndTime().truncatedTo(ChronoUnit.MILLIS));


        final Optional<PlayerMatchJpaEntity> player1MatchJpaEntityOptional = playerMatchRepository.findFirstByMatch_IdAndPlayer_Id(MATCH_ID, PLAYER1_ID);
        assertEquals(player1MatchJpaEntityOptional.isPresent(), Boolean.TRUE);
        PlayerMatchJpaEntity playerMatchJpaEntity1 = player1MatchJpaEntityOptional.get();
        assertEquals(PLAYER1_ID, playerMatchJpaEntity1.getPlayer().getId());
        assertEquals(MatchOutcome.WIN, playerMatchJpaEntity1.getOutcome());

        final Optional<PlayerMatchJpaEntity> player2MatchJpaEntityOptional = playerMatchRepository.findFirstByMatch_IdAndPlayer_Id(MATCH_ID, PLAYER2_ID);
        PlayerMatchJpaEntity playerMatchJpaEntity2 = player2MatchJpaEntityOptional.get();
        assertEquals(PLAYER2_ID, playerMatchJpaEntity2.getPlayer().getId());
        assertEquals(MatchOutcome.LOSS, playerMatchJpaEntity2.getOutcome());
    }
}
