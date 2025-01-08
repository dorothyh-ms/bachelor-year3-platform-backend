package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.common.domain.PlayerMatchOutcome;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EndMatchCommand(UUID matchId, LocalDateTime endDateTime) {
}
