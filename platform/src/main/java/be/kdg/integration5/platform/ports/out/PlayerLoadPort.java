package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Player;

import java.util.List;

public interface PlayerLoadPort {
List<Player> loadPlayers(String username);
}
