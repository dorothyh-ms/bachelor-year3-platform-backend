package be.kdg.integration5.adapters.in.web;


import be.kdg.integration5.adapters.in.web.dto.PlayerProfileDto;
import be.kdg.integration5.domain.PlayerProfile;
import be.kdg.integration5.ports.in.GetPlayerProfileUseCase;
import be.kdg.integration5.ports.in.PlayerLoggedInUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/player-profiles")
public class PlayerProfileController {
    private GetPlayerProfileUseCase getPlayerProfileUseCase;
    private PlayerLoggedInUseCase playerLoggedInUseCase;

    public PlayerProfileController(GetPlayerProfileUseCase getPlayerProfileUseCase, PlayerLoggedInUseCase playerLoggedInUseCase) {
        this.getPlayerProfileUseCase = getPlayerProfileUseCase;
        this.playerLoggedInUseCase = playerLoggedInUseCase;
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<PlayerProfileDto> getGames(@AuthenticationPrincipal Jwt token) {
        UUID userId = UUID.fromString((String) token.getClaims().get("sub"));
        PlayerProfile playerProfile = getPlayerProfileUseCase.getPlayerProfileById(userId);
        return new ResponseEntity<>(
                new PlayerProfileDto(
                        playerProfile.getId(),
                        playerProfile.getUserName(),
                        playerProfile.getFirstName(),
                        playerProfile.getLastName(),
                        playerProfile.getBirthDate(),
                        playerProfile.getGender(),
                        playerProfile.getLocation()

                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/me/logins")
    @PreAuthorize("hasAuthority('player')")
    public void recordLogin(@AuthenticationPrincipal Jwt token) {
        UUID userId = UUID.fromString((String) token.getClaims().get("sub"));
        playerLoggedInUseCase.playerLoggedIn(userId);


    }
}
