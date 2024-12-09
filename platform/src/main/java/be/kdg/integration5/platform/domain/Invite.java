package be.kdg.integration5.platform.domain;

import java.util.UUID;

public class Invite {

    private UUID id;


    private Player sender;


    private Player recipient;


    private Lobby lobby;

    private InviteStatus inviteStatus;


    public Invite(UUID id, Player sender, Player recipient, Lobby lobby) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.lobby = lobby;
        this.inviteStatus = InviteStatus.OPEN;
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

    public void accepted( ){
        this.inviteStatus = InviteStatus.ACCEPTED;
    }

    public void denied(){
        this.inviteStatus = InviteStatus.DENIED;
    }

    public void expired(){
        this.inviteStatus = InviteStatus.EXPIRED;
    }

    public boolean isOpen(){
        return this.inviteStatus.equals(InviteStatus.OPEN);
    }
    public boolean isAccepted(){
        return this.inviteStatus.equals(InviteStatus.ACCEPTED);
    }

    public boolean isDenied(){
        return this.inviteStatus.equals(InviteStatus.DENIED);
    }
    public boolean isExpired(){
        return this.inviteStatus.equals(InviteStatus.EXPIRED);
    }

    public boolean isRecipient(UUID id) {
        return recipient.getPlayerId().equals(id);
    }
}
