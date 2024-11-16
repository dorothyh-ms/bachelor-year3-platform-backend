package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Game;

import java.util.List;

public interface GetGamesUseCase {

    public List<Game> getGames();
}
