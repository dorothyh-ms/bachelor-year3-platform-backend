package be.kdg.integration5.predictions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class GameOrPlayerNotFound extends RuntimeException {
    public GameOrPlayerNotFound(String message) {
        super(message);
    }
}
