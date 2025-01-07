package be.kdg.integration5.common.events;

import java.util.UUID;

public record PlayerGameAchievementEvent(UUID playerId, String gameName, String achievementName, String description) {
}
