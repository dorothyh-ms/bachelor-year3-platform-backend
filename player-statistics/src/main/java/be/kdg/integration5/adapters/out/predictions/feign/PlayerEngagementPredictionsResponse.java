package be.kdg.integration5.adapters.out.predictions.feign;

import java.util.List;

public class PlayerEngagementPredictionsResponse {
    private String username;
    private String game_name;
    private List<PredictionResponse> predictions;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public List<PredictionResponse> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionResponse> predictions) {
        this.predictions = predictions;
    }
}
