package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.platform.adapters.in.web.dtos.FriendDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.PlayerDto;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.in.FriendServiceUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/friends")
public class FriendController {
    private final FriendServiceUseCase friendService;

    public FriendController(FriendServiceUseCase friendService) {
        this.friendService = friendService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<FriendDto>> getFriends(@AuthenticationPrincipal Jwt token) {
        UUID playerId = UUID.fromString(token.getClaimAsString("sub"));
        List<Player> friends = friendService.getFriends(playerId);
        if (!friends.isEmpty()) {
            return new ResponseEntity<>(
                    friends.stream()
                            .map(friend -> new FriendDto(friend.getPlayerId(), friend.getUsername()))
                            .toList(),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<Void> addFriend(@RequestParam UUID friendId, @AuthenticationPrincipal Jwt token) {
        UUID playerId = UUID.fromString(token.getClaimAsString("sub"));
        boolean success = friendService.addFriend(playerId, friendId);
        if (success) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
