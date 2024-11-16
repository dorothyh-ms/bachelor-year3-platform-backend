package be.kdg.integration5.platform.adapters.out.db.mappers;

import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.domain.Player;

public class UserMapper {

    // Convert PlayerJpaEntity to Player
    public static Player toUser(PlayerJpaEntity playerJpaEntity) {
        if (playerJpaEntity == null) {
            return null;
        }

        Player player = new Player();
        player.setPlayerId(playerJpaEntity.getPlayerId());
        player.setUsername(playerJpaEntity.getUsername());
        player.setLastName(playerJpaEntity.getLastName());
        player.setFirstName(playerJpaEntity.getFirstName());
        player.setAge(playerJpaEntity.getAge());
        player.setGender(playerJpaEntity.getGender());
        player.setLocation(playerJpaEntity.getLocation());

        return player;
    }

    // Convert Player to PlayerJpaEntity
    public static PlayerJpaEntity toUserEntity(Player player) {
        if (player == null) {
            return null;
        }

        PlayerJpaEntity playerJpaEntity = new PlayerJpaEntity();
        playerJpaEntity.setPlayerId(player.getPlayerId());
        playerJpaEntity.setUsername(player.getUsername());
        playerJpaEntity.setLastName(player.getLastName());
        playerJpaEntity.setFirstName(player.getFirstName());
        playerJpaEntity.setAge(player.getAge());
        playerJpaEntity.setGender(player.getGender());
        playerJpaEntity.setLocation(player.getLocation());

        return playerJpaEntity;
    }
}

