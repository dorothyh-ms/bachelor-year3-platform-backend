package be.kdg.integration5.predictions.adapters.out.predictions.feign;

public class PlayerEngagementPredictionRequest {
    private String username;
    private String game_name;

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

    @Override
    public String toString() {
        return "PlayerEngagementPredictionRequest{" +
                "username='" + username + '\'' +
                ", game_name='" + game_name + '\'' +
                '}';
    }
}