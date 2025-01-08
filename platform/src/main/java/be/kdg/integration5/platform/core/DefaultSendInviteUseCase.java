package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.exceptions.LobbyNotFoundException;
import be.kdg.integration5.platform.exceptions.PlayerNotFoundException;
import be.kdg.integration5.platform.ports.in.GetPlayerUseCase;
import be.kdg.integration5.platform.ports.in.PlayerCreatesInviteUseCase;
import be.kdg.integration5.platform.ports.out.InviteCreatePort;
import be.kdg.integration5.platform.ports.out.LobbyLoadPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultSendInviteUseCase implements PlayerCreatesInviteUseCase {
    private final InviteCreatePort inviteCreatePort;
    private final GetPlayerUseCase getPlayerUseCase;
    private final LobbyLoadPort lobbyLoadPort;

    public DefaultSendInviteUseCase(InviteCreatePort inviteCreatePort, GetPlayerUseCase getPlayerUseCase, LobbyLoadPort lobbyLoadPort) {
        this.inviteCreatePort = inviteCreatePort;
        this.getPlayerUseCase = getPlayerUseCase;
        this.lobbyLoadPort = lobbyLoadPort;
    }

    @Override
    public Invite createInvite(UUID sender, UUID recipient, UUID lobbyId) {
        Optional<Player> senderPlayer = getPlayerUseCase.getPlayerById(sender);
        if (senderPlayer.isEmpty()) {
            throw new PlayerNotFoundException("Sender not found");
        }
        Optional<Player> recipientPlayer = getPlayerUseCase.getPlayerById(recipient);
        if (recipientPlayer.isEmpty()) {
            throw new PlayerNotFoundException("Recipient not found");
        }
        Lobby lobby = lobbyLoadPort.loadLobby(lobbyId).orElseThrow(() -> new LobbyNotFoundException("Lobby not found"));
        if (!lobby.isOpen()){
            throw new LobbyClosedException("Cannot create invitation for closed lobby");
        }
        Invite invite = senderPlayer.get().createInvite(recipientPlayer.get(), lobby);
        inviteCreatePort.createInvite(invite);
        return invite;
    }
}
