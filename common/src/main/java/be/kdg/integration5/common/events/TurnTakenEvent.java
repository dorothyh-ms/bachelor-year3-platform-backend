package be.kdg.integration5.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record TurnTakenEvent(UUID matchId, UUID playerId, LocalDateTime turnDateTime, double pointsEarned) {
}

