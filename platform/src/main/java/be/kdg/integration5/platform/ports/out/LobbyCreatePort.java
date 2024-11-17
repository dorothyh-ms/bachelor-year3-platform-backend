package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Lobby;

public interface LobbyCreatePort {
    public void lobbyCreated(Lobby lobby);
}
