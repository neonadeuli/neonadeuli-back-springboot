package back.neonadeuli.picture.exception;

import back.neonadeuli.exception.CustomException;
import org.springframework.http.HttpStatus;

public class SavePictureFailException extends CustomException {
    private static final String MESSAGE = "이미지 저장에 실패하였습니다. 다시 시도해주세요.";

    public SavePictureFailException() {
        super(MESSAGE);
    }

    public SavePictureFailException(Exception e) {
        super(MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
