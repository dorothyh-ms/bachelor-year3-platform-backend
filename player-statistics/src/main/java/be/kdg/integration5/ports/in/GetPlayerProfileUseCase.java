package be.kdg.integration5.ports.in;

import be.kdg.integration5.domain.PlayerProfile;

import java.util.UUID;

public interface GetPlayerProfileUseCase {

    public PlayerProfile getPlayerProfileById(UUID userId);
}
