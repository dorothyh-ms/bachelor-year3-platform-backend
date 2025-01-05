package be.kdg.integration5.playerstatistics.domain;

import java.util.List;

public class PlayerEngagementPredictions {

    private String username;
    private String gameName;
    private List<Prediction> predictions;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    @Override
    public String toString() {
        return "PlayerEngagementPredictions{" +
                "username='" + username + '\'' +
                ", game_id='" + gameName + '\'' +
                ", predictions=" + predictions +
                '}';
    }
}