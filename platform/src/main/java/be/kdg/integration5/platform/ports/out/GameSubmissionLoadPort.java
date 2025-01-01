package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.GameSubmission;

import java.util.List;
public interface GameSubmissionLoadPort {
    List<GameSubmission> loadAllGameSubmissions();
    List<GameSubmission> loadPendingGameSubmissions();
    List<GameSubmission> loadDeniedGameSubmissions();

}
