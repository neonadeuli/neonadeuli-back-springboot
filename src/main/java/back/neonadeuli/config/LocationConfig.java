package back.neonadeuli.config;

import com.uber.h3core.H3Core;
import java.io.IOException;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationConfig {
    private static final int WGS84_SRID = 4326;

    @Bean
    public H3Core h3() throws IOException {
        return H3Core.newInstance();
    }

    @Bean
    public GeometryFactory geometryFactory() {
        return new GeometryFactory(new PrecisionModel(), WGS84_SRID);
    }
}
