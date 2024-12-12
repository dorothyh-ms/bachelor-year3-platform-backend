package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Invite;

import java.util.List;
import java.util.UUID;

public interface GetInvitesUseCase {
    List<Invite> getInvites(UUID userId);
}
