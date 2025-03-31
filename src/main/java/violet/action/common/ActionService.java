package violet.action.common;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import violet.action.common.proto_gen.action.*;
import violet.action.common.service.RelationService;
import violet.action.common.service.UserService;

@GrpcService
public class ActionService extends ActionServiceGrpc.ActionServiceImplBase {
    @Autowired
    private UserService userService;
    @Autowired
    private RelationService relationService;

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        LoginResponse resp = userService.login(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
        RegisterResponse resp = userService.register(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserInfos(GetUserInfosRequest request, StreamObserver<GetUserInfosResponse> responseObserver) {
        GetUserInfosResponse resp = userService.getUserInfos(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void searchUsers(SearchUsersRequest request, StreamObserver<SearchUsersResponse> responseObserver) {
        SearchUsersResponse resp = userService.searchUsers(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void follow(FollowRequest request, StreamObserver<FollowResponse> responseObserver) {
        FollowResponse resp = relationService.follow(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void unfollow(FollowRequest request, StreamObserver<FollowResponse> responseObserver) {
        FollowResponse resp = relationService.unfollow(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void getFollowingList(GetFollowListRequest request, StreamObserver<GetFollowListResponse> responseObserver) {
        GetFollowListResponse resp = relationService.getFollowingList(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void getFollowerList(GetFollowListRequest request, StreamObserver<GetFollowListResponse> responseObserver) {
        GetFollowListResponse resp = relationService.getFollowerList(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void getFriendList(GetFollowListRequest request, StreamObserver<GetFollowListResponse> responseObserver) {
        GetFollowListResponse resp = relationService.getFriendList(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void getFollowCount(MGetFollowCountRequest request, StreamObserver<MGetFollowCountResponse> responseObserver) {
        MGetFollowCountResponse resp = relationService.mGetFollowCount(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
