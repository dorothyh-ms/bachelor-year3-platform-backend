package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InviteLoadPort {
    Optional<Invite> loadInvite(UUID inviteId);
    List<Invite> loadAllInvitesOfUser(Player player);
}
