package be.kdg.integration5.platform.adapters.out.db;

import be.kdg.integration5.platform.adapters.out.db.entities.InviteJpaEntity;
import be.kdg.integration5.platform.adapters.out.db.mappers.InviteMapper;
import be.kdg.integration5.platform.adapters.out.db.mappers.PlayerMapper;
import be.kdg.integration5.platform.adapters.out.db.repositories.InviteRepository;
import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.out.InviteCreatePort;
import be.kdg.integration5.platform.ports.out.InviteLoadPort;
import be.kdg.integration5.platform.ports.out.InviteUpdatePort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InviteDbAdapter implements InviteCreatePort, InviteLoadPort, InviteUpdatePort {
    private final InviteRepository inviteRepository;

    public InviteDbAdapter(InviteRepository inviteRepository) {
        this.inviteRepository = inviteRepository;
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
    public List<Invite> loadAllInvitesOfUser(Player player) {
        return inviteRepository.getInvitesByRecipient(PlayerMapper.toPlayerJpaEntity(player)).stream().map(InviteMapper::toInvite).toList();
    }

    @Override
    public void updateInvite(Invite invite) {
        inviteRepository.save(InviteMapper.toInviteJpaEntity(invite));
    }
}
