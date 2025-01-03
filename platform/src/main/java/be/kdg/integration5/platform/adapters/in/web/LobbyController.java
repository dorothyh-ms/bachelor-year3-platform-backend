package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.platform.adapters.in.web.dtos.GameDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.InviteDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.LobbyDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.PlayerDto;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.ports.in.GetOpenLobbiesUseCase;
import be.kdg.integration5.platform.ports.in.PlayerCreatesInviteUseCase;
import be.kdg.integration5.platform.ports.in.PlayerJoinsLobbyUseCase;
import be.kdg.integration5.platform.ports.in.commands.JoinLobbyCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/lobbies")
public class LobbyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LobbyController.class);

    private final PlayerJoinsLobbyUseCase playerJoinsLobbyUseCase;
    private final PlayerCreatesInviteUseCase playerCreatesInviteUseCase;
    private final GetOpenLobbiesUseCase getLobbyUseCase;


    public LobbyController(
            PlayerJoinsLobbyUseCase playerJoinsLobbyUseCase,
            PlayerCreatesInviteUseCase playerCreatesInviteUseCase,
            GetOpenLobbiesUseCase getLobbyUseCase) {
        this.playerJoinsLobbyUseCase = playerJoinsLobbyUseCase;
        this.playerCreatesInviteUseCase = playerCreatesInviteUseCase;
        this.getLobbyUseCase = getLobbyUseCase;
    }

    @PostMapping("/{lobbyId}/invites")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<InviteDto> invitePlayer(@AuthenticationPrincipal Jwt token,
                                                  @PathVariable UUID lobbyId,
                                                  @RequestBody Map<String, String> body) {
        LOGGER.info("LobbyController is running invitePlayer");
        UUID userId = UUID.fromString(token.getClaimAsString("sub"));
        UUID invitedUserId = UUID.fromString(body.get("userId"));

        Invite invite = playerCreatesInviteUseCase.createInvite(userId, invitedUserId, lobbyId);
        Lobby lobby = invite.getLobby();
        Game game = lobby.getGame();

        return new ResponseEntity<>(new InviteDto(
                invite.getId(),
                invite.getSender(),
                invite.getRecipient(),
                new LobbyDto(
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
                        null,
                        lobby.getDateCreated(),
                        lobby.getStatus(),
                        null
                ),
                invite.getInviteStatus(),
                invite.getDateSent()
        ), HttpStatus.OK);
    }

    @PatchMapping("/{lobbyId}")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<LobbyDto> joinLobby(@PathVariable UUID lobbyId, @AuthenticationPrincipal Jwt token) {
        LOGGER.info("LobbyController is running joinLobby");
        UUID userId = UUID.fromString(token.getClaimAsString("sub"));
        Lobby lobby = playerJoinsLobbyUseCase.joinLobby(new JoinLobbyCommand(userId, lobbyId));
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
                new PlayerDto(
                        lobby.getJoinedPlayer().getPlayerId(),
                        lobby.getJoinedPlayer().getUsername()
                ),
                lobby.getDateCreated(),
                lobby.getStatus(),
                String.format("%s%s", game.getUrl(), lobby.getMatchId().toString())
        ), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<LobbyDto>> getActiveLobbies(@AuthenticationPrincipal Jwt token) {
        LOGGER.info("LobbyController is running getActiveLobbies");
        LOGGER.info("Looking for open lobbies with token {}", token);
        List<Lobby> lobbies = getLobbyUseCase.getOpenLobbies();

        if (!lobbies.isEmpty()) {
            return new ResponseEntity<>(
                    lobbies.stream().map(lobby -> {
                        Game game = lobby.getGame();
                        return new LobbyDto(
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
                                        lobby.getInitiatingPlayer().getUsername()),
                                null,
                                lobby.getDateCreated(),
                                lobby.getStatus()
                        );
                    }).toList(),
                    HttpStatus.OK);
        }

        LOGGER.info("There were no open lobbies");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
