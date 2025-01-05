package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.in.web.PlayerController;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPlayerAcceptsInviteUseCase.class);
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

    public Invite playerAnswersInvite(UUID inviteId, UUID userId, String action) {
        InviteAction decision = InviteAction.fromString(action);
        if (decision.equals(InviteAction.ACCEPT)) {
            return playerAcceptsInvite(inviteId, userId);
        }
        return playerDeclinesInvite(inviteId, userId);

    }

    private Invite playerAcceptsInvite(UUID inviteId, UUID userId) {
        LOGGER.info("DefaultPlayerAcceptsInviteUseCase is running playerAcceptsInvite");
        Optional<Invite> optionalInvite = inviteLoadPort.loadInvite(inviteId);
        Invite invite;
        if (optionalInvite.isEmpty()) {
            LOGGER.info("Invite not found");
            throw new InvalidInviteException("Invite not found");
        }
        invite = optionalInvite.get();
        if (!invite.isRecipient(userId)) {
            LOGGER.info("Player cannot accept this invite");
            throw new InvalidInviteUserException("Player is not the recipient of the invite");
        }
        Optional<Lobby> lobbyOptional = lobbyLoadPort.loadLobby(invite.getLobby().getId());
        Lobby lobby;
        if (lobbyOptional.isEmpty()) {
            LOGGER.info("Lobby not found");
            throw new LobbyNotFoundException("Lobby not found");
        }
        lobby = lobbyOptional.get();
        if (invite.isExpired()) {
            LOGGER.info("Invite expired");
            throw new ExpiredInviteException("Invite has expired");
        } else if (invite.isAccepted()) {
            LOGGER.info("Invite already accepted");
            throw new InvalidInviteException("Invite has already been accepted");
        } else if (invite.isDenied()) {
            LOGGER.info("Invite already declined");
            throw new InvalidInviteException("Invite has already been declined");
        }

        boolean playerWasAdmitted = lobby.admitPlayer(invite.getRecipient());
        if (!playerWasAdmitted) {
            LOGGER.info("Player not admitted");
            throw new PlayerNotAdmittedToLobbyException("Player could not be admitted to the requested lobby");
        }

        LOGGER.info("Invite accepted successfully");
        invite.accepted();
        invite.setLobby(lobby);
        inviteUpdatePort.updateInvite(invite);
        lobbyJoinedPorts.forEach(lobbyJoinedPort -> lobbyJoinedPort.lobbyJoined(lobby));
        return invite;

    }

    private Invite playerDeclinesInvite(UUID inviteId, UUID userId) {
        LOGGER.info("DefaultPlayerAcceptsInviteUseCase is running playerAcceptsInvite");
        Optional<Invite> optionalInvite = inviteLoadPort.loadInvite(inviteId);
        Invite invite;
        if (optionalInvite.isEmpty()) {
            LOGGER.info("Invite not found");
            throw new InvalidInviteException("Invite not found");
        }
        invite = optionalInvite.get();
        if (!invite.isRecipient(userId)) {
            LOGGER.info("Player cannot decline own invite");
            throw new InvalidInviteUserException("Player is not the recipient of the invite");
        }
        Optional<Lobby> lobbyOptional = lobbyLoadPort.loadLobby(invite.getLobby().getId());
        Lobby lobby;
        if (lobbyOptional.isEmpty()) {
            LOGGER.info("Lobby not found");
            throw new LobbyNotFoundException("Lobby not found");
        }
        lobby = lobbyOptional.get();
        if (invite.isExpired()) {
            LOGGER.info("Invite expired");
            throw new ExpiredInviteException("Invite has expired");
        } else if (invite.isAccepted()) {
            LOGGER.info("Invite already accepted");
            throw new InvalidInviteException("Invite has already been accepted");
        } else if (invite.isDenied()) {
            LOGGER.info("Invite already declined");
            throw new InvalidInviteException("Invite has already been declined");
        }

        LOGGER.info("Invite declined successfully");
        invite.denied();
        invite.setLobby(lobby);
        inviteUpdatePort.updateInvite(invite);
        return invite;
    }
}
