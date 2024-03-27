package back.neonadeuli.picture.operation.picture;

import back.neonadeuli.annotation.Supplier;
import back.neonadeuli.picture.entity.Picture;
import back.neonadeuli.picture.operation.image.ImageProvider;
import back.neonadeuli.picture.repository.PictureRepository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Supplier
@RequiredArgsConstructor
public class PictureSupplier {

    private static final String IMAGE = "image";

    private final ImageProvider imageProvider;
    private final PictureRepository pictureRepository;

    public List<Picture> supply(String folderName, List<MultipartFile> multipartFiles) {

        if (Objects.isNull(multipartFiles)) {
            return Collections.emptyList();
        }

        List<Picture> pictures = multipartFiles.stream()
                .map(multipartFile -> fromMultipartFile(folderName, multipartFile))
                .filter(Objects::nonNull)
                .toList();

        pictureRepository.saveAllAndFlush(pictures);

        return pictures;
    }

    private Picture fromMultipartFile(String folderName, MultipartFile multipartFile) {

        if (isNotImage(multipartFile)) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();

        if (Objects.isNull(originalFilename)) {
            throw new IllegalArgumentException();
        }

        String filenameExtension = StringUtils.getFilenameExtension(originalFilename);
        String stripFilenameExtension = StringUtils.stripFilenameExtension(originalFilename);
        Picture picture = new Picture(folderName, stripFilenameExtension, filenameExtension, multipartFile);

        imageProvider.provide(picture);

        return picture;
    }

    private boolean isNotImage(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();

        if (Objects.isNull(contentType)) {
            return true;
        }

        return !contentType.startsWith(IMAGE);
    }
}
