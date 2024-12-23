package be.kdg.integration5.platform.adapters.out.db.repositories;

import be.kdg.integration5.platform.adapters.out.db.entities.InviteJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.LobbyJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface InviteRepository extends JpaRepository<InviteJpaEntity, UUID> {
    @Query("SELECT i FROM InviteJpaEntity i WHERE i.inviteStatus = be.kdg.integration5.platform.domain.InviteStatus.OPEN AND i.lobby.lobbyStatus = be.kdg.integration5.platform.domain.LobbyStatus.OPEN AND i.recipient = :recipient")
    List<InviteJpaEntity> findAllOpenInvitesWithOpenLobbiesForRecipient(@Param("recipient") PlayerJpaEntity recipient);
}
