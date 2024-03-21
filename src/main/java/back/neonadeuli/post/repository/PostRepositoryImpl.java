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
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.spatial.locationtech.jts.JTSGeometryExpressions;
import com.querydsl.sql.SQLExpressions;
import com.uber.h3core.util.LatLng;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final Map<Resolution, Supplier<NumberPath<Long>>> resolutionPaths =
            createResolutionPathSupplier();

    private Map<Resolution, Supplier<NumberPath<Long>>> createResolutionPathSupplier() {

        EnumMap<Resolution, Supplier<NumberPath<Long>>> resolutionFunctions =
                new EnumMap<>(Resolution.class);

        resolutionFunctions.put(Resolution.RES_6, this::res6Path);
        resolutionFunctions.put(Resolution.RES_4, this::res4Path);

        return resolutionFunctions;
    }

    private final JPAQueryFactory queryFactory;
    private final LocationSupplier locationSupplier;

    @Override
    public List<PostResponseDto> retrievePosts(AccountDetail accountDetail, LatLng latLng,
                                               GeometryDistance geometryDistance,
                                               Pageable pageable) {

        BooleanExpression contains = JTSGeometryExpressions.asJTSGeometry(
                        locationSupplier.newPoint(latLng.lat, latLng.lng))
                .buffer(geometryDistance.distance())
                .contains(location.point);

        OrderSpecifier<Comparable<?>> notOrder = new OrderSpecifier<>(Order.ASC, Expressions.nullExpression());

        return retrievePosts(accountDetail, pageable, contains, notOrder);
    }

    private List<PostResponseDto> retrievePosts(AccountDetail accountDetail, Pageable pageable,
                                                BooleanExpression locationFilter, OrderSpecifier<?> specifier) {

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

                .where(locationFilter,
                        post.locationAvailable.isTrue(),
                        post.visibility.eq(Visibility.PUBLIC)
                                .or(accountPrivatePost(accountDetail))
                )

                .orderBy(specifier)

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())

                .fetch();
    }

    @Override
    public List<PostResponseDto> retrievePosts(AccountDetail accountDetail, Resolution resolution, List<Long> h3Indexes,
                                               Pageable pageable) {

        NumberPath<Long> resPath = resolutionPaths.getOrDefault(resolution, () -> {
            throw new IllegalStateException();
        }).get();

        OrderSpecifier<Long> orderSpecifier = new OrderSpecifier<>(Order.DESC,
                SQLExpressions.rowNumber().over().partitionBy(resPath));

        return retrievePosts(accountDetail, pageable, resPath.in(h3Indexes), orderSpecifier);
    }

    private NumberPath<Long> res4Path() {
        return location.h3Res4;
    }

    private NumberPath<Long> res6Path() {
        return location.h3Res6;
    }

    private static BooleanExpression accountPrivatePost(AccountDetail accountDetail) {
        if (Objects.isNull(accountDetail)) {
            return null;
        }

        return post.account.id.eq(accountDetail.getAccountId())
                .and(post.visibility.eq(Visibility.PRIVATE));
    }
}
