package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.platform.adapters.in.web.dtos.*;
import be.kdg.integration5.platform.domain.*;
import be.kdg.integration5.platform.ports.in.GetInvitesUseCase;
import be.kdg.integration5.platform.ports.in.PlayerAcceptsInviteUseCase;
import be.kdg.integration5.platform.ports.in.PlayerCreatesInviteUseCase;
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
@RequestMapping("/invites")
public class InviteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(InviteController.class);
    private final PlayerCreatesInviteUseCase playerCreatesInviteUseCase;
    private final PlayerAcceptsInviteUseCase playerAcceptsInviteUseCase;
    private final GetInvitesUseCase getInvitesUseCase;

    public InviteController(PlayerCreatesInviteUseCase playerCreatesInviteUseCase, PlayerAcceptsInviteUseCase playerAcceptsInviteUseCase, GetInvitesUseCase getInvitesUseCase) {
        this.playerCreatesInviteUseCase = playerCreatesInviteUseCase;
        this.playerAcceptsInviteUseCase = playerAcceptsInviteUseCase;
        this.getInvitesUseCase = getInvitesUseCase;
    }

    @PostMapping("/{lobbyId}/invite")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<InviteDto> invitePlayer(@AuthenticationPrincipal Jwt token,
                                                  @PathVariable UUID lobbyId,
                                                  @RequestBody Map<String, String> body) {
        LOGGER.info("InviteController is running invitePlayer");
        UUID userId = UUID.fromString((String) token.getClaims().get("sub"));
        UUID invitedUserId = UUID.fromString(body.get("userId"));
        Invite invite = playerCreatesInviteUseCase.createInvite(userId, invitedUserId, lobbyId);
        return new ResponseEntity<>(new InviteDto(
                invite.getId(),
                invite.getSender(),
                invite.getRecipient(),
                invite.getLobby(),
                invite.getDateSent()
        ), HttpStatus.OK);
    }

    @PatchMapping("/{inviteId}")
    public ResponseEntity<LobbyDto> answerInvite(@AuthenticationPrincipal Jwt token,
                                                 @PathVariable UUID inviteId,
                                                 @RequestBody InviteActionDTO action) {
        LOGGER.info("InviteController is running answerInvite");
        UUID userId = UUID.fromString((String) token.getClaims().get("sub"));
        Lobby lobby = playerAcceptsInviteUseCase.playerAnswersInvite(inviteId, userId, action.getAction());
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
                String.format("%s%s", game.getUrl(), lobby.getMatchId().toString()
                )

        ), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<InviteDto>> getAllInvites(@AuthenticationPrincipal Jwt token) {
        LOGGER.info("InviteController is running getAllInvites");
        UUID userId = UUID.fromString((String) token.getClaims().get("sub"));
        List<Invite> invites = getInvitesUseCase.getInvites(userId);
        if (!invites.isEmpty()) {
            return new ResponseEntity<>(invites.stream().map(invite -> new InviteDto(invite.getId(),
                            invite.getSender(),
                            invite.getRecipient(),
                            invite.getLobby(),
                            invite.getInviteStatus(),
                            invite.getDateSent()
                            )
                    )
                    .toList(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
