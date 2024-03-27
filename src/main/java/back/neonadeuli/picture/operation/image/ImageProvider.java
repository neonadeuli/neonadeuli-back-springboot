package back.neonadeuli.picture.operation.image;

import back.neonadeuli.annotation.Provider;
import back.neonadeuli.picture.entity.Picture;
import back.neonadeuli.picture.operation.event.ImageEvent;
import java.util.ArrayList;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Provider
@RequiredArgsConstructor
public class ImageProvider {

    private final ApplicationEventPublisher eventPublisher;

    @Transactional(propagation = Propagation.MANDATORY)
    public void provide(Picture picture) {
        Image image = new Image(picture);

        ImageBundle imageBundle =
                (ImageBundle) TransactionSynchronizationManager.getResource(ImageBundle.class);

        if (Objects.isNull(imageBundle)) {
            imageBundle = putImageBundle();
        }

        imageBundle.add(image);
    }

    private ImageBundle putImageBundle() {
        ImageBundle imageBundle = createImageBundleAndPublishEvent();
        TransactionSynchronizationManager.bindResource(ImageBundle.class, imageBundle);
        return imageBundle;
    }

    private ImageBundle createImageBundleAndPublishEvent() {
        ImageBundle imageBundle = new ImageBundle(new ArrayList<>());
        eventPublisher.publishEvent(new ImageEvent(imageBundle));

        return imageBundle;
    }
}
