package be.kdg.integration5.platform.adapters.out.db.repositories;

import be.kdg.integration5.platform.adapters.out.db.entities.LobbyJpaEntity;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.domain.LobbyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LobbyRepository extends JpaRepository<LobbyJpaEntity, UUID> {

    List<LobbyJpaEntity> getAllByLobbyStatusIs(LobbyStatus status);
}
