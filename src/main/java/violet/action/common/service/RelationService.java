package violet.action.common.service;

import violet.action.common.proto_gen.action.FollowRequest;
import violet.action.common.proto_gen.action.FollowResponse;
import violet.action.common.proto_gen.action.GetFollowListRequest;
import violet.action.common.proto_gen.action.GetFollowListResponse;

public interface RelationService {
    FollowResponse follow(FollowRequest req);
    FollowResponse unfollow(FollowRequest req);
    GetFollowListResponse getFollowingList(GetFollowListRequest req);
    GetFollowListResponse getFollowedList(GetFollowListRequest req);
    GetFollowListResponse getFriendList(GetFollowListRequest req);
}
