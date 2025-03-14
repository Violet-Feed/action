package violet.action.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import violet.action.common.proto_gen.action.*;
import violet.action.common.service.RelationService;

@RestController
@RequestMapping("/api/action/relation")
public class RelationController {
    @Autowired
    private RelationService relationService;

    @PostMapping("/follow")
    public FollowResponse follow(@RequestBody FollowRequest req) {
        return relationService.follow(req);
    }

    @PostMapping("/unfollow")
    public FollowResponse unfollow(@RequestBody FollowRequest req) {
        return relationService.unfollow(req);
    }

    @GetMapping("/is_following")
    public MIsFollowResponse isFollowing(@RequestBody MIsFollowRequest req) {
        return relationService.mIsFollowing(req);
    }

    @GetMapping("/is_follower")
    public MIsFollowResponse isFollower(@RequestBody MIsFollowRequest req) {
        return relationService.mIsFollower(req);
    }

    @GetMapping("/get_following_list")
    public GetFollowListResponse getFollowingList(@RequestBody GetFollowListRequest req) {
        return relationService.getFollowingList(req);
    }

    @GetMapping("/get_follower_list")
    public GetFollowListResponse getFollowerList(@RequestBody GetFollowListRequest req) {
        return relationService.getFollowerList(req);
    }

    @GetMapping("/get_friend_list")
    public GetFollowListResponse getFriendList(@RequestBody GetFollowListRequest req) {
        return relationService.getFriendList(req);
    }

    @GetMapping("/get_follow_count")
    public MGetFollowCountResponse mGetFollowCount(@RequestBody MGetFollowCountRequest req) {
        return relationService.mGetFollowCount(req);
    }
}
