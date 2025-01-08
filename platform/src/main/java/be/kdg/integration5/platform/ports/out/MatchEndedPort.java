package be.kdg.integration5.platform.ports.out;

import be.kdg.integration5.platform.domain.Match;

public interface MatchEndedPort {
    public void matchEnded(Match match);
}
