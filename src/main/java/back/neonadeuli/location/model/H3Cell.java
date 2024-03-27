package back.neonadeuli.location.model;

public record H3Cell(Resolution resolution, LatLngToH3IndexesFunction latLngToH3Indexes) implements SearchBound {
}
