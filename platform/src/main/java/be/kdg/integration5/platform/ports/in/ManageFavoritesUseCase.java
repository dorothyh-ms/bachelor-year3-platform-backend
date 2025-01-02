package be.kdg.integration5.platform.ports.in;


import be.kdg.integration5.platform.adapters.in.web.dtos.FavoriteDto;
import be.kdg.integration5.platform.domain.Game;
import java.util.List;

public interface ManageFavoritesUseCase {
    void addToFavorites(String playerId, String gameId);
    void removeFromFavorites(String playerId, String gameId);

    List<FavoriteDto> getFavorites(String playerId);
}