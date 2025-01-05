package be.kdg.integration5.playerstatistics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class PlayerProfileNotFoundException extends RuntimeException{
    public PlayerProfileNotFoundException(String message) {
        super(message);
    }
}
