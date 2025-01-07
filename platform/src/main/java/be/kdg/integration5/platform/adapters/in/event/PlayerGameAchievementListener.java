package be.kdg.integration5.platform.adapters.in.event;

import be.kdg.integration5.common.events.PlayerGameAchievementEvent;
import be.kdg.integration5.platform.ports.in.CreatePlayerGameAchievementUseCase;
import be.kdg.integration5.platform.ports.in.commands.CreatePlayerAchievementCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PlayerGameAchievementListener {

    private final CreatePlayerGameAchievementUseCase createPlayerGameAchievementUseCase;
    private static final String PLAYER_ACHIEVEMENT_EVENTS = "player_achievements_queue";

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerGameAchievementListener.class);

    public PlayerGameAchievementListener(CreatePlayerGameAchievementUseCase createPlayerGameAchievementUseCase) {
        this.createPlayerGameAchievementUseCase = createPlayerGameAchievementUseCase;
    }

    @RabbitListener(queues = PLAYER_ACHIEVEMENT_EVENTS)
    public void playerGainedAchievement(PlayerGameAchievementEvent event) {
        LOGGER.info("MatchCreatedListener is running playerGainedAchievement with event {}", event);
        createPlayerGameAchievementUseCase.createPlayerAchievement(new CreatePlayerAchievementCommand(
                event.playerId(),
                event.gameName(),
                event.achievementName(),
                event.description()
        ));
    }
}