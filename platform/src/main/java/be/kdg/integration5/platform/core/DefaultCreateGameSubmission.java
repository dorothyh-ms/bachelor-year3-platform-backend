package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.in.web.GameSubmissionController;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.util.TemporaryFileManager;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.domain.SubmissionState;
import be.kdg.integration5.platform.exceptions.GameAlreadyExistsException;
import be.kdg.integration5.platform.exceptions.InvalidGameURLException;
import be.kdg.integration5.platform.ports.in.CreateGameSubmissionUseCase;
import be.kdg.integration5.platform.ports.in.commands.CreateGameSubmissionCommand;
import be.kdg.integration5.platform.ports.out.GameLoadPort;
import be.kdg.integration5.platform.ports.out.GameSavePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultCreateGameSubmission implements CreateGameSubmissionUseCase {
    private final GameLoadPort gameLoadPort;
    private final GameSavePort gameSavePort;

    public DefaultCreateGameSubmission(GameLoadPort gameLoadPort, GameSavePort gameSavePort) {
        this.gameLoadPort = gameLoadPort;
        this.gameSavePort = gameSavePort;
    }

    @Override
    public GameSubmission createGameSubmission(CreateGameSubmissionCommand gameSubmissionCommand) {
        List<Game> games = gameLoadPort.loadGames();
        TemporaryFileManager fileManager = new TemporaryFileManager();
        String rules = "";
        try {
            rules = fileManager.saveFile(gameSubmissionCommand.file());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GameSubmission gameSubmission = GameMapper.toGameSubmission(gameSubmissionCommand, rules, UUID.randomUUID(), SubmissionState.IN_PROGRESS);
        if (games.stream().anyMatch(game -> game.getName().equals(gameSubmission.getName()))){
            fileManager.deleteFile(rules);
            throw new GameAlreadyExistsException("This game already exists, if this is your own game, rename it");
        }
        if (!gameSubmission.isValidUrl()){
            fileManager.deleteFile(rules);
            throw new InvalidGameURLException("The given URL is not valid.");
        }
        gameSavePort.saveGameSubmission(gameSubmission);
        return gameSubmission;
    }
}
