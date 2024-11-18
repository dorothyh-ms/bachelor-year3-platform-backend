package be.kdg.integration5.platform.domain;

import java.util.UUID;

public class Invite {

    private UUID id;


    private Player sender;


    private Player recipient;


    private Lobby lobby;

    private InviteStatus inviteStatus;

    private InviteExpired inviteExpired = InviteExpired.NOT_EXPIRED;


    public Invite(UUID id, Player sender, Player recipient, Lobby lobby) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.lobby = lobby;
    }

    public Invite() {
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

    public InviteExpired getInviteExpired() {
        return inviteExpired;
    }

    public void setInviteExpired(InviteExpired inviteExpired) {
        this.inviteExpired = inviteExpired;
    }

    void inviteeAccepts( ){
        this.inviteExpired = InviteExpired.EXPIRED;
        this.inviteStatus = InviteStatus.ACCEPTED;
    }

    void InviteeDeclines(){
        this.inviteExpired = InviteExpired.EXPIRED;
        this.inviteStatus = InviteStatus.DENIED;
    }
}
