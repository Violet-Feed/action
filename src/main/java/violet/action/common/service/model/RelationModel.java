package violet.action.common.service.model;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.RelationMapper;
import violet.action.common.pojo.User;
import violet.action.common.utils.RedisMutex;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RelationModel {
    public final String FOLLOW_STAR_KEY = "relation:follow_star:%d";
    public final String FOLLOWING_COUNT_KEY = "relation:following_count:%d";
    public final String FOLLOWER_COUNT_KEY = "relation:follower_count:%d";
    public final String FRIEND_COUNT_KEY = "relation:friend_count:%d";
    public final String FOLLOWING_LIST_KEY = "relation:following_list:%d";
    public final String FOLLOWER_LIST_KEY = "relation:follower_list:%d";
    public final String FRIEND_LIST_KEY = "relation:friend_list:%d";
    public final String FOLLOWING_HASH_KEY = "relation:following_hash:%d";

    public final String guardIndex = "0";

    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisMutex redisMutex;

    public List<Long> getFollowingListFromDB(Long userId) {
        String followingListKey = String.format(FOLLOWING_LIST_KEY, userId);
        String followingListStr = redisTemplate.opsForValue().get(followingListKey);
        if (followingListStr != null) {
            return JSONObject.parseArray(followingListStr, Long.class);
        }
        boolean locked = redisMutex.lock(followingListKey);
        followingListStr = redisTemplate.opsForValue().get(followingListKey);
        if (followingListStr != null) {
            return JSONObject.parseArray(followingListStr, Long.class);
        }
        if (!locked) {
            log.error("[RelationModel:getFollowingListFromDB] err = redis mutex failed");
            return null;
        }
        List<User> userList = relationMapper.getFollowingList(userId);
        List<Long> followingList = new ArrayList<>();
        for (User user : userList) {
            followingList.add(user.getUserId());
        }
        redisTemplate.opsForValue().set(followingListKey, JSONObject.toJSONString(followingList), Duration.ofDays(1));
        redisMutex.unlock(followingListKey);
        return followingList;
    }

    public List<Long> getFollowerListFromDB(Long userId) {
        String followerListKey = String.format(FOLLOWER_LIST_KEY, userId);
        String followerListStr = redisTemplate.opsForValue().get(followerListKey);
        if (followerListStr != null) {
            return JSONObject.parseArray(followerListStr, Long.class);
        }
        boolean locked = redisMutex.lock(followerListKey);
        followerListStr = redisTemplate.opsForValue().get(followerListKey);
        if (followerListStr != null) {
            return JSONObject.parseArray(followerListStr, Long.class);
        }
        if (!locked) {
            log.error("[RelationModel:getFollowerListFromDB] err = redis mutex failed");
            return null;
        }
        List<User> userList = relationMapper.getFollowerList(userId);
        List<Long> followerList = new ArrayList<>();
        for (User user : userList) {
            followerList.add(user.getUserId());
        }
        redisTemplate.opsForValue().set(followerListKey, JSONObject.toJSONString(followerList), Duration.ofDays(1));
        redisMutex.unlock(followerListKey);
        return followerList;
    }

    public List<Long> getFriendListFromDB(Long userId) {
        String friendListKey = String.format(FRIEND_LIST_KEY, userId);
        String friendListStr = redisTemplate.opsForValue().get(friendListKey);
        if (friendListStr != null) {
            return JSONObject.parseArray(friendListStr, Long.class);
        }
        boolean locked = redisMutex.lock(friendListKey);
        friendListStr = redisTemplate.opsForValue().get(friendListKey);
        if (friendListStr != null) {
            return JSONObject.parseArray(friendListStr, Long.class);
        }
        if (!locked) {
            log.error("[RelationModel:getFriendListFromDB] err = redis mutex failed");
            return null;
        }
        List<User> userList = relationMapper.getFriendList(userId);
        List<Long> friendList = new ArrayList<>();
        Map<Long, Boolean> friendMap = new HashMap<>();
        for (User user : userList) {
            if (friendMap.containsKey(user.getUserId())) {
                friendList.add(user.getUserId());
            } else {
                friendMap.put(user.getUserId(), Boolean.TRUE);
            }
        }
        redisTemplate.opsForValue().set(friendListKey, JSONObject.toJSONString(friendList), Duration.ofDays(1));
        redisMutex.unlock(friendListKey);
        return friendList;
    }

    public Map<Long, Boolean> getFollowingHashFromDB(Long userId, List<Long> toUserIds) {
        Map<Long, Boolean> isFollowing = new HashMap<>();
        String followingHashKey = String.format(FOLLOWING_HASH_KEY, userId);
        List<Object> fields = new ArrayList<>();
        for (Long toUserId : toUserIds) {
            fields.add(toUserId.toString());
        }
        fields.add(guardIndex);
        List<Object> followingHash = redisTemplate.opsForHash().multiGet(followingHashKey, fields);
        if (followingHash.get(followingHash.size() - 1) != null) {
            for (int i = 0; i < followingHash.size() - 1; i++) {
                if (followingHash.get(i) == null) {
                    isFollowing.put(toUserIds.get(i), Boolean.FALSE);
                } else {
                    isFollowing.put(toUserIds.get(i), Boolean.TRUE);
                }
            }
            return isFollowing;
        }
        boolean locked = redisMutex.lock(followingHashKey);
        followingHash = redisTemplate.opsForHash().multiGet(followingHashKey, fields);
        if (followingHash.get(followingHash.size() - 1) != null) {
            for (int i = 0; i < followingHash.size() - 1; i++) {
                if (followingHash.get(i) == null) {
                    isFollowing.put(toUserIds.get(i), Boolean.FALSE);
                } else {
                    isFollowing.put(toUserIds.get(i), Boolean.TRUE);
                }
            }
            return isFollowing;
        }
        if (!locked) {
            log.error("[RelationModel:getFollowingHashFromDB] err = redis mutex failed");
            return null;
        }
        List<User> followingList = relationMapper.getFollowingList(userId);
        Map<String, String> followingMap = new HashMap<>();
        for (User user : followingList) {
            followingMap.put(user.getUserId().toString(), "1");
        }
        for (Long toUserId : toUserIds) {
            if (followingMap.containsKey(toUserId.toString())) {
                isFollowing.put(toUserId, Boolean.TRUE);
            } else {
                isFollowing.put(toUserId, Boolean.FALSE);
            }
        }
        followingMap.put(guardIndex, "1");
        redisTemplate.opsForHash().putAll(followingHashKey, followingMap);
        redisTemplate.expire(followingHashKey, Duration.ofDays(2));
        redisMutex.unlock(followingHashKey);
        return isFollowing;
    }

    public Map<Long, Boolean> getFollowerHashFromDB(Long userId, List<Long> toUserIds) {
        Map<Long, Boolean> isFollower = new HashMap<>();
        List<Object> results = redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (Long toUserId : toUserIds) {
                    String followingHashKey = String.format(FOLLOWING_HASH_KEY, toUserId);
                    operations.opsForHash().get(followingHashKey, guardIndex);
                    operations.opsForHash().get(followingHashKey, userId.toString());
                }
                return null;
            }
        });
        List<Long> missIds = new ArrayList<>();
        for (int i = 0; i < results.size(); i += 2) {
            if (results.get(i) == null) {
                missIds.add(toUserIds.get(i / 2));
            } else if (results.get(i + 1) == null) {
                isFollower.put(toUserIds.get(i / 2), Boolean.FALSE);
            } else {
                isFollower.put(toUserIds.get(i / 2), Boolean.TRUE);
            }
        }
        if (missIds.isEmpty()) {
            return isFollower;
        }
        //批量加锁？
        Map<Long, Map<String, String>> followingMaps = relationMapper.mGetFollowingMap(missIds);
        for (Long toUserId : missIds) {
            if (!followingMaps.containsKey(toUserId)) {
                isFollower.put(toUserId, Boolean.FALSE);
                followingMaps.put(toUserId, new HashMap<>());
            } else if (!followingMaps.get(toUserId).containsKey(userId.toString())) {
                isFollower.put(toUserId, Boolean.FALSE);
            } else {
                isFollower.put(toUserId, Boolean.TRUE);
            }
            followingMaps.get(toUserId).put(guardIndex, "1");
        }
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (Map.Entry<Long, Map<String, String>> entry : followingMaps.entrySet()) {
                    String followingHashKey = String.format(FOLLOWING_HASH_KEY, entry.getKey());
                    operations.opsForHash().putAll(followingHashKey, entry.getValue());
                    operations.expire(followingHashKey, Duration.ofDays(2));
                }
                return null;
            }
        });
        return isFollower;
    }

    public Map<Long, Long> getFollowingCountFromDB(List<Long> userIds) {
        Map<Long, Long> followingCountMap = new HashMap<>();
        List<String> keys = new ArrayList<>();
        for (Long userId : userIds) {
            String followingCountKey = String.format(FOLLOWING_COUNT_KEY, userId);
            keys.add(followingCountKey);
        }
        List<String> results = redisTemplate.opsForValue().multiGet(keys);
        List<Long> missIds = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i) == null) {
                missIds.add(userIds.get(i));
            } else {
                followingCountMap.put(userIds.get(i), Long.parseLong(results.get(i)));
            }
        }
        if (missIds.isEmpty()) {
            return followingCountMap;
        }
        Map<Long, Long> dbCounts = relationMapper.mGetFollowingCount(missIds);
        for (Long userId : missIds) {
            Long count = dbCounts.getOrDefault(userId, 0L);
            followingCountMap.put(userId, count);
        }
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (Long userId : missIds) {
                    String followingCountKey = String.format(FOLLOWING_COUNT_KEY, userId);
                    operations.opsForValue().set(followingCountKey, followingCountMap.get(userId).toString(), Duration.ofDays(2));
                }
                return null;
            }
        });
        return followingCountMap;
    }

    public Map<Long, Long> getFollowerCountFromDB(List<Long> userIds) {
        Map<Long, Long> followerCountMap = new HashMap<>();
        List<String> keys = new ArrayList<>();
        for (Long userId : userIds) {
            String followerCountKey = String.format(FOLLOWER_COUNT_KEY, userId);
            keys.add(followerCountKey);
        }
        List<String> results = redisTemplate.opsForValue().multiGet(keys);
        List<Long> missIds = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i) == null) {
                missIds.add(userIds.get(i));
            } else {
                followerCountMap.put(userIds.get(i), Long.parseLong(results.get(i)));
            }
        }
        if (missIds.isEmpty()) {
            return followerCountMap;
        }
        Map<Long, Long> dbCounts = relationMapper.mGetFollowerCount(missIds);
        for (Long userId : missIds) {
            Long count = dbCounts.getOrDefault(userId, 0L);
            followerCountMap.put(userId, count);
        }
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (Long userId : missIds) {
                    String followingCountKey = String.format(FOLLOWER_COUNT_KEY, userId);
                    operations.opsForValue().set(followingCountKey, followerCountMap.get(userId).toString(), Duration.ofDays(2));
                }
                return null;
            }
        });
        return followerCountMap;
    }

    public void updateCache(Long formUserId, Long toUserId, int delta) {
        String followingListKey = String.format(FOLLOWING_LIST_KEY, formUserId);
        String followListStr = redisTemplate.opsForValue().get(followingListKey);
        if (followListStr != null) {
            List<Long> followingList = JSONObject.parseArray(followListStr, Long.class);
            if (delta > 0) {
                followingList.add(toUserId);
                redisTemplate.opsForValue().set(followingListKey, JSONObject.toJSONString(followingList), Duration.ofDays(1));
            } else {
                followingList.remove(toUserId);
                redisTemplate.opsForValue().set(followingListKey, JSONObject.toJSONString(followingList), Duration.ofDays(1));
            }
        }
        String followerListKey = String.format(FOLLOWER_LIST_KEY, toUserId);
        redisTemplate.delete(followerListKey);
        String friendListKey = String.format(FRIEND_LIST_KEY, formUserId);
        redisTemplate.delete(friendListKey);
        friendListKey = String.format(FRIEND_LIST_KEY, toUserId);
        redisTemplate.delete(friendListKey);
        String followingHashKey = String.format(FOLLOWING_HASH_KEY, formUserId);
        Object guard = redisTemplate.opsForHash().get(followingHashKey, guardIndex);
        if (guard != null) {
            if (delta > 0) {
                redisTemplate.opsForHash().put(followingHashKey, toUserId.toString(), "1");
                redisTemplate.expire(followingHashKey, Duration.ofDays(2));
            } else {
                redisTemplate.opsForHash().delete(followingHashKey, toUserId.toString());
            }
        }
        String followingCountKey = String.format(FOLLOWING_COUNT_KEY, formUserId);
        String followingCount = redisTemplate.opsForValue().get(followingCountKey);
        if (followingCount != null) {
            if (delta > 0) {
                redisTemplate.opsForValue().increment(followingCountKey, 1);
            } else {
                redisTemplate.opsForValue().decrement(followingCountKey, 1);
            }
        }
        String followerCountKey = String.format(FOLLOWER_COUNT_KEY, toUserId);
        String followerCount = redisTemplate.opsForValue().get(followerCountKey);
        if (followerCount != null) {
            if (delta > 0) {
                redisTemplate.opsForValue().increment(followerCountKey, 1);
            } else {
                redisTemplate.opsForValue().decrement(followerCountKey, 1);
            }
        }
    }
}
