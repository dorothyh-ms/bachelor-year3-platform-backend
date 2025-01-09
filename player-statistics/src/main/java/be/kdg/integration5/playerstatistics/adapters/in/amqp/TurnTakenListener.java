package be.kdg.integration5.playerstatistics.adapters.in.amqp;

import be.kdg.integration5.common.events.TurnTakenEvent;
import be.kdg.integration5.playerstatistics.ports.in.PlayerMatchProjector;
import be.kdg.integration5.playerstatistics.ports.in.commands.ProjectPlayerMatchCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TurnTakenListener {
    private final PlayerMatchProjector playerMatchProjector;
    private static final String MATCH_TURN_EVENTS_QUEUE = "turn_events_queue";

    private static final Logger LOGGER = LoggerFactory.getLogger(TurnTakenListener.class);

    public TurnTakenListener(PlayerMatchProjector playerMatchProjector) {
        this.playerMatchProjector = playerMatchProjector;
    }

    @RabbitListener(queues = MATCH_TURN_EVENTS_QUEUE)
    public void turnTaken(TurnTakenEvent turnEvent) {
        LOGGER.info("TurnTakenListener is running turnTaken with event {}", turnEvent);
        playerMatchProjector.projectPlayerMatch(new ProjectPlayerMatchCommand(
                turnEvent.matchId(),
                turnEvent.playerId(),
                turnEvent.turnDateTime(),
                turnEvent.pointsEarned()
        ));
    }
}
