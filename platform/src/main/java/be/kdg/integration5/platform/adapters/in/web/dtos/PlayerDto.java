package be.kdg.integration5.platform.adapters.in.web.dtos;

import be.kdg.integration5.platform.domain.Gender;

import java.util.UUID;

public class PlayerDto {
    private UUID playerId;
    private String username;

    public PlayerDto() {
    }

    public PlayerDto(UUID playerId, String username) {
        this.playerId = playerId;
        this.username = username;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
