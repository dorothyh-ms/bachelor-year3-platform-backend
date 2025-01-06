package be.kdg.integration5.common.events;

import be.kdg.integration5.common.domain.MatchOutcome;
import be.kdg.integration5.common.domain.PlayerMatchOutcome;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public record MatchEndedEvent(UUID matchId, LocalDateTime endDateTime, List<PlayerMatchOutcome> playerMatchOutcomes) {
}
