package be.kdg.integration5.platform.adapters.in.web.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class MatchDto {
    private UUID id;
    private String url;
    private String opponentName;
    private String gameName;
    private LocalDateTime dateCreated;

    public MatchDto() {
    }

    public MatchDto(UUID id, String url, String opponentName, String gameName, LocalDateTime dateCreated) {
        this.id = id;
        this.url = url;
        this.opponentName = opponentName;
        this.gameName = gameName;
        this.dateCreated = dateCreated;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
