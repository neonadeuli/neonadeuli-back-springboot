package back.neonadeuli.post.dto.response;

import back.neonadeuli.picture.entity.Picture;
import com.querydsl.core.annotations.QueryProjection;
import com.uber.h3core.util.LatLng;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Getter
public class PostResponseDto {

    private final Long postId;
    private final AccountInfo accountInfo;
    private final String content;
    private final LatLng location;

    @QueryProjection
    public PostResponseDto(Long postId, Long accountId, String nickname, Picture picture,
                           String content, Point point) {

        this.postId = postId;
        this.accountInfo = new AccountInfo(accountId, nickname, picture.getSavePath());
        this.content = content;
        this.location = pointToLatLng(point);
    }

    private static LatLng pointToLatLng(Point point) {

        if (Objects.isNull(point)) {
            return null;
        }

        return new LatLng(point.getY(), point.getX());
    }

    @Getter
    @AllArgsConstructor
    private static class AccountInfo {
        private Long accountId;
        private String nickname;
        private String pictureSavePath;
    }
}
