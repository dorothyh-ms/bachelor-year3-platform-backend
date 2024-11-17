package be.kdg.integration5.platform.adapters.out.db.mappers;

import be.kdg.integration5.platform.adapters.out.db.entities.LobbyJpaEntity;
import be.kdg.integration5.platform.domain.Lobby;

public class LobbyMapper {

    // Convert LobbyJpaEntity to Lobby
    public static Lobby toLobby(LobbyJpaEntity lobbyJpaEntity) {
        if (lobbyJpaEntity == null) {
            return null;
        }

        Lobby lobby = new Lobby();
        lobby.setId(lobbyJpaEntity.getId());
        lobby.setGame(GameMapper.toGame(lobbyJpaEntity.getGame()));
        lobby.setInitiatingPlayer(PlayerMapper.toPlayer(lobbyJpaEntity.getInitiatingPlayer()));
        lobby.setJoinedPlayer(PlayerMapper.toPlayer(lobbyJpaEntity.getJoinedPlayer()));
        lobby.setStatus(lobbyJpaEntity.getLobbyStatus());
        lobby.setDateCreated(lobbyJpaEntity.getDateCreated());

        return lobby;
    }

    // Convert Lobby to LobbyJpaEntity
    public static LobbyJpaEntity toLobbyJpaEntity(Lobby lobby) {
        if (lobby == null) {
            return null;
        }

        LobbyJpaEntity lobbyJpaEntity = new LobbyJpaEntity();
        lobbyJpaEntity.setId(lobby.getId());
        lobbyJpaEntity.setGame(GameMapper.toGameJpaEntity(lobby.getGame()));
        lobbyJpaEntity.setInitiatingPlayer(PlayerMapper.toPlayerJpaEntity(lobby.getInitiatingPlayer()));
        lobbyJpaEntity.setJoinedPlayer(PlayerMapper.toPlayerJpaEntity(lobby.getJoinedPlayer()));
        lobbyJpaEntity.setLobbyStatus(lobby.getStatus());
        lobbyJpaEntity.setDateCreated(lobby.getDateCreated());

        return lobbyJpaEntity;
    }
}
