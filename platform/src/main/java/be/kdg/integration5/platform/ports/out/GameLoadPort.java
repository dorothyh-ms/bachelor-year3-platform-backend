package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameLoadPort {
    public List<Game> loadGames();

    Optional<Game> loadGameById(UUID gameId);

    Optional<Game> loadGameByName(String name);
}
