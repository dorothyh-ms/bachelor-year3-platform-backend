package be.kdg.integration5.platform.ports.in.commands;

import java.util.UUID;

public record CreatePlayerAchievementCommand(UUID playerId, String gameName, String name, String description) {
}
