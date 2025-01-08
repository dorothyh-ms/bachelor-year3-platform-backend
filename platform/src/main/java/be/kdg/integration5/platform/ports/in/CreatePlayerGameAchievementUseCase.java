package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.PlayerAchievement;
import be.kdg.integration5.platform.ports.in.commands.CreatePlayerAchievementCommand;

public interface CreatePlayerGameAchievementUseCase {
    public PlayerAchievement createPlayerAchievement(CreatePlayerAchievementCommand command);
}
