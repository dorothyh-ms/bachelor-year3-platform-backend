package be.kdg.integration5.platform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class PlayerNotAdmittedToLobbyException extends RuntimeException {
    public PlayerNotAdmittedToLobbyException(String message) {
        super(message);
    }
}
