package back.neonadeuli.post.operation;

import back.neonadeuli.account.model.authonticate.AccountDetail;
import back.neonadeuli.annotation.Reader;
import back.neonadeuli.location.model.GeometryDistance;
import back.neonadeuli.location.model.Resolution;
import back.neonadeuli.post.dto.response.PostResponseDto;
import back.neonadeuli.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Pageable;

@Reader
@RequiredArgsConstructor
public class PostReader {

    private final PostRepository postRepository;

    public List<PostResponseDto> retrievePosts(AccountDetail accountDetail, Point point,
                                               GeometryDistance geometryDistance, Pageable pageable) {

        return postRepository.retrievePosts(accountDetail, point, geometryDistance, pageable);
    }

    public List<PostResponseDto> retrievePosts(AccountDetail accountDetail, Resolution resolution, List<Long> h3Indexes,
                                               Pageable pageable) {

        return postRepository.retrievePosts(accountDetail, resolution, h3Indexes, pageable);
    }
}
