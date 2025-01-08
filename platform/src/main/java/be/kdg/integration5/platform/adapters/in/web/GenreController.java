package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.common.domain.GameGenre;
import be.kdg.integration5.platform.ports.in.GetGameGenresUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GetGameGenresUseCase getGameGenresUseCase;

    public GenreController(GetGameGenresUseCase getGameGenresUseCase) {
        this.getGameGenresUseCase = getGameGenresUseCase;
    }

    @GetMapping
    public ResponseEntity<List<GameGenre>> getAllGameGenres(){
       return new ResponseEntity<>(getGameGenresUseCase.getAllGameGenres(), HttpStatus.OK);
    }
}
