package be.kdg.integration5.messagesdk.commands;

import be.kdg.integration5.messagesdk.BaseMessage;

import java.util.UUID;

public class StartGameCommand extends BaseMessage {
    private UUID player1Id;
    private UUID player2Id;
    private UUID matchId;

    public StartGameCommand(UUID player1Id, UUID player2Id, UUID matchId) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.matchId = matchId;
    }

    public UUID getPlayer1Id() {
        return player1Id;
    }

    public UUID getPlayer2Id() {
        return player2Id;
    }

    public UUID getMatchId() {
        return matchId;
    }
}
