package be.kdg.integration5.playerstatistics.ports.out;

import be.kdg.integration5.playerstatistics.domain.Match;

public interface MatchCreatePort {
    public Match matchCreated(Match match);
}
