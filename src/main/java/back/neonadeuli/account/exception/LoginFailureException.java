package back.neonadeuli.account.exception;

import back.neonadeuli.exception.CustomException;
import org.springframework.http.HttpStatus;

public class LoginFailureException extends CustomException {
    public LoginFailureException() {
        super("ID 혹은 PW가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
    }
}
