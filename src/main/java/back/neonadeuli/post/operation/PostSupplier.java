package back.neonadeuli.post.operation;

import back.neonadeuli.account.entity.Account;
import back.neonadeuli.annotation.Supplier;
import back.neonadeuli.location.entity.Location;
import back.neonadeuli.picture.entity.Picture;
import back.neonadeuli.post.entity.Post;
import back.neonadeuli.post.entity.Visibility;
import back.neonadeuli.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Supplier
@RequiredArgsConstructor
public class PostSupplier {

    private final PostRepository postRepository;

    public Post supply(Visibility visibility, Account account, String content, boolean locationAvailable,
                       Location location, List<Picture> pictures) {

        Post post = new Post(account, content, visibility, locationAvailable, location);
        post.addPictures(pictures);

        return postRepository.save(post);
    }
}
