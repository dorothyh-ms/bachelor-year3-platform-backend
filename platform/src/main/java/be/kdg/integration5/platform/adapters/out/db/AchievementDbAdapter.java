package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.AchievementJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.AchievementRepository;
import be.kdg.integration5.platform.domain.Achievement;
import be.kdg.integration5.platform.ports.out.AchievementCreatedPort;
import be.kdg.integration5.platform.ports.out.AchievementLoadPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AchievementDbAdapter implements AchievementLoadPort, AchievementCreatedPort {
    private final AchievementRepository achievementRepository;

    public AchievementDbAdapter(AchievementRepository achievementRepostory) {
        this.achievementRepository = achievementRepostory;
    }

    @Override
    public Achievement achievementCreated(Achievement achievement) {
        achievementRepository.save(new AchievementJpaEntity(
                achievement.getId(),
                GameMapper.toGameJpaEntity(achievement.getGame()),
                achievement.getName(),
                achievement.getDescription()
        ));
        return achievement;
    }

    @Override
    public Optional<Achievement> loadAchievementByGameIdAndAchievementName(UUID gameId, String achievementName) {
        Optional<AchievementJpaEntity> achievementOptional = achievementRepository.findByNameAndAndGame_Id(achievementName, gameId);
        if (achievementOptional.isPresent()){
            AchievementJpaEntity achievementJpaEntity = achievementOptional.get();
            return Optional.of(new Achievement(
                    achievementJpaEntity.getId(),
                    achievementJpaEntity.getName(),
                    achievementJpaEntity.getDescription(),
                    GameMapper.toGame(achievementJpaEntity.getGame())
                    ));
        }
        return Optional.empty();
    }
}
