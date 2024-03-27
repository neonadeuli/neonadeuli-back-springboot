package back.neonadeuli.post.dto.request;

import back.neonadeuli.post.entity.Visibility;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewPostRequestDto {

    @Range(min = -90, max = 90)
    private Double lat;

    @Range(min = -180, max = 180)
    private Double lng;

    @NotNull
    private Boolean locationAvailable;

    @NotNull
    private Visibility visibility;

    @NotNull
    private String content;

    private List<MultipartFile> images;
}
