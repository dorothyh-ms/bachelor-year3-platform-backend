package be.kdg.integration5.playerstatistics.adapters.out.db;

import be.kdg.integration5.playerstatistics.adapters.out.db.entities.LocationJpaEntity;
import be.kdg.integration5.playerstatistics.adapters.out.db.entities.PlayerMatchJpaEntity;
import be.kdg.integration5.playerstatistics.adapters.out.db.entities.PlayerProfileJpaEntity;
import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.PlayerMatchRepository;
import be.kdg.integration5.playerstatistics.domain.Location;
import be.kdg.integration5.playerstatistics.domain.PlayerMatch;
import be.kdg.integration5.playerstatistics.domain.PlayerProfile;
import be.kdg.integration5.playerstatistics.ports.out.PlayerMatchLoadPort;
import be.kdg.integration5.playerstatistics.ports.out.PlayerMatchUpdatePort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PlayerMatchDbAdapter implements PlayerMatchLoadPort, PlayerMatchUpdatePort {
    private final PlayerMatchRepository playerMatchRepository;

    public PlayerMatchDbAdapter(PlayerMatchRepository playerMatchRepository) {
        this.playerMatchRepository = playerMatchRepository;
    }

    @Override
    public Optional<PlayerMatch> loadPlayerMatchByMatchIdAndPlayerId(UUID matchId, UUID playerId) {
        Optional<PlayerMatchJpaEntity> playerMatchJpaEntityOptional = playerMatchRepository.findFirstByMatch_IdAndPlayer_Id(matchId, playerId);
        if (playerMatchJpaEntityOptional.isPresent()){
            PlayerMatchJpaEntity playerMatchJpaEntity = playerMatchJpaEntityOptional.get();
            PlayerProfileJpaEntity playerProfileJpa = playerMatchJpaEntity.getPlayer();
            LocationJpaEntity locationJpa = playerProfileJpa.getLocation();
            return Optional.of(
                    new PlayerMatch(
                    playerMatchJpaEntity.getId(),
                    new PlayerProfile(
                            playerProfileJpa.getId(),
                            playerProfileJpa.getUserName(),
                            playerProfileJpa.getFirstName(),
                            playerProfileJpa.getLastName(),
                            playerProfileJpa.getBirthDate(),
                            playerProfileJpa.getGender(),
                            new Location(locationJpa.getCity(), locationJpa.getCountry().getName())
                    ),
                    playerMatchJpaEntity.getNumberOfTurns(),
                    playerMatchJpaEntity.getScore(),
                    playerMatchJpaEntity.getOutcome()
            ));
        }
        return Optional.empty();
    }

    @Override
    public void updatePlayerMatch(PlayerMatch pm) {
        Optional<PlayerMatchJpaEntity> optionalPlayerMatchJpaEntity = playerMatchRepository.findById(pm.getId());
        if (optionalPlayerMatchJpaEntity.isPresent()) {
            PlayerMatchJpaEntity playerMatchJpaEntity = optionalPlayerMatchJpaEntity.get();
            playerMatchJpaEntity.setOutcome(pm.getOutcome());
            playerMatchJpaEntity.setScore(pm.getScore());
            playerMatchJpaEntity.setNumberOfTurns(pm.getNumberOfTurns());
            playerMatchRepository.save(playerMatchJpaEntity);
        }
    }
}
