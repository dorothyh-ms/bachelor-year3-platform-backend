package be.kdg.integration5.playerstatistics.ports.out;

import be.kdg.integration5.playerstatistics.domain.Match;

import java.util.Optional;
import java.util.UUID;

public interface MatchLoadPort {

    public Optional<Match> loadMatchById(UUID matchId);
}
