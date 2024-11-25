package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.exceptions.PlayerNotAdmittedToLobbyException;
import be.kdg.integration5.platform.ports.in.PlayerJoinsLobbyUseCase;
import be.kdg.integration5.platform.ports.in.commands.JoinLobbyCommand;
import be.kdg.integration5.platform.ports.out.LobbyLoadPort;
import be.kdg.integration5.platform.ports.out.LobbyJoinedPort;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultPlayerJoinsLobbyUseCase implements PlayerJoinsLobbyUseCase {

    private LobbyLoadPort lobbyLoadPort;
    private PlayerLoadPort playerLoadPort;
    private List<LobbyJoinedPort> lobbyJoinedPorts;

    public DefaultPlayerJoinsLobbyUseCase(LobbyLoadPort lobbyLoadPort, PlayerLoadPort playerLoadPort, List<LobbyJoinedPort> lobbyJoinedPorts) {
        this.lobbyLoadPort = lobbyLoadPort;
        this.playerLoadPort = playerLoadPort;
        this.lobbyJoinedPorts = lobbyJoinedPorts;
    }

    @Override
    public Lobby joinLobby(JoinLobbyCommand command) {
        Optional<Lobby> lobbyOptional = lobbyLoadPort.loadLobby(command.lobbyId());
        Optional<Player> playerOptional = playerLoadPort.loadPlayerById(command.joinPlayerId());
        if (lobbyOptional.isPresent() && playerOptional.isPresent()) {
            Lobby lobby = lobbyOptional.get();
            Player player = playerOptional.get();
            boolean playerWasAdmitted = lobby.admitPlayer(player);
            if (!playerWasAdmitted) {
                throw new PlayerNotAdmittedToLobbyException("Player could not be admitted to the requested lobby");
            }
            lobbyJoinedPorts.forEach(lobbyJoinedPort -> lobbyJoinedPort.lobbyJoined(lobby));
            return lobby;
        }
        return null;
    }
}
