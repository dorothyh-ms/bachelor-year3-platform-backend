package be.kdg.integration5.playerstatistics.adapters.out.db;

import be.kdg.integration5.playerstatistics.adapters.out.db.entities.*;
import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.BoardGameRepository;
import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.MatchRepository;
import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.PlayerMatchRepository;
import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.PlayerProfileRepository;
import be.kdg.integration5.playerstatistics.domain.*;
import be.kdg.integration5.playerstatistics.ports.out.MatchCreatePort;
import be.kdg.integration5.playerstatistics.ports.out.MatchLoadPort;
import be.kdg.integration5.playerstatistics.ports.out.MatchUpdatePort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MatchDbAdapter implements MatchCreatePort, MatchUpdatePort, MatchLoadPort {
    private final MatchRepository matchRepository;
    private final PlayerMatchRepository playerMatchRepository;
    private final PlayerProfileRepository playerProfileRepository;
    private final BoardGameRepository boardGameRepository;


    public MatchDbAdapter(MatchRepository matchRepository, PlayerMatchRepository playerMatchRepository, PlayerProfileRepository playerProfileRepository, BoardGameRepository boardGameRepository) {
        this.matchRepository = matchRepository;
        this.playerMatchRepository = playerMatchRepository;
        this.playerProfileRepository = playerProfileRepository;
        this.boardGameRepository = boardGameRepository;
    }

    @Override
    public Match matchCreated(Match match) {
        BoardGame game = match.getBoardGame();

        MatchJpaEntity matchJpa = new MatchJpaEntity(
                match.getId(),
                new BoardGameJpaEntity(
                        game.getGameId(),
                        game.getGameName(),
                        game.getGenre(),
                        game.getDifficultyLevel(),
                        game.getPrice(),
                        game.getDescription()
                ),
                match.getStartTime(),
                match.getEndTime(),
                match.getStatus());
        matchJpa = matchRepository.save(matchJpa);

        MatchJpaEntity finalMatchJpa = matchJpa;
        match.getPlayerMatchList().forEach(pm -> {
            Optional<PlayerProfileJpaEntity> playerProfileJpaEntityOptional = playerProfileRepository.findById(pm.getPlayer().getId());
            playerProfileJpaEntityOptional.ifPresent(playerProfileJpaEntity -> playerMatchRepository.save(new PlayerMatchJpaEntity(
                    pm.getId(),
                    finalMatchJpa,
                    pm.getNumberOfTurns(),
                    pm.getScore(),
                    pm.getOutcome(),
                    playerProfileJpaEntity
            )));
        });
        return match;
    }

    @Override
    public Optional<Match> loadMatchById(UUID matchId) {
        Optional<MatchJpaEntity> matchJpaEntityOptional = matchRepository.findById(matchId);
        if(matchJpaEntityOptional.isPresent()){
            List<PlayerMatchJpaEntity> playerMatchJpas = playerMatchRepository.findByMatch_Id(matchId);
            MatchJpaEntity matchJpaEntity = matchJpaEntityOptional.get();
            BoardGameJpaEntity boardGameJpaEntity = matchJpaEntity.getGame();
            return Optional.of(
                    new Match(
                    matchJpaEntity.getId(),
                    new BoardGame(
                            boardGameJpaEntity.getGameId(),
                            boardGameJpaEntity.getGameName(),
                            boardGameJpaEntity.getGenre(),
                            boardGameJpaEntity.getDifficultyLevel(),
                            boardGameJpaEntity.getPrice(),
                            boardGameJpaEntity.getDescription()
                    ),
                    playerMatchJpas.stream().map(pmj ->
                        {
                            PlayerProfileJpaEntity playerProfileJpa = pmj.getPlayer();
                            LocationJpaEntity locationJpa = playerProfileJpa.getLocation();
                            return new PlayerMatch(
                                    pmj.getId(),
                                    new PlayerProfile(
                                            playerProfileJpa.getId(),
                                            playerProfileJpa.getUserName(),
                                            playerProfileJpa.getFirstName(),
                                            playerProfileJpa.getLastName(),
                                            playerProfileJpa.getBirthDate(),
                                            playerProfileJpa.getGender(),
                                            new Location(locationJpa.getCity(), locationJpa.getCountry().getName())
                                    ),
                                    pmj.getNumberOfTurns(),
                                    pmj.getScore(),
                                    pmj.getOutcome());
                        }
                    )
                            .toList(),
                    matchJpaEntity.getStartTime(),
                    matchJpaEntity.getEndTime(),
                    matchJpaEntity.getStatus()

            ));
        }
        return Optional.empty();
    }

    @Override
    public void updateMatch(Match match) {
        Optional<MatchJpaEntity> matchJpaOptional = matchRepository.findById(match.getId());
        if (matchJpaOptional.isPresent()){
            MatchJpaEntity matchJpa = matchJpaOptional.get();
            matchJpa.setEndTime(match.getEndTime());
            matchJpa.setStatus(match.getStatus());
            matchRepository.save(matchJpa);
        }

    }
}
