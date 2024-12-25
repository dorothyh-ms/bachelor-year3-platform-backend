package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.platform.adapters.in.web.dtos.FavoriteDto;
import be.kdg.integration5.platform.ports.in.ManageFavoritesUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final ManageFavoritesUseCase manageFavoritesUseCase;

    public FavoriteController(@Qualifier("manageFavoritesAdapter") ManageFavoritesUseCase manageFavoritesUseCase) {
        this.manageFavoritesUseCase = manageFavoritesUseCase;
    }

    @PostMapping("/{playerId}/{gameId}")
    public ResponseEntity<?> addToFavorites(@PathVariable String playerId, @PathVariable String gameId) {
        if (playerId == null || playerId.isEmpty()) {
            return ResponseEntity.badRequest().body("Player ID is required");
        }
        if (gameId == null || gameId.isEmpty()) {
            return ResponseEntity.badRequest().body("Game ID is required");
        }

        manageFavoritesUseCase.addToFavorites(playerId, gameId);
        return ResponseEntity.ok("Game successfully added to favorites");
    }

    @DeleteMapping("/{playerId}/{gameId}")
    public ResponseEntity<?> removeFromFavorites(@PathVariable String playerId, @PathVariable String gameId) {
        if (playerId == null || playerId.isEmpty()) {
            return ResponseEntity.badRequest().body("Player ID is required");
        }
        if (gameId == null || gameId.isEmpty()) {
            return ResponseEntity.badRequest().body("Game ID is required");
        }

        manageFavoritesUseCase.removeFromFavorites(playerId, gameId);
        return ResponseEntity.ok("Game successfully removed from favorites");
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<List<FavoriteDto>> getFavorites(@PathVariable String playerId) {
        if (playerId == null || playerId.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<FavoriteDto> favorites = manageFavoritesUseCase.getFavorites(playerId);
        return ResponseEntity.ok(favorites);
    }
}
