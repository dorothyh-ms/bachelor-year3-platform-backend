package be.kdg.integration5.playerstatistics.core;

import be.kdg.integration5.playerstatistics.domain.Match;
import be.kdg.integration5.playerstatistics.domain.PlayerMatch;

import be.kdg.integration5.playerstatistics.ports.in.RecordEndMatchUseCase;
import be.kdg.integration5.playerstatistics.ports.in.RecordMatchEndCommand;
import be.kdg.integration5.playerstatistics.ports.out.MatchLoadPort;
import be.kdg.integration5.playerstatistics.ports.out.MatchUpdatePort;
import be.kdg.integration5.playerstatistics.ports.out.PlayerMatchUpdatePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultEndMatchUseCase implements RecordEndMatchUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEndMatchUseCase.class);
    private final PlayerMatchUpdatePort playerMatchUpdatePort;
    private final MatchLoadPort matchLoadPort;
    private final MatchUpdatePort matchUpdatePort;

    public DefaultEndMatchUseCase( PlayerMatchUpdatePort playerMatchUpdatePort, MatchLoadPort matchLoadPort, MatchUpdatePort matchUpdatePort) {
        this.playerMatchUpdatePort = playerMatchUpdatePort;
        this.matchLoadPort = matchLoadPort;
        this.matchUpdatePort = matchUpdatePort;
    }

    @Override
    public void endMatch(RecordMatchEndCommand command) {
        LOGGER.info("DefaultEndMatchUseCase is running endMatch with command {}", command);
        Optional<Match> matchOptional = matchLoadPort.loadMatchById(command.matchId());

        if (matchOptional.isPresent()){
            Match match = matchOptional.get();
            LOGGER.info("Retrieved {}", match);
            match.end(command.endDateTime());
            command.playerMatchOutcomes().forEach(playerMatchOutcome -> {
                Optional<PlayerMatch> playerMatchOptional = match.getPlayerMatchList()
                        .stream()
                        .filter(pm -> pm.getPlayer().getId().equals(playerMatchOutcome.playerId())).findFirst();
                if (playerMatchOptional.isPresent()){
                    PlayerMatch playerMatch = playerMatchOptional.get();
                    playerMatch.setOutcome(playerMatchOutcome.outcome());
                    playerMatchUpdatePort.updatePlayerMatch(playerMatch);
                }
            });

            LOGGER.info("DefaultEndMatchUseCase is updating match {}", match);
            matchUpdatePort.updateMatch(match);
        }

    }
}
