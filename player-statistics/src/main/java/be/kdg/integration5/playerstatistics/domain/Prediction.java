package be.kdg.integration5.playerstatistics.domain;

import java.time.LocalDate;

public record Prediction(LocalDate date, double predictedMinutes) {
}
