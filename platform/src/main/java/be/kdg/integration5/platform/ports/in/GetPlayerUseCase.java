package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetPlayerUseCase {
    List<Player> getPlayers(String username);
    Optional<Player> getPlayerById(UUID uuid);
}
