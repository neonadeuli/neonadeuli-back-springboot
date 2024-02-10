package back.neonadeuli.exception;

public abstract class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
