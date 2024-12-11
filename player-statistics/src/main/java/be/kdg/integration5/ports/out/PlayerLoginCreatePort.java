package be.kdg.integration5.ports.out;

import be.kdg.integration5.domain.PlayerLogin;

public interface PlayerLoginCreatePort {
    public void playerLoggedIn(PlayerLogin playerLogin);
}
