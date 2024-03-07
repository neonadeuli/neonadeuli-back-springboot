package back.neonadeuli.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;

    protected CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    protected CustomException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}
