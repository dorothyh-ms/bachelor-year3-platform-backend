package be.kdg.integration5.messagesdk;

import be.kdg.integration5.messagesdk.commands.StartGameCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TestPublisher {
    private final MessagePublisher publisher;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    public TestPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    public void sendTestCommand() {
        StartGameCommand command = new StartGameCommand(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );
        publisher.publish(exchange, "game.test.start", command);
    }
}
