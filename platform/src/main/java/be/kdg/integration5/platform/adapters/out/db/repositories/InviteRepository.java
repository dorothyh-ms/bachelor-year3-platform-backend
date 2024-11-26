package be.kdg.integration5.platform.adapters.out.db.repositories;

import be.kdg.integration5.platform.adapters.out.db.entities.InviteJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.LobbyJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InviteRepository extends JpaRepository<InviteJpaEntity, UUID> {
    List<InviteJpaEntity> getInvitesByRecipient(PlayerJpaEntity recipient);
}
