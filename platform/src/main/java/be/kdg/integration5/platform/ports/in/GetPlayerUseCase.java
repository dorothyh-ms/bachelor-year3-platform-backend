package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Player;

import java.util.List;

public interface GetPlayerUseCase {
    List<Player> getPlayers(String username);
}
