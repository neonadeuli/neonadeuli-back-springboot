package back.neonadeuli.picture.model;

import back.neonadeuli.picture.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

public record PictureTemp(Picture picture, MultipartFile multipartFile) {

}
