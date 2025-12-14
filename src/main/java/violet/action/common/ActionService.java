package violet.action.common;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import violet.action.common.proto_gen.action.*;
import violet.action.common.service.*;

@Slf4j
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
    @Autowired
    private ForwardService forwardService;

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        try {
            responseObserver.onNext(userService.login(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("login error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
        try {
            responseObserver.onNext(userService.register(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("register error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getUserInfos(GetUserInfosRequest request, StreamObserver<GetUserInfosResponse> responseObserver) {
        try {
            responseObserver.onNext(userService.getUserInfos(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("getUserInfos error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void searchUsers(SearchUsersRequest request, StreamObserver<SearchUsersResponse> responseObserver) {
        try {
            responseObserver.onNext(userService.searchUsers(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("searchUsers error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void reportClick(ReportClickRequest request, StreamObserver<ReportClickResponse> responseObserver) {
        try {
            responseObserver.onNext(userService.reportClick(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("reportClick error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void follow(FollowRequest request, StreamObserver<FollowResponse> responseObserver) {
        try {
            responseObserver.onNext(relationService.follow(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("follow error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void unfollow(FollowRequest request, StreamObserver<FollowResponse> responseObserver) {
        try {
            responseObserver.onNext(relationService.unfollow(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("unfollow error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void mIsFollowing(MIsFollowRequest request, StreamObserver<MIsFollowResponse> responseObserver) {
        try {
            responseObserver.onNext(relationService.mIsFollowing(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("mIsFollowing error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void mIsFollower(MIsFollowRequest request, StreamObserver<MIsFollowResponse> responseObserver) {
        try {
            responseObserver.onNext(relationService.mIsFollower(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("mIsFollower error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getFollowingList(GetFollowListRequest request, StreamObserver<GetFollowListResponse> responseObserver) {
        try {
            responseObserver.onNext(relationService.getFollowingList(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("getFollowingList error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getFollowerList(GetFollowListRequest request, StreamObserver<GetFollowListResponse> responseObserver) {
        try {
            responseObserver.onNext(relationService.getFollowerList(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("getFollowerList error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getFriendList(GetFollowListRequest request, StreamObserver<GetFollowListResponse> responseObserver) {
        try {
            responseObserver.onNext(relationService.getFriendList(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("getFriendList error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void mGetFollowCount(MGetFollowCountRequest request, StreamObserver<MGetFollowCountResponse> responseObserver) {
        try {
            responseObserver.onNext(relationService.mGetFollowCount(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("mGetFollowCount error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void digg(DiggRequest request, StreamObserver<DiggResponse> responseObserver) {
        try {
            responseObserver.onNext(diggService.digg(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("digg error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void cancelDigg(DiggRequest request, StreamObserver<DiggResponse> responseObserver) {
        try {
            responseObserver.onNext(diggService.cancelDigg(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("cancelDigg error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getDiggListByUser(GetDiggListByUserRequest request, StreamObserver<GetDiggListByUserResponse> responseObserver) {
        try {
            responseObserver.onNext(diggService.getDiggListByUser(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("getDiggListByUser error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void mGetDiggCountByEntity(MGetDiggCountByEntityRequest request, StreamObserver<MGetDiggCountByEntityResponse> responseObserver) {
        try {
            responseObserver.onNext(diggService.mGetDiggCountByEntity(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("mGetDiggCountByEntity error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void mIsDigg(MIsDiggRequest request, StreamObserver<MIsDiggResponse> responseObserver) {
        try {
            responseObserver.onNext(diggService.mIsDigg(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("mIsDigg error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void createComment(CreateCommentRequest request, StreamObserver<CreateCommentResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.createComment(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("createComment error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void createCommentReply(CreateCommentReplyRequest request, StreamObserver<CreateCommentReplyResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.createCommentReply(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("createCommentReply error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCommentById(GetCommentByIdRequest request, StreamObserver<GetCommentByIdResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.getCommentById(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("getCommentById error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCommentList(GetCommentListRequest request, StreamObserver<GetCommentListResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.getCommentList(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("getCommentList error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCommentReplyList(GetCommentReplyListRequest request, StreamObserver<GetCommentReplyListResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.getCommentReplyList(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("getCommentReplyList error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCommentCount(GetCommentCountRequest request, StreamObserver<GetCommentCountResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.getCommentCount(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("getCommentCount error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void diggComment(DiggCommentRequest request, StreamObserver<DiggCommentResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.diggComment(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("diggComment error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void cancelDiggComment(DiggCommentRequest request, StreamObserver<DiggCommentResponse> responseObserver) {
        try {
            responseObserver.onNext(commentService.cancelDiggComment(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("cancelDiggComment error", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void forward(ForwardRequest request, StreamObserver<ForwardResponse> responseObserver) {
        try {
            responseObserver.onNext(forwardService.forward(request));
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("forward error", e);
            responseObserver.onError(e);
        }
    }
}
