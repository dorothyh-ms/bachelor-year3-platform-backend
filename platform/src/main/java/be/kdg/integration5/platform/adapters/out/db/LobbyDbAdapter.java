package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.LobbyJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.mappers.UserMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.LobbyRepository;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.ports.out.LobbyCreatePort;
import org.springframework.stereotype.Repository;

@Repository
public class LobbyDbAdapter implements LobbyCreatePort {

    private final LobbyRepository lobbyRepository;

    public LobbyDbAdapter(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    @Override
    public void lobbyCreated(Lobby lobby) {
        lobbyRepository.save(new LobbyJpaEntity(
                lobby.getId(),
                GameMapper.toGameJpaEntity(lobby.getGame()),
                UserMapper.toUserEntity(lobby.getInitiatingPlayer()),
                lobby.getDateCreated(),
                lobby.getStatus()
        ));
    }
}
