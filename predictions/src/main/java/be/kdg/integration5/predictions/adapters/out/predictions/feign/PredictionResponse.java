package be.kdg.integration5.predictions.adapters.out.predictions.feign;
public class PredictionResponse {
    private String date;
    private double predicted_minutes;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPredicted_minutes() {
        return predicted_minutes;
    }

    public void setPredicted_minutes(double predicted_minutes) {
        this.predicted_minutes = predicted_minutes;
    }
}