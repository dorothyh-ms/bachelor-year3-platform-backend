package be.kdg.integration5.platform.adapters.in.event;

import be.kdg.integration5.common.events.MatchCreatedEvent;


import be.kdg.integration5.platform.ports.in.CreateMatchCommand;
import be.kdg.integration5.platform.ports.in.CreateMatchUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class MatchStartedListener {

    private final CreateMatchUseCase createMatchUseCase;
    private static final String MATCH_CREATE_EVENTS = "match_create_events_platform_queue";

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchStartedListener.class);

    public MatchStartedListener( CreateMatchUseCase createMatchUseCase) {
        this.createMatchUseCase = createMatchUseCase;
    }

    @RabbitListener(queues = MATCH_CREATE_EVENTS)
    public void matchCreated(MatchCreatedEvent matchCreatedEvent) {
        LOGGER.info("MatchStartedListener is running matchCreated with event {}", matchCreatedEvent);
        createMatchUseCase.createMatch(new CreateMatchCommand(
                matchCreatedEvent.matchId(),
                matchCreatedEvent.player1Id(),
                matchCreatedEvent.player2Id(),
                matchCreatedEvent.gameName(),
                matchCreatedEvent.createdDateTime()
        ));
    }
}
