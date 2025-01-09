package be.kdg.integration5.platform.adapters.in.web;

import be.kdg.integration5.platform.adapters.in.web.dtos.MatchDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.PlayerDto;
import be.kdg.integration5.platform.domain.Match;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.in.GetAvailableMatchesUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchController.class);

    private GetAvailableMatchesUseCase getAvailableMatchesUseCase;

    public MatchController(GetAvailableMatchesUseCase getAvailableMatchesUseCase) {
        this.getAvailableMatchesUseCase = getAvailableMatchesUseCase;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<MatchDto>> getAvailableMatches(@AuthenticationPrincipal Jwt token) {
        UUID playerId = UUID.fromString(token.getClaimAsString("sub"));
        LOGGER.info("MatchController is running getAvailableMatches with player id {}", playerId);
        List<Match> matches = getAvailableMatchesUseCase.getUnfinishedMatchesOfPlayer(playerId);
        if (matches.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(matches
                .stream()
                .map(match ->
                    {
                        Player opponent = match.getPlayer1().getPlayerId().equals(playerId) ? match.getPlayer2() : match.getPlayer1();
                        return new MatchDto(
                            match.getId(),
                            match.getUrl(),
                            opponent.getUsername(),
                            match.getGame().getName(),
                                match.getDateCreated()
                        );
                    }
        ).toList(), HttpStatus.OK);
    }
}
