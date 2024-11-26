package be.kdg.integration5.platform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class InvalidInviteUserException extends RuntimeException{
    public InvalidInviteUserException(String message) {
        super(message);
    }
}
