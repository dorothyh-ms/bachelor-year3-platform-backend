package be.kdg.integration5.platform.adapters.in.web;


import be.kdg.integration5.platform.adapters.in.web.dtos.PlayerDto;
import be.kdg.integration5.platform.domain.Player;
import be.kdg.integration5.platform.ports.in.GetPlayersUsesCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final GetPlayersUsesCase getPlayersUsesCase;

    public PlayerController(GetPlayersUsesCase getPlayersUsesCase) {
        this.getPlayersUsesCase = getPlayersUsesCase;
    }


    @GetMapping
    public ResponseEntity<List<PlayerDto>> searchUser(@RequestParam String username) {
        List<Player> players = getPlayersUsesCase.getPlayers(username);
        if (!players.isEmpty()) {
            return new ResponseEntity<>(players.stream().map(player -> new PlayerDto(player.getPlayerId(), player.getUsername())).toList(),
                            HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
