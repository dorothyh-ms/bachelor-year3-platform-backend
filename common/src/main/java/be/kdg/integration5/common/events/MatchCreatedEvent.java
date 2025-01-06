package be.kdg.integration5.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record MatchCreatedEvent(UUID player1Id, UUID player2Id, UUID matchId, LocalDateTime createdDateTime, String gameName) {
}

