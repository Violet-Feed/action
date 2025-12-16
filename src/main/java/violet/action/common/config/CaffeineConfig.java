package violet.action.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import violet.action.common.service.model.RelationModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
public class CaffeineConfig {
    @Autowired
    private RelationModel relationModel;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class StarCacheResult {
        private List<Long> followingList;
        private List<Long> followerList;
        private Map<Long, Boolean> followingMap;
    }

    @Bean
    public LoadingCache<String, StarCacheResult> starCaffeineCache() {
        CacheLoader<String, StarCacheResult> loader = new CacheLoader<String, StarCacheResult>() {
            @Override
            public StarCacheResult load(@NotNull String key) {
                String[] parts = key.split(":");
                Long userId = Long.parseLong(parts[parts.length - 1]);
                List<Long> followingList = relationModel.getFollowingListFromDB(userId, 0, Integer.MAX_VALUE);
                List<Long> followerList = relationModel.getFollowerListFromDB(userId, 0, Integer.MAX_VALUE);
                Map<Long, Boolean> followingMap = new HashMap<>();
                for (Long followingId : followingList) {
                    followingMap.put(followingId, Boolean.TRUE);
                }
                return new StarCacheResult(followingList, followerList, followingMap);
            }
        };
        LoadingCache<String, StarCacheResult> cache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(200)
                .refreshAfterWrite(10, TimeUnit.SECONDS)
                .build(loader);
        return cache;
    }

    @Bean
    public Cache<String, List<Long>> listCaffeineCache() {
        Cache<String, List<Long>> cache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(200)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
        return cache;
    }

    @Bean
    public Cache<String, Map<Long, Boolean>> hashCaffeineCache() {
        Cache<String, Map<Long, Boolean>> cache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(200)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
        return cache;
    }

    @Bean
    public Cache<String, Long> countCaffeineCache() {
        Cache<String, Long> cache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(200)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
        return cache;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class HitCaffeineCache {
        private Cache<String, AtomicInteger> cache;
        private AtomicInteger totalCount;
    }

    @Bean
    public HitCaffeineCache hitCaffeineCache() {
        Cache<String, AtomicInteger> cache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(200)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
        AtomicInteger totalCount = new AtomicInteger(0);
        return new HitCaffeineCache(cache, totalCount);
    }
}
