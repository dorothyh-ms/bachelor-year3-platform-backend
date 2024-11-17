package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Lobby;

import java.util.Optional;
import java.util.UUID;

public interface LobbyLoadPort {

    public Optional<Lobby> loadLobby(UUID lobbyId);
}
