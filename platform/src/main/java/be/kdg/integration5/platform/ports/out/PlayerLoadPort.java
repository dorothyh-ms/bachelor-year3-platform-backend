package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerLoadPort {
    List<Player> loadPlayers(String username);

    Optional<Player> loadPlayerById(UUID uuid);
}
