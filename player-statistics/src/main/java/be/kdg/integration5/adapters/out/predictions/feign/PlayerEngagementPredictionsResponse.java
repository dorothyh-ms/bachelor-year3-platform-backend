package be.kdg.integration5.adapters.out.predictions.feign;

import java.util.List;

public class PlayerEngagementPredictionsResponse {
    private String username;
    private String game_id;
    private List<PredictionResponse> predictions;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public List<PredictionResponse> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionResponse> predictions) {
        this.predictions = predictions;
    }
}
