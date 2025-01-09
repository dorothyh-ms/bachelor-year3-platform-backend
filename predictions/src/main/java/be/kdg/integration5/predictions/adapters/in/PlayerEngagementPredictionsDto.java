package be.kdg.integration5.predictions.adapters.in;

import be.kdg.integration5.predictions.domain.Prediction;

import java.util.List;

public class PlayerEngagementPredictionsDto {
    private String username;
    private String gameName;
    private List<PredictionDto> predictions;

    public PlayerEngagementPredictionsDto() {
    }

    public PlayerEngagementPredictionsDto(String username, String gameName, List<PredictionDto> predictions) {
        this.username = username;
        this.gameName = gameName;
        this.predictions = predictions;
    }

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

    public List<PredictionDto> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionDto> predictions) {
        this.predictions = predictions;
    }
}
