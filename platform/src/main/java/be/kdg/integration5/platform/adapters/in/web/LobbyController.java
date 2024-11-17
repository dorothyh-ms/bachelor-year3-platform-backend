package be.kdg.integration5.platform.adapters.in.web;


import be.kdg.integration5.platform.adapters.in.web.dtos.GameDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.LobbyDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.PlayerDto;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.ports.in.GetLobbyUseCase;
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
import java.util.UUID;

@RestController
@RequestMapping("/lobbies")
public class LobbyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    private final PlayerJoinsLobbyUseCase playerJoinsLobbyUseCase;
    private final GetLobbyUseCase getLobbyUseCase;


    public LobbyController(PlayerJoinsLobbyUseCase playerJoinsLobbyUseCase, GetLobbyUseCase getLobbyUseCase) {
        this.playerJoinsLobbyUseCase = playerJoinsLobbyUseCase;
        this.getLobbyUseCase = getLobbyUseCase;
    }

    @PatchMapping("/{lobbyId}")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<LobbyDto> joinLobby(@PathVariable UUID lobbyId, @AuthenticationPrincipal Jwt token) {
        LOGGER.info("LobbyController is running LobbyController ");
        UUID userId = UUID.fromString((String) token.getClaims().get("sub") );
        Lobby lobby = playerJoinsLobbyUseCase.joinLobby(new JoinLobbyCommand(userId, lobbyId));
        return new ResponseEntity<>(new LobbyDto(
                lobby.getId(),
                new GameDto(
                        lobby.getGame().getId(),
                        lobby.getGame().getName()
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
                lobby.getStatus()

        ), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<LobbyDto>> getActiveLobbies() {
        LOGGER.info("Looking for open lobbies ");
        List<Lobby> lobbies = getLobbyUseCase.getLobbies();
        if (!lobbies.isEmpty()) {
            return new ResponseEntity<>(
                    lobbies.stream().map(lobby ->
                            new LobbyDto(lobby.getId(),
                                new GameDto(
                                        lobby.getGame().getId(),
                                        lobby.getGame().getName()),
                                new PlayerDto(
                                        lobby.getInitiatingPlayer().getPlayerId(),
                                        lobby.getInitiatingPlayer().getUsername()),
                     null,
                                lobby.getDateCreated(),
                                lobby.getStatus())).toList(),
                    HttpStatus.OK);
        }
        LOGGER.info("there were no open lobbies");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
