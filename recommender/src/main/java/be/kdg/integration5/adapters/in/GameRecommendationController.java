package be.kdg.integration5.adapters.in;


import be.kdg.integration5.adapters.in.dtos.GameDto;
import be.kdg.integration5.adapters.out.GameRecommendation;
import be.kdg.integration5.domain.Game;
import be.kdg.integration5.ports.in.RecommendGamesForPlayerUseCase;
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
@RequestMapping("/game-recommendations")
public class GameRecommendationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameRecommendationController.class);
    private final RecommendGamesForPlayerUseCase recommendGamesForPlayerUseCase;

    public GameRecommendationController(RecommendGamesForPlayerUseCase recommendGamesForPlayerUseCase) {
        this.recommendGamesForPlayerUseCase = recommendGamesForPlayerUseCase;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<GameDto>> getGames(@AuthenticationPrincipal Jwt token) {
        LOGGER.info("LobbyController is running LobbyController ");
        UUID userId = UUID.fromString((String) token.getClaims().get("sub") );
        List<Game> games = recommendGamesForPlayerUseCase.recommendGamesForPlayer(userId);
        return new ResponseEntity<>(
                games.stream()
                        .map(game -> new GameDto(game.getId(), game.getName(), game.getDifficulty(), game.getDescription()))
                        .toList()
                , HttpStatus.OK);
    }


}
