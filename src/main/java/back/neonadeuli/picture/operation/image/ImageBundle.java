package back.neonadeuli.picture.operation.image;

import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageBundle {
    private final List<Image> images;

    public void add(Image image) {
        images.add(image);
    }

    public Stream<Image> stream() {
        return images.stream();
    }
}
