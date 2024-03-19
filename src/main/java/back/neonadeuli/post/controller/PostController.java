package back.neonadeuli.post.controller;

import back.neonadeuli.account.model.authonticate.AccountDetail;
import back.neonadeuli.post.dto.request.NewPostRequestDto;
import back.neonadeuli.post.dto.request.PostRequestDto;
import back.neonadeuli.post.dto.response.PostResponseDto;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> writePost(@ModelAttribute @Valid NewPostRequestDto requestDto,
                                          BindingResult bindingResult,
                                          @AuthenticationPrincipal AccountDetail accountDetail) {

        return ResponseEntity.ok(postService.uploadNewPost(requestDto, accountDetail));
    }

    @GetMapping("/geo")
    public ResponseEntity<List<PostResponseDto>> getGeoFeed(@AuthenticationPrincipal AccountDetail accountDetail,
                                                            @ModelAttribute PostRequestDto postRequestDto,
                                                            Pageable pageable) {

        List<PostResponseDto> posts = postService.getPosts(accountDetail, postRequestDto.getLatLng(),
                postRequestDto.getZoomLevel(), pageable);

        return ResponseEntity.ok(posts);
    }

    @GetMapping
    public String getNormalFeed() {
        // TODO: 추천 시스템 사용하여 적용해볼 것
        return "조회 성공";
    }
}
