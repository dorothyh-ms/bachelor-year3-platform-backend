package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.PlayerAchievement;
import be.kdg.integration5.platform.ports.in.GetAchievementsOfPlayerUseCase;
import be.kdg.integration5.platform.ports.out.AchievementLoadPort;
import be.kdg.integration5.platform.ports.out.PlayerAchievementLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultGetAchievementsOfPlayerUseCase implements GetAchievementsOfPlayerUseCase {
    private final PlayerAchievementLoadPort playerAchievementLoadPort;

    public DefaultGetAchievementsOfPlayerUseCase(PlayerAchievementLoadPort playerAchievementLoadPort) {
        this.playerAchievementLoadPort = playerAchievementLoadPort;
    }

    @Override
    public List<PlayerAchievement> getPlayerAchievements(UUID playerId) {
        return playerAchievementLoadPort.loadPlayerAchievementsById(playerId);
    }
}
