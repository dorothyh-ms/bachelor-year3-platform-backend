package be.kdg.integration5.playerstatistics.adapters.in.web;


import be.kdg.integration5.common.domain.PlayerGameClassification;
import be.kdg.integration5.playerstatistics.ports.in.GetPlayerClassificationsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/player-classifications")
public class PlayerGameClassificationsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerGameClassificationsController.class);

    private final GetPlayerClassificationsUseCase playerClassificationsUseCase;

    public PlayerGameClassificationsController(GetPlayerClassificationsUseCase playerClassificationsUseCase) {
        this.playerClassificationsUseCase = playerClassificationsUseCase;
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<PlayerGameClassification>> getGameClassificationsOfPlayer(@AuthenticationPrincipal Jwt token) {
        LOGGER.info("PlayerGameClassificationsController is running getGameClassificationsOfPlayer");
        UUID userId = UUID.fromString((String) token.getClaims().get("sub"));
        List<PlayerGameClassification> playerGameClassificationList =playerClassificationsUseCase.getPlayerGameClassifications(userId);
        return new ResponseEntity<>(playerGameClassificationList, HttpStatus.OK);
    }
}
