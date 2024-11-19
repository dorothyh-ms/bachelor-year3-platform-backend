package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.in.GetPlayerUseCase;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultPlayerUseCase implements GetPlayerUseCase {
    private final PlayerLoadPort userPort;

    public DefaultPlayerUseCase(PlayerLoadPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public List<Player> getPlayers(String username) {
        return userPort.loadPlayers(username);
    }

    @Override
    public Optional<Player> getPlayerById(UUID uuid) {
        return userPort.loadPlayerById(uuid);
    }
}
