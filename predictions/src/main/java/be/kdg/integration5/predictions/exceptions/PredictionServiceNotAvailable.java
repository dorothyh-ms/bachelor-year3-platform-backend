package be.kdg.integration5.predictions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.SERVICE_UNAVAILABLE)
public class PredictionServiceNotAvailable extends RuntimeException {
    public PredictionServiceNotAvailable(String message) {
        super(message);
    }
}
