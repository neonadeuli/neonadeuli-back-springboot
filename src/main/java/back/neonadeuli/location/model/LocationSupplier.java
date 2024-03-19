package back.neonadeuli.location.model;

import back.neonadeuli.location.entity.Location;
import com.uber.h3core.util.LatLng;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationSupplier {

    private final GeometryFactory geometryFactory;

    public Location supply(Double lat, Double lng) {

        if (Objects.isNull(lat) || Objects.isNull(lng)) {
            return null;
        }

        LatLng latLng = new LatLng(lat, lng);

        Long h3Res4 = Resolution.RES_4.latLngToH3Index(latLng);
        Long h3Res6 = Resolution.RES_6.latLngToH3Index(latLng);
        Point point = newPoint(lat, lng);

        return new Location(h3Res4, h3Res6, point);
    }

    public Point newPoint(double lat, double lng) {
        return geometryFactory.createPoint(new Coordinate(lng, lat));
    }
}
