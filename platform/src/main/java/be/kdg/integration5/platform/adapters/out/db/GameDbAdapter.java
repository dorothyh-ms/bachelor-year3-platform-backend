package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.GameRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.GameSubmissionRepository;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.domain.SubmissionState;
import be.kdg.integration5.platform.ports.out.GameLoadPort;
import be.kdg.integration5.platform.ports.out.GameSavePort;
import be.kdg.integration5.platform.ports.out.GameSubmissionLoadPort;
import org.springframework.security.core.parameters.P;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GameDbAdapter implements GameLoadPort, GameSavePort {

    private static final Logger log = LoggerFactory.getLogger(GameDbAdapter.class);
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
                    gameJpaEntity.getName(),
                    gameJpaEntity.getGenre(),
                    gameJpaEntity.getDifficultyLevel(),
                    gameJpaEntity.getPrice(),
                    gameJpaEntity.getDescription(),
                    gameJpaEntity.getImage(),
                    gameJpaEntity.getUrl()
            ));
        }
        return Optional.empty();
    }

    @Override
    public void SaveGame(Game game) {
        gameRepository.save(GameMapper.toGameJpaEntity(game));
    }


    @Override
    public Optional<Game> loadGameByName(String name) {
        Optional<GameJpaEntity> gameJpaEntityOptional = gameRepository.findFirstByGameNameEqualsIgnoreCase(name);
        return gameJpaEntityOptional.map(GameMapper::toGame);
    }
}
