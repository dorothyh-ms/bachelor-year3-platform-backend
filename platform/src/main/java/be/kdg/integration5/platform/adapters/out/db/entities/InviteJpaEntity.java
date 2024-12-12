package be.kdg.integration5.platform.adapters.out.db.entities;

import be.kdg.integration5.platform.domain.InviteStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog="platform", name="invite")
public class InviteJpaEntity {


    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "invite_id", updatable = false, nullable = false)
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "sender_id", updatable = false, nullable = false)
    private PlayerJpaEntity sender;


    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private PlayerJpaEntity recipient;


    @ManyToOne
    @JoinColumn(name = "lobby_id")
    private LobbyJpaEntity lobby;

    @Enumerated(value = EnumType.STRING)
    private InviteStatus inviteStatus;

    private LocalDateTime dateSent;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PlayerJpaEntity getSender() {
        return sender;
    }

    public void setSender(PlayerJpaEntity sender) {
        this.sender = sender;
    }

    public PlayerJpaEntity getRecipient() {
        return recipient;
    }

    public void setRecipient(PlayerJpaEntity recipient) {
        this.recipient = recipient;
    }

    public LobbyJpaEntity getLobby() {
        return lobby;
    }

    public void setLobby(LobbyJpaEntity lobby) {
        this.lobby = lobby;
    }

    public InviteStatus getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(InviteStatus inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public InviteJpaEntity() {
    }

    public InviteJpaEntity(UUID id, PlayerJpaEntity sender, PlayerJpaEntity recipient, LobbyJpaEntity lobby, InviteStatus inviteStatus) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.lobby = lobby;
        this.inviteStatus = inviteStatus;
    }

    public LocalDateTime getDateSent() {
        return dateSent;
    }

    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
    }
}
