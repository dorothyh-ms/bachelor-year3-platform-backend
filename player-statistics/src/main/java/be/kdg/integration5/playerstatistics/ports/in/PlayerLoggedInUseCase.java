package be.kdg.integration5.playerstatistics.ports.in;

import java.util.UUID;

public interface PlayerLoggedInUseCase {

    public void playerLoggedIn(UUID userId);
}
