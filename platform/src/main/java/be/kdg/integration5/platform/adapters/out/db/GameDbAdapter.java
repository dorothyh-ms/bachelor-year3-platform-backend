package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.GameRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.GameSubmissionRepository;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.ports.out.GameLoadPort;
import be.kdg.integration5.platform.ports.out.GameSavePort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GameDbAdapter implements GameLoadPort, GameSavePort {

    private final GameRepository gameRepository;
    private final GameSubmissionRepository gameSubmissionRepository;

    public GameDbAdapter(GameRepository gameRepository, GameSubmissionRepository gameSubmissionRepository) {
        this.gameRepository = gameRepository;
        this.gameSubmissionRepository = gameSubmissionRepository;
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
    public void saveGameSubmission(GameSubmission gameSubmission) {
        gameSubmissionRepository.save(GameMapper.toGameSubmissionJpaEntity(gameSubmission));
    }
}
