package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.*;
import be.kdg.integration5.platform.exceptions.*;
import be.kdg.integration5.platform.ports.in.PlayerAcceptsInviteUseCase;
import be.kdg.integration5.platform.ports.out.InviteLoadPort;
import be.kdg.integration5.platform.ports.out.InviteUpdatePort;
import be.kdg.integration5.platform.ports.out.LobbyJoinedPort;
import be.kdg.integration5.platform.ports.out.LobbyLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultPlayerAcceptsInviteUseCase implements PlayerAcceptsInviteUseCase {
    private final InviteLoadPort inviteLoadPort;
    private final LobbyLoadPort lobbyLoadPort;
    private final List<LobbyJoinedPort> lobbyJoinedPorts;
    private final InviteUpdatePort inviteUpdatePort;

    public DefaultPlayerAcceptsInviteUseCase(InviteLoadPort inviteLoadPort, LobbyLoadPort lobbyLoadPort, List<LobbyJoinedPort> lobbyJoinedPorts, InviteUpdatePort inviteUpdatePort) {
        this.inviteLoadPort = inviteLoadPort;
        this.lobbyLoadPort = lobbyLoadPort;
        this.lobbyJoinedPorts = lobbyJoinedPorts;
        this.inviteUpdatePort = inviteUpdatePort;
    }

    public Lobby playerAnswersInvite(UUID inviteId, UUID userId, String action) {
        InviteAction decision = InviteAction.fromString(action);
        if (decision.equals(InviteAction.ACCEPT)) {
            return playerAcceptsInvite(inviteId, userId);
        } else if (decision.equals(InviteAction.DECLINE)) {
            return playerDeclinesInvite(inviteId, userId);
        } else {
            throw new InvalidInviteActionException("Invalid action: " + action);
        }
    }

    private Lobby playerAcceptsInvite(UUID inviteId, UUID userId) {
        Optional<Invite> optionalInvite = inviteLoadPort.loadInvite(inviteId);
        Invite invite;
        if (optionalInvite.isEmpty()) {
            throw new InvalidInviteException("Invite not found");
        }
        invite = optionalInvite.get();
        if (!invite.isRecipient(userId)) {
            throw new InvalidInviteUserException("User is not the recipient of the invite");
        }
        Optional<Lobby> lobbyOptional = lobbyLoadPort.loadLobby(invite.getLobby().getId());
        Lobby lobby;
        if (lobbyOptional.isEmpty()) {
            throw new LobbyNotFoundException("Lobby not found");
        }
        lobby = lobbyOptional.get();
        if (invite.isExpired()) {
            throw new ExpiredInviteException("Invite has expired");
        } else if (invite.isAccepted()) {
            throw new InvalidInviteException("Invite has already been accepted");
        } else if (invite.isDenied()) {
            throw new InvalidInviteException("Invite has already been declined");
        }

        boolean playerWasAdmitted = lobby.admitPlayer(invite.getRecipient());
        if (!playerWasAdmitted) {
            throw new PlayerNotAdmittedToLobbyException("Player could not be admitted to the requested lobby");
        }

        invite.accepted();
        inviteUpdatePort.updateInvite(invite);
        lobbyJoinedPorts.forEach(lobbyJoinedPort -> lobbyJoinedPort.lobbyJoined(lobby));
        return lobby;

    }

    private Lobby playerDeclinesInvite(UUID inviteId, UUID userId) {
//            TODO: implement decline invite
        return null;
    }
}
