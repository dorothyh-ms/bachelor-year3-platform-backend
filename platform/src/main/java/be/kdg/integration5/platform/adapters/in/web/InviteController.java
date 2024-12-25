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
    private final PlayerAcceptsInviteUseCase playerAcceptsInviteUseCase;
    private final GetInvitesUseCase getInvitesUseCase;
    private Invite invite;

    public InviteController( PlayerAcceptsInviteUseCase playerAcceptsInviteUseCase, GetInvitesUseCase getInvitesUseCase) {

        this.playerAcceptsInviteUseCase = playerAcceptsInviteUseCase;
        this.getInvitesUseCase = getInvitesUseCase;
    }



    @PatchMapping("/{inviteId}")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<InviteDto> answerInvite(@AuthenticationPrincipal Jwt token,
                                                 @PathVariable UUID inviteId,
                                                 @RequestBody InviteActionDTO action) {
        LOGGER.info("InviteController is running answerInvite");
        UUID userId = UUID.fromString((String) token.getClaims().get("sub"));

            Invite invite = playerAcceptsInviteUseCase.playerAnswersInvite(inviteId, userId, action.getAction());
            Lobby lobby = invite.getLobby();
            Game game = lobby.getGame();
            return new ResponseEntity<>(
                    new InviteDto(
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
                                    new PlayerDto(
                                            lobby.getJoinedPlayer().getPlayerId(),
                                            lobby.getJoinedPlayer().getUsername()
                                    ),
                                    lobby.getDateCreated(),
                                    lobby.getStatus(),
                                    String.format("%s%s", game.getUrl(), lobby.getMatchId().toString())

                            ),
                            invite.getInviteStatus(),
                            invite.getDateSent()
            ), HttpStatus.OK);

    }



    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<InviteDto>> getAllInvites(@AuthenticationPrincipal Jwt token) {
        LOGGER.info("InviteController is running getAllInvites");
        UUID userId = UUID.fromString((String) token.getClaims().get("sub"));
        List<Invite> invites = getInvitesUseCase.getInvites(userId);
        if (!invites.isEmpty()) {
            return new ResponseEntity<>(invites.stream().map(invite -> {
                Lobby lobby = invite.getLobby();
                Game game = lobby.getGame();
                return new InviteDto(invite.getId(),
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
                            );}
                    )
                    .toList(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
