package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.AchievementJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.GameJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerAchievementJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.mappers.PlayerMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.PlayerAchievementRepository;
import be.kdg.integration5.platform.domain.Achievement;
import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.domain.PlayerAchievement;
import be.kdg.integration5.platform.ports.out.PlayerAchievementCreatedPort;
import be.kdg.integration5.platform.ports.out.PlayerAchievementLoadPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PlayerAchievementDbAdapter implements PlayerAchievementCreatedPort, PlayerAchievementLoadPort {
    private final PlayerAchievementRepository playerAchievementRepository;

    public PlayerAchievementDbAdapter(PlayerAchievementRepository playerAchievementRepository) {
        this.playerAchievementRepository = playerAchievementRepository;
    }

    @Override
    public PlayerAchievement playerAchievementCreated(PlayerAchievement playerAchievement) {
        Achievement achievement = playerAchievement.getAchievement();
        playerAchievementRepository.save(new PlayerAchievementJpaEntity(
                playerAchievement.getId(),
                PlayerMapper.toPlayerJpaEntity(playerAchievement.getPlayer()),
                new AchievementJpaEntity(
                        achievement.getId(),
                        GameMapper.toGameJpaEntity(achievement.getGame()),
                        achievement.getName(),
                        achievement.getDescription()
                        )
        ));
        return playerAchievement;
    }

    @Override
    public Optional<PlayerAchievement> loadByGameIdAchievementIdAndPlayerId(UUID gameId, UUID achievementId, UUID playerId) {
        Optional<PlayerAchievementJpaEntity> playerAchievementJpaEntityOptional = playerAchievementRepository.findFirstByAchievement_Game_IdAndAchievement_IdAndPlayer_PlayerId(gameId, achievementId, playerId);
        if (playerAchievementJpaEntityOptional.isPresent()){
            PlayerAchievementJpaEntity playerAchievementJpaEntity = playerAchievementJpaEntityOptional.get();
            AchievementJpaEntity achievementJpaEntity = playerAchievementJpaEntity.getAchievement();
            return Optional.of(
                    new PlayerAchievement(
                            playerAchievementJpaEntity.getId(),
                            new Achievement(
                                    achievementJpaEntity.getId(),
                                    achievementJpaEntity.getName(),
                                    achievementJpaEntity.getDescription(),
                                    GameMapper.toGame(achievementJpaEntity.getGame())
                            ),
                            PlayerMapper.toPlayer(playerAchievementJpaEntity.getPlayer())
                    )
            );
        }
        return Optional.empty();
    }

    @Override
    public List<PlayerAchievement> loadPlayerAchievementsById(UUID id) {
        List<PlayerAchievementJpaEntity> playerAchievementJpaEntities = playerAchievementRepository.findAllByPlayer_PlayerId(id);
        return playerAchievementJpaEntities.stream().map((paJpa ->{
            AchievementJpaEntity achievementJpaEntity = paJpa.getAchievement();
                return new PlayerAchievement(
                        paJpa.getId(),
                        new Achievement(
                                achievementJpaEntity.getId(),
                                achievementJpaEntity.getName(),
                                achievementJpaEntity.getDescription(),
                                GameMapper.toGame(achievementJpaEntity.getGame())
                        ),
                        PlayerMapper.toPlayer(paJpa.getPlayer())
            );
        }
        )).toList();
    }

    @Override
    public List<PlayerAchievement> loadPlayerAchievementsByIdAndGame(PlayerJpaEntity player, GameJpaEntity game) {
        List<PlayerAchievementJpaEntity> playerAchievementJpaEntities = playerAchievementRepository.findAllByPlayerAndAchievement_Game(player, game);
        return playerAchievementJpaEntities.stream().map((paJpa ->{
            AchievementJpaEntity achievementJpaEntity = paJpa.getAchievement();
            return new PlayerAchievement(
                    paJpa.getId(),
                    new Achievement(
                            achievementJpaEntity.getId(),
                            achievementJpaEntity.getName(),
                            achievementJpaEntity.getDescription(),
                            GameMapper.toGame(achievementJpaEntity.getGame())
                    ),
                    PlayerMapper.toPlayer(paJpa.getPlayer())
            );
        }
        )).toList();
    }
}
