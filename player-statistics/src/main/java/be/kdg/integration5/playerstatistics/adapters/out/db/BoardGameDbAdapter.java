package be.kdg.integration5.playerstatistics.adapters.out.db;

import be.kdg.integration5.playerstatistics.adapters.out.db.entities.BoardGameJpaEntity;
import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.BoardGameRepository;
import be.kdg.integration5.playerstatistics.domain.BoardGame;
import be.kdg.integration5.playerstatistics.ports.out.BoardGameLoadPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BoardGameDbAdapter implements BoardGameLoadPort {
    private final BoardGameRepository boardGameRepository;

    public BoardGameDbAdapter(BoardGameRepository boardGameRepository) {
        this.boardGameRepository = boardGameRepository;
    }

    @Override
    public Optional<BoardGame> loadBoardGameByName(String name) {
        Optional<BoardGameJpaEntity> boardGameJpaEntityOptional = boardGameRepository.findFirstByGameNameEqualsIgnoreCase(name);
        if (boardGameJpaEntityOptional.isPresent()){
            BoardGameJpaEntity boardGameJpaEntity = boardGameJpaEntityOptional.get();
            return Optional.of(new BoardGame(
                    boardGameJpaEntity.getGameId(),
                    boardGameJpaEntity.getGameName(),
                    boardGameJpaEntity.getGenre(),
                    boardGameJpaEntity.getDifficultyLevel(),
                    boardGameJpaEntity.getPrice(),
                    boardGameJpaEntity.getDescription()
            ));
        }
        return Optional.empty();
    }
}
