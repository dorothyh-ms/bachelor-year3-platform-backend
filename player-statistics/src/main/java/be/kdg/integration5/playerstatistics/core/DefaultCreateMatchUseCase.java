package be.kdg.integration5.playerstatistics.core;

import be.kdg.integration5.playerstatistics.domain.BoardGame;
import be.kdg.integration5.playerstatistics.domain.Match;
import be.kdg.integration5.playerstatistics.domain.PlayerMatch;
import be.kdg.integration5.playerstatistics.domain.PlayerProfile;
import be.kdg.integration5.playerstatistics.ports.in.CreateMatchUseCase;
import be.kdg.integration5.playerstatistics.ports.in.commands.CreateMatchCommand;
import be.kdg.integration5.playerstatistics.ports.out.BoardGameLoadPort;
import be.kdg.integration5.playerstatistics.ports.out.MatchCreatePort;
import be.kdg.integration5.playerstatistics.ports.out.PlayerProfileLoadPort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultCreateMatchUseCase implements CreateMatchUseCase {
    private MatchCreatePort matchCreatePort;
    private BoardGameLoadPort gameLoadPort;
    private PlayerProfileLoadPort playerProfileLoadPort;


    public DefaultCreateMatchUseCase(MatchCreatePort matchCreatePort, BoardGameLoadPort gameLoadPort, PlayerProfileLoadPort playerProfileLoadPort) {
        this.matchCreatePort = matchCreatePort;
        this.gameLoadPort = gameLoadPort;
        this.playerProfileLoadPort = playerProfileLoadPort;
    }

    @Override
    public void createMatch(CreateMatchCommand createMatchCommand) {
        Optional<BoardGame> boardGameOptional = gameLoadPort.loadBoardGameByName(createMatchCommand.gameName());
        Optional<PlayerProfile> player1Optional = playerProfileLoadPort.loadPlayerProfileById(createMatchCommand.player1Id());
        Optional<PlayerProfile> player2Optional = playerProfileLoadPort.loadPlayerProfileById(createMatchCommand.player2Id());
        if (boardGameOptional.isPresent() && player2Optional.isPresent() && player1Optional.isPresent()){
            PlayerMatch player1Match = new PlayerMatch(player1Optional.get());
            PlayerMatch player2Match = new PlayerMatch(player2Optional.get());
            List<PlayerMatch> playerMatches = new ArrayList<>();
            playerMatches.add(player1Match);
            playerMatches.add(player2Match);
            Match match  = new Match(createMatchCommand.matchId(), boardGameOptional.get(), createMatchCommand.startDateTime(), playerMatches);
            matchCreatePort.matchCreated(match);
        }
    }
}
