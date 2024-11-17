package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.ports.in.commands.JoinLobbyCommand;

public interface PlayerJoinsLobbyUseCase {

    public void joinLobby(JoinLobbyCommand command);
}
