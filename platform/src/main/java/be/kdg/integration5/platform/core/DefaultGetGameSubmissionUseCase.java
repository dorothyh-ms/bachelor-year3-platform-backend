package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.GameSubmission;
import be.kdg.integration5.platform.ports.in.GetGameSubmissionsUseCase;
import be.kdg.integration5.platform.ports.out.GameSubmissionLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGetGameSubmissionUseCase implements GetGameSubmissionsUseCase {
    private GameSubmissionLoadPort gameSubmissionLoadPort;

    public DefaultGetGameSubmissionUseCase(GameSubmissionLoadPort gameSubmissionLoadPort) {
        this.gameSubmissionLoadPort = gameSubmissionLoadPort;
    }

    @Override
    public List<GameSubmission> getGameSubmission() {
        return gameSubmissionLoadPort.loadAllGameSubmissions();
    }
}
