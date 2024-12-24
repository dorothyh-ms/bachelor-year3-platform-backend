package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.domain.SubmissionState;
import be.kdg.integration5.platform.ports.in.CreateGameSubmissionUseCase;
import be.kdg.integration5.platform.ports.in.commands.CreateGameSubmissionCommand;
import be.kdg.integration5.platform.ports.out.GameLoadPort;
import be.kdg.integration5.platform.ports.out.GameSavePort;
import org.springframework.stereotype.Service;

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
        GameSubmission gameSubmission = GameMapper.toGameSubmission(gameSubmissionCommand, UUID.randomUUID(), SubmissionState.IN_PROGRESS);
        gameSavePort.saveGameSubmission(gameSubmission);
        return gameSubmission;
    }
}
