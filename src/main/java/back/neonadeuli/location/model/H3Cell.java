package back.neonadeuli.location.model;

import com.uber.h3core.util.LatLng;
import java.util.List;
import java.util.function.Function;

public record H3Cell(Resolution resolution, Function<LatLng, List<Long>> latLngToH3Indexes) implements SearchBound {
}
