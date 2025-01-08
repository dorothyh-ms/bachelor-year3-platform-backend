package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.platform.adapters.in.web.dtos.*;
import be.kdg.integration5.platform.domain.*;
import be.kdg.integration5.platform.domain.events.GameInviteEvent;
import be.kdg.integration5.platform.ports.in.GetInvitesUseCase;
import be.kdg.integration5.platform.ports.in.PlayerAcceptsInviteUseCase;
import be.kdg.integration5.platform.ports.in.PlayerCreatesInviteUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import be.kdg.integration5.platform.adapters.in.web.dtos.InviteActionDto;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;

    public InviteController(
            PlayerCreatesInviteUseCase playerCreatesInviteUseCase,
            PlayerAcceptsInviteUseCase playerAcceptsInviteUseCase,
            GetInvitesUseCase getInvitesUseCase,
            ApplicationEventPublisher eventPublisher) {
        this.playerCreatesInviteUseCase = playerCreatesInviteUseCase;
        this.playerAcceptsInviteUseCase = playerAcceptsInviteUseCase;
        this.getInvitesUseCase = getInvitesUseCase;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/{lobbyId}/invite")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<InviteDto> invitePlayer(
            @AuthenticationPrincipal Jwt token,
            @PathVariable UUID lobbyId,
            @RequestBody Map<String, String> body) {
        LOGGER.info("InviteController is running invitePlayer");
        UUID senderId = UUID.fromString(token.getClaimAsString("sub"));
        UUID recipientId = UUID.fromString(body.get("userId"));

        Invite invite = playerCreatesInviteUseCase.createInvite(senderId, recipientId, lobbyId);

        eventPublisher.publishEvent(new GameInviteEvent(recipientId, invite));
        return ResponseEntity.ok(mapToInviteDto(invite));
    }

    @PatchMapping("/{inviteId}")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<InviteDto> answerInvite(
            @AuthenticationPrincipal Jwt token,
            @PathVariable UUID inviteId,
            @RequestBody InviteActionDto action) {
        LOGGER.info("InviteController is running answerInvite");
        UUID userId = UUID.fromString(token.getClaimAsString("sub"));

        Invite invite = playerAcceptsInviteUseCase.playerAnswersInvite(inviteId, userId, action.getAction());
        return new ResponseEntity<>(mapToInviteDto(invite), HttpStatus.OK);
    }


    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<InviteDto>> getAllInvites(@AuthenticationPrincipal Jwt token) {
        LOGGER.info("InviteController is running getAllInvites");
        UUID userId = UUID.fromString(token.getClaimAsString("sub"));
        List<Invite> invites = getInvitesUseCase.getInvites(userId);

        if (!invites.isEmpty()) {
            return new ResponseEntity<>(invites.stream()
                    .map(this::mapToInviteDto)
                    .toList(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private InviteDto mapToInviteDto(Invite invite) {
        Lobby lobby = invite.getLobby();
        Game game = lobby.getGame();

        return new InviteDto(
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
        );
    }
}
