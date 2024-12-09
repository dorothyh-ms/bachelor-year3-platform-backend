package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.ports.in.GetInvitesUseCase;
import be.kdg.integration5.platform.ports.out.InviteLoadPort;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultGetInvitesUseCase implements GetInvitesUseCase {
    private final InviteLoadPort inviteLoadPort;
    private final PlayerLoadPort playerLoadPort;

    public DefaultGetInvitesUseCase(InviteLoadPort inviteLoadPort, PlayerLoadPort playerLoadPort) {
        this.inviteLoadPort = inviteLoadPort;
        this.playerLoadPort = playerLoadPort;
    }

    @Override
    public List<Invite> getInvites(UUID userId) {
        return inviteLoadPort.loadAllInvitesOfUser(playerLoadPort.loadPlayerById(userId).orElseThrow());
    }
}
