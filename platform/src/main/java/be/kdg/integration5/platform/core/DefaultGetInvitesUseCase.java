package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.in.web.InviteController;
import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.exceptions.PlayerNotFoundException;
import be.kdg.integration5.platform.ports.in.GetInvitesUseCase;
import be.kdg.integration5.platform.ports.out.InviteLoadPort;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultGetInvitesUseCase implements GetInvitesUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGetInvitesUseCase.class);
    private final InviteLoadPort inviteLoadPort;
    private final PlayerLoadPort playerLoadPort;

    public DefaultGetInvitesUseCase(InviteLoadPort inviteLoadPort, PlayerLoadPort playerLoadPort) {
        this.inviteLoadPort = inviteLoadPort;
        this.playerLoadPort = playerLoadPort;
    }

    @Override
    public List<Invite> getInvites(UUID userId) {
        LOGGER.info("DefaultGetInvitesUseCase is running getInvites with id {}", userId);
        Optional<Player> playerOptional = playerLoadPort.loadPlayerById(userId);
        if (playerOptional.isEmpty()){
            throw new PlayerNotFoundException(String.format("Player with id %s does not exist", userId ));
        }

        return inviteLoadPort.loadAllInvitesOfUser(playerOptional.get());
    }
}
