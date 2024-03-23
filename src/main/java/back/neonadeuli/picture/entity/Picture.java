package back.neonadeuli.picture.entity;

import back.neonadeuli.picture.exception.SavePictureFailException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "pictures")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Picture {

    public static final UUID DEFAULT_UUID = UUID.fromString("222f1dcb-80fa-46fe-a055-1214fe176561");

    @Id
    @Column
    @UuidGenerator
    private UUID savedName;

    @Column
    @NotNull
    private String folderName;

    @Column
    @NotNull
    private String originName;

    @Column
    @NotNull
    private String extension;

    @Transient
    private MultipartFile multipartFile;

    private Picture(String folderName, String originName, String extension, MultipartFile multipartFile) {
        this.folderName = folderName;
        this.originName = originName;
        this.extension = extension;
        this.multipartFile = multipartFile;
    }

    public String getSavePath() {
        return folderName + "/" + savedName + "." + extension;
    }

    public static List<Picture> fromMultipartFiles(String folderName, List<MultipartFile> multipartFiles) {

        if (Objects.isNull(multipartFiles)) {
            return Collections.emptyList();
        }

        return multipartFiles.stream()
                .map(multipartFile -> fromMultipartFile(folderName, multipartFile))
                .filter(Objects::nonNull)
                .toList();
    }

    public static Picture fromMultipartFile(String folderName, MultipartFile multipartFile) {
        return ImageProcessor.createPictureFromMultipartFile(folderName, multipartFile);
    }


    @PostPersist
    public void saveImage() {
        ImageProcessor.writeImage(this);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class ImageProcessor {
        private static final String IMAGE = "image";

        private static Picture createPictureFromMultipartFile(String folderName, MultipartFile multipartFile) {
            if (isNotImage(multipartFile)) {
                return null;
            }

            String originalFilename = multipartFile.getOriginalFilename();

            if (Objects.isNull(originalFilename)) {
                throw new IllegalArgumentException();
            }

            String filenameExtension = StringUtils.getFilenameExtension(originalFilename);
            String stripFilenameExtension = StringUtils.stripFilenameExtension(originalFilename);
            return new Picture(folderName, stripFilenameExtension, filenameExtension, multipartFile);
        }

        private static boolean isNotImage(MultipartFile multipartFile) {
            String contentType = multipartFile.getContentType();

            if (Objects.isNull(contentType)) {
                return true;
            }

            return !contentType.startsWith(IMAGE);
        }

        private static void writeImage(Picture picture) {
            MultipartFile multipartFile = picture.getMultipartFile();

            if (Objects.isNull(multipartFile)) {
                throw new SavePictureFailException();
            }

            try {
                File file = new File(picture.getSavePath());
                multipartFile.transferTo(file);
            } catch (IOException e) {
                throw new SavePictureFailException(e);
            }
        }
    }
}
