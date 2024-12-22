package be.kdg.integration5.platform.domain;

import be.kdg.integration5.common.domain.Gender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Player {
    private UUID playerId = UUID.randomUUID();
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }


    public Player(UUID playerId, String username) {
        this.playerId = playerId;
        this.username = username;

    }

    public Player() {
    }

    public Invite createInvite(Player recipient, Lobby lobby){
        return new Invite(UUID.randomUUID(), this, recipient, lobby, LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(username, player.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}
