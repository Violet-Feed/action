package violet.action.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.RelationMapper;
import violet.action.common.pojo.User;
import violet.action.common.proto_gen.action.*;
import violet.action.common.service.RelationService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationServiceImpl implements RelationService {
    @Autowired
    private RelationMapper relationMapper;

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
    public GetFollowListResponse getFollowingList(GetFollowListRequest req) {
        List<User> userList=relationMapper.getFollowingList(req.getUserId());
        List<Long> userIdList=new ArrayList<>();
        for(User user:userList){
            userIdList.add(user.getUserId());
        }
        int total=userIdList.size();
        BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(userIdList).setTotal(total).build();
    }

    @Override
    public GetFollowListResponse getFollowedList(GetFollowListRequest req) {
        List<User> userList=relationMapper.getFollowedList(req.getUserId());
        List<Long> userIdList=new ArrayList<>();
        for(User user:userList){
            userIdList.add(user.getUserId());
        }
        int total=userIdList.size();
        BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(userIdList).setTotal(total).build();
    }

    @Override
    public GetFollowListResponse getFriendList(GetFollowListRequest req) {
        //TODO：改成查询两个list再取并集
        List<User> userList=relationMapper.getFriendList(req.getUserId());
        List<Long> userIdList=new ArrayList<>();
        for(User user:userList){
            userIdList.add(user.getUserId());
        }
        int total=userIdList.size();
        BaseResp baseResp= BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return GetFollowListResponse.newBuilder().setBaseResp(baseResp).addAllUserIds(userIdList).setTotal(total).build();
    }
}
