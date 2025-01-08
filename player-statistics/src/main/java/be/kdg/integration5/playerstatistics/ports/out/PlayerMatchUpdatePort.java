package be.kdg.integration5.playerstatistics.ports.out;

import be.kdg.integration5.playerstatistics.domain.PlayerMatch;

public interface PlayerMatchUpdatePort {
    public void updatePlayerMatch(PlayerMatch pm);
}
