package be.kdg.integration5.platform.adapters.in.event;

import be.kdg.integration5.common.events.MatchEndedEvent;


import be.kdg.integration5.platform.ports.in.EndMatchCommand;
import be.kdg.integration5.platform.ports.in.EndMatchUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MatchFinishedListener {

    private final EndMatchUseCase endMatchUseCase;
    public static final String MATCH_END_EVENTS_QUEUE = "match_end_events_platform_queue";
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchFinishedListener.class);

    public MatchFinishedListener(EndMatchUseCase endMatchUseCase) {
        this.endMatchUseCase = endMatchUseCase;
    }

    @RabbitListener(queues = MATCH_END_EVENTS_QUEUE)
    public void matchEnded(MatchEndedEvent matchEndedEvent) {
        LOGGER.info("MatchFinishedListener is running matchEnded with event {}", matchEndedEvent);
        endMatchUseCase.endMatch(new EndMatchCommand(
                matchEndedEvent.matchId(),
                matchEndedEvent.endDateTime()
        ));
    }
}
