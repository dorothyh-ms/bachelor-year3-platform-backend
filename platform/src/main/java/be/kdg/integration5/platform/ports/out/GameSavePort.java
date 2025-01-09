package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.GameSubmission;

public interface GameSavePort {
    void SaveGame(Game game);
}
