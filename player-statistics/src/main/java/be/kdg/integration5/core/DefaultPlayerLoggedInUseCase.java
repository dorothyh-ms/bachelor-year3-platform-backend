package be.kdg.integration5.core;

import be.kdg.integration5.domain.PlayerLogin;
import be.kdg.integration5.domain.PlayerProfile;
import be.kdg.integration5.exceptions.PlayerProfileNotFoundException;
import be.kdg.integration5.ports.in.PlayerLoggedInUseCase;
import be.kdg.integration5.ports.out.PlayerLoginCreatePort;
import be.kdg.integration5.ports.out.PlayerProfileLoadPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultPlayerLoggedInUseCase implements PlayerLoggedInUseCase {
    private final PlayerProfileLoadPort playerProfileLoadPort;
    private final PlayerLoginCreatePort playerLoginCreatePort;



    public DefaultPlayerLoggedInUseCase(PlayerProfileLoadPort playerProfileLoadPort, PlayerLoginCreatePort playerLoginCreatePort) {
        this.playerProfileLoadPort = playerProfileLoadPort;
        this.playerLoginCreatePort = playerLoginCreatePort;
    }

    @Override
    public void playerLoggedIn(UUID userId) {
        Optional<PlayerProfile> playerProfileOptional = playerProfileLoadPort.loadPlayerProfileById(userId);
        if (playerProfileOptional.isEmpty()){
            throw new PlayerProfileNotFoundException(String.format("Player with id %s was not found", userId.toString()));
        }
        PlayerLogin login = new PlayerLogin(playerProfileOptional.get());
        playerLoginCreatePort.playerLoggedIn(login);

    }
}
