package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Invite;

import java.util.Optional;
import java.util.UUID;

public interface InviteLoadPort {
    Optional<Invite> loadInvite(UUID inviteId);
}
