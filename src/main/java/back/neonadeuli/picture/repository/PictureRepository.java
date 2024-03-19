package back.neonadeuli.picture.repository;

import back.neonadeuli.picture.entity.Picture;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, UUID> {
}
