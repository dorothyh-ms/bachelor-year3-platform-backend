package be.kdg.integration5.platform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class ExpiredInviteException extends RuntimeException {
    public ExpiredInviteException(String message) {
        super(message);
    }
}
