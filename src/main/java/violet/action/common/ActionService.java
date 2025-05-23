package violet.action.common;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import violet.action.common.proto_gen.action.*;
import violet.action.common.service.CommentService;
import violet.action.common.service.DiggService;
import violet.action.common.service.RelationService;
import violet.action.common.service.UserService;

@GrpcService
public class ActionService extends ActionServiceGrpc.ActionServiceImplBase {
    @Autowired
    private UserService userService;
    @Autowired
    private RelationService relationService;
    @Autowired
    private DiggService diggService;
    @Autowired
    private CommentService commentService;

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
    public void mIsFollowing(MIsFollowRequest request, StreamObserver<MIsFollowResponse> responseObserver) {
        MIsFollowResponse resp = relationService.mIsFollowing(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void mIsFollower(MIsFollowRequest request, StreamObserver<MIsFollowResponse> responseObserver) {
        MIsFollowResponse resp = relationService.mIsFollower(request);
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
    public void mGetFollowCount(MGetFollowCountRequest request, StreamObserver<MGetFollowCountResponse> responseObserver) {
        MGetFollowCountResponse resp = relationService.mGetFollowCount(request);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void digg(DiggRequest request, StreamObserver<DiggResponse> responseObserver) {
        try {
            responseObserver.onNext(diggService.digg(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void cancelDigg(DiggRequest request, StreamObserver<DiggResponse> responseObserver) {
        try {
            responseObserver.onNext(diggService.cancelDigg(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getDiggListByUser(GetDiggListByUserRequest request, StreamObserver<GetDiggListByUserResponse> responseObserver) {
        try {
            responseObserver.onNext(diggService.getDiggListByUser(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void mGetDiggCountByEntity(MGetDiggCountByEntityRequest request, StreamObserver<MGetDiggCountByEntityResponse> responseObserver) {
        try {
            responseObserver.onNext(diggService.mGetDiggCountByEntity(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void mHasDigg(MHasDiggRequest request, StreamObserver<MHasDiggResponse> responseObserver) {
        try {
            responseObserver.onNext(diggService.mHasDigg(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void createComment(CreateCommentRequest request, StreamObserver<CreateCommentResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.createComment(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void createCommentReply(CreateCommentReplyRequest request, StreamObserver<CreateCommentReplyResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.createCommentReply(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCommentList(GetCommentListRequest request, StreamObserver<GetCommentListResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.getCommentList(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCommentReplyList(GetCommentReplyListRequest request, StreamObserver<GetCommentReplyListResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.getCommentReplyList(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCommentCount(GetCommentCountRequest request, StreamObserver<GetCommentCountResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.getCommentCount(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
