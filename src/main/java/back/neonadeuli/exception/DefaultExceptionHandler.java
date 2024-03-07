package back.neonadeuli.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(CustomException.class)
    ResponseEntity<String> handleCustomException(CustomException customException) {
        return ResponseEntity.status(customException.getHttpStatus())
                .body(customException.getMessage());
    }
}
