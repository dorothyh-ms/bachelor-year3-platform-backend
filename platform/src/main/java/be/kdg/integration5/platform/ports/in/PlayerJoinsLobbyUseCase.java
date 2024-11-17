package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.ports.in.commands.JoinLobbyCommand;

public interface PlayerJoinsLobbyUseCase {

    public Lobby joinLobby(JoinLobbyCommand command);
}
