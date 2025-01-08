package be.kdg.integration5.playerstatistics.core;

import be.kdg.integration5.playerstatistics.domain.PlayerMatch;
import be.kdg.integration5.playerstatistics.ports.in.PlayerMatchProjector;
import be.kdg.integration5.playerstatistics.ports.in.commands.ProjectPlayerMatchCommand;
import be.kdg.integration5.playerstatistics.ports.out.PlayerMatchLoadPort;
import be.kdg.integration5.playerstatistics.ports.out.PlayerMatchUpdatePort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerMatchProjectorImpl implements PlayerMatchProjector {
    private PlayerMatchLoadPort playerMatchLoadPort;
    private PlayerMatchUpdatePort playerMatchUpdatePort;

    public PlayerMatchProjectorImpl(PlayerMatchLoadPort playerMatchLoadPort, PlayerMatchUpdatePort playerMatchUpdatePort) {
        this.playerMatchLoadPort = playerMatchLoadPort;
        this.playerMatchUpdatePort = playerMatchUpdatePort;
    }

    @Override
    public void projectPlayerMatch(ProjectPlayerMatchCommand command) {
        Optional<PlayerMatch> playerMatchOptional = playerMatchLoadPort.loadPlayerMatchByMatchIdAndPlayerId(command.matchId(), command.playerId());
        if (playerMatchOptional.isPresent()){
            PlayerMatch playerMatch = playerMatchOptional.get();
            playerMatch.incrementNumberOfTurns();
            playerMatch.updateScore(command.pointsEarned());
            playerMatchUpdatePort.updatePlayerMatch(playerMatch);
        }

    }
}
