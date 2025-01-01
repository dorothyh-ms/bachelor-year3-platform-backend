package be.kdg.integration5.messagesdk;

import be.kdg.integration5.messagesdk.commands.StartGameCommand;
import org.springframework.stereotype.Component;

@Component
public class TestListener extends MessageListener<StartGameCommand> {

    public TestListener() {
        super(StartGameCommand.class);
    }

    @Override
    protected void processMessage(StartGameCommand message) {
        System.out.println("Received StartGameCommand: " + message);
    }
}
