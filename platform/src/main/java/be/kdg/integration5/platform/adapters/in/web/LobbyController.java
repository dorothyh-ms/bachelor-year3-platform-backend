package be.kdg.integration5.platform.adapters.in.web;


import be.kdg.integration5.platform.adapters.in.web.dtos.GameDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.InviteDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.LobbyDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.PlayerDto;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.ports.in.GetLobbyUseCase;
import be.kdg.integration5.platform.ports.in.PlayerAcceptsInviteUseCase;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    private final PlayerJoinsLobbyUseCase playerJoinsLobbyUseCase;
    private final GetLobbyUseCase getLobbyUseCase;
    private final PlayerCreatesInviteUseCase playerCreatesInviteUseCase;
    private final PlayerAcceptsInviteUseCase playerAcceptsInviteUseCase;


    public LobbyController(PlayerJoinsLobbyUseCase playerJoinsLobbyUseCase, GetLobbyUseCase getLobbyUseCase, PlayerCreatesInviteUseCase playerCreatesInviteUseCase, PlayerAcceptsInviteUseCase playerAcceptsInviteUseCase) {
        this.playerJoinsLobbyUseCase = playerJoinsLobbyUseCase;
        this.getLobbyUseCase = getLobbyUseCase;
        this.playerCreatesInviteUseCase = playerCreatesInviteUseCase;
        this.playerAcceptsInviteUseCase = playerAcceptsInviteUseCase;
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



    @PostMapping("/{lobbyId}/invite")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<InviteDto> invitePlayer(@AuthenticationPrincipal Jwt token,
                                                  @PathVariable UUID lobbyId,
                                                  @RequestBody Map<String, String> body) {
        UUID userId = UUID.fromString((String) token.getClaims().get("sub") );
        UUID invitedUserId = UUID.fromString(body.get("userId"));
        Invite invite = playerCreatesInviteUseCase.createInvite(userId, invitedUserId, lobbyId);
        return new ResponseEntity<>(new InviteDto(invite.getId(), invite.getSender(), invite.getRecipient(), invite.getLobby()), HttpStatus.OK);
    }

    @PatchMapping("/invite/{inviteId}/accept")
    public ResponseEntity<LobbyDto> acceptInvite(@AuthenticationPrincipal Jwt token, @PathVariable UUID inviteId) {
        UUID userId = UUID.fromString((String) token.getClaims().get("sub") );
        Lobby lobby = playerAcceptsInviteUseCase.playerAcceptsInvite(inviteId, userId);
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
}
