package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Match;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatchLoadPort {
    public List<Match> loadUnfinishedMatchesOfPlayer(UUID playerId);

    Optional<Match> loadById(UUID uuid);
}
