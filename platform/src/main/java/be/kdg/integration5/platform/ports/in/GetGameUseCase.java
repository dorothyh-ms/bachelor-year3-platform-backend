package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Game;

import java.util.UUID;

public interface GetGameUseCase {
    public Game getGameById(UUID gameId);
}
