package violet.action.common.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import violet.action.common.config.CaffeineConfig;
import violet.action.common.mapper.RelationMapper;
import violet.action.common.proto_gen.action.*;
import violet.action.common.proto_gen.common.BaseResp;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.proto_gen.im.*;
import violet.action.common.service.RelationService;
import violet.action.common.service.model.RelationModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class RelationServiceImpl implements RelationService {
    private final Long MAX_FOLLOWING_COUNT = 5000L;

    @GrpcClient("im")
    private IMServiceGrpc.IMServiceBlockingStub imStub;
    @Autowired
    private RelationModel relationModel;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private LoadingCache<String, CaffeineConfig.StarCacheResult> starCaffeineCache;
    @Autowired
    private Cache<String, List<Long>> listCaffeineCache;
    @Autowired
    private Cache<String, Map<Long, Boolean>> hashCaffeineCache;
    @Autowired
    private Cache<String, Long> countCaffeineCache;
    @Autowired
    private CaffeineConfig.HitCaffeineCache hitCaffeineCache;

    public boolean isStar(Long userId) {
        return false;
    }

    @Override
    public FollowResponse follow(FollowRequest req) {
        //判断存在?->每日关注上限redis200->总关注上限5000(获取关注数pack/counter/redis)->写mysql(成功写备,失败mq?)->写bg->
        //更新关注粉丝列表关注hash缓存->更新每日上限->更新计数->发送消息mq->检验mq
        List<Long> userIds = new ArrayList<>();
        userIds.add(req.getFromUserId());
        Long followingCount = relationModel.getFollowingCountFromDB(userIds).get(req.getFromUserId());
        if (followingCount >= MAX_FOLLOWING_COUNT) {
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.OverLimit_Error).setStatusMessage("关注人数达到上限").build();
            return FollowResponse.newBuilder().setBaseResp(baseResp).build();
        }
        relationMapper.follow(req.getFromUserId(), req.getToUserId());
        relationModel.updateCache(req.getFromUserId(), req.getToUserId(), 1);
        CompletableFuture.runAsync(() -> {
            SendNoticeRequest sendNoticeRequest = SendNoticeRequest.newBuilder()
                    .setUserId(req.getToUserId())
                    .setGroup(NoticeGroup.Follow_Group_VALUE)
                    .setNoticeType(NoticeType.Follow_VALUE)
                    .setSenderId(req.getFromUserId())
                    .build();
            SendNoticeResponse sendNoticeResponse = imStub.sendNotice(sendNoticeRequest);
            if (sendNoticeResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
                log.error("[follow] send follow notice failed, resp = {}", sendNoticeResponse.getBaseResp());
            }
        });
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return FollowResponse.newBuilder().setBaseResp(baseResp).build();
    }

    @Override
    public FollowResponse unfollow(FollowRequest req) {
        //每日取关上限redis200->更新mysql(成功写备,失败mq?)->写bg->更新关注粉丝列表关注hash缓存->更新每日上限->更新计数->发送消息mq->检验mq
        relationMapper.unfollow(req.getFromUserId(), req.getToUserId());
        relationModel.updateCache(req.getFromUserId(), req.getToUserId(), -1);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return FollowResponse.newBuilder().setBaseResp(baseResp).build();
    }

    @Override
    public MIsFollowResponse mIsFollowing(MIsFollowRequest req) {
        //热点用户->获取from关注量->
        //关注量小于2000或查询量大于20或失败->list->获取关注列表
        //hash->redis(hmget)+锁+redis+db+写redis
        Long userId = req.getFromUserId();
        List<Long> toUserIds = req.getToUserIdsList();
        Map<Long, Boolean> isFollowing = new HashMap<>();
        if (isStar(userId)) {
            String followCaffeineKey = String.format(relationModel.FOLLOW_STAR_KEY, userId);
            CaffeineConfig.StarCacheResult starCacheResult = starCaffeineCache.get(followCaffeineKey);
            if (starCacheResult == null) {
                log.error("[RelationServiceImpl:mIsFollowing] user_id = {}, err = starCacheResult is null", userId);
                BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
                return MIsFollowResponse.newBuilder().setBaseResp(baseResp).build();
            }
            Map<Long, Boolean> followingMap = starCacheResult.getFollowingMap();
            for (Long toUserId : toUserIds) {
                if (followingMap.containsKey(toUserId)) {
                    isFollowing.put(toUserId, Boolean.TRUE);
                } else {
                    isFollowing.put(toUserId, Boolean.FALSE);
                }
            }
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
            return MIsFollowResponse.newBuilder().setBaseResp(baseResp).putAllIsFollowing(isFollowing).build();
        }
        MGetFollowCountRequest countReq = MGetFollowCountRequest.newBuilder().addUserIds(userId).setNeedFollow(true).build();
        long followingCount = mGetFollowCount(countReq).getFollowingCountOrThrow(userId);
        if (followingCount <= 2000 || toUserIds.size() > 20) {
            List<Long> followingList = relationModel.getFollowingListFromDB(userId);
            for (Long toUserId : toUserIds) {
                isFollowing.put(toUserId, false);
            }
            for (Long followingId : followingList) {
                if (isFollowing.containsKey(followingId)) {
                    isFollowing.put(followingId, true);
                }
            }
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
            return MIsFollowResponse.newBuilder().setBaseResp(baseResp).putAllIsFollowing(isFollowing).build();
        }
        isFollowing = relationModel.getFollowingHashFromDB(userId, toUserIds);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return MIsFollowResponse.newBuilder().setBaseResp(baseResp).putAllIsFollowing(isFollowing).build();
    }

    @Override
    public MIsFollowResponse mIsFollower(MIsFollowRequest req) {
        //粉丝量小于800?或查询量大于0?且粉丝小于5000->list->获取粉丝列表
        //hash->获取to是否关注->热点用户->本地缓存LFU(是热key但未中->获取list,不是热key->batch hget(守护索引)+加锁+hget+db(list))->hmset2.5~3天
        Long userId = req.getFromUserId();
        List<Long> toUserIds = req.getToUserIdsList();
        Map<Long, Boolean> isFollower = new HashMap<>();
        MGetFollowCountRequest countReq = MGetFollowCountRequest.newBuilder().addUserIds(userId).setNeedFollow(true).build();
        long followerCount = mGetFollowCount(countReq).getFollowerCountOrThrow(userId);
        if (followerCount <= 1000 || followerCount <= 5000 && toUserIds.size() > 20) {
            List<Long> followerList = relationModel.getFollowerListFromDB(userId);
            for (Long toUserId : toUserIds) {
                isFollower.put(toUserId, false);
            }
            for (Long followerId : followerList) {
                if (isFollower.containsKey(followerId)) {
                    isFollower.put(followerId, true);
                }
            }
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
            return MIsFollowResponse.newBuilder().setBaseResp(baseResp).putAllIsFollower(isFollower).build();
        }
        List<Long> missIds = new ArrayList<>();
        for (Long toUserId : toUserIds) {
            Map<Long, Boolean> followingMap;
            String followCaffeineKey = String.format(relationModel.FOLLOW_STAR_KEY, toUserId);
            if (isStar(toUserId)) {
                CaffeineConfig.StarCacheResult starCacheResult = starCaffeineCache.get(followCaffeineKey);
                if (starCacheResult == null) {
                    log.error("[RelationServiceImpl:mIsFollower] to_user_id = {}, err = starCacheResult is null", toUserId);
                }
                followingMap = starCacheResult.getFollowingMap();
            } else {
                AtomicInteger value = hitCaffeineCache.getCache().get(followCaffeineKey, fun -> new AtomicInteger(0));
                int total = hitCaffeineCache.getTotalCount().incrementAndGet();
                int hit = value.incrementAndGet();
                if ((double) hit / total > 0.001 && hit > 1) {
                    followingMap = hashCaffeineCache.getIfPresent(followCaffeineKey);
                    log.info("[RelationServiceImpl:mIsFollower] hit cache, toUserId = {}, total = {}, hit = {}, isLoad = {}", toUserId, total, hit, followingMap == null);
                    if (followingMap == null) {
                        //是否应该调getFollowingHashFromDB？
                        List<Long> followingList = relationModel.getFollowingListFromDB(toUserId);
                        followingMap = new HashMap<>();
                        for (Long followingId : followingList) {
                            followingMap.put(followingId, Boolean.TRUE);
                        }
                        hashCaffeineCache.put(followCaffeineKey, followingMap);
                    }
                } else {
                    missIds.add(toUserId);
                    continue;
                }
            }
            if (followingMap.containsKey(userId)) {
                isFollower.put(toUserId, Boolean.TRUE);
            } else {
                isFollower.put(toUserId, Boolean.FALSE);
            }
        }
        if (!missIds.isEmpty()) {
            Map<Long, Boolean> isFollowerDB = relationModel.getFollowerHashFromDB(userId, missIds);
            isFollower.putAll(isFollowerDB);
        }
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return MIsFollowResponse.newBuilder().setBaseResp(baseResp).putAllIsFollower(isFollower).build();
    }

    @Override
    public GetFollowListResponse getFollowingList(GetFollowListRequest req) {
        //是否热点用户->热点异步本地缓存(key:userId:followType,10s刷新，从redis/db读数据)
        //非热点redis(缓存容灾or指定bg)->加锁->再次redis？>mysql/bg->一致性检查->写缓存1天
        Long userId = req.getUserId();
        if (isStar(userId)) {
            String followCaffeineKey = String.format(relationModel.FOLLOW_STAR_KEY, userId);
            CaffeineConfig.StarCacheResult starCacheResult = starCaffeineCache.get(followCaffeineKey);
            if (starCacheResult == null) {
                log.error("[RelationServiceImpl:getFollowingList] user_id = {}, err = starCacheResult is null", userId);
                BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
                return GetFollowListResponse.newBuilder().setBaseResp(baseResp).build();
            }
            List<Long> followingList = starCacheResult.getFollowingList();
            int total = followingList.size();
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
            return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(followingList).setTotal(total).build();
        }
        List<Long> followingList = relationModel.getFollowingListFromDB(userId);
        int total = followingList.size();
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(followingList).setTotal(total).build();
    }

    @Override
    public GetFollowListResponse getFollowerList(GetFollowListRequest req) {
        //热点用户异步本地缓存->本地LRU缓存->redis+锁+redis+db+写redis->写本地缓存5s
        Long userId = req.getUserId();
        if (isStar(userId)) {
            String followCaffeineKey = String.format(relationModel.FOLLOW_STAR_KEY, userId);
            CaffeineConfig.StarCacheResult starCacheResult = starCaffeineCache.get(followCaffeineKey);
            if (starCacheResult == null) {
                log.error("[RelationServiceImpl:getFollowerList] user_id = {}, err = starCacheResult is null", userId);
                BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
                return GetFollowListResponse.newBuilder().setBaseResp(baseResp).build();
            }
            List<Long> followerList = starCacheResult.getFollowerList();
            int total = followerList.size();
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
            return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(followerList).setTotal(total).build();
        }
        String followerListKey = String.format(relationModel.FOLLOWER_LIST_KEY, userId);
        List<Long> followerList = listCaffeineCache.get(followerListKey, fun -> relationModel.getFollowerListFromDB(userId));
        int total = followerList.size();
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(followerList).setTotal(total).build();
    }

    @Override
    public GetFollowListResponse getFriendList(GetFollowListRequest req) {
        Long userId = req.getUserId();
        List<Long> friendList = relationModel.getFriendListFromDB(userId);
        int total = friendList.size();
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(friendList).setTotal(total).build();
    }

    @Override
    public MGetFollowCountResponse mGetFollowCount(MGetFollowCountRequest req) {
        //关注粉丝->无降级->本地缓存->调ies.counter->写本地缓存5s
        //降级->本地缓存->bg->写本地
        //互关->本地->获取互关列表->写本地
        List<Long> userIds = req.getUserIdsList();
        boolean needFollow = req.getNeedFollow();
        boolean needFriend = req.getNeedFriend();
        Map<Long, Long> followingCountMap = new HashMap<>();
        Map<Long, Long> followerCountMap = new HashMap<>();
        Map<Long, Long> friendCountMap = new HashMap<>();
        //本地缓存放入model？
        if (needFollow) {
            List<Long> missIds = new ArrayList<>();
            for (Long userId : userIds) {
                String followingCountKey = String.format(relationModel.FOLLOWING_COUNT_KEY, userId);
                Long cacheResult = countCaffeineCache.getIfPresent(followingCountKey);
                if (cacheResult == null) {
                    missIds.add(userId);
                } else {
                    followingCountMap.put(userId, cacheResult);
                }
            }
            if (!missIds.isEmpty()) {
                Map<Long, Long> followingCountMapDB = relationModel.getFollowingCountFromDB(missIds);
                for (Long userId : missIds) {
                    if (followingCountMapDB.containsKey(userId)) {
                        followingCountMap.put(userId, followingCountMapDB.get(userId));
                        String followingCountKey = String.format(relationModel.FOLLOWING_COUNT_KEY, userId);
                        countCaffeineCache.put(followingCountKey, followingCountMapDB.get(userId));
                    } else {
                        followingCountMap.put(userId, 0L);
                        String followingCountKey = String.format(relationModel.FOLLOWING_COUNT_KEY, userId);
                        countCaffeineCache.put(followingCountKey, 0L);
                    }
                }
            }
            missIds = new ArrayList<>();
            for (Long userId : userIds) {
                String followerCountKey = String.format(relationModel.FOLLOWER_COUNT_KEY, userId);
                Long cacheResult = countCaffeineCache.getIfPresent(followerCountKey);
                if (cacheResult == null) {
                    missIds.add(userId);
                } else {
                    followerCountMap.put(userId, cacheResult);
                }
            }
            if (!missIds.isEmpty()) {
                Map<Long, Long> followerCountMapDB = relationModel.getFollowerCountFromDB(missIds);
                for (Long userId : missIds) {
                    if (followerCountMapDB.containsKey(userId)) {
                        followerCountMap.put(userId, followerCountMapDB.get(userId));
                        String followerCountKey = String.format(relationModel.FOLLOWER_COUNT_KEY, userId);
                        countCaffeineCache.put(followerCountKey, followerCountMapDB.get(userId));
                    } else {
                        followerCountMap.put(userId, 0L);
                        String followerCountKey = String.format(relationModel.FOLLOWER_COUNT_KEY, userId);
                        countCaffeineCache.put(followerCountKey, 0L);
                    }
                }
            }
        }
        if (needFriend) {
            for (Long userId : userIds) {
                String friendCountKey = String.format(relationModel.FRIEND_COUNT_KEY, userId);
                Long cacheResult = countCaffeineCache.getIfPresent(friendCountKey);
                if (cacheResult == null) {
                    List<Long> friendList = relationModel.getFriendListFromDB(userId);
                    friendCountMap.put(userId, (long) friendList.size());
                    countCaffeineCache.put(friendCountKey, (long) friendList.size());
                } else {
                    friendCountMap.put(userId, cacheResult);
                }
            }
        }
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return MGetFollowCountResponse.newBuilder().setBaseResp(baseResp).putAllFollowingCount(followingCountMap).putAllFollowerCount(followerCountMap).putAllFriendCount(friendCountMap).build();
    }
}
