package be.kdg.integration5.platform.adapters.in.event;

import be.kdg.integration5.platform.domain.events.GameInviteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GameInviteEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameInviteEventListener.class);

    @EventListener
    public void handleGameInviteEvent(GameInviteEvent event) {
        LOGGER.info("Received GameInviteEvent for recipient ID: {}", event.getRecipientId());


    }
}
