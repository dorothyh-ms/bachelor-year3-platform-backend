package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Invite;

public interface InviteCreatePort {
    void createInvite(Invite invite);
}
