package be.kdg.integration5.playerstatistics.ports.out;

import be.kdg.integration5.playerstatistics.domain.PlayerMatch;

import java.util.Optional;
import java.util.UUID;

public interface PlayerMatchLoadPort {
    public Optional<PlayerMatch> loadPlayerMatchByMatchIdAndPlayerId(UUID matchId, UUID playerId);
}
