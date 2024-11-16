package be.kdg.integration5.platform.ports.in.commands;

import java.util.UUID;

public record JoinLobbyCommand (UUID joinPlayerId, UUID lobbyId) {

}
