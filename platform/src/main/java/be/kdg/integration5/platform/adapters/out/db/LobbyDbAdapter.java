package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.InviteJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.LobbyJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.GameMapper;
import be.kdg.integration5.platform.adapters.out.db.mappers.InviteMapper;
import be.kdg.integration5.platform.adapters.out.db.mappers.LobbyMapper;
import be.kdg.integration5.platform.adapters.out.db.mappers.PlayerMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.InviteRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.LobbyRepository;
import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.Lobby;
import be.kdg.integration5.platform.domain.LobbyStatus;
import be.kdg.integration5.platform.ports.out.*;
import jakarta.persistence.Lob;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LobbyDbAdapter implements LobbyCreatePort, LobbyJoinedPort, LobbyLoadPort, InviteCreatePort, InviteLoadPort, InviteUpdatePort {

    private final LobbyRepository lobbyRepository;
    private final InviteRepository inviteRepository;

    public LobbyDbAdapter(LobbyRepository lobbyRepository, InviteRepository inviteRepository) {
        this.lobbyRepository = lobbyRepository;
        this.inviteRepository = inviteRepository;
    }


    @Override
    public List<Lobby> loadActiveLobbies() {
        List<LobbyJpaEntity> lobbyJpaEntityList = lobbyRepository.getAllByLobbyStatusIs(LobbyStatus.OPEN);
        if (lobbyJpaEntityList != null){
            List<Lobby> lobbyList = new ArrayList<>();
            lobbyJpaEntityList.forEach(lobbyJpaEntity -> lobbyList.add(LobbyMapper.toLobby(lobbyJpaEntity)));
            return lobbyList;
        } else {
            return List.of();
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

    @Override
    public void createInvite(Invite invite) {
        inviteRepository.save(InviteMapper.toInviteJpaEntity(invite));
    }

    @Override
    public Optional<Invite> loadInvite(UUID inviteId) {
        Optional<InviteJpaEntity> invite = inviteRepository.findById(inviteId);
        return invite.map(InviteMapper::toInvite);

    }

    @Override
    public void updateInvite(Invite invite) {
        inviteRepository.save(InviteMapper.toInviteJpaEntity(invite));
    }
}
