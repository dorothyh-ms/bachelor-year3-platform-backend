package be.kdg.integration5.playerstatistics.ports.out;

import be.kdg.integration5.playerstatistics.domain.BoardGame;

import java.util.Optional;

public interface BoardGameLoadPort {
    public Optional<BoardGame> loadBoardGameByName(String name);
}
