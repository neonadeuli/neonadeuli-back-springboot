package back.neonadeuli.picture.model;

import back.neonadeuli.picture.entity.Picture;
import back.neonadeuli.picture.exception.SavePictureFailException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import lombok.Getter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Component
public class PictureSupplier {

    private static final String POST_PICTURE_FOLDER_NAME = "neonadeuli.post.picture.folder-name";
    private static final String IMAGE = "image";

    private final String postPictureFolderName;

    public PictureSupplier(Environment env) {
        this.postPictureFolderName = env.getProperty(POST_PICTURE_FOLDER_NAME);
    }

    public boolean isImage(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();

        if (Objects.isNull(contentType)) {
            return false;
        }

        return contentType.startsWith(IMAGE);
    }

    public PictureTemp createPictureTemp(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originalFilename);
        return new PictureTemp(new Picture(postPictureFolderName, originalFilename, extension), multipartFile);
    }

    public void savePicture(PictureTemp pictureTemp) {
        Picture picture = pictureTemp.picture();
        MultipartFile multipartFile = pictureTemp.multipartFile();

        try {
            File file = new File(picture.getSavePath());

            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new SavePictureFailException(e);
        }
    }
}
