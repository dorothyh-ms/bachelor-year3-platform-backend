package be.kdg.integration5.platform.domain.events;

import be.kdg.integration5.platform.domain.Invite;

import java.util.UUID;

public class GameInviteEvent {
    private final UUID recipientId;
    private final Invite invite;

    public GameInviteEvent(UUID recipientId, Invite invite) {
        this.recipientId = recipientId;
        this.invite = invite;
    }

    public UUID getRecipientId() {
        return recipientId;
    }

    public Invite getInvite() {
        return invite;
    }
}
