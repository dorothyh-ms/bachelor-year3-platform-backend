package be.kdg.integration5.platform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class InvalidGameURLException extends RuntimeException {
    public InvalidGameURLException(String message) {
        super(message);
    }
}
