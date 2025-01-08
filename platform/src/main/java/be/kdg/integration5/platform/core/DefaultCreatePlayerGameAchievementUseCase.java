package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.in.event.PlayerGameAchievementListener;
import be.kdg.integration5.platform.domain.Achievement;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.domain.PlayerAchievement;
import be.kdg.integration5.platform.ports.in.CreatePlayerGameAchievementUseCase;
import be.kdg.integration5.platform.ports.in.commands.CreatePlayerAchievementCommand;
import be.kdg.integration5.platform.ports.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultCreatePlayerGameAchievementUseCase implements CreatePlayerGameAchievementUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCreatePlayerGameAchievementUseCase.class);

    private final GameLoadPort gameLoadPort;
    private final PlayerLoadPort playerLoadPort;
    private final AchievementLoadPort achievementLoadPort;
    private final AchievementCreatedPort achievementCreatedPort;
    private final PlayerAchievementLoadPort playerAchievementLoadPort;
    private final PlayerAchievementCreatedPort playerAchievementCreatedPort;

    public DefaultCreatePlayerGameAchievementUseCase(GameLoadPort gameLoadPort, PlayerLoadPort playerLoadPort, AchievementLoadPort achievementLoadPort, AchievementCreatedPort achievementCreatedPort, PlayerAchievementLoadPort playerAchievementLoadPort, PlayerAchievementCreatedPort playerAchievementCreatedPort) {
        this.gameLoadPort = gameLoadPort;
        this.playerLoadPort = playerLoadPort;
        this.achievementLoadPort = achievementLoadPort;
        this.achievementCreatedPort = achievementCreatedPort;
        this.playerAchievementLoadPort = playerAchievementLoadPort;
        this.playerAchievementCreatedPort = playerAchievementCreatedPort;
    }

    @Override
    public PlayerAchievement createPlayerAchievement(CreatePlayerAchievementCommand command) {
        LOGGER.info("DefaultCreatePlayerGameAchievementUseCase is running createPlayerAchievement with command {}",command);
        Game game = loadGameByName(command.gameName());
        if (game == null) {
            return null;
        }

        Player player = loadPlayerById(command.playerId());
        if (player == null) {
            return null;
        }

        Achievement achievement = findOrCreateAchievement(command, game);
        return findOrCreatePlayerAchievement(game, player, achievement);
    }

    private Game loadGameByName(String gameName) {
        return gameLoadPort.loadGameByName(gameName).orElse(null);
    }

    private Player loadPlayerById(UUID playerId) {
        return playerLoadPort.loadPlayerById(playerId).orElse(null);
    }

    private Achievement findOrCreateAchievement(CreatePlayerAchievementCommand command, Game game) {
        return achievementLoadPort
                .loadAchievementByGameIdAndAchievementName(game.getId(), command.name())
                .orElseGet(() -> {
                    Achievement newAchievement = new Achievement(
                            UUID.randomUUID(),
                            command.name(),
                            command.description(),
                            game
                    );
                    achievementCreatedPort.achievementCreated(newAchievement);
                    return newAchievement;
                });
    }

    private PlayerAchievement findOrCreatePlayerAchievement(Game game, Player player, Achievement achievement) {
        return playerAchievementLoadPort
                .loadByGameIdAchievementIdAndPlayerId(game.getId(), achievement.getId(), player.getPlayerId())
                .orElseGet(() -> {
                    PlayerAchievement newPlayerAchievement = new PlayerAchievement(achievement, player);
                    LOGGER.info("DefaultCreatePlayerGameAchievementUseCase is saving achievement {}", newPlayerAchievement);
                    playerAchievementCreatedPort.playerAchievementCreated(newPlayerAchievement);
                    return newPlayerAchievement;
                });
    }
}
