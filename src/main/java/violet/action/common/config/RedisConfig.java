package violet.action.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // ========== 第一个Redis配置（默认，标记为Primary） ==========
    @Bean
    @Primary  // 主Redis，当注入RedisTemplate时默认使用这个
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisProperties redisProperties() {
        return new RedisProperties();
    }

    @Bean("redisConnectionFactory")
    @Primary
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisProperties properties = redisProperties();
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(properties.getHost());
        config.setPort(properties.getPort());
        config.setDatabase(properties.getDatabase());
        if (properties.getPassword() != null) {
            config.setPassword(properties.getPassword());
        }
        return new LettuceConnectionFactory(config);
    }

    @Bean("redisTemplate")
    @Primary
    public RedisTemplate<String, String> redisTemplate(@Qualifier("redisConnectionFactory") LettuceConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    // ========== 第二个Redis配置（自定义） ==========
    @Bean("kvrocksProperties")
    @ConfigurationProperties(prefix = "spring.kvrocks")
    public RedisProperties redisSecondProperties() {
        return new RedisProperties();
    }

    @Bean("kvrocksConnectionFactory")
    public LettuceConnectionFactory redisSecondConnectionFactory(@Qualifier("kvrocksProperties") RedisProperties properties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(properties.getHost());
        config.setPort(properties.getPort());
        config.setDatabase(properties.getDatabase());
        if (properties.getPassword() != null) {
            config.setPassword(properties.getPassword());
        }
        return new LettuceConnectionFactory(config);
    }

    @Bean("kvrocksTemplate")
    public RedisTemplate<String, String> redisSecondTemplate(@Qualifier("kvrocksConnectionFactory") LettuceConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}