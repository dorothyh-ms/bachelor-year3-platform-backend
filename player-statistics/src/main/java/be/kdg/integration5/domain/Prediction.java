package be.kdg.integration5.domain;

import java.time.LocalDate;

public record Prediction(LocalDate date, int predictedMinutes) {
}
