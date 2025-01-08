package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.in.web.dtos.FavoriteDto;
import be.kdg.integration5.platform.adapters.out.db.entities.FavoriteJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.repositories.FavoriteRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.GameRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.PlayerRepository;
import be.kdg.integration5.platform.ports.in.ManageFavoritesUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Component("manageFavoritesAdapter")
@Transactional
public class ManageFavoritesAdapter implements ManageFavoritesUseCase {

    private final FavoriteRepository favoriteRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public ManageFavoritesAdapter(FavoriteRepository favoriteRepository,
                                  PlayerRepository playerRepository,
                                  GameRepository gameRepository) {
        this.favoriteRepository = favoriteRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void addToFavorites(String playerId, String gameId) {
        PlayerJpaEntity player = fetchPlayerById(UUID.fromString(playerId));
        GameJpaEntity game = fetchGameById(UUID.fromString(gameId));

        if (!favoriteRepository.existsByPlayerAndGame(player, game)) {
            FavoriteJpaEntity favorite = new FavoriteJpaEntity(player, game);
            favoriteRepository.save(favorite);
        }
    }

    @Override
    public void removeFromFavorites(String favoriteId) {
        FavoriteJpaEntity favorite = fetchFavoriteById(UUID.fromString(favoriteId));
        favoriteRepository.delete(favorite);
    }

    @Override
    public List<FavoriteDto> getFavorites(String playerId) {
        return favoriteRepository.findFavoritesByPlayerId(UUID.fromString(playerId));
    }

    private GameJpaEntity fetchGameById(UUID gameId) {
        return gameRepository.findById(gameId).orElseThrow(() ->
                new IllegalArgumentException("Game with ID " + gameId + " not found"));
    }

    private PlayerJpaEntity fetchPlayerById(UUID playerId) {
        return playerRepository.findById(playerId).orElseThrow(() ->
                new IllegalArgumentException("Player with ID " + playerId + " not found"));
    }

    private FavoriteJpaEntity fetchFavoriteById(UUID favoriteId) {
        return favoriteRepository.findById(favoriteId).orElseThrow(() ->
                new IllegalArgumentException("Favorite with ID " + favoriteId + " not found"));
    }
}
