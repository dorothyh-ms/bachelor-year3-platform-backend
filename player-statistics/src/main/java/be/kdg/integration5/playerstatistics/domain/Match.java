package be.kdg.integration5.playerstatistics.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Match {
    private UUID id;
    private BoardGame boardGame;
    private List<PlayerMatch> playerMatchList;

    private LocalDateTime startTime;

    private LocalDateTime endTime;


    private MatchStatus status;

    public Match(UUID id, BoardGame boardGame, LocalDateTime startTime, List<PlayerMatch> playerMatchList) {
        this.id = id;
        this.boardGame = boardGame;
        this.playerMatchList = playerMatchList;
        this.startTime = startTime;
        this.endTime = null;
        this.status = MatchStatus.IN_PROGRESS;
    }

    public Match(UUID id, BoardGame boardGame, List<PlayerMatch> playerMatchList, LocalDateTime startTime, LocalDateTime endTime, MatchStatus status) {
        this.id = id;
        this.boardGame = boardGame;
        this.playerMatchList = playerMatchList;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BoardGame getGame() {
        return boardGame;
    }

    public void setGame(BoardGame boardGame) {
        this.boardGame = boardGame;
    }

    public List<PlayerMatch> getPlayerMatchList() {
        return playerMatchList;
    }

    public void setPlayerMatchList(List<PlayerMatch> playerMatchList) {
        this.playerMatchList = playerMatchList;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(BoardGame boardGame) {
        this.boardGame = boardGame;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void end(LocalDateTime endTime) {
        this.status = MatchStatus.COMPLETED;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", boardGame=" + boardGame +
                ", playerMatchList=" + playerMatchList +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}
