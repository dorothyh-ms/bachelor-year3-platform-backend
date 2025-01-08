package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.platform.adapters.in.web.dtos.FavoriteDto;
import be.kdg.integration5.platform.ports.in.ManageFavoritesUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final ManageFavoritesUseCase manageFavoritesUseCase;

    public FavoriteController(@Qualifier("manageFavoritesAdapter") ManageFavoritesUseCase manageFavoritesUseCase) {
        this.manageFavoritesUseCase = manageFavoritesUseCase;
    }

    @PostMapping
    public ResponseEntity<?> addToFavorites(@RequestBody Map<String, String> payload, @AuthenticationPrincipal Jwt token) {
        String gameId = payload.get("gameId");
        if (gameId == null || gameId.isEmpty()) {
            return ResponseEntity.badRequest().body("Game ID is required");
        }

        String playerId = token.getSubject();
        manageFavoritesUseCase.addToFavorites(playerId, gameId);
        return ResponseEntity.ok("Game successfully added to favorites");
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<?> removeFromFavorites(@PathVariable UUID favoriteId) {
        if (favoriteId == null) {
            return ResponseEntity.badRequest().body("Favorite ID is required");
        }

        manageFavoritesUseCase.removeFromFavorites(favoriteId.toString());
        return ResponseEntity.ok("Game successfully removed from favorites");
    }

    @GetMapping
    public ResponseEntity<List<FavoriteDto>> getFavorites(@AuthenticationPrincipal Jwt token) {
        String playerId = token.getSubject();
        List<FavoriteDto> favorites = manageFavoritesUseCase.getFavorites(playerId);
        return ResponseEntity.ok(favorites);
    }
}
