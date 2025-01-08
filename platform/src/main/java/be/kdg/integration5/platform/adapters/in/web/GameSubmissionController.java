package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.platform.adapters.in.web.dtos.GameSubmissionDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.NewGameSubmissionDto;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.ports.in.CreateGameSubmissionUseCase;
import be.kdg.integration5.platform.ports.in.GetGameSubmissionsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game-submissions")
public class GameSubmissionController {

    private static final Logger log = LoggerFactory.getLogger(GameSubmissionController.class);
    private final CreateGameSubmissionUseCase createGameSubmissionUseCase;
    private final GetGameSubmissionsUseCase getGameSubmissionsUseCase;


    public GameSubmissionController(CreateGameSubmissionUseCase createGameSubmissionUseCase, GetGameSubmissionsUseCase getGameSubmissionsUseCase) {
        this.createGameSubmissionUseCase = createGameSubmissionUseCase;
        this.getGameSubmissionsUseCase = getGameSubmissionsUseCase;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('platformAdmin')")
    public ResponseEntity<List<GameSubmissionDto>> getAllGameSubmissions(){
        List<GameSubmission> gameSubmissionList = getGameSubmissionsUseCase.getGameSubmission();
        if (!gameSubmissionList.isEmpty()) {
            return new ResponseEntity<>(
                    gameSubmissionList.stream().map(GameMapper::toGameSubmissionDto).toList(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('gameDev')")
    public ResponseEntity<NewGameSubmissionDto> createGame(@AuthenticationPrincipal Jwt token, @ModelAttribute NewGameSubmissionDto newGameSubmissionDto){
        UUID userId = UUID.fromString((String) token.getClaims().get("sub") );
        GameSubmission game = createGameSubmissionUseCase.createGameSubmission(GameMapper.toCreateGameSubmissionCommand(newGameSubmissionDto, userId));
        return new ResponseEntity<>(GameMapper.toNewGameSubmissionDto(game), HttpStatus.CREATED);
    }

    @GetMapping("/my-submissions")
    @PreAuthorize("hasAnyAuthority('gameDev')")
    public ResponseEntity<List<GameSubmissionDto>> getMySubmissions(){
        List<GameSubmission> gameSubmissionList = getGameSubmissionsUseCase.getGameSubmission();
        if (!gameSubmissionList.isEmpty()) {
            return new ResponseEntity<>(
                    gameSubmissionList.stream().map(GameMapper::toGameSubmissionDto).toList(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
