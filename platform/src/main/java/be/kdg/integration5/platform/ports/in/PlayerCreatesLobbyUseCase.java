package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.ports.in.commands.CreateLobbyCommand;

public interface PlayerCreatesLobbyUseCase {

    public Lobby createLobby(CreateLobbyCommand command);
}
