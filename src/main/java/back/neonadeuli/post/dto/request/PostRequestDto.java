package back.neonadeuli.post.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotNull
    private Double zoomLevel;
}
