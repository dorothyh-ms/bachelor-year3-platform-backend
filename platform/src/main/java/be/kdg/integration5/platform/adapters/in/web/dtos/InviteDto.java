package be.kdg.integration5.platform.adapters.in.web.dtos;

import be.kdg.integration5.platform.domain.InviteStatus;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.domain.Player;

import java.time.LocalDateTime;
import java.util.UUID;

public class InviteDto {
    private UUID id;


    private Player sender;


    private Player recipient;


    private Lobby lobby;

    private InviteStatus inviteStatus;

    private LocalDateTime dateSent;

    public InviteDto(UUID id, Player sender, Player recipient, Lobby lobby, LocalDateTime dateSent) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.lobby = lobby;
        this.dateSent = dateSent;
    }
    public InviteDto(UUID id, Player sender, Player recipient, Lobby lobby,InviteStatus inviteStatus, LocalDateTime dateSent) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.lobby = lobby;
        this.inviteStatus = inviteStatus;
        this.dateSent = dateSent;
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

    public LocalDateTime getDateSent() {
        return dateSent;
    }

    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
    }
}
