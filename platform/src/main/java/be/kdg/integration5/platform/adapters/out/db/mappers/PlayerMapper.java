package be.kdg.integration5.platform.adapters.out.db.mappers;

import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.domain.Player;

public class PlayerMapper {

    // Convert PlayerJpaEntity to Player
    public static Player toPlayer(PlayerJpaEntity playerJpaEntity) {
        if (playerJpaEntity == null) {
            return null;
        }

        Player player = new Player();
        player.setPlayerId(playerJpaEntity.getPlayerId());
        player.setUsername(playerJpaEntity.getUsername());

        return player;
    }

    // Convert Player to PlayerJpaEntity
    public static PlayerJpaEntity toPlayerJpaEntity(Player player) {
        if (player == null) {
            return null;
        }

        PlayerJpaEntity playerJpaEntity = new PlayerJpaEntity();
        playerJpaEntity.setPlayerId(player.getPlayerId());
        playerJpaEntity.setUsername(player.getUsername());


        return playerJpaEntity;
    }
}

