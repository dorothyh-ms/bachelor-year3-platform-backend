package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Player;

import java.util.List;

public interface GetPlayersUsesCase {
    List<Player> getPlayers(String username);
}
