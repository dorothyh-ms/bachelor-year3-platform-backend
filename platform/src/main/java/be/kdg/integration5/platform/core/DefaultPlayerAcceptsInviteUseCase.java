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
    private static final Logger log = LoggerFactory.getLogger(DefaultPlayerAcceptsInviteUseCase.class);
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
            log.debug("Invalid action");
            throw new InvalidInviteActionException("Invalid action: " + action);
        }
    }

    private Lobby playerAcceptsInvite(UUID inviteId, UUID userId) {
        Optional<Invite> optionalInvite = inviteLoadPort.loadInvite(inviteId);
        Invite invite;
        if (optionalInvite.isEmpty()) {
            log.debug("Invite not found");
            throw new InvalidInviteException("Invite not found");
        } else {
            log.debug("Invite found");
            invite = optionalInvite.get();
        }
        if (!invite.getRecipient().getPlayerId().equals(userId)) {
            log.debug("User not recipient of invite");
            throw new InvalidInviteUserException("User is not the recipient of the invite");
        }

        Optional<Lobby> lobbyOptional = lobbyLoadPort.loadLobby(invite.getLobby().getId());
        Lobby lobby;
        if (lobbyOptional.isEmpty()) {
            log.debug("Lobby not found");
            throw new InvalidLobbyException("Lobby not found");
        } else {
            log.info("Lobby found");
            lobby = lobbyOptional.get();
        }

        if (lobby.getStatus().equals(LobbyStatus.CLOSED)) {
            log.debug("Lobby is closed");
            throw new InvalidLobbyException("Lobby is closed");
        }

        if (invite.isOpen()) {
            log.info("Invite accepted");
            invite.accepted();
            lobby.admitPlayer(invite.getRecipient());
            inviteUpdatePort.updateInvite(invite);
            lobbyJoinedPorts.forEach(lobbyJoinedPort -> lobbyJoinedPort.lobbyJoined(lobby));
        } else if (invite.isExpired()) {
            log.debug("Invite expired");
            throw new ExpiredInviteException("Invite has expired");
        } else if (invite.isAccepted()) {
            log.debug("Invite already accepted");
            throw new InvalidInviteException("Invite has already been accepted");
        } else if (invite.isDenied()) {
            log.debug("Invite already declined");
            throw new InvalidInviteException("Invite has already been declined");
        }
        return lobby;
    }

    private Lobby playerDeclinesInvite(UUID inviteId, UUID userId) {
//            TODO: implement decline invite
        return null;
    }
}
