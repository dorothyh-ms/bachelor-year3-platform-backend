package be.kdg.integration5.platform.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class InvalidPlayerException extends RuntimeException{
    public InvalidPlayerException(String message) {
        super(message);
    }
}
