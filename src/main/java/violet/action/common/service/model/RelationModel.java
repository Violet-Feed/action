package violet.action.common.service.model;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.RelationMapper;
import violet.action.common.pojo.User;
import violet.action.common.utils.RedisMutex;

import java.time.Duration;
import java.util.*;

@Slf4j
@Service
public class RelationModel {
    public final String FOLLOWING_LIST_KEY = "action:relation:following_list:%d";
    public final String FOLLOWER_LIST_KEY = "action:relation:follower_list:%d";
    public final String FRIEND_LIST_KEY = "action:relation:friend_list:%d";
    public final String FOLLOWING_HASH_KEY = "action:relation:following_hash:%d";
    public final String FOLLOWER_HASH_KEY = "action:relation:follower_hash:%d";

    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private RedisMutex redisMutex;

    public List<Long> getFollowingListFromCache(Long userId) {
        String followingListKey= String.format(FOLLOWING_LIST_KEY, userId);
        String followingListStr=redisTemplate.opsForValue().get(followingListKey);
        if(followingListStr!=null){
            return JSONObject.parseArray(followingListStr, Long.class);
        }
        boolean locked= redisMutex.lock(followingListKey);
        followingListStr=redisTemplate.opsForValue().get(followingListKey);
        if(followingListStr!=null){
            return JSONObject.parseArray(followingListStr, Long.class);
        }
        if(!locked){
            log.error("[getFollowingListFromCache] err = redis mutex failed");
            return null;
        }
        List<User> userList=relationMapper.getFollowingList(userId);
        List<Long> followingList=new ArrayList<>();
        for(User user:userList){
            followingList.add(user.getUserId());
        }
        redisTemplate.opsForValue().set(followingListKey,JSONObject.toJSONString(followingList), Duration.ofDays(1));
        redisMutex.unlock(followingListKey);
        return followingList;
    }

    public List<Long> getFollowerListFromCache(Long userId) {
        String followerListKey= String.format(FOLLOWER_LIST_KEY, userId);
        String followerListStr=redisTemplate.opsForValue().get(followerListKey);
        if(followerListStr!=null){
            return JSONObject.parseArray(followerListStr, Long.class);
        }
        boolean locked= redisMutex.lock(followerListKey);
        followerListStr=redisTemplate.opsForValue().get(followerListKey);
        if(followerListStr!=null){
            return JSONObject.parseArray(followerListStr, Long.class);
        }
        if(!locked){
            log.error("[starCaffeineCache:load] err = redis mutex failed");
            return null;
        }
        List<User> userList=relationMapper.getFollowerList(userId);
        List<Long> followerList=new ArrayList<>();
        for(User user:userList){
            followerList.add(user.getUserId());
        }
        redisTemplate.opsForValue().set(followerListKey,JSONObject.toJSONString(followerList), Duration.ofDays(1));
        redisMutex.unlock(followerListKey);
        return followerList;
    }

    public List<Long> getFriendListFromCache(Long userId) {
        String friendListKey= String.format(FRIEND_LIST_KEY, userId);
        String friendListStr=redisTemplate.opsForValue().get(friendListKey);
        if(friendListStr!=null){
            return JSONObject.parseArray(friendListStr, Long.class);
        }
        boolean locked= redisMutex.lock(friendListKey);
        friendListStr=redisTemplate.opsForValue().get(friendListKey);
        if(friendListStr!=null){
            return JSONObject.parseArray(friendListStr, Long.class);
        }
        if(!locked){
            log.error("[starCaffeineCache:load] err = redis mutex failed");
            return null;
        }
        List<User> userList=relationMapper.getFriendList(userId);
        List<Long> friendList=new ArrayList<>();
        Map<Long,Boolean> friendMap=new HashMap<>();
        for(User user:userList){
            if(friendMap.containsKey(user.getUserId())){
                friendList.add(user.getUserId());
            }
            else{
                friendMap.put(user.getUserId(),Boolean.TRUE);
            }
        }
        redisTemplate.opsForValue().set(friendListKey,JSONObject.toJSONString(friendList), Duration.ofDays(1));
        redisMutex.unlock(friendListKey);
        return friendList;
    }

    public Map<Long,Boolean> getFollowingHashFromCache(Long userId,List<Long> toUserIds) {
        Map<Long,Boolean> isFollowing=new HashMap<>();
        String followingHashKey= String.format(FOLLOWING_HASH_KEY, userId);
        List<Object> fields = new ArrayList<>();
        for(Long toUserId:toUserIds){
            fields.add(toUserId.toString());
        }
        fields.add("0");
        List<Object> followingHash = redisTemplate.opsForHash().multiGet(followingHashKey, fields);
        if(followingHash.get(followingHash.size()-1)!=null){
            for(int i=0;i<followingHash.size()-1;i++){
                if(followingHash.get(i)==null||!(boolean) followingHash.get(i)){
                    isFollowing.put(toUserIds.get(i),Boolean.FALSE);
                }
                else{
                    isFollowing.put(toUserIds.get(i),Boolean.TRUE);
                }
            }
            return isFollowing;
        }
        //TODO:DB
        return isFollowing;
    }
}
