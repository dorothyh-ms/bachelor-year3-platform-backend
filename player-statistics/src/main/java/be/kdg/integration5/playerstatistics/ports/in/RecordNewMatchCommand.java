package be.kdg.integration5.playerstatistics.ports.in;

import java.time.LocalDateTime;
import java.util.UUID;

public record RecordNewMatchCommand(UUID matchId, UUID player1Id, UUID player2Id, String gameName, LocalDateTime startDateTime) {
}
