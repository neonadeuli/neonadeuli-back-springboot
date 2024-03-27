package back.neonadeuli.post.service;

import back.neonadeuli.account.entity.Account;
import back.neonadeuli.account.model.authonticate.AccountDetail;
import back.neonadeuli.account.operation.AccountReader;
import back.neonadeuli.location.entity.Location;
import back.neonadeuli.location.model.GeometryDistance;
import back.neonadeuli.location.model.H3Cell;
import back.neonadeuli.location.model.Resolution;
import back.neonadeuli.location.model.SearchBound;
import back.neonadeuli.location.operation.LocationSupplier;
import back.neonadeuli.location.operation.PointProvider;
import back.neonadeuli.location.operation.ZoomLevelSearchBoundConverter;
import back.neonadeuli.picture.entity.Picture;
import back.neonadeuli.picture.operation.picture.PictureSupplier;
import back.neonadeuli.post.dto.response.PostResponseDto;
import back.neonadeuli.post.entity.Post;
import back.neonadeuli.post.entity.Visibility;
import back.neonadeuli.post.operation.PostReader;
import back.neonadeuli.post.operation.PostSupplier;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final LocationSupplier locationSupplier;
    private final AccountReader accountReader;
    private final PostSupplier postSupplier;
    private final PostReader postReader;
    private final PictureSupplier pictureSupplier;
    private final ZoomLevelSearchBoundConverter zoomLevelSearchBoundConverter;
    private final PointProvider pointProvider;

    @Value("${neonadeuli.folder-name.post.picture:#{null}}")
    private String folderName;

    private Map<Class<? extends SearchBound>, GetPostsFunction> postResponseFunctions;

    @PostConstruct
    private void createPostResponseSupplier() {
        postResponseFunctions = Map.of(
                GeometryDistance.class, (accountDetail, lat, lng, geometryRange, pageable) ->
                        getPostsUsingGeo(accountDetail, lat, lng, (GeometryDistance) geometryRange, pageable),
                H3Cell.class, (accountDetail, lat, lng, h3Cells, pageable) ->
                        getPostsUsingH3(accountDetail, lat, lng, (H3Cell) h3Cells, pageable)
        );
    }

    @Transactional
    public Long uploadNewPost(Visibility visibility, String content, boolean locationAvailable, Double lat, Double lng,
                              List<MultipartFile> images) {

        Account account = accountReader.getLoginAccountReference();
        Location location = locationSupplier.supply(lat, lng);

        List<Picture> pictures = pictureSupplier.supply(folderName, images);
        Post post = postSupplier.supply(visibility, account, content, locationAvailable, location, pictures);

        return post.getId();
    }

    public List<PostResponseDto> getPosts(AccountDetail accountDetail, double lat, double lng, double zoomLevel,
                                          Pageable pageable) {

        SearchBound searchBound = zoomLevelSearchBoundConverter.getSearchBound(zoomLevel);

        return postResponseFunctions.getOrDefault(searchBound.getClass(), (not, support, search, bound, range) -> {
            throw new IllegalStateException();
        }).getPosts(accountDetail, lat, lng, searchBound, pageable);
    }

    private List<PostResponseDto> getPostsUsingGeo(AccountDetail accountDetail, double lat, double lng,
                                                   GeometryDistance geometryDistance, Pageable pageable) {

        Point point = pointProvider.provide(lat, lng);

        return postReader.retrievePosts(accountDetail, point, geometryDistance, pageable);
    }

    private List<PostResponseDto> getPostsUsingH3(AccountDetail accountDetail, double lat, double lng, H3Cell h3Cell,
                                                  Pageable pageable) {

        Resolution resolution = h3Cell.resolution();
        List<Long> h3Indexes = h3Cell.latLngToH3Indexes().apply(lat, lng);

        return postReader.retrievePosts(accountDetail, resolution, h3Indexes, pageable);
    }
}
