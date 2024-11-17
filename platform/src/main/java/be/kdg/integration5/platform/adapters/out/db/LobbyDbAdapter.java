package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.LobbyJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.mappers.LobbyMapper;
import be.kdg.integration5.platform.adapters.out.db.mappers.PlayerMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.LobbyRepository;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.domain.LobbyStatus;
import be.kdg.integration5.platform.ports.out.LobbyCreatePort;
import be.kdg.integration5.platform.ports.out.LobbyJoinedPort;
import be.kdg.integration5.platform.ports.out.LobbyLoadPort;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import jakarta.persistence.Lob;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LobbyDbAdapter implements LobbyCreatePort, LobbyJoinedPort, LobbyLoadPort {

    private final LobbyRepository lobbyRepository;

    public LobbyDbAdapter(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }


    @Override
    public List<Lobby> loadLobbiesByActiveStatus() {
        List<LobbyJpaEntity> lobbyJpaEntityList = lobbyRepository.getAllByLobbyStatusIs(LobbyStatus.OPEN);
        if (lobbyJpaEntityList != null){
            List<Lobby> lobbyList = new ArrayList<>();
            lobbyJpaEntityList.forEach(lobbyJpaEntity -> lobbyList.add(LobbyMapper.toLobby(lobbyJpaEntity)));
            return lobbyList;
        } else {
            return null;
        }
    }

    @Override
    public Optional<Lobby> loadLobby(UUID lobbyId) {
        Optional<LobbyJpaEntity> lobbyJpaEntityOptional = lobbyRepository.findById(lobbyId);
        if (lobbyJpaEntityOptional.isPresent()){
            LobbyJpaEntity lobbyJpa = lobbyJpaEntityOptional.get();
            return Optional.of(new Lobby(
                    lobbyJpa.getId(),
                    GameMapper.toGame(lobbyJpa.getGame()),
                    PlayerMapper.toPlayer(lobbyJpa.getInitiatingPlayer()),
                    PlayerMapper.toPlayer(lobbyJpa.getJoinedPlayer()),
                    lobbyJpa.getLobbyStatus(),
                    lobbyJpa.getDateCreated()
                    ));
        }
        return Optional.empty();
    }

    @Override
    public void lobbyCreated(Lobby lobby) {
        lobbyRepository.save(new LobbyJpaEntity(
                lobby.getId(),
                GameMapper.toGameJpaEntity(lobby.getGame()),
                PlayerMapper.toPlayerJpaEntity(lobby.getInitiatingPlayer()),
                lobby.getDateCreated(),
                lobby.getStatus()
        ));
    }

    @Override
    public void lobbyJoined(Lobby lobby) {
        lobbyRepository.save(new LobbyJpaEntity(
                lobby.getId(),
                GameMapper.toGameJpaEntity(lobby.getGame()),
                PlayerMapper.toPlayerJpaEntity(lobby.getInitiatingPlayer()),
                lobby.getDateCreated(),
                lobby.getStatus(),
                PlayerMapper.toPlayerJpaEntity(lobby.getJoinedPlayer())
        ));
    }
}
