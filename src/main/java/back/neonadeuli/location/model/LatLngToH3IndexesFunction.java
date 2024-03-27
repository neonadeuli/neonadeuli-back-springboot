package back.neonadeuli.location.model;

import java.util.List;

@FunctionalInterface
public interface LatLngToH3IndexesFunction {
    List<Long> apply(double lat, double lng);
}
