package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.InviteAction;
import be.kdg.integration5.platform.domain.Lobby;

import java.util.UUID;

public interface PlayerAcceptsInviteUseCase {
    Invite playerAnswersInvite(UUID inviteId, UUID userId, String action);
}
