package be.kdg.integration5.platform.adapters.in.web.dtos;

import be.kdg.integration5.platform.domain.Player;

import java.time.LocalDateTime;
import java.util.UUID;

public class LobbyDto {
    private UUID id;

    private GameDto gameDto;

    private PlayerDto createdBy;

    private LocalDateTime createdDate;

    public LobbyDto(UUID id, GameDto gameDto, PlayerDto createdBy, LocalDateTime createdDate) {
        this.id = id;
        this.gameDto = gameDto;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GameDto getGameDto() {
        return gameDto;
    }

    public void setGameDto(GameDto gameDto) {
        this.gameDto = gameDto;
    }

    public PlayerDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(PlayerDto createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
