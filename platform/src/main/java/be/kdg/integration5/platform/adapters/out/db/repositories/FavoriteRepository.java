package be.kdg.integration5.platform.adapters.out.db.repositories;

import be.kdg.integration5.platform.adapters.in.web.dtos.FavoriteDto;
import be.kdg.integration5.platform.adapters.out.db.entities.FavoriteJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<FavoriteJpaEntity, Long> {
    boolean existsByPlayerAndGame(PlayerJpaEntity player, GameJpaEntity game);
    void deleteByPlayerAndGame(PlayerJpaEntity player, GameJpaEntity game);

    @Query("SELECT new be.kdg.integration5.platform.adapters.in.web.dtos.FavoriteDto(" +
            "f.id, g.gameId, g.gameName, CAST(g.genre AS string), g.image, g.description, g.price, " +
            "CAST(g.difficultyLevel AS string), g.url, f.createdAt) " +
            "FROM FavoriteJpaEntity f " +
            "JOIN f.game g WHERE f.player.playerId = :playerId")
    List<FavoriteDto> findFavoritesByPlayerId(@Param("playerId") UUID playerId);

}
