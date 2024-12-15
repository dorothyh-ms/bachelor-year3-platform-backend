package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.ports.in.GetOpenLobbiesUseCase;
import be.kdg.integration5.platform.ports.out.LobbyLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGetOpenLobbiesUseCase implements GetOpenLobbiesUseCase {
    private final LobbyLoadPort lobbyLoadPort;

    public DefaultGetOpenLobbiesUseCase(LobbyLoadPort lobbyLoadPort) {
        this.lobbyLoadPort = lobbyLoadPort;
    }

    @Override
    public List<Lobby> getOpenLobbies() {
        return lobbyLoadPort.loadActiveLobbies();
    }


}
