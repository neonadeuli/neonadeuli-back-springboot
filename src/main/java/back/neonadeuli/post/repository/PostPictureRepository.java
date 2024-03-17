package back.neonadeuli.post.repository;

import back.neonadeuli.post.entity.PostPicture;
import back.neonadeuli.post.entity.PostPicture.PostPictureId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostPictureRepository extends JpaRepository<PostPicture, PostPictureId> {
}
