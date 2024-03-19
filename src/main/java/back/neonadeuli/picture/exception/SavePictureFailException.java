package back.neonadeuli.picture.exception;

import back.neonadeuli.exception.CustomException;
import org.springframework.http.HttpStatus;

public class SavePictureFailException extends CustomException {

    public SavePictureFailException(Exception e) {
        super("이미지 저장에 실패하였습니다. 다시 시도해주세요.", e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
