package be.kdg.integration5.platform.ports.in;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateMatchCommand(UUID matchId, UUID player1Id, UUID player2Id, String gameName, LocalDateTime startDateTime) {
}
