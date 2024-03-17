package back.neonadeuli.post.repository;

import back.neonadeuli.account.model.authonticate.AccountDetail;
import back.neonadeuli.location.model.GeometryDistance;
import back.neonadeuli.location.model.Resolution;
import back.neonadeuli.post.dto.response.PostResponseDto;
import com.uber.h3core.util.LatLng;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    List<PostResponseDto> retrievePosts(AccountDetail accountDetail, Resolution resolution, List<Long> h3Indexes,
                                        Pageable pageable);

    List<PostResponseDto> retrievePosts(AccountDetail accountDetail, LatLng latLng, GeometryDistance geometryDistance,
                                        Pageable pageable);
}
