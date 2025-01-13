package violet.action.common.service.model;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.RelationMapper;
import violet.action.common.pojo.User;
import violet.action.common.utils.RedisMutex;

import java.time.Duration;
import java.util.*;

@Slf4j
@Service
public class RelationModel {
    public final String FOLLOW_CAFFEINE_KEY = "action:relation:follow_caffeine:%d";
    public final String FOLLOWING_COUNT_KEY = "action:relation:following_count:%d";
    public final String FOLLOWER_COUNT_KEY = "action:relation:follower_count:%d";
    public final String FRIEND_COUNT_KEY = "action:relation:friend_count:%d";
    public final String FOLLOWING_LIST_KEY = "action:relation:following_list:%d";
    public final String FOLLOWER_LIST_KEY = "action:relation:follower_list:%d";
    public final String FRIEND_LIST_KEY = "action:relation:friend_list:%d";
    public final String FOLLOWING_HASH_KEY = "action:relation:following_hash:%d";

    public final String guardIndex="0";

    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private RedisMutex redisMutex;
    @Autowired
    private Neo4jClient neo4jClient;

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
            log.error("[RelationModel:getFollowingListFromCache] err = redis mutex failed");
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
            log.error("[RelationModel:getFollowerListFromCache] err = redis mutex failed");
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
            log.error("[RelationModel:getFriendListFromCache] err = redis mutex failed");
            return null;
        }
        List<User> userList=relationMapper.getFriendList(userId);
        List<Long> friendList=new ArrayList<>();
        Map<Long,Boolean> friendMap=new HashMap<>();
        for(User user:userList){
            if(friendMap.containsKey(user.getUserId())){
                friendList.add(user.getUserId());
            }else{
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
        fields.add(guardIndex);
        List<Object> followingHash = redisTemplate.opsForHash().multiGet(followingHashKey, fields);
        if(followingHash.get(followingHash.size()-1)!=null){
            for(int i=0;i<followingHash.size()-1;i++){
                if(followingHash.get(i)==null){
                    isFollowing.put(toUserIds.get(i),Boolean.FALSE);
                }else{
                    isFollowing.put(toUserIds.get(i),Boolean.TRUE);
                }
            }
            return isFollowing;
        }
        boolean locked= redisMutex.lock(followingHashKey);
        followingHash = redisTemplate.opsForHash().multiGet(followingHashKey, fields);
        if(followingHash.get(followingHash.size()-1)!=null){
            for(int i=0;i<followingHash.size()-1;i++){
                if(followingHash.get(i)==null){
                    isFollowing.put(toUserIds.get(i),Boolean.FALSE);
                }else{
                    isFollowing.put(toUserIds.get(i),Boolean.TRUE);
                }
            }
            return isFollowing;
        }
        if(!locked){
            log.error("[RelationModel:getFollowingHashFromCache] err = redis mutex failed");
            return null;
        }
        List<User> followingList=relationMapper.getFollowingList(userId);
        Map<String,String> followingMap=new HashMap<>();
        for(User user:followingList){
            followingMap.put(user.getUserId().toString(),"1");
        }
        for(Long toUserId:toUserIds){
            if(followingMap.containsKey(toUserId.toString())){
                isFollowing.put(toUserId,Boolean.TRUE);
            }else{
                isFollowing.put(toUserId,Boolean.FALSE);
            }
        }
        followingMap.put(guardIndex,"1");
        redisTemplate.opsForHash().putAll(followingHashKey,followingMap);
        redisTemplate.expire(followingHashKey,Duration.ofDays(2));
        redisMutex.unlock(followingHashKey);
        return isFollowing;
    }

    public Map<Long,Boolean> getFollowerHashFromCache(Long userId,List<Long> toUserIds) {
        Map<Long,Boolean> isFollower=new HashMap<>();
        List<Object> results = redisTemplate.executePipelined(new SessionCallback<Object>(){
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for(Long toUserId:toUserIds){
                    String followingHashKey= String.format(FOLLOWING_HASH_KEY, toUserId);
                    operations.opsForHash().get(followingHashKey,guardIndex);
                    operations.opsForHash().get(followingHashKey,userId.toString());
                }
                return null;
            }
        });
        List<Long> missIds=new ArrayList<>();
        for(int i=0;i<results.size();i+=2){
            if(results.get(i)==null){
                missIds.add(toUserIds.get(i/2));
            }else if(results.get(i+1)==null){
                isFollower.put(toUserIds.get(i/2),Boolean.FALSE);
            }else{
                isFollower.put(toUserIds.get(i/2),Boolean.TRUE);
            }
        }
//        boolean locked= redisMutex.lock(followingHashKey);
        results = redisTemplate.executePipelined(new SessionCallback<Object>(){
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for(Long toUserId:missIds){
                    String followingHashKey= String.format(FOLLOWING_HASH_KEY, toUserId);
                    operations.opsForHash().get(followingHashKey,guardIndex);
                    operations.opsForHash().get(followingHashKey,userId.toString());
                }
                return null;
            }
        });
        List<Long> newMissIds=new ArrayList<>();
        for(int i=0;i<results.size();i+=2){
            if(results.get(i)==null){
                newMissIds.add(missIds.get(i/2));
            }else if(results.get(i+1)==null){
                isFollower.put(missIds.get(i/2),Boolean.FALSE);
            }else{
                isFollower.put(missIds.get(i/2),Boolean.TRUE);
            }
        }
//        if(!locked){
//            log.error("[RelationModel:getFollowerHashFromCache] err = redis mutex failed");
//            return null;
//        }
        Map<Long,Map<String,String>> followingMaps=mGetFollowingMap(newMissIds);
        for(Long toUserId:newMissIds){
            if(!followingMaps.containsKey(toUserId)) {
                isFollower.put(toUserId,Boolean.FALSE);
                followingMaps.put(toUserId,new HashMap<>());
            }else if(!followingMaps.get(toUserId).containsKey(userId.toString())){
                isFollower.put(toUserId,Boolean.FALSE);
            }else{
                isFollower.put(toUserId,Boolean.TRUE);
            }
            followingMaps.get(toUserId).put(guardIndex,"1");
        }
        redisTemplate.executePipelined(new SessionCallback<Object>(){
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for(Map.Entry<Long,Map<String,String>> entry:followingMaps.entrySet()){
                    String followingHashKey= String.format(FOLLOWING_HASH_KEY, entry.getKey());
                    operations.opsForHash().putAll(followingHashKey,entry.getValue());
                    operations.expire(followingHashKey,Duration.ofDays(2));
                }
                return null;
            }
        });
//        redisMutex.unlock(followingHashKey);
        return isFollower;
    }

    public Map<Long,Map<String,String>> mGetFollowingMap(List<Long> userIds) {
        Map<Long,Map<String,String>> followingMaps=new HashMap<>();
        String cypher="UNWIND $userIds as row MATCH (a:user {userId: row})-[:follow]->(b:user) RETURN a.userId, b.userId";
        Map<String,Object> param=new HashMap<>();
        param.put("userIds",userIds);
        Object[] records = neo4jClient.query(cypher).bindAll(param).fetch().all().toArray();
        for(Object record:records){
            Map<String,Long> map = (Map<String, Long>) record;
            if(!followingMaps.containsKey(map.get("a.userId"))){
                followingMaps.put(map.get("a.userId"),new HashMap<>());
            }
            followingMaps.get(map.get("a.userId")).put(map.get("b.userId").toString(),"1");
        }
        return followingMaps;
    }
}
