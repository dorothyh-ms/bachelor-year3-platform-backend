package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.platform.adapters.in.web.dtos.NewGameSubmissionDto;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.ports.in.CreateGameSubmissionUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/game-submissions")
public class GameSubmissionController {

    private final CreateGameSubmissionUseCase createGameSubmissionUseCase;

    public GameSubmissionController(CreateGameSubmissionUseCase createGameSubmissionUseCase) {
        this.createGameSubmissionUseCase = createGameSubmissionUseCase;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<GameSubmission> getAllGameSubmissions(){
//               TODO: add function

        return null;
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('gameDev')")
    public ResponseEntity<NewGameSubmissionDto> createGame(@AuthenticationPrincipal Jwt token, @RequestBody NewGameSubmissionDto newGameSubmissionDto){
        UUID userId = UUID.fromString((String) token.getClaims().get("sub") );
        GameSubmission game = createGameSubmissionUseCase.createGameSubmission(GameMapper.toCreateGameSubmissionCommand(newGameSubmissionDto, userId));
        //        return dto
        return new ResponseEntity<>(GameMapper.toGameSubmissionDto(game), HttpStatus.CREATED);
    }
}
