package be.kdg.integration5.messagesdk;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public abstract class MessageListener<T extends BaseMessage> {
    private final Class<T> messageType;

    protected MessageListener(Class<T> messageType) {
        this.messageType = messageType;
    }

    @RabbitListener(queues = "#{queueName}")
    public void handleMessage(String messageJson) {
        T message = deserialize(messageJson);
        processMessage(message);
    }

    protected abstract void processMessage(T message);

    private T deserialize(String messageJson) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(messageJson, messageType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize message", e);
        }
    }
}
