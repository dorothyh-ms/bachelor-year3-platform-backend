package be.kdg.integration5.playerstatistics.adapters.out.db;

import be.kdg.integration5.playerstatistics.adapters.out.db.entities.PlayerLoginJpaEntity;
import be.kdg.integration5.playerstatistics.adapters.out.db.entities.PlayerProfileJpaEntity;
import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.PlayerLoginRepository;
import be.kdg.integration5.playerstatistics.adapters.out.db.repositories.PlayerProfileRepository;
import be.kdg.integration5.playerstatistics.domain.PlayerLogin;
import be.kdg.integration5.playerstatistics.domain.PlayerProfile;
import be.kdg.integration5.playerstatistics.ports.out.PlayerLoginCreatePort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlayerLoginDbAdapter implements PlayerLoginCreatePort {
    private final PlayerLoginRepository playerLoginRepository;
    private final PlayerProfileRepository playerProfileRepository;

    public PlayerLoginDbAdapter(PlayerLoginRepository playerLoginRepository, PlayerProfileRepository playerProfileRepository) {
        this.playerLoginRepository = playerLoginRepository;
        this.playerProfileRepository = playerProfileRepository;
    }

    @Override
    public void playerLoggedIn(PlayerLogin playerLogin) {
        PlayerProfile player = playerLogin.getPlayer();
        Optional<PlayerProfileJpaEntity> playerProfileOptional = playerProfileRepository.findById(player.getId());
        playerProfileOptional.ifPresent(playerProfileJpaEntity -> playerLoginRepository.save(new PlayerLoginJpaEntity(
                playerLogin.getId(),
                playerProfileJpaEntity,
                playerLogin.getDate()
        )));
    }
}
