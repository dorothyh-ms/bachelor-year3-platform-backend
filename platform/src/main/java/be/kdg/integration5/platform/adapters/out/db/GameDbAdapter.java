package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.GameRepository;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.ports.out.GameLoadPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GameDbAdapter implements GameLoadPort {

    private final GameRepository gameRepository;

    public GameDbAdapter(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> loadGames() {
        return gameRepository.findAll().stream().map(GameMapper::toGame).toList();
    }

    @Override
    public Optional<Game> loadGameById(UUID gameId) {
        Optional<GameJpaEntity> gameJpaEntityOptional = gameRepository.findById(gameId);
        if (gameJpaEntityOptional.isPresent()) {
            GameJpaEntity gameJpaEntity = gameJpaEntityOptional.get();
            return Optional.of(new Game(
                    gameJpaEntity.getId(),
                    gameJpaEntity.getName()
            ));
        }
        return Optional.empty();
    }
}
