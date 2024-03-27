package back.neonadeuli.location.operation;

import back.neonadeuli.annotation.Provider;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

@Provider
@RequiredArgsConstructor
public class PointProvider {

    private final GeometryFactory geometryFactory;

    public Point provide(double lat, double lng) {
        return geometryFactory.createPoint(new Coordinate(lng, lat));
    }
}
