package be.kdg.integration5.platform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class LobbyClosedException extends RuntimeException {
    public LobbyClosedException(String message) {
        super(message);
    }
}
