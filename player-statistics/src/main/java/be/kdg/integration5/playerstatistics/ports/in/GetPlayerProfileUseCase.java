package be.kdg.integration5.playerstatistics.ports.in;

import be.kdg.integration5.playerstatistics.domain.PlayerProfile;

import java.util.UUID;

public interface GetPlayerProfileUseCase {

    public PlayerProfile getPlayerProfileById(UUID userId);
}
