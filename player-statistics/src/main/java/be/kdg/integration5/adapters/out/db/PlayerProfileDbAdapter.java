package be.kdg.integration5.adapters.out.db;

import be.kdg.integration5.adapters.out.db.entities.LocationJpaEntity;
import be.kdg.integration5.adapters.out.db.entities.PlayerProfileJpaEntity;
import be.kdg.integration5.adapters.out.db.repositories.PlayerProfileRepository;
import be.kdg.integration5.domain.Location;
import be.kdg.integration5.domain.PlayerProfile;
import be.kdg.integration5.ports.out.PlayerProfileLoadPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PlayerProfileDbAdapter implements PlayerProfileLoadPort {
    private final PlayerProfileRepository playerProfileRepository;

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
}
