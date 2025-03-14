package violet.action.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonProtobufConfig {

    @Bean
    @SuppressWarnings("unchecked")
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.featuresToDisable(
                    JsonGenerator.Feature.IGNORE_UNKNOWN,
                    MapperFeature.DEFAULT_VIEW_INCLUSION,
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
            );
            jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            jacksonObjectMapperBuilder.modulesToInstall(ProtobufModule.class);
        };
    }

}

