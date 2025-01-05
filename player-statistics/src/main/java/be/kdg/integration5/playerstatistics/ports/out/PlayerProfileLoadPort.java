package be.kdg.integration5.playerstatistics.ports.out;

import be.kdg.integration5.playerstatistics.domain.PlayerProfile;

import java.util.Optional;
import java.util.UUID;

public interface PlayerProfileLoadPort {
    public Optional<PlayerProfile> loadPlayerProfileById(UUID id);
}
