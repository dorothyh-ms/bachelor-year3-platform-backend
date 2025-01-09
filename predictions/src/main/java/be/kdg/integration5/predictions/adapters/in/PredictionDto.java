package be.kdg.integration5.predictions.adapters.in;

import java.time.LocalDate;

public class PredictionDto {
   private LocalDate date;
   double predictedMinutes;

    public PredictionDto() {
    }

    public PredictionDto(LocalDate date, double predictedMinutes) {
        this.date = date;
        this.predictedMinutes = predictedMinutes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPredictedMinutes() {
        return predictedMinutes;
    }

    public void setPredictedMinutes(double predictedMinutes) {
        this.predictedMinutes = predictedMinutes;
    }
}
