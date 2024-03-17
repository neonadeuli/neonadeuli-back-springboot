package back.neonadeuli.post.service;

import back.neonadeuli.account.entity.Account;
import back.neonadeuli.account.model.authonticate.AccountDetail;
import back.neonadeuli.account.repository.AccountRepository;
import back.neonadeuli.location.entity.Location;
import back.neonadeuli.location.model.LocationSupplier;
import back.neonadeuli.location.repository.LocationRepository;
import back.neonadeuli.picture.entity.Picture;
import back.neonadeuli.picture.model.PictureSupplier;
import back.neonadeuli.picture.model.PictureTemp;
import back.neonadeuli.picture.repository.PictureRepository;
import back.neonadeuli.post.dto.request.NewPostRequestDto;
import back.neonadeuli.post.entity.Post;
import back.neonadeuli.post.entity.PostPicture;
import back.neonadeuli.post.repository.PostPictureRepository;
import back.neonadeuli.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    private final LocationRepository locationRepository;

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
}
