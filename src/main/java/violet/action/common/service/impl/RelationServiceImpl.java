package violet.action.common.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import violet.action.common.config.CaffeineConfig;
import violet.action.common.mapper.RelationMapper;
import violet.action.common.proto_gen.action.*;
import violet.action.common.service.RelationService;
import violet.action.common.service.model.RelationModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RelationServiceImpl implements RelationService {
    @Autowired
    private RelationModel relationModel;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private LoadingCache<String,Object> starCaffeineCache;
    @Autowired
    private Cache<String,Object> caffeineCache;
    @Autowired
    private Neo4jClient neo4jClient;

    public boolean isStar(Long userId){
        return false;
    }

    @Override
    public FollowResponse follow(FollowRequest req) {
        relationMapper.follow(req.getFromUserId(),req.getToUserId());
        BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return FollowResponse.newBuilder().setBaseResp(baseResp).build();
    }

    @Override
    public FollowResponse unfollow(FollowRequest req) {
        relationMapper.unfollow(req.getFromUserId(),req.getToUserId());
        BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return FollowResponse.newBuilder().setBaseResp(baseResp).build();
    }

    @Override
    public MIsFollowResponse mIsFollowing(MIsFollowRequest req) {
        //热点用户->获取from关注量->
        //关注量小于2000或查询量大于20或失败->list->获取关注列表
        //hash->redis(hmget)+锁+redis+db+写redis
        Long userId=req.getFromUserId();
        List<Long> toUserIds=req.getToUserIdsList();
        Map<Long,Boolean> isFollowing=new HashMap<>();
        if(isStar(userId)){
            String followingListKey= String.format(relationModel.FOLLOWING_LIST_KEY, userId);
            CaffeineConfig.StarCacheResult starCacheResult= (CaffeineConfig.StarCacheResult) starCaffeineCache.get(followingListKey);
            //TODO
            return null;
        }
        MGetFollowCountRequest countReq=MGetFollowCountRequest.newBuilder().addUserIds(userId).setNeedFollowing(true).build();
        long followingCount=mGetFollowCount(countReq).getFollowingCountOrDefault(userId,0);
        if(followingCount<=2000||toUserIds.size()>20){
            List<Long> followingList=relationModel.getFollowingListFromCache(userId);
            for(Long toUserId:toUserIds){
                isFollowing.put(toUserId,false);
            }
            for(Long followingId:followingList){
                if(isFollowing.containsKey(followingId)){
                    isFollowing.put(followingId,true);
                }
            }
            BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
            return MIsFollowResponse.newBuilder().setBaseResp(baseResp).putAllIsFollowing(isFollowing).build();
        }
        isFollowing=relationModel.getFollowingHashFromCache(userId,toUserIds);
        BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return MIsFollowResponse.newBuilder().setBaseResp(baseResp).putAllIsFollowing(isFollowing).build();
    }

    @Override
    public MIsFollowResponse mIsFollower(MIsFollowRequest req) {
        return null;
    }

    @Override
    public GetFollowListResponse getFollowingList(GetFollowListRequest req) {
        //是否热点用户->热点异步本地缓存(key:userId:followType,10s刷新，从redis/db读数据)
        //非热点redis(缓存容灾or指定bg)->加锁->再次redis？>mysql/bg->一致性检查->写缓存1天
        Long userId=req.getUserId();
        String followingListKey= String.format(relationModel.FOLLOWING_LIST_KEY, userId);
        if(isStar(userId)){
            CaffeineConfig.StarCacheResult starCacheResult= (CaffeineConfig.StarCacheResult) starCaffeineCache.get(followingListKey);
            if(starCacheResult==null){
                log.error("[RelationServiceImpl:getFollowingList] user_id = {}, err = starCacheResult is null",userId);
                BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
                return GetFollowListResponse.newBuilder().setBaseResp(baseResp).build();
            }
            List<Long> followingList=starCacheResult.getFollowingList();
            int total=followingList.size();
            BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
            return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(followingList).setTotal(total).build();
        }
        List<Long> followingList=relationModel.getFollowingListFromCache(userId);
        int total=followingList.size();
        BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(followingList).setTotal(total).build();
    }

    @Override
    public GetFollowListResponse getFollowerList(GetFollowListRequest req) {
        //热点用户异步本地缓存->本地LRU缓存->redis+锁+redis+db+写redis->写本地缓存5s
        Long userId=req.getUserId();
        String followerListKey= String.format(relationModel.FOLLOWER_LIST_KEY, userId);
        if(isStar(userId)){
            CaffeineConfig.StarCacheResult starCacheResult= (CaffeineConfig.StarCacheResult) starCaffeineCache.get(followerListKey);
            if(starCacheResult==null){
                log.error("[RelationServiceImpl:getFollowerList] user_id = {}, err = starCacheResult is null",userId);
                BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
                return GetFollowListResponse.newBuilder().setBaseResp(baseResp).build();
            }
            List<Long> followerList=starCacheResult.getFollowerList();
            int total=followerList.size();
            BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
            return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(followerList).setTotal(total).build();
        }
        List<Long> followerList= (List<Long>) caffeineCache.get(followerListKey, fun->relationModel.getFollowerListFromCache(userId));
        int total=followerList.size();
        BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(followerList).setTotal(total).build();
    }

    @Override
    public GetFollowListResponse getFriendList(GetFollowListRequest req) {
        Long userId=req.getUserId();
        List<Long> friendList=relationModel.getFriendListFromCache(userId);
        int total=friendList.size();
        BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(friendList).setTotal(total).build();
    }

    @Override
    public MGetFollowCountResponse mGetFollowCount(MGetFollowCountRequest req) {
        //关注粉丝->无降级->本地缓存->调ies.counter->写本地缓存5s
        //降级->本地缓存->bg->写本地
        //互关->本地->获取互关列表->写本地
        List<Long> userIds=req.getUserIdsList();
        Map<Long,Long> followingCountMap=new HashMap<>();
        Map<Long,Long> followerCountMap=new HashMap<>();
        //Map<Long,Long> friendCountMap=new HashMap<>();
        //TODO:local
        Map<String,Object> param=new HashMap<>();
        param.put("user_ids",userIds);
        String cypher="UNWIND $user_ids as row MATCH (a:user {userId: row})-[:follow]->(b:user) RETURN a.userId as userId, Count(*) as count";
        Object[] records = neo4jClient.query(cypher).bindAll(param).fetch().all().toArray();
        for(Object record:records){
            Map<String, Object> map = (Map<String, Object>) record;
            followingCountMap.put((Long) map.get("userId"), (Long) map.get("count"));
        }
        cypher="UNWIND $user_ids as row MATCH (a:user)-[:follow]->(b:user {userId: row}) RETURN b.userId as userId, Count(*) as count";
        records = neo4jClient.query(cypher).bindAll(param).fetch().all().toArray();
        for(Object record:records){
            Map<String, Object> map = (Map<String, Object>) record;
            followerCountMap.put((Long) map.get("userId"), (Long) map.get("count"));
        }
        BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return MGetFollowCountResponse.newBuilder().setBaseResp(baseResp).putAllFollowingCount(followingCountMap).putAllFollowerCount(followerCountMap).build();
    }
}
