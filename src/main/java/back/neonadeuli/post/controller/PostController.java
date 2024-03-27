package back.neonadeuli.post.controller;

import back.neonadeuli.account.model.authonticate.AccountDetail;
import back.neonadeuli.post.dto.request.NewPostRequestDto;
import back.neonadeuli.post.dto.request.PostRequestDto;
import back.neonadeuli.post.dto.response.PostResponseDto;
import back.neonadeuli.post.entity.Visibility;
import back.neonadeuli.post.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> writePost(@ModelAttribute @Valid NewPostRequestDto requestDto,
                                          BindingResult bindingResult) {

        Visibility visibility = requestDto.getVisibility();
        String content = requestDto.getContent();
        Boolean locationAvailable = requestDto.getLocationAvailable();
        Double lat = requestDto.getLat();
        Double lng = requestDto.getLng();
        List<MultipartFile> images = requestDto.getImages();

        Long postId = postService.uploadNewPost(visibility, content, locationAvailable, lat, lng, images);

        return ResponseEntity.ok(postId);
    }

    @GetMapping("/geo")
    public ResponseEntity<List<PostResponseDto>> getGeoFeed(@AuthenticationPrincipal AccountDetail accountDetail,
                                                            @ModelAttribute PostRequestDto postRequestDto,
                                                            BindingResult bindingResult, Pageable pageable) {

        List<PostResponseDto> posts = postService.getPosts(accountDetail, postRequestDto.getLat(),
                postRequestDto.getLng(), postRequestDto.getZoomLevel(), pageable);

        return ResponseEntity.ok(posts);
    }

    @GetMapping
    public String getNormalFeed() {
        // TODO: 추천 시스템 사용하여 적용해볼 것
        return "조회 성공";
    }
}
