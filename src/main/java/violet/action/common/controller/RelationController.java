package violet.action.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import violet.action.common.proto_gen.action.FollowRequest;
import violet.action.common.proto_gen.action.FollowResponse;
import violet.action.common.proto_gen.action.GetFollowListRequest;
import violet.action.common.proto_gen.action.GetFollowListResponse;
import violet.action.common.service.RelationService;

@RestController
@RequestMapping("/api/action/relation")
public class RelationController {
    @Autowired
    private RelationService relationService;

    @PostMapping("/follow/")
    public FollowResponse follow(@RequestBody FollowRequest req) {
        return relationService.follow(req);
    }

    @PostMapping("/unfollow/")
    public FollowResponse unfollow(@RequestBody FollowRequest req) {
        return relationService.unfollow(req);
    }

    @GetMapping("/get_following_list/")
    public GetFollowListResponse getFollowingList(@RequestBody GetFollowListRequest req) {
        return relationService.getFollowingList(req);
    }

    @GetMapping("/get_followed_list/")
    public GetFollowListResponse getFollowedList(@RequestBody GetFollowListRequest req) {
        return relationService.getFollowedList(req);
    }

    @GetMapping("/get_friend_list/")
    public GetFollowListResponse getFriendList(@RequestBody GetFollowListRequest req) {
        return relationService.getFriendList(req);
    }
}
