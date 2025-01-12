package violet.action.common.service;

import org.jetbrains.annotations.NotNull;
import violet.action.common.proto_gen.action.*;

import java.util.List;

public interface RelationService {
    FollowResponse follow(FollowRequest req);
    FollowResponse unfollow(FollowRequest req);
    MIsFollowResponse mIsFollowing(MIsFollowRequest req);
    MIsFollowResponse mIsFollower(MIsFollowRequest req);
    GetFollowListResponse getFollowingList(GetFollowListRequest req);
    GetFollowListResponse getFollowerList(GetFollowListRequest req);
    GetFollowListResponse getFriendList(GetFollowListRequest req);
    MGetFollowCountResponse mGetFollowCount(MGetFollowCountRequest req);
}