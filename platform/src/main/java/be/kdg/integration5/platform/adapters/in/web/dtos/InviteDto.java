package be.kdg.integration5.platform.adapters.in.web.dtos;

import be.kdg.integration5.platform.domain.InviteStatus;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.domain.Player;

import java.util.UUID;

public class InviteDto {
    private UUID id;


    private Player sender;


    private Player recipient;


    private Lobby lobby;

    private InviteStatus inviteStatus;

    public InviteDto(UUID id, Player sender, Player recipient, Lobby lobby) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.lobby = lobby;
    }
    public InviteDto(UUID id, Player sender, Player recipient, Lobby lobby,InviteStatus inviteStatus) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.lobby = lobby;
        this.inviteStatus = inviteStatus;
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

    public InviteStatus getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(InviteStatus inviteStatus) {
        this.inviteStatus = inviteStatus;
    }
}
