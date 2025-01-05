package be.kdg.integration5.playerstatistics.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class PlayerLogin {
    private UUID id;

    private PlayerProfile player;
    private LocalDateTime date;

    public PlayerLogin(PlayerProfile player) {
        this.id = UUID.randomUUID();
        this.player = player;
        this.date = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PlayerProfile getPlayer() {
        return player;
    }

    public void setPlayer(PlayerProfile player) {
        this.player = player;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
