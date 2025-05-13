package violet.action.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.convert.Neo4jConversions;

@Configuration
public class Neo4jConfig {

    @Bean
    public Neo4jConversions neo4jConversions() {
        return new Neo4jConversions();
    }
}
