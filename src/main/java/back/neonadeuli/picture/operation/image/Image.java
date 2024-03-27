package back.neonadeuli.picture.operation.image;

import back.neonadeuli.picture.entity.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class Image {

    private final Picture picture;

    public String path() {
        return picture.getSavePath();
    }

    public MultipartFile multipartFile() {
        return picture.getMultipartFile();
    }
}
