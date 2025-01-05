package be.kdg.integration5.playerstatistics.ports.out;

import be.kdg.integration5.playerstatistics.domain.PlayerLogin;

public interface PlayerLoginCreatePort {
    public void playerLoggedIn(PlayerLogin playerLogin);
}
