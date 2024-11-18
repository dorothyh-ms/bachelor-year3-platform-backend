package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.exceptions.InvalidPlayerException;
import be.kdg.integration5.platform.ports.in.GetLobbyUseCase;
import be.kdg.integration5.platform.ports.in.GetPlayerUseCase;
import be.kdg.integration5.platform.ports.in.PlayerCreatesInviteUseCase;
import be.kdg.integration5.platform.ports.out.InviteCreatePort;
import be.kdg.integration5.platform.ports.out.LobbyLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Invite createInvite(UUID sender, String recipient, UUID lobbyId) {
        Optional<Player> senderPlayer = getPlayerUseCase.getPlayerById(sender);
        if (senderPlayer.isEmpty()) {
            throw new InvalidPlayerException("Sender not found");
        }
        Lobby lobby = lobbyLoadPort.loadLobby(lobbyId).orElseThrow(() -> new IllegalArgumentException("Lobby not found"));
        List<Player> recipientPlayer = getPlayerUseCase.getPlayers(recipient);
        Player invitee;
        if (recipientPlayer.isEmpty()) {
            throw new InvalidPlayerException("Recipient not found");
        } else {
            invitee = recipientPlayer.get(0);
        }
        Invite invite = new Invite(UUID.randomUUID(), senderPlayer.get(), invitee, lobby);
        inviteCreatePort.createInvite(invite);
        return invite;
    }
}
