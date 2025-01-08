package be.kdg.integration5.platform.ports.in;

import be.kdg.integration5.platform.domain.Match;

import java.util.List;
import java.util.UUID;

public interface GetAvailableMatchesUseCase {

    public List<Match> getUnfinishedMatchesOfPlayer(UUID playerId);
}
