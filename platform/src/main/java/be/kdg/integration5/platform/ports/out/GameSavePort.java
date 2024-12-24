package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.GameSubmission;

public interface GameSavePort {
    void saveGameSubmission(GameSubmission gameSubmission);
}
