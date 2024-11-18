package be.kdg.integration5.platform.adapters.in.web;


import be.kdg.integration5.platform.adapters.in.web.dtos.InviteDto;
import be.kdg.integration5.platform.adapters.in.web.dtos.PlayerDto;
import be.kdg.integration5.platform.domain.Invite;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.in.GetPlayerUseCase;
import be.kdg.integration5.platform.ports.in.PlayerCreatesInviteUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final GetPlayerUseCase getPlayerUseCase;

    public PlayerController(GetPlayerUseCase getPlayerUseCase) {
        this.getPlayerUseCase = getPlayerUseCase;
    }


    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<PlayerDto>> searchUser(@RequestParam String username, @AuthenticationPrincipal Jwt token) {
        List<Player> players = getPlayerUseCase.getPlayers(username);
        if (!players.isEmpty()) {
            return new ResponseEntity<>(players.stream().map(player -> new PlayerDto(player.getPlayerId(), player.getUsername())).toList(),
                            HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
