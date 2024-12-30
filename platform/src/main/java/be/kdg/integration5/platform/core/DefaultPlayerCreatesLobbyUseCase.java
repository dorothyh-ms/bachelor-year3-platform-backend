package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.exceptions.GameNotFoundException;
import be.kdg.integration5.platform.exceptions.PlayerNotFoundException;
import be.kdg.integration5.platform.ports.in.PlayerCreatesLobbyUseCase;
import be.kdg.integration5.platform.ports.in.commands.CreateLobbyCommand;
import be.kdg.integration5.platform.ports.out.GameLoadPort;
import be.kdg.integration5.platform.ports.out.LobbyCreatePort;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultPlayerCreatesLobbyUseCase implements PlayerCreatesLobbyUseCase {

    private final LobbyCreatePort lobbyCreatePort;
    private final GameLoadPort gameLoadPort;
    private final PlayerLoadPort playerLoadPort;


    public DefaultPlayerCreatesLobbyUseCase(LobbyCreatePort lobbyCreatePort, GameLoadPort gameLoadPort, PlayerLoadPort playerLoadPort) {
        this.lobbyCreatePort = lobbyCreatePort;
        this.gameLoadPort = gameLoadPort;
        this.playerLoadPort = playerLoadPort;
    }

    @Override
    public Lobby createLobby(CreateLobbyCommand command) {
        Optional<Game> gameOptional = gameLoadPort.loadGameById(command.gameId());
        Optional<Player> playerOptional = playerLoadPort.loadPlayerById(command.initiatingPlayerId());
        if (gameOptional.isEmpty()) {
            throw new GameNotFoundException(String.format("Game with id %s does not exist", command.gameId().toString()));
        }
        if (playerOptional.isEmpty()) {
            throw new PlayerNotFoundException(String.format("Player with id %s does not exist", command.initiatingPlayerId().toString()));
        }

        Lobby lobby = new Lobby(
                UUID.randomUUID(),
                gameOptional.get(),
                playerOptional.get()
        );
        lobbyCreatePort.lobbyCreated(lobby);
        return lobby;

    }
}
