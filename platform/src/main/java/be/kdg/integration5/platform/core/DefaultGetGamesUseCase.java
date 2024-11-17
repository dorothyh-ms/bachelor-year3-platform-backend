package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.in.GetGamesUseCase;
import be.kdg.integration5.platform.ports.out.GameLoadPort;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGetGamesUseCase implements GetGamesUseCase {
    private final GameLoadPort gameLoadPort;

    public DefaultGetGamesUseCase(GameLoadPort gameLoadPort) {
        this.gameLoadPort = gameLoadPort;
    }

    @Override
    public List<Game> getGames() {
        return gameLoadPort.loadGames();
    }
}