package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Invite;

import java.util.UUID;

public interface PlayerCreatesInviteUseCase {
    Invite createInvite(UUID sender, UUID recipient, UUID lobbyId);
}
