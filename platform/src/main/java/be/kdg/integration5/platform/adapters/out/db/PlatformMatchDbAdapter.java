package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.PlatformMatchJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.mappers.PlayerMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.PlatformMatchRepository;
import be.kdg.integration5.platform.domain.Match;
import be.kdg.integration5.platform.domain.MatchStatus;
import be.kdg.integration5.platform.ports.out.MatchCreatedPort;
import be.kdg.integration5.platform.ports.out.MatchEndedPort;
import be.kdg.integration5.platform.ports.out.MatchLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PlatformMatchDbAdapter implements MatchCreatedPort, MatchEndedPort, MatchLoadPort {
    private final PlatformMatchRepository platformMatchRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformMatchDbAdapter.class);

    public PlatformMatchDbAdapter(PlatformMatchRepository platformMatchRepository) {
        this.platformMatchRepository = platformMatchRepository;
    }

    @Override
    public List<Match> loadUnfinishedMatchesOfPlayer(UUID playerId) {
        List<Match> matches =  platformMatchRepository.findAllByPlayerIdAndStatus(playerId, MatchStatus.IN_PROGRESS)
                .stream()
                .map(matchJpa -> new Match(
                        matchJpa.getId(),
                        PlayerMapper.toPlayer(matchJpa.getPlayer1()),
                        PlayerMapper.toPlayer(matchJpa.getPlayer2()),
                        GameMapper.toGame(matchJpa.getGameJpaEntity()),
                        matchJpa.getUrl(),
                        matchJpa.getStatus(),
                        matchJpa.getDateCreated()
                        ))
                .toList();
        LOGGER.info("PlatformMatchDbAdapter is returning {}", matches);
        return matches;

    }

    private void saveMatch(Match match){
        platformMatchRepository.save(new PlatformMatchJpaEntity(
                match.getId(),
                PlayerMapper.toPlayerJpaEntity(match.getPlayer1()),
                PlayerMapper.toPlayerJpaEntity(match.getPlayer2()),
                GameMapper.toGameJpaEntity(match.getGame()),
                match.getUrl(),
                match.getDateCreated(),
                match.getStatus()
        ));
    }

    @Override
    public void matchCreated(Match match) {
        saveMatch(match);
    }

    @Override
    public void matchEnded(Match match) {
        saveMatch(match);
    }

    @Override
    public Optional<Match> loadById(UUID uuid) {
        Optional<PlatformMatchJpaEntity> matchJpaEntityOptional = platformMatchRepository.findById(uuid);
        if (matchJpaEntityOptional.isPresent()){
            PlatformMatchJpaEntity matchJpa = matchJpaEntityOptional.get();
            return Optional.of(new Match(
                    matchJpa.getId(),
                    PlayerMapper.toPlayer(matchJpa.getPlayer1()),
                    PlayerMapper.toPlayer(matchJpa.getPlayer2()),
                    GameMapper.toGame(matchJpa.getGameJpaEntity()),
                    matchJpa.getUrl(),
                    matchJpa.getStatus(),
                    matchJpa.getDateCreated()
            ));
        }
        return Optional.empty();
    }
}
