package be.kdg.integration5.common.domain;

import java.util.UUID;

public record PlayerMatchOutcome(UUID playerId, MatchOutcome outcome) {
}
