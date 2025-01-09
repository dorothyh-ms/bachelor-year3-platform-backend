package be.kdg.integration5.predictions.domain;

import java.time.LocalDate;

public record Prediction(LocalDate date, double predictedMinutes) {
}
