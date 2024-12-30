package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.out.db.repositories.GameRepository;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.exceptions.GameNotFoundException;
import be.kdg.integration5.platform.ports.in.GetGameUseCase;
import be.kdg.integration5.platform.ports.out.GameLoadPort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultGetGameUseCase implements GetGameUseCase {

    private final GameLoadPort gameLoadPort;

    public DefaultGetGameUseCase(GameLoadPort gameLoadPort) {
        this.gameLoadPort = gameLoadPort;
    }

    @Override
    public Game getGameById(UUID gameId) {
        Optional<Game> gameOptional = gameLoadPort.loadGameById(gameId);
        if (gameOptional.isEmpty()){
            throw new GameNotFoundException(String.format("Game with id %s does not exist", gameId));
        }
        return gameOptional.get();
    }
}
