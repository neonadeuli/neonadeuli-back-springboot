package back.neonadeuli.location.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Table(name = "locations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @Column(name = "location_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "h3_res_4")
    private Long h3Res4;

    @Column(name = "h3_res_6")
    private Long h3Res6;

    @Column(name = "lat_lng")
    private Point point;

    public Location(Long h3Res4, Long h3Res6, Point point) {
        this.h3Res4 = h3Res4;
        this.h3Res6 = h3Res6;
        this.point = point;
    }
}
