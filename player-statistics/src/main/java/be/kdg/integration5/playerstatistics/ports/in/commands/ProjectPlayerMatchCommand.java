package be.kdg.integration5.playerstatistics.ports.in.commands;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


public record ProjectPlayerMatchCommand(UUID matchId, UUID playerId, LocalDateTime turnDateTime, double pointsEarned) {
}
