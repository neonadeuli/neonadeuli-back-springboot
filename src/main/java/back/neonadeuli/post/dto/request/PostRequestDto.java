package back.neonadeuli.post.dto.request;

import com.uber.h3core.util.LatLng;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {

    private double lat;

    private double lng;

    @Getter
    private double zoomLevel;

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }
}
