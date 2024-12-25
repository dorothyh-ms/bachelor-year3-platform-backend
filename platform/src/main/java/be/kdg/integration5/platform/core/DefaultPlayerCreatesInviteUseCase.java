package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.in.PlayerCreatesInviteUseCase;
import be.kdg.integration5.platform.ports.out.InviteUpdatePort;
import be.kdg.integration5.platform.ports.out.LobbyLoadPort;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import be.kdg.integration5.platform.ports.out.LobbyJoinedPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Qualifier("defaultPlayerCreatesInviteUseCase")
public class DefaultPlayerCreatesInviteUseCase implements PlayerCreatesInviteUseCase {
    private final InviteUpdatePort inviteUpdatePort;
    private final PlayerLoadPort playerLoadPort;
    private final LobbyLoadPort lobbyLoadPort;
    private final LobbyJoinedPort lobbyJoinedPort;

    public DefaultPlayerCreatesInviteUseCase(InviteUpdatePort inviteUpdatePort,
                                             PlayerLoadPort playerLoadPort,
                                             LobbyLoadPort lobbyLoadPort,
                                             @Qualifier("lobbyDbAdapter") LobbyJoinedPort lobbyJoinedPort) {
        this.inviteUpdatePort = inviteUpdatePort;
        this.playerLoadPort = playerLoadPort;
        this.lobbyLoadPort = lobbyLoadPort;
        this.lobbyJoinedPort = lobbyJoinedPort;
    }

    @Override
    public Invite createInvite(UUID sender, UUID recipient, UUID lobbyId) {
        Player senderPlayer = playerLoadPort.loadPlayerById(sender)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Player recipientPlayer = playerLoadPort.loadPlayerById(recipient)
                .orElseThrow(() -> new RuntimeException("Recipient not found"));
        Lobby lobby = lobbyLoadPort.loadLobby(lobbyId)
                .orElseThrow(() -> new RuntimeException("Lobby not found"));

        // Notify the lobby was joined
        lobbyJoinedPort.lobbyJoined(lobby);

        Invite invite = new Invite(UUID.randomUUID(), senderPlayer, recipientPlayer, lobby, LocalDateTime.now());
        inviteUpdatePort.updateInvite(invite);
        return invite;
    }
}
