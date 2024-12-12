package be.kdg.integration5.platform.adapters.in.web.dtos;

import be.kdg.integration5.platform.domain.LobbyStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class LobbyDto {
    private UUID id;
    private GameDto game;
    private PlayerDto createdBy;
    private PlayerDto joinedPlayer;
    private LocalDateTime createdDate;
    private LobbyStatus lobbyStatus;
    private String matchURL;

    public LobbyDto(UUID id, GameDto game, PlayerDto createdBy, LocalDateTime createdDate, LobbyStatus lobbyStatus) {
        this.id = id;
        this.game = game;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lobbyStatus = lobbyStatus;
    }

    public LobbyDto(UUID id, GameDto game, PlayerDto createdBy, PlayerDto joinedPlayer, LocalDateTime createdDate, LobbyStatus lobbyStatus) {
        this.id = id;
        this.game = game;
        this.createdBy = createdBy;
        this.joinedPlayer = joinedPlayer;
        this.createdDate = createdDate;
        this.lobbyStatus = lobbyStatus;
    }

    public LobbyDto(UUID id, GameDto game, PlayerDto createdBy, PlayerDto joinedPlayer, LocalDateTime createdDate, LobbyStatus lobbyStatus, String matchURL) {
        this.id = id;
        this.game = game;
        this.createdBy = createdBy;
        this.joinedPlayer = joinedPlayer;
        this.createdDate = createdDate;
        this.lobbyStatus = lobbyStatus;
        this.matchURL = matchURL;
    }

    public LobbyStatus getLobbyStatus() {
        return lobbyStatus;
    }

    public void setLobbyStatus(LobbyStatus lobbyStatus) {
        this.lobbyStatus = lobbyStatus;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
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

    public PlayerDto getJoinedPlayer() {
        return joinedPlayer;
    }

    public void setJoinedPlayer(PlayerDto joinedPlayer) {
        this.joinedPlayer = joinedPlayer;
    }

    public String getMatchURL() {
        return matchURL;
    }

    public void setMatchURL(String matchURL) {
        this.matchURL = matchURL;
    }
}
