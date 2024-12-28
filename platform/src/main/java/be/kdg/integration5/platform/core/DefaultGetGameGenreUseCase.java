package be.kdg.integration5.platform.core;

import be.kdg.integration5.common.domain.GameGenre;
import be.kdg.integration5.platform.ports.in.GetGameGenresUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DefaultGetGameGenreUseCase implements GetGameGenresUseCase {
    @Override
    public List<GameGenre> getAllGameGenres() {
        return Stream.of(GameGenre.values())
            .collect(Collectors.toList());
    }
}
