package back.neonadeuli.picture.operation.event;

import back.neonadeuli.picture.exception.SavePictureFailException;
import back.neonadeuli.picture.operation.image.Image;
import back.neonadeuli.picture.operation.image.ImageBundle;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageEventListener {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void writeImages(ImageEvent event) {
        ImageBundle images = event.imageBundle();
        writeDirectories(images);
        images.stream().forEach(ImageEventListener::writeImage);
    }

    private static void writeDirectories(ImageBundle images) {
        Set<File> parentPath = getParentPath(images);

        parentPath.forEach(directory -> {
            try {
                Files.createDirectories(directory.toPath());
            } catch (IOException e) {
                throw new SavePictureFailException(e);
            }
        });
    }

    private static Set<File> getParentPath(ImageBundle images) {
        return images.stream()
                .map(image -> new File(image.path()))
                .map(File::getParentFile)
                .collect(Collectors.toSet());
    }

    private static void writeImage(Image image) {
        MultipartFile multipartFile = image.multipartFile();

        if (Objects.isNull(multipartFile)) {
            throw new SavePictureFailException();
        }

        try {
            File file = new File(image.path());

            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new SavePictureFailException(e);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void afterRollback(ImageEvent event) {
        ImageBundle images = event.imageBundle();
        images.stream().forEach(this::deleteImage);
    }

    private void deleteImage(Image image) {
        String imagePath = image.path();
        try {
            Files.delete(Path.of(imagePath));
        } catch (NoSuchFileException ignore) {
            // ignore
        } catch (IOException e) {
            log.error("delete exception: {}", imagePath, e);
        }
    }
}
