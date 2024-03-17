package back.neonadeuli.post.service;

import back.neonadeuli.account.model.authonticate.AccountDetail;
import back.neonadeuli.location.model.SearchBound;
import back.neonadeuli.post.dto.response.PostResponseDto;
import com.uber.h3core.util.LatLng;
import java.util.List;
import org.springframework.data.domain.Pageable;

@FunctionalInterface
public interface GetPostsFunction {

    List<PostResponseDto> getPosts(AccountDetail accountDetail, LatLng latLng, SearchBound searchBound,
                                   Pageable pageable);
}
