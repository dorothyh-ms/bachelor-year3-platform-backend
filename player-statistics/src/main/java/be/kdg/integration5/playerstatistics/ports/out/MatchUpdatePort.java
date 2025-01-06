package be.kdg.integration5.playerstatistics.ports.out;

import be.kdg.integration5.playerstatistics.domain.Match;

public interface MatchUpdatePort {
    public void updateMatch(Match match);
}
