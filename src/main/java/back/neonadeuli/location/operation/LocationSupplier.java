package back.neonadeuli.location.operation;

import back.neonadeuli.annotation.Supplier;
import back.neonadeuli.location.entity.Location;
import back.neonadeuli.location.model.Resolution;
import back.neonadeuli.location.repository.LocationRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;

@Supplier
@RequiredArgsConstructor
public class LocationSupplier {

    private final LocationRepository locationRepository;
    private final PointProvider pointProvider;

    public Location supply(Double lat, Double lng) {

        if (Objects.isNull(lat) || Objects.isNull(lng)) {
            return null;
        }

        Long h3Res4 = Resolution.RES_4.latLngToH3Index(lat, lng);
        Long h3Res6 = Resolution.RES_6.latLngToH3Index(lat, lng);
        Point point = pointProvider.provide(lat, lng);

        Location location = new Location(h3Res4, h3Res6, point);
        return locationRepository.save(location);
    }
}
