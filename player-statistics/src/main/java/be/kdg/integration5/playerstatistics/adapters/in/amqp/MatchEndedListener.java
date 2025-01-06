package be.kdg.integration5.playerstatistics.adapters.in.amqp;

import be.kdg.integration5.common.events.MatchEndedEvent;
import be.kdg.integration5.common.events.TurnTakenEvent;
import be.kdg.integration5.playerstatistics.ports.in.EndMatchUseCase;
import be.kdg.integration5.playerstatistics.ports.in.PlayerMatchProjector;
import be.kdg.integration5.playerstatistics.ports.in.commands.EndMatchCommand;
import be.kdg.integration5.playerstatistics.ports.in.commands.ProjectPlayerMatchCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MatchEndedListener {
    private final EndMatchUseCase endMatchUseCase;
    public static final String MATCH_END_EVENTS_QUEUE = "match_end_events_queue";
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchEndedListener.class);

    public MatchEndedListener(EndMatchUseCase endMatchUseCase) {
        this.endMatchUseCase = endMatchUseCase;
    }

    @RabbitListener(queues = MATCH_END_EVENTS_QUEUE)
    public void matchEnded(MatchEndedEvent matchEndedEvent) {
        LOGGER.info("MatchEndedListener is running matchEnded with event {}", matchEndedEvent);
        endMatchUseCase.endMatch(new EndMatchCommand(
                matchEndedEvent.matchId(),
                matchEndedEvent.endDateTime(),
                matchEndedEvent.playerMatchOutcomes()
        ));
    }
}
