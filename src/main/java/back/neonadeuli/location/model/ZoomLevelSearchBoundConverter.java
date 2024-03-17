package back.neonadeuli.location.model;

import static back.neonadeuli.location.model.Resolution.RES_4;
import static back.neonadeuli.location.model.Resolution.RES_6;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_10;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_11;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_12;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_13;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_14;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_15;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_16;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_17;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_18;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_19;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_20;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_6;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_7;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_8;
import static back.neonadeuli.location.model.ZoomLevel.LEVEL_9;

import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ZoomLevelSearchBoundConverter {

    private final Map<ZoomLevel, SearchBound> zoomLevelSearchBounds = createZoomLevelSearchBounds();

    private Map<ZoomLevel, SearchBound> createZoomLevelSearchBounds() {
        EnumMap<ZoomLevel, SearchBound> searchBounds = new EnumMap<>(ZoomLevel.class);

        searchBounds.put(LEVEL_20, new GeometryDistance(20));
        searchBounds.put(LEVEL_19, new GeometryDistance(40));
        searchBounds.put(LEVEL_18, new GeometryDistance(100));
        searchBounds.put(LEVEL_17, new GeometryDistance(200));
        searchBounds.put(LEVEL_16, new GeometryDistance(400));
        searchBounds.put(LEVEL_15, new GeometryDistance(800));
        searchBounds.put(LEVEL_14, new GeometryDistance(1_000));
        searchBounds.put(LEVEL_13, new GeometryDistance(2_000));
        searchBounds.put(LEVEL_12, new GeometryDistance(4_000));
        searchBounds.put(LEVEL_11, RES_6.calculateH3Cell(1));
        searchBounds.put(LEVEL_10, RES_6.calculateH3Cell(2));
        searchBounds.put(LEVEL_9, RES_6.calculateH3Cell(3));
        searchBounds.put(LEVEL_8, RES_4.calculateH3Cell(1));
        searchBounds.put(LEVEL_7, RES_4.calculateH3Cell(2));
        searchBounds.put(LEVEL_6, RES_4.calculateH3Cell(3));

        return searchBounds;
    }

    public SearchBound getSearchBound(double zoomLevel) {
        ZoomLevel level = ZoomLevel.getZoomLevel(zoomLevel);
        return zoomLevelSearchBounds.get(level);
    }
}
