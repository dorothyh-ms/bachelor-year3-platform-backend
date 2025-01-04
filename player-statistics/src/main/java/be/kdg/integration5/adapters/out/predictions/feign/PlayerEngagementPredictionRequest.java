package be.kdg.integration5.adapters.out.predictions.feign;

public class PlayerEngagementPredictionRequest {
    private String username;
    private String game_id;

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
}