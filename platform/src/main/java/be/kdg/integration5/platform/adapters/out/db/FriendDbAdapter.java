package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.FriendshipJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.entities.PlayerJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.PlayerMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.FriendRepository;
import be.kdg.integration5.platform.adapters.out.db.repositories.PlayerRepository;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.out.FriendLoadPort;
import be.kdg.integration5.platform.ports.out.FriendSavePort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class FriendDbAdapter implements FriendLoadPort, FriendSavePort {

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
                .map(playerEntity -> friendRepository.findByPlayerOrFriend(playerEntity, playerEntity)
                        .stream()
                        .map(friendship -> friendship.getPlayer().getPlayerId().equals(playerId)
                                ? friendship.getFriend()
                                : friendship.getPlayer())

                        .map(PlayerMapper::toPlayer)
                        .toList())
                .orElse(List.of());
    }

    @Override
    @Transactional
    public void saveFriendship(UUID playerId, UUID friendId) {
        PlayerJpaEntity playerEntity = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found for ID: " + playerId));
        PlayerJpaEntity friendEntity = playerRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found for ID: " + friendId));

        // Check if the friendship exists in either direction
        if (friendRepository.arePlayersFriends(playerEntity, friendEntity)) {
            throw new IllegalStateException("Friendship already exists.");
        }

        // Save the friendship
        FriendshipJpaEntity friendship = new FriendshipJpaEntity(playerEntity, friendEntity);
        friendRepository.save(friendship);
    }


    @Override
    @Transactional(readOnly = true)
    public boolean arePlayersFriends(UUID playerId, UUID friendId) {
        PlayerJpaEntity playerEntity = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found for ID: " + playerId));
        PlayerJpaEntity friendEntity = playerRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found for ID: " + friendId));

        return friendRepository.arePlayersFriends(playerEntity, friendEntity);
    }
}
