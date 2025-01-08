package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.mappers.PlayerMapper;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.domain.PlayerAchievement;
import be.kdg.integration5.platform.exceptions.GameNotFoundException;
import be.kdg.integration5.platform.exceptions.PlayerNotFoundException;
import be.kdg.integration5.platform.ports.in.GetAchievementsOfPlayerUseCaseByGame;
import be.kdg.integration5.platform.ports.out.GameLoadPort;
import be.kdg.integration5.platform.ports.out.PlayerAchievementLoadPort;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultGetAchievementsOfPlayerByGameUseCase implements GetAchievementsOfPlayerUseCaseByGame {
    private final PlayerAchievementLoadPort playerAchievementLoadPort;
    private final PlayerLoadPort playerLoadPort;
    private final GameLoadPort gameLoadPort;

    public DefaultGetAchievementsOfPlayerByGameUseCase(PlayerAchievementLoadPort playerAchievementLoadPort, PlayerLoadPort playerLoadPort, GameLoadPort gameLoadPort) {
        this.playerAchievementLoadPort = playerAchievementLoadPort;
        this.playerLoadPort = playerLoadPort;
        this.gameLoadPort = gameLoadPort;
    }

    @Override
    public List<PlayerAchievement> getPlayerAchievementsByGame(UUID playerId, UUID gameId) {
        Optional<Player> player = playerLoadPort.loadPlayerById(playerId);
        if (player.isEmpty()) throw new PlayerNotFoundException("Player not found");

        Optional<Game> game = gameLoadPort.loadGameById(gameId);
        if (game.isEmpty()) throw new GameNotFoundException("Game not found");
        return playerAchievementLoadPort.loadPlayerAchievementsByIdAndGame(PlayerMapper.toPlayerJpaEntity(player.get()), GameMapper.toGameJpaEntity(game.get()));
    }
}
