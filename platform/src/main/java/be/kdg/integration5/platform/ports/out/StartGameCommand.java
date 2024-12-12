package be.kdg.integration5.platform.ports.out;

import java.util.UUID;

public record StartGameCommand(UUID player1Id, UUID player2Id, UUID matchId) {
}
