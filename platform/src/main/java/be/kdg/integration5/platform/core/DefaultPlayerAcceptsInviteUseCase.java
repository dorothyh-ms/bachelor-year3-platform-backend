package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.InviteStatus;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.domain.LobbyStatus;
import be.kdg.integration5.platform.exceptions.ExpiredInviteException;
import be.kdg.integration5.platform.exceptions.InvalidInviteException;
import be.kdg.integration5.platform.exceptions.InvalidLobbyException;
import be.kdg.integration5.platform.ports.in.PlayerAcceptsInviteUseCase;
import be.kdg.integration5.platform.ports.out.InviteLoadPort;
import be.kdg.integration5.platform.ports.out.InviteUpdatePort;
import be.kdg.integration5.platform.ports.out.LobbyJoinedPort;
import be.kdg.integration5.platform.ports.out.LobbyLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultPlayerAcceptsInviteUseCase implements PlayerAcceptsInviteUseCase {
    private static final Logger log = LoggerFactory.getLogger(DefaultPlayerAcceptsInviteUseCase.class);
    private final InviteLoadPort inviteLoadPort;
    private final LobbyLoadPort LobbyLoadPort;
    private final LobbyJoinedPort lobbyJoinedPort;
    private final InviteUpdatePort inviteUpdatePort;

    public DefaultPlayerAcceptsInviteUseCase(InviteLoadPort inviteLoadPort, be.kdg.integration5.platform.ports.out.LobbyLoadPort lobbyLoadPort, LobbyJoinedPort lobbyJoinedPort, InviteUpdatePort inviteUpdatePort) {
        this.inviteLoadPort = inviteLoadPort;
        LobbyLoadPort = lobbyLoadPort;
        this.lobbyJoinedPort = lobbyJoinedPort;
        this.inviteUpdatePort = inviteUpdatePort;
    }

    public Lobby playerAcceptsInvite(UUID inviteId, UUID userId) {
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
            throw new InvalidInviteException("User is not the recipient of the invite");
        }

        Optional<Lobby> lobbyOptional = LobbyLoadPort.loadLobby(invite.getLobby().getId());
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

        if (invite.getInviteStatus().equals(InviteStatus.OPEN)) {
            log.info("Invite accepted");
            invite.accepted();
            lobby.admitPlayer(invite.getRecipient());
            inviteUpdatePort.updateInvite(invite);
            lobbyJoinedPort.lobbyJoined(lobby);
        } else if (invite.getInviteStatus().equals(InviteStatus.EXPIRED)) {
            log.debug("Invite expired");
            throw new ExpiredInviteException("Invite has expired");
        } else if (invite.getInviteStatus().equals(InviteStatus.ACCEPTED)) {
            log.debug("Invite already accepted");
            throw new InvalidInviteException("Invite has already been accepted");
        } else if (invite.getInviteStatus().equals(InviteStatus.DENIED)) {
            log.debug("Invite already declined");
            throw new InvalidInviteException("Invite has already been declined");
        }
        return lobby;
    }
}
