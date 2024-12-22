package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.platform.adapters.in.web.dtos.FriendDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.NewFriendDto;
import be.kdg.integration5.platform.exceptions.AddSelfAsFriendException;
import be.kdg.integration5.platform.exceptions.AlreadyFriendsException;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.in.GetFriendsUseCase;
import be.kdg.integration5.platform.ports.in.CreateFriendUseCase;
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
@RequestMapping("/friends")
public class FriendController {
    private final GetFriendsUseCase getFriendsUseCase;
    private final CreateFriendUseCase createFriendUseCase;
    private static final Logger LOGGER = LoggerFactory.getLogger(LobbyController.class);

    public FriendController(GetFriendsUseCase getFriendsUseCase, CreateFriendUseCase createFriendUseCase) {
        this.getFriendsUseCase = getFriendsUseCase;
        this.createFriendUseCase = createFriendUseCase;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<FriendDto>> getFriends(@AuthenticationPrincipal Jwt token) {
        LOGGER.info("FriendController is running getFriends");
        UUID playerId = UUID.fromString(token.getClaimAsString("sub"));
        List<Player> friends = getFriendsUseCase.getFriends(playerId);
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
    public ResponseEntity<Void> addFriend(@RequestBody NewFriendDto newFriendDto, @AuthenticationPrincipal Jwt token) {
        LOGGER.info("FriendController is running addFriend");
        UUID playerId = UUID.fromString(token.getClaimAsString("sub"));
        createFriendUseCase.addFriend(playerId, newFriendDto.getFriendId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Exception handlers

    @ExceptionHandler(AddSelfAsFriendException.class)
    public ResponseEntity<String> handleAddSelfAsFriendException(AddSelfAsFriendException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyFriendsException.class)
    public ResponseEntity<String> handleAlreadyFriendsException(AlreadyFriendsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
