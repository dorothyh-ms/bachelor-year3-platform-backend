package be.kdg.integration5.ports.out;

import be.kdg.integration5.domain.PlayerProfile;

import java.util.Optional;
import java.util.UUID;

public interface PlayerProfileLoadPort {
    public Optional<PlayerProfile> loadPlayerProfileById(UUID id);
}
