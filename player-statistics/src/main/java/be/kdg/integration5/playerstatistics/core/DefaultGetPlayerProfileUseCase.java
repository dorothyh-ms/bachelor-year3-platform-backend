package be.kdg.integration5.playerstatistics.core;

import be.kdg.integration5.playerstatistics.domain.PlayerProfile;
import be.kdg.integration5.playerstatistics.exceptions.PlayerProfileNotFoundException;
import be.kdg.integration5.playerstatistics.ports.in.GetPlayerProfileUseCase;
import be.kdg.integration5.playerstatistics.ports.out.PlayerProfileLoadPort;
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
