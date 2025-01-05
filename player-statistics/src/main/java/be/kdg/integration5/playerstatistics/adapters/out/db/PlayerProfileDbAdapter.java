package be.kdg.integration5.playerstatistics.adapters.out.db;

import be.kdg.integration5.playerstatistics.adapters.out.db.entities.LocationJpaEntity;
import be.kdg.integration5.playerstatistics.adapters.out.db.entities.PlayerProfileJpaEntity;
import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.PlayerProfileRepository;
import be.kdg.integration5.common.domain.PlayerGameClassification;
import be.kdg.integration5.common.domain.PlayerStatistics;
import be.kdg.integration5.playerstatistics.domain.Location;
import be.kdg.integration5.playerstatistics.domain.PlayerProfile;
import be.kdg.integration5.playerstatistics.ports.out.PlayerGameClassificationLoadPort;
import be.kdg.integration5.playerstatistics.ports.out.PlayerProfileLoadPort;
import be.kdg.integration5.playerstatistics.ports.out.PlayerStatisticsLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
        List<Object[]> classificationsObjects =  playerProfileRepository.loadPlayerClassifications(userId.toString());
        System.out.println(classificationsObjects.get(1)[0]);
        System.out.println(classificationsObjects.get(1)[1]);
        System.out.println(classificationsObjects.get(1)[2]);
        System.out.println(classificationsObjects.get(1)[3]);
        System.out.println(classificationsObjects.get(1)[4]);
        System.out.println(classificationsObjects.get(1)[5]);
        System.out.println(classificationsObjects.get(1)[6]);
        System.out.println(classificationsObjects.get(1)[7]);
        List<PlayerGameClassification> classifications = classificationsObjects.stream().map(classification -> new PlayerGameClassification(
                UUID.fromString((String) classification[0]),
                UUID.fromString((String) classification[1]),
                (String) classification[2],
                ((Number) classification[3]).intValue(),
                ((Number) classification[4]).intValue(),
                ((Number) classification[5]).intValue(),
                ((Number) classification[6]).doubleValue(),
                (String) classification[7]

        )).toList();
        LOGGER.info("Returning list {}", classifications);
        return classifications;
    }
}
