package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.common.domain.GameGenre;

import java.util.List;

public interface GetGameGenresUseCase {
    List<GameGenre> getAllGameGenres();
}
