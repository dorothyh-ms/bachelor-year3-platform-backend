package be.kdg.integration5.platform.adapters.out.db.mappers;

import be.kdg.integration5.platform.adapters.out.db.entities.InviteJpaEntity;
import be.kdg.integration5.platform.domain.Invite;

public class InviteMapper {

    // Convert InviteJpaEntity to Invite
    public static Invite toInvite(InviteJpaEntity inviteJpaEntity) {
        if (inviteJpaEntity == null) {
            return null;
        }

        Invite invite = new Invite();
        invite.setId(inviteJpaEntity.getId());
        invite.setSender(PlayerMapper.toPlayer(inviteJpaEntity.getSender()));  // Assuming PlayerMapper exists for Player domain objects
        invite.setRecipient(PlayerMapper.toPlayer(inviteJpaEntity.getRecipient()));  // Assuming PlayerMapper exists for Player domain objects
        invite.setLobby(LobbyMapper.toLobby(inviteJpaEntity.getLobby()));  // Assuming LobbyMapper exists for Lobby domain objects
        invite.setInviteStatus(inviteJpaEntity.getInviteStatus());
        invite.setDateSent(inviteJpaEntity.getDateSent());

        return invite;
    }

    // Convert Invite to InviteJpaEntity
    public static InviteJpaEntity toInviteJpaEntity(Invite invite) {
        if (invite == null) {
            return null;
        }

        InviteJpaEntity inviteJpaEntity = new InviteJpaEntity();
        inviteJpaEntity.setId(invite.getId());
        inviteJpaEntity.setSender(PlayerMapper.toPlayerJpaEntity(invite.getSender()));  // Assuming PlayerMapper exists for PlayerJpaEntity
        inviteJpaEntity.setRecipient(PlayerMapper.toPlayerJpaEntity(invite.getRecipient()));  // Assuming PlayerMapper exists for PlayerJpaEntity
        inviteJpaEntity.setLobby(LobbyMapper.toLobbyJpaEntity(invite.getLobby()));  // Assuming LobbyMapper exists for LobbyJpaEntity
        inviteJpaEntity.setInviteStatus(invite.getInviteStatus());
        inviteJpaEntity.setDateSent(invite.getDateSent());

        return inviteJpaEntity;
    }
}
