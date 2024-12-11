package be.kdg.integration5.platform.adapters.out.db.mappers;

import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Player;

public class GameMapper {
    // Convert GameJpaEntity to Game
    public static Game toGame(GameJpaEntity gameJpaEntity) {
        if (gameJpaEntity == null) {
            return null;
        }
        return new Game(
                gameJpaEntity.getId(),
                gameJpaEntity.getName(),
                gameJpaEntity.getGenre(),
                gameJpaEntity.getDifficultyLevel(),
                gameJpaEntity.getPrice(),
                gameJpaEntity.getDescription(),
                gameJpaEntity.getImage(),
                gameJpaEntity.getUrl()
        );
    }

    public static GameJpaEntity toGameJpaEntity(Game game){
        if (game == null) {
            return null;
        }
        return new GameJpaEntity(
                game.getId(),
                game.getName(),
                game.getGenre(),
                game.getDifficultyLevel(),
                game.getPrice(),
                game.getDescription(),
                game.getImage(),
                game.getUrl()
        );

    }
}
