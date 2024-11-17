package be.kdg.integration5.platform.domain;

import java.util.UUID;

public class Invite {

    private UUID id;


    private Player sender;


    private Player recipient;


    private Lobby lobby;


    public Invite(UUID id, Player sender, Player recipient, Lobby lobby) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.lobby = lobby;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Player getRecipient() {
        return recipient;
    }

    public void setRecipient(Player recipient) {
        this.recipient = recipient;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
}
