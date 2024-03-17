package back.neonadeuli.post.service;

import back.neonadeuli.account.entity.Account;
import back.neonadeuli.account.model.authonticate.AccountDetail;
import back.neonadeuli.account.repository.AccountRepository;
import back.neonadeuli.location.entity.Location;
import back.neonadeuli.location.model.GeometryDistance;
import back.neonadeuli.location.model.H3Cell;
import back.neonadeuli.location.model.LocationSupplier;
import back.neonadeuli.location.model.Resolution;
import back.neonadeuli.location.model.SearchBound;
import back.neonadeuli.location.model.ZoomLevelSearchBoundConverter;
import back.neonadeuli.location.repository.LocationRepository;
import back.neonadeuli.picture.entity.Picture;
import back.neonadeuli.picture.model.PictureSupplier;
import back.neonadeuli.picture.model.PictureTemp;
import back.neonadeuli.picture.repository.PictureRepository;
import back.neonadeuli.post.dto.request.NewPostRequestDto;
import back.neonadeuli.post.dto.response.PostResponseDto;
import back.neonadeuli.post.entity.Post;
import back.neonadeuli.post.entity.PostPicture;
import back.neonadeuli.post.repository.PostPictureRepository;
import back.neonadeuli.post.repository.PostRepository;
import com.uber.h3core.util.LatLng;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PictureSupplier pictureSupplier;
    private final LocationSupplier locationSupplier;
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final PostPictureRepository postPictureRepository;
    private final PictureRepository pictureRepository;
    private final ZoomLevelSearchBoundConverter zoomLevelSearchBoundConverter;
    private final LocationRepository locationRepository;
    private Map<Class<? extends SearchBound>, GetPostsFunction> postResponseFunctions;

    @PostConstruct
    private void createPostResponseSupplier() {
        postResponseFunctions = Map.of(
                GeometryDistance.class, (accountDetail, latLng, geometryRange, pageable) ->
                        getPostsUsingGeo(accountDetail, latLng, (GeometryDistance) geometryRange, pageable),
                H3Cell.class, (accountDetail, latLng, h3Cells, pageable) ->
                        getPostsUsingH3(accountDetail, latLng, (H3Cell) h3Cells, pageable)
        );
    }

    @Transactional
    public Long uploadNewPost(NewPostRequestDto requestDto, AccountDetail accountDetail) {
        Long accountId = accountDetail.getAccountId();
        Account account = accountRepository.getReferenceById(accountId);

        Location location = locationSupplier.supply(requestDto.getLat(), requestDto.getLng());
        locationRepository.save(location);

        Post post = requestDto.toPostEntity(account, location);

        List<PictureTemp> pictureTemps = requestDto.getImages().stream()
                .filter(pictureSupplier::isImage)
                .map(pictureSupplier::createPictureTemp)
                .toList();

        List<Picture> pictures = pictureTemps.stream()
                .map(PictureTemp::picture)
                .toList();

        postRepository.save(post);
        pictureRepository.saveAll(pictures);

        List<PostPicture> postPictures = pictures.stream()
                .map(picture -> new PostPicture(post, picture))
                .toList();

        postPictureRepository.saveAll(postPictures);

        pictureTemps.forEach(pictureSupplier::savePicture);

        return post.getId();
    }

    public List<PostResponseDto> getPosts(AccountDetail accountDetail, LatLng latLng, double zoomLevel,
                                          Pageable pageable) {

        SearchBound searchBound = zoomLevelSearchBoundConverter.getSearchBound(zoomLevel);

        return postResponseFunctions.getOrDefault(searchBound.getClass(), (not, support, search, bound) -> {
            throw new IllegalStateException();
        }).getPosts(accountDetail, latLng, searchBound, pageable);
    }

    private List<PostResponseDto> getPostsUsingGeo(AccountDetail accountDetail, LatLng latLng,
                                                   GeometryDistance geometryDistance, Pageable pageable) {

        return postRepository.retrievePosts(accountDetail, latLng, geometryDistance, pageable);
    }

    private List<PostResponseDto> getPostsUsingH3(AccountDetail accountDetail, LatLng latLng, H3Cell h3Cell,
                                                  Pageable pageable) {

        List<Long> h3Indexes = h3Cell.latLngToH3Indexes().apply(latLng);
        Resolution resolution = h3Cell.resolution();
        return postRepository.retrievePosts(accountDetail, resolution, h3Indexes, pageable);
    }
}
