package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.in.web.dtos.FavoriteDto;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.ports.in.ManageFavoritesUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("defaultManageFavoritesUseCase")
public class DefaultManageFavoritesUseCase implements ManageFavoritesUseCase {

    private final ManageFavoritesUseCase manageFavoritesAdapter;

    public DefaultManageFavoritesUseCase(ManageFavoritesUseCase manageFavoritesAdapter) {
        this.manageFavoritesAdapter = manageFavoritesAdapter;
    }

    @Override
    public void addToFavorites(String playerId, String gameId) {

        manageFavoritesAdapter.addToFavorites(playerId, gameId);
    }

    @Override
    public void removeFromFavorites(String playerId, String gameId) {

        manageFavoritesAdapter.removeFromFavorites(playerId, gameId);
    }

    @Override
    public List<FavoriteDto> getFavorites(String playerId) {

        return manageFavoritesAdapter.getFavorites(playerId);
    }
}
