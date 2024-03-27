package back.neonadeuli.post.repository;

import back.neonadeuli.account.model.authonticate.AccountDetail;
import back.neonadeuli.location.model.GeometryDistance;
import back.neonadeuli.location.model.Resolution;
import back.neonadeuli.post.dto.response.PostResponseDto;
import java.util.List;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    List<PostResponseDto> retrievePosts(AccountDetail accountDetail, Resolution resolution, List<Long> h3Indexes,
                                        Pageable pageable);

    List<PostResponseDto> retrievePosts(AccountDetail accountDetail, Point point, GeometryDistance geometryDistance,
                                        Pageable pageable);
}
