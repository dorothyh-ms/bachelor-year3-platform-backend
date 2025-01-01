package be.kdg.integration5.messagesdk;

import java.time.Instant;

public abstract class BaseMessage {
    private String messageId;
    private Instant timestamp;

    protected BaseMessage() {
        this.messageId = java.util.UUID.randomUUID().toString();
        this.timestamp = Instant.now();
    }

    public String getMessageId() {
        return messageId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
