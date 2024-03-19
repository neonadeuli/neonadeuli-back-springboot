package back.neonadeuli.post.repository;

import static back.neonadeuli.account.entity.QAccount.account;
import static back.neonadeuli.location.entity.QLocation.location;
import static back.neonadeuli.picture.entity.QPicture.picture;
import static back.neonadeuli.post.entity.QPost.post;

import back.neonadeuli.account.model.authonticate.AccountDetail;
import back.neonadeuli.location.model.GeometryDistance;
import back.neonadeuli.location.model.LocationSupplier;
import back.neonadeuli.location.model.Resolution;
import back.neonadeuli.post.dto.response.PostResponseDto;
import back.neonadeuli.post.dto.response.QPostResponseDto;
import back.neonadeuli.post.entity.Visibility;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.spatial.locationtech.jts.JTSGeometryExpressions;
import com.uber.h3core.util.LatLng;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final Map<Resolution, Function<List<Long>, BooleanExpression>> resolutionExpressions =
            createResolutionFunctions();

    private Map<Resolution, Function<List<Long>, BooleanExpression>> createResolutionFunctions() {

        EnumMap<Resolution, Function<List<Long>, BooleanExpression>> resolutionFunctions =
                new EnumMap<>(Resolution.class);

        resolutionFunctions.put(Resolution.RES_6, this::res6Expression);
        resolutionFunctions.put(Resolution.RES_4, this::res4Expression);

        return resolutionFunctions;
    }

    private final JPAQueryFactory queryFactory;
    private final LocationSupplier locationSupplier;

    @Override
    public List<PostResponseDto> retrievePosts(AccountDetail accountDetail, LatLng latLng,
                                               GeometryDistance geometryDistance,
                                               Pageable pageable) {

        return retrievePosts(accountDetail, pageable,
                JTSGeometryExpressions.asJTSGeometry(locationSupplier.newPoint(latLng.lat, latLng.lng))
                        .buffer(geometryDistance.distance())
                        .contains(location.point));
    }

    private List<PostResponseDto> retrievePosts(AccountDetail accountDetail, Pageable pageable,
                                                BooleanExpression expression) {

        return queryFactory

                .select(new QPostResponseDto(
                        post.id,
                        account.id,
                        account.nickname,
                        picture,
                        post.content,
                        location.point))

                .from(post)

                .innerJoin(post.account, account)
                .innerJoin(account.picture, picture)
                .innerJoin(post.location, location)

                .where(expression,
                        post.locationAvailable.isTrue(),
                        post.visibility.eq(Visibility.PUBLIC)
                                .or(accountPrivatePost(accountDetail))
                )

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())

                .fetch();
    }

    @Override
    public List<PostResponseDto> retrievePosts(AccountDetail accountDetail, Resolution resolution, List<Long> h3Indexes,
                                               Pageable pageable) {
        BooleanExpression expression = resolutionExpressions.getOrDefault(resolution, ignore -> {
            throw new IllegalStateException();
        }).apply(h3Indexes);

        return retrievePosts(accountDetail, pageable, expression);
    }

    private BooleanExpression res4Expression(List<Long> h3Res4Indexes) {
        return location.h3Res4.in(h3Res4Indexes);
    }

    private BooleanExpression res6Expression(List<Long> h3Res6Indexes) {
        return location.h3Res6.in(h3Res6Indexes);
    }

    private static BooleanExpression accountPrivatePost(AccountDetail accountDetail) {
        if (Objects.isNull(accountDetail)) {
            return null;
        }

        return post.account.id.eq(accountDetail.getAccountId())
                .and(post.visibility.eq(Visibility.PRIVATE));
    }
}
