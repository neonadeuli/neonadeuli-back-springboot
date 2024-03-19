package back.neonadeuli.location.model;

import com.uber.h3core.H3Core;
import com.uber.h3core.util.LatLng;
import java.io.IOException;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Resolution {
    RES_6(6),
    RES_4(4);

    private static final H3Core H3 = getH3Core();

    private static H3Core getH3Core() {
        try {
            return H3Core.newInstance();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private final int level;

    public H3Cell calculateH3Cell(int cellSpacingDistance) {
        return new H3Cell(this, latLng -> latLngToH3Indexes(latLng, cellSpacingDistance));
    }

    private List<Long> latLngToH3Indexes(LatLng latLng, int cellSpacingDistance) {
        long middleH3Index = latLngToH3Index(latLng);
        return H3.gridDisk(middleH3Index, cellSpacingDistance);
    }

    public long latLngToH3Index(LatLng latLng) {
        return H3.latLngToCell(latLng.lat, latLng.lng, this.level);
    }
}
