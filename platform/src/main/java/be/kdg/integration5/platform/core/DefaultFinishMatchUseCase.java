package be.kdg.integration5.platform.core;

import be.kdg.integration5.platform.domain.Match;
import be.kdg.integration5.platform.ports.in.EndMatchCommand;
import be.kdg.integration5.platform.ports.in.EndMatchUseCase;
import be.kdg.integration5.platform.ports.out.MatchEndedPort;
import be.kdg.integration5.platform.ports.out.MatchLoadPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultFinishMatchUseCase implements EndMatchUseCase {
    private final MatchLoadPort matchLoadPort;
    private final MatchEndedPort matchEndedPort;

    public DefaultFinishMatchUseCase(MatchLoadPort matchLoadPort, MatchEndedPort matchEndedPort) {
        this.matchLoadPort = matchLoadPort;
        this.matchEndedPort = matchEndedPort;
    }

    @Override
    public void endMatch(EndMatchCommand command) {
        Optional<Match> matchOptional = matchLoadPort.loadById(command.matchId());
        if (matchOptional.isPresent()){
            Match match = matchOptional.get();
            match.end();
            matchEndedPort.matchEnded(match);
        }
    }
}
