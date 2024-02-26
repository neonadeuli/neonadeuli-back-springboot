package back.neonadeuli.exception;

import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidFailureException extends CustomException {

    public ValidFailureException(BindingResult bindingResult) {
        super(createExceptionMessage(bindingResult));
    }

    private static String createExceptionMessage(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, ValidFailureException::getDefaultMessage))
                .toString();
    }

    private static String getDefaultMessage(FieldError fieldError) {
        if (Objects.isNull(fieldError.getDefaultMessage())) {
            return "조건이 충족되지 않았습니다.";
        }

        return fieldError.getDefaultMessage();
    }
}
