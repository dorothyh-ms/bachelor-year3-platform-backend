package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.platform.adapters.in.web.dtos.GameDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.LobbyDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.PlayerDto;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.ports.in.GetGamesUseCase;
import be.kdg.integration5.platform.ports.in.PlayerCreatesLobbyUseCase;
import be.kdg.integration5.platform.ports.in.commands.CreateLobbyCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GetGamesUseCase getGamesUseCase;
    private final PlayerCreatesLobbyUseCase playerCreatesLobbyUseCase;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    public GameController(GetGamesUseCase getGamesUseCase, PlayerCreatesLobbyUseCase playerCreatesLobbyUseCase) {
        this.getGamesUseCase = getGamesUseCase;
        this.playerCreatesLobbyUseCase = playerCreatesLobbyUseCase;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<GameDto>> getGames(@AuthenticationPrincipal Jwt token) {
        LOGGER.info("GameController is running getGames");

        List<Game> games = getGamesUseCase.getGames();
        if (!games.isEmpty()) {
            return new ResponseEntity<>(
                    games.stream().map(game -> new GameDto(
                            game.getId(),
                            game.getName(),
                            game.getGenre(),
                            game.getDifficultyLevel(),
                            game.getPrice(),
                            game.getDescription(),
                            game.getImage(),
                            game.getUrl()
                    )).toList(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{gameId}/lobbies")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<LobbyDto> createLobby( @PathVariable UUID gameId, @AuthenticationPrincipal Jwt token) {
        LOGGER.info("GameController is running createLobby");
        LOGGER.info("Claims of token {}", token.getClaims());
        UUID userId = UUID.fromString((String) token.getClaims().get("sub") );
        Lobby lobby = playerCreatesLobbyUseCase.createLobby(new CreateLobbyCommand(userId, gameId));
        Game game = lobby.getGame();
        return new ResponseEntity<>(new LobbyDto(
                lobby.getId(),
                new GameDto(
                        game.getId(),
                        game.getName(),
                        game.getGenre(),
                        game.getDifficultyLevel(),
                        game.getPrice(),
                        game.getDescription(),
                        game.getImage(),
                        game.getUrl()
                ),
                new PlayerDto(
                        lobby.getInitiatingPlayer().getPlayerId(),
                        lobby.getInitiatingPlayer().getUsername()
                ),
                lobby.getDateCreated(),
                lobby.getStatus()
        ), HttpStatus.OK);
    }
}