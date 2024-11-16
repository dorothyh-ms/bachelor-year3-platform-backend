package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.in.GetPlayerUseCase;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
