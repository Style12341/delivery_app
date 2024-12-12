package deso.delivery_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResourceNotValidException extends RuntimeException {
    public ResourceNotValidException(String message) {
        super(message);
    }
}
