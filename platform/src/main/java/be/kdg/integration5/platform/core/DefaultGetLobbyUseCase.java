package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.ports.in.GetLobbyUseCase;
import be.kdg.integration5.platform.ports.out.LobbyLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGetLobbyUseCase implements GetLobbyUseCase {
    private final LobbyLoadPort lobbyLoadPort;

    public DefaultGetLobbyUseCase(LobbyLoadPort lobbyLoadPort) {
        this.lobbyLoadPort = lobbyLoadPort;
    }

    @Override
    public List<Lobby> getLobbies() {
        return lobbyLoadPort.loadLobbiesByActiveStatus();
    }
}
