package be.kdg.integration5.playerstatistics.adapters.in.amqp;


import be.kdg.integration5.common.events.MatchEndedEvent;
import be.kdg.integration5.playerstatistics.ports.in.RecordEndMatchUseCase;
import be.kdg.integration5.playerstatistics.ports.in.RecordMatchEndCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;



@Component
public class MatchEndedListener {
    private final RecordEndMatchUseCase endMatchUseCase;
    public static final String MATCH_END_EVENTS_QUEUE = "match_end_events_statistics_queue";
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchEndedListener.class);

    public MatchEndedListener(RecordEndMatchUseCase endMatchUseCase) {
        this.endMatchUseCase = endMatchUseCase;
    }

    @RabbitListener(queues = MATCH_END_EVENTS_QUEUE)
    public void matchEnded(MatchEndedEvent matchEndedEvent) {
        LOGGER.info("MatchEndedListener is running matchEnded with event {}", matchEndedEvent);
        endMatchUseCase.endMatch(new RecordMatchEndCommand(
                matchEndedEvent.matchId(),
                matchEndedEvent.endDateTime(),
                matchEndedEvent.playerMatchOutcomes()
        ));
    }
}
