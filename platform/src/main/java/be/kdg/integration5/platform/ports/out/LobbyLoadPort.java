package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Lobby;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LobbyLoadPort {

    List<Lobby> loadActiveLobbies();


    public Optional<Lobby> loadLobby(UUID lobbyId);
}
