package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.PlayerMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.FriendRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.PlayerRepository;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PlayerDbAdapter implements PlayerLoadPort {
    private final PlayerRepository playerRepository;


    public PlayerDbAdapter(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> loadPlayers(String username) {
        return playerRepository.findPlayerJpaEntitiesByUsernameContainingIgnoreCase(username).stream().map(PlayerMapper::toPlayer).toList();
    }

    @Override
    public Optional<Player> loadPlayerById(UUID uuid) {
        Optional<PlayerJpaEntity> optionalPlayerJpaEntity = playerRepository.findById(uuid);

        if (optionalPlayerJpaEntity.isPresent()){
            PlayerJpaEntity playerJpaEntity = optionalPlayerJpaEntity.get();
            return Optional.of(PlayerMapper.toPlayer(playerJpaEntity));
        }
        return Optional.empty();
    }
}
