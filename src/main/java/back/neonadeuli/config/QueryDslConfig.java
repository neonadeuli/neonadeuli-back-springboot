package back.neonadeuli.config;

import com.querydsl.jpa.Hibernate5Templates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLOps;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(new CustomTemplates(), entityManager);
    }

    private static class CustomTemplates extends Hibernate5Templates {
        public CustomTemplates() {
            add(SQLOps.ROWNUMBER, "row_number()");
        }
    }
}
