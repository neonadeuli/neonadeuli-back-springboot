package back.neonadeuli.config;

import com.uber.h3core.H3Core;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H3Config {

    @Bean
    public H3Core h3Core() throws IOException {
        return H3Core.newInstance();
    }
}
