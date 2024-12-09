package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.FriendshipJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.PlayerMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.FriendRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.PlayerRepository;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.out.FriendLoadPort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FriendDbAdapter implements FriendLoadPort {

    private final FriendRepository friendRepository;
    private final PlayerRepository playerRepository;

    public FriendDbAdapter(FriendRepository friendRepository, PlayerRepository playerRepository) {
        this.friendRepository = friendRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Player> loadFriendsByPlayerId(UUID playerId) {
        return playerRepository.findById(playerId)
                .map(playerEntity -> friendRepository.findByPlayer(playerEntity).stream()
                        .map(friendship -> PlayerMapper.toPlayer(friendship.getFriend()))
                        .toList())
                .orElse(List.of());
    }

    @Override
    @Transactional
    public void saveFriendship(UUID playerId, UUID friendId) {
        Optional<PlayerJpaEntity> playerEntityOpt = playerRepository.findById(playerId);
        Optional<PlayerJpaEntity> friendEntityOpt = playerRepository.findById(friendId);

        if (playerEntityOpt.isEmpty() || friendEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("Player or Friend not found for the given IDs.");
        }

        PlayerJpaEntity playerEntity = playerEntityOpt.get();
        PlayerJpaEntity friendEntity = friendEntityOpt.get();

        // Check if the friendship already exists to prevent duplicates
        boolean alreadyExists = friendRepository.existsByPlayerAndFriend(playerEntity, friendEntity);
        if (alreadyExists) {
            throw new IllegalStateException("Friendship already exists.");
        }

        // Save the bidirectional friendship
        FriendshipJpaEntity friendship = new FriendshipJpaEntity(playerEntity, friendEntity);
        FriendshipJpaEntity reverseFriendship = new FriendshipJpaEntity(friendEntity, playerEntity);

        friendRepository.save(friendship);
        friendRepository.save(reverseFriendship);
    }
}
