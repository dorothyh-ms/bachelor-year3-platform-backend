package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.ports.in.PlayerJoinsLobbyUseCase;
import be.kdg.integration5.platform.ports.in.commands.JoinLobbyCommand;
import org.springframework.stereotype.Service;

@Service
public class DefaultPlayerJoinsLobbyUseCase implements PlayerJoinsLobbyUseCase {

    @Override
    public void joinLobby(JoinLobbyCommand command) {

    }
}
