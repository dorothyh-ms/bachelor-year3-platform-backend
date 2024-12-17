package be.kdg.integration5.adapters.out.db;

import be.kdg.integration5.adapters.out.db.entities.LocationJpaEntity;
import be.kdg.integration5.adapters.out.db.entities.PlayerProfileJpaEntity;
import be.kdg.integration5.adapters.out.db.repositories.PlayerProfileRepository;
import be.kdg.integration5.domain.PlayerGameClassification;
import be.kdg.integration5.common.domain.PlayerStatistics;
import be.kdg.integration5.domain.Location;
import be.kdg.integration5.domain.PlayerProfile;
import be.kdg.integration5.ports.out.PlayerGameClassificationLoadPort;
import be.kdg.integration5.ports.out.PlayerProfileLoadPort;
import be.kdg.integration5.ports.out.PlayerStatisticsLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PlayerProfileDbAdapter implements PlayerProfileLoadPort, PlayerStatisticsLoadPort, PlayerGameClassificationLoadPort {
    private final PlayerProfileRepository playerProfileRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerProfileDbAdapter.class);

    public PlayerProfileDbAdapter(PlayerProfileRepository playerProfileRepository) {
        this.playerProfileRepository = playerProfileRepository;
    }

    @Override
    public Optional<PlayerProfile> loadPlayerProfileById(UUID id) {
        Optional<PlayerProfileJpaEntity> playerProfileJpaEntityOptional = playerProfileRepository.findById(id);
        if (playerProfileJpaEntityOptional.isPresent()){
            PlayerProfileJpaEntity pJpa = playerProfileJpaEntityOptional.get();
            LocationJpaEntity locationJpa = pJpa.getLocation();
            return Optional.of(new PlayerProfile(
                    pJpa.getId(),
                    pJpa.getUserName(),
                    pJpa.getFirstName(),
                    pJpa.getLastName(),
                    pJpa.getBirthDate(),
                    pJpa.getGender(),
                    new Location(locationJpa.getCity(), locationJpa.getCountry().getName())
            ));
        }
        return Optional.empty();
    }

    public List<PlayerStatistics> loadPlayerStatistics() {
        LOGGER.info("PlayerProfileDbAdapter is running loadPlayerStatistics");
        return playerProfileRepository.findPlayerStatistics();
    }

    @Override
    public List<PlayerGameClassification> loadPlayerGameClassifications(UUID userId) {
        LOGGER.info("PlayerProfileDbAdapter is running loadPlayerGameClassifications with id {}", userId );
        List<PlayerGameClassificationDto> classificationsObjects =  playerProfileRepository.loadPlayerClassifications(userId.toString());
        List<PlayerGameClassification> classifications = classificationsObjects.stream().map(classification -> new PlayerGameClassification(
                classification.getPlayerId(),
                classification.getGameId(),
                classification.getGameName(),
                classification.getTotalWins(),
                classification.getTotalLosses(),
                classification.getClassification()
        )).toList();
        LOGGER.info("Returning list {}", classifications);
        return classifications;
    }
}
