package be.kdg.integration5.core;

import be.kdg.integration5.adapters.out.db.entities.PlayerProfileJpaEntity;
import be.kdg.integration5.adapters.out.db.repositories.PlayerProfileRepository;
import be.kdg.integration5.domain.PlayerProfile;
import be.kdg.integration5.exceptions.PlayerProfileNotFoundException;
import be.kdg.integration5.ports.in.GetPlayerProfileUseCase;
import be.kdg.integration5.ports.out.PlayerProfileLoadPort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultGetPlayerProfileUseCase implements GetPlayerProfileUseCase {

    private final PlayerProfileLoadPort playerProfileLoadPort;

    public DefaultGetPlayerProfileUseCase(PlayerProfileLoadPort playerProfileLoadPort) {
        this.playerProfileLoadPort = playerProfileLoadPort;
    }

    @Override
    public PlayerProfile getPlayerProfileById(UUID userId) {
        Optional<PlayerProfile> playerProfileOptional = playerProfileLoadPort.loadPlayerProfileById(userId);
        if (playerProfileOptional.isEmpty()){
            throw new PlayerProfileNotFoundException(String.format("Player with id %s was not found", userId.toString()));
        }
        return playerProfileOptional.get();
    }
}
