package back.neonadeuli.location.model;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ZoomLevel {
    LEVEL_20(20),
    LEVEL_19(19),
    LEVEL_18(18),
    LEVEL_17(17),
    LEVEL_16(16),
    LEVEL_15(15),
    LEVEL_14(14),
    LEVEL_13(13),
    LEVEL_12(12),
    LEVEL_11(11),
    LEVEL_10(10),
    LEVEL_9(9),
    LEVEL_8(8),
    LEVEL_7(7),
    LEVEL_6(6);

    private static final int MAX_VALUE = maxValue();
    private static final int MIN_VALUE = minValue();

    private static int maxValue() {
        return Arrays.stream(ZoomLevel.values())
                .mapToInt(ZoomLevel::getValue)
                .max()
                .orElseThrow(IllegalStateException::new);
    }

    private static int minValue() {
        return Arrays.stream(ZoomLevel.values())
                .mapToInt(ZoomLevel::getValue)
                .min()
                .orElseThrow(IllegalStateException::new);
    }

    public static ZoomLevel getZoomLevel(double zoomValue) {
        if (zoomValue < MIN_VALUE) {
            return getZoomLevel(MIN_VALUE);
        }

        if (MAX_VALUE < zoomValue) {
            return getZoomLevel(MAX_VALUE);
        }

        int roundValue = (int) Math.round(zoomValue);

        return Arrays.stream(ZoomLevel.values())
                .filter(zoomLevel -> zoomLevel.value == roundValue)
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    private final int value;
}
