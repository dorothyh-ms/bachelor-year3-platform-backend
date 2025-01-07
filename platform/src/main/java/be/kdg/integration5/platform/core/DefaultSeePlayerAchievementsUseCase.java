package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.PlayerAchievement;
import be.kdg.integration5.platform.ports.in.SeePlayerAchievementsUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultSeePlayerAchievementsUseCase implements SeePlayerAchievementsUseCase {
    @Override
    public List<PlayerAchievement> getPlayerAchievements(UUID playerId) {
        return List.of();
    }
}
