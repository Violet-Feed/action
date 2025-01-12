package violet.action.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class RedisMutex {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private final int maxRetries=2;
    private final int retryInterval=20;
    private final int timeout=1;

    public boolean lock(String key) {
        int retry =0;
        boolean locked = false;
        while(!locked) {
            locked = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent("lock:"+key, "lock", Duration.ofSeconds(timeout)));
            if(locked) {
                break;
            }
            retry += 1;
            if(retry > maxRetries) {
                break;
            }
            try {
                Thread.sleep(retryInterval);
            } catch (InterruptedException e) {
                log.error("[RedisMutex:lock] key = {}, err = {}",key, e.getMessage());
            }
        }
        return locked;
    }

    public void unlock(String key) {
        redisTemplate.delete("lock:"+key);
    }
}
