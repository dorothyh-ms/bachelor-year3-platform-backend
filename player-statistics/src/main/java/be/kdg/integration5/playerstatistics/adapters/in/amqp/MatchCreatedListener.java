package be.kdg.integration5.playerstatistics.adapters.in.amqp;

import be.kdg.integration5.common.events.MatchCreatedEvent;
import be.kdg.integration5.playerstatistics.ports.in.CreateMatchUseCase;
import be.kdg.integration5.playerstatistics.ports.in.commands.CreateMatchCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MatchCreatedListener {

    private final CreateMatchUseCase createMatchUseCase;
    private static final String MATCH_CREATE_EVENTS = "match_create_events_queue";

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchCreatedListener.class);

    public MatchCreatedListener(CreateMatchUseCase createMatchUseCase) {
        this.createMatchUseCase = createMatchUseCase;
    }

    @RabbitListener(queues = MATCH_CREATE_EVENTS)
    public void matchCreated(MatchCreatedEvent matchCreatedEvent) {
        LOGGER.info("MatchCreatedListener is running matchCreated with event {}", matchCreatedEvent);
        createMatchUseCase.createMatch(new CreateMatchCommand(
                matchCreatedEvent.matchId(),
                matchCreatedEvent.player1Id(),
                matchCreatedEvent.player2Id(),
                matchCreatedEvent.gameName(),
                matchCreatedEvent.createdDateTime()
        ));
    }
}
