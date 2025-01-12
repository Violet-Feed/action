package violet.action.common.config;

import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import violet.action.common.mapper.RelationMapper;
import violet.action.common.pojo.User;
import violet.action.common.service.RelationService;
import violet.action.common.service.impl.RelationServiceImpl;
import violet.action.common.service.model.RelationModel;
import violet.action.common.utils.RedisMutex;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class CaffeineConfig {
    @Autowired
    private RelationModel relationModel;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class StarCacheResult{
        private List<Long> followingList;
        private List<Long> followerList;
    }

    @Bean
    public LoadingCache<String, Object> starCaffeineCache() {
        CacheLoader<String, Object> loader = new CacheLoader<String, Object>() {
            @Override
            public Object load(@NotNull String key) {
                String[] parts = key.split(":");
                Long userId = Long.parseLong(parts[parts.length - 1]);
                List<Long> followingList=relationModel.getFollowingListFromCache(userId);
                List<Long> followerList=relationModel.getFollowerListFromCache(userId);
                return new StarCacheResult(followingList,followerList);
            }
        };
        LoadingCache<String, Object> cache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500)
                .refreshAfterWrite(10, TimeUnit.SECONDS)
                .build(loader);
        return cache;
    }

    @Bean
    public Cache<String, Object> caffeineCache() {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
        return cache;
    }
}
