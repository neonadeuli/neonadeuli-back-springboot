package back.neonadeuli.picture.operation.picture;

import back.neonadeuli.annotation.Reader;
import back.neonadeuli.picture.entity.Picture;
import back.neonadeuli.picture.repository.PictureRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Reader
@RequiredArgsConstructor
public class PictureReader {

    public static final UUID DEFAULT_UUID = UUID.fromString("222f1dcb-80fa-46fe-a055-1214fe176561");

    private final PictureRepository pictureRepository;

    public Picture getReferenceDefault() {
        return pictureRepository.getReferenceById(DEFAULT_UUID);
    }
}
