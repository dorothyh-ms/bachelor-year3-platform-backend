package be.kdg.integration5.platform.core;


import be.kdg.integration5.platform.domain.Game;
import be.kdg.integration5.platform.domain.Match;
import be.kdg.integration5.platform.domain.MatchStatus;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.in.CreateMatchCommand;
import be.kdg.integration5.platform.ports.in.CreateMatchUseCase;
import be.kdg.integration5.platform.ports.out.GameLoadPort;
import be.kdg.integration5.platform.ports.out.MatchCreatedPort;
import be.kdg.integration5.platform.ports.out.PlayerLoadPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultStartNewMatchUseCase implements CreateMatchUseCase {
    private PlayerLoadPort playerLoadPort;
    private GameLoadPort gameLoadPort;
    private MatchCreatedPort matchCreatedPort;

    public DefaultStartNewMatchUseCase(PlayerLoadPort playerLoadPort, GameLoadPort gameLoadPort, MatchCreatedPort matchCreatedPort) {
        this.playerLoadPort = playerLoadPort;
        this.gameLoadPort = gameLoadPort;
        this.matchCreatedPort = matchCreatedPort;
    }


    @Override
    public void createMatch(CreateMatchCommand createMatchCommand) {
        Optional<Player> player1Optional = playerLoadPort.loadPlayerById(createMatchCommand.player1Id());
        Optional<Player> player2Optional = playerLoadPort.loadPlayerById(createMatchCommand.player2Id());
        Optional<Game> gameOptional = gameLoadPort.loadGameByName(createMatchCommand.gameName());
        if (player1Optional.isPresent() && player2Optional.isPresent() && gameOptional.isPresent()){
            Game game = gameOptional.get();

            String matchUrl = game.getUrl() + createMatchCommand.matchId().toString();

            Match match = new Match(
                    createMatchCommand.matchId(),
                    player1Optional.get(),
                    player2Optional.get(),
                    game,
                    matchUrl,
                    MatchStatus.IN_PROGRESS,
                    createMatchCommand.startDateTime()
            );
            matchCreatedPort.matchCreated(match);
        }

    }
}
