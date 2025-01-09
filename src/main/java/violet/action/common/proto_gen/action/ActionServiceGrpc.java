package violet.action.common.proto_gen.action;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.6.1)",
    comments = "Source: proto/action.proto")
public final class ActionServiceGrpc {

  private ActionServiceGrpc() {}

  public static final String SERVICE_NAME = "ActionService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<violet.action.common.proto_gen.action.FollowRequest,
      violet.action.common.proto_gen.action.FollowResponse> METHOD_FOLLOW =
      io.grpc.MethodDescriptor.<violet.action.common.proto_gen.action.FollowRequest, violet.action.common.proto_gen.action.FollowResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ActionService", "Follow"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.FollowRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.FollowResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<violet.action.common.proto_gen.action.FollowRequest,
      violet.action.common.proto_gen.action.FollowResponse> METHOD_UNFOLLOW =
      io.grpc.MethodDescriptor.<violet.action.common.proto_gen.action.FollowRequest, violet.action.common.proto_gen.action.FollowResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ActionService", "Unfollow"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.FollowRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.FollowResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<violet.action.common.proto_gen.action.MIsFollowRequest,
      violet.action.common.proto_gen.action.MIsFollowResponse> METHOD_MIS_FOLLOWING =
      io.grpc.MethodDescriptor.<violet.action.common.proto_gen.action.MIsFollowRequest, violet.action.common.proto_gen.action.MIsFollowResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ActionService", "MIsFollowing"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.MIsFollowRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.MIsFollowResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<violet.action.common.proto_gen.action.MIsFollowRequest,
      violet.action.common.proto_gen.action.MIsFollowResponse> METHOD_MIS_FOLLOWER =
      io.grpc.MethodDescriptor.<violet.action.common.proto_gen.action.MIsFollowRequest, violet.action.common.proto_gen.action.MIsFollowResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ActionService", "MIsFollower"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.MIsFollowRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.MIsFollowResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<violet.action.common.proto_gen.action.GetFollowListRequest,
      violet.action.common.proto_gen.action.GetFollowListResponse> METHOD_GET_FOLLOWING_LIST =
      io.grpc.MethodDescriptor.<violet.action.common.proto_gen.action.GetFollowListRequest, violet.action.common.proto_gen.action.GetFollowListResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ActionService", "GetFollowingList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.GetFollowListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.GetFollowListResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<violet.action.common.proto_gen.action.GetFollowListRequest,
      violet.action.common.proto_gen.action.GetFollowListResponse> METHOD_GET_FOLLOWER_LIST =
      io.grpc.MethodDescriptor.<violet.action.common.proto_gen.action.GetFollowListRequest, violet.action.common.proto_gen.action.GetFollowListResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ActionService", "GetFollowerList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.GetFollowListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.GetFollowListResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<violet.action.common.proto_gen.action.GetFollowListRequest,
      violet.action.common.proto_gen.action.GetFollowListResponse> METHOD_GET_FRIEND_LIST =
      io.grpc.MethodDescriptor.<violet.action.common.proto_gen.action.GetFollowListRequest, violet.action.common.proto_gen.action.GetFollowListResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ActionService", "GetFriendList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.GetFollowListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.GetFollowListResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<violet.action.common.proto_gen.action.MGetFollowCountRequest,
      violet.action.common.proto_gen.action.MGetFollowCountResponse> METHOD_GET_FOLLOW_COUNT =
      io.grpc.MethodDescriptor.<violet.action.common.proto_gen.action.MGetFollowCountRequest, violet.action.common.proto_gen.action.MGetFollowCountResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ActionService", "GetFollowCount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.MGetFollowCountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              violet.action.common.proto_gen.action.MGetFollowCountResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ActionServiceStub newStub(io.grpc.Channel channel) {
    return new ActionServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ActionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ActionServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ActionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ActionServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ActionServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void follow(violet.action.common.proto_gen.action.FollowRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.FollowResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_FOLLOW, responseObserver);
    }

    /**
     */
    public void unfollow(violet.action.common.proto_gen.action.FollowRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.FollowResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UNFOLLOW, responseObserver);
    }

    /**
     */
    public void mIsFollowing(violet.action.common.proto_gen.action.MIsFollowRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.MIsFollowResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MIS_FOLLOWING, responseObserver);
    }

    /**
     */
    public void mIsFollower(violet.action.common.proto_gen.action.MIsFollowRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.MIsFollowResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MIS_FOLLOWER, responseObserver);
    }

    /**
     */
    public void getFollowingList(violet.action.common.proto_gen.action.GetFollowListRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.GetFollowListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_FOLLOWING_LIST, responseObserver);
    }

    /**
     */
    public void getFollowerList(violet.action.common.proto_gen.action.GetFollowListRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.GetFollowListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_FOLLOWER_LIST, responseObserver);
    }

    /**
     */
    public void getFriendList(violet.action.common.proto_gen.action.GetFollowListRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.GetFollowListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_FRIEND_LIST, responseObserver);
    }

    /**
     */
    public void getFollowCount(violet.action.common.proto_gen.action.MGetFollowCountRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.MGetFollowCountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_FOLLOW_COUNT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_FOLLOW,
            asyncUnaryCall(
              new MethodHandlers<
                violet.action.common.proto_gen.action.FollowRequest,
                violet.action.common.proto_gen.action.FollowResponse>(
                  this, METHODID_FOLLOW)))
          .addMethod(
            METHOD_UNFOLLOW,
            asyncUnaryCall(
              new MethodHandlers<
                violet.action.common.proto_gen.action.FollowRequest,
                violet.action.common.proto_gen.action.FollowResponse>(
                  this, METHODID_UNFOLLOW)))
          .addMethod(
            METHOD_MIS_FOLLOWING,
            asyncUnaryCall(
              new MethodHandlers<
                violet.action.common.proto_gen.action.MIsFollowRequest,
                violet.action.common.proto_gen.action.MIsFollowResponse>(
                  this, METHODID_MIS_FOLLOWING)))
          .addMethod(
            METHOD_MIS_FOLLOWER,
            asyncUnaryCall(
              new MethodHandlers<
                violet.action.common.proto_gen.action.MIsFollowRequest,
                violet.action.common.proto_gen.action.MIsFollowResponse>(
                  this, METHODID_MIS_FOLLOWER)))
          .addMethod(
            METHOD_GET_FOLLOWING_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                violet.action.common.proto_gen.action.GetFollowListRequest,
                violet.action.common.proto_gen.action.GetFollowListResponse>(
                  this, METHODID_GET_FOLLOWING_LIST)))
          .addMethod(
            METHOD_GET_FOLLOWER_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                violet.action.common.proto_gen.action.GetFollowListRequest,
                violet.action.common.proto_gen.action.GetFollowListResponse>(
                  this, METHODID_GET_FOLLOWER_LIST)))
          .addMethod(
            METHOD_GET_FRIEND_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                violet.action.common.proto_gen.action.GetFollowListRequest,
                violet.action.common.proto_gen.action.GetFollowListResponse>(
                  this, METHODID_GET_FRIEND_LIST)))
          .addMethod(
            METHOD_GET_FOLLOW_COUNT,
            asyncUnaryCall(
              new MethodHandlers<
                violet.action.common.proto_gen.action.MGetFollowCountRequest,
                violet.action.common.proto_gen.action.MGetFollowCountResponse>(
                  this, METHODID_GET_FOLLOW_COUNT)))
          .build();
    }
  }

  /**
   */
  public static final class ActionServiceStub extends io.grpc.stub.AbstractStub<ActionServiceStub> {
    private ActionServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ActionServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ActionServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ActionServiceStub(channel, callOptions);
    }

    /**
     */
    public void follow(violet.action.common.proto_gen.action.FollowRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.FollowResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_FOLLOW, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void unfollow(violet.action.common.proto_gen.action.FollowRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.FollowResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UNFOLLOW, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void mIsFollowing(violet.action.common.proto_gen.action.MIsFollowRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.MIsFollowResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MIS_FOLLOWING, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void mIsFollower(violet.action.common.proto_gen.action.MIsFollowRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.MIsFollowResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MIS_FOLLOWER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getFollowingList(violet.action.common.proto_gen.action.GetFollowListRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.GetFollowListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_FOLLOWING_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getFollowerList(violet.action.common.proto_gen.action.GetFollowListRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.GetFollowListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_FOLLOWER_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getFriendList(violet.action.common.proto_gen.action.GetFollowListRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.GetFollowListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_FRIEND_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getFollowCount(violet.action.common.proto_gen.action.MGetFollowCountRequest request,
        io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.MGetFollowCountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_FOLLOW_COUNT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ActionServiceBlockingStub extends io.grpc.stub.AbstractStub<ActionServiceBlockingStub> {
    private ActionServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ActionServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ActionServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ActionServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public violet.action.common.proto_gen.action.FollowResponse follow(violet.action.common.proto_gen.action.FollowRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_FOLLOW, getCallOptions(), request);
    }

    /**
     */
    public violet.action.common.proto_gen.action.FollowResponse unfollow(violet.action.common.proto_gen.action.FollowRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UNFOLLOW, getCallOptions(), request);
    }

    /**
     */
    public violet.action.common.proto_gen.action.MIsFollowResponse mIsFollowing(violet.action.common.proto_gen.action.MIsFollowRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MIS_FOLLOWING, getCallOptions(), request);
    }

    /**
     */
    public violet.action.common.proto_gen.action.MIsFollowResponse mIsFollower(violet.action.common.proto_gen.action.MIsFollowRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MIS_FOLLOWER, getCallOptions(), request);
    }

    /**
     */
    public violet.action.common.proto_gen.action.GetFollowListResponse getFollowingList(violet.action.common.proto_gen.action.GetFollowListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_FOLLOWING_LIST, getCallOptions(), request);
    }

    /**
     */
    public violet.action.common.proto_gen.action.GetFollowListResponse getFollowerList(violet.action.common.proto_gen.action.GetFollowListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_FOLLOWER_LIST, getCallOptions(), request);
    }

    /**
     */
    public violet.action.common.proto_gen.action.GetFollowListResponse getFriendList(violet.action.common.proto_gen.action.GetFollowListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_FRIEND_LIST, getCallOptions(), request);
    }

    /**
     */
    public violet.action.common.proto_gen.action.MGetFollowCountResponse getFollowCount(violet.action.common.proto_gen.action.MGetFollowCountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_FOLLOW_COUNT, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ActionServiceFutureStub extends io.grpc.stub.AbstractStub<ActionServiceFutureStub> {
    private ActionServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ActionServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ActionServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ActionServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<violet.action.common.proto_gen.action.FollowResponse> follow(
        violet.action.common.proto_gen.action.FollowRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_FOLLOW, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<violet.action.common.proto_gen.action.FollowResponse> unfollow(
        violet.action.common.proto_gen.action.FollowRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UNFOLLOW, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<violet.action.common.proto_gen.action.MIsFollowResponse> mIsFollowing(
        violet.action.common.proto_gen.action.MIsFollowRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MIS_FOLLOWING, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<violet.action.common.proto_gen.action.MIsFollowResponse> mIsFollower(
        violet.action.common.proto_gen.action.MIsFollowRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MIS_FOLLOWER, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<violet.action.common.proto_gen.action.GetFollowListResponse> getFollowingList(
        violet.action.common.proto_gen.action.GetFollowListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_FOLLOWING_LIST, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<violet.action.common.proto_gen.action.GetFollowListResponse> getFollowerList(
        violet.action.common.proto_gen.action.GetFollowListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_FOLLOWER_LIST, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<violet.action.common.proto_gen.action.GetFollowListResponse> getFriendList(
        violet.action.common.proto_gen.action.GetFollowListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_FRIEND_LIST, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<violet.action.common.proto_gen.action.MGetFollowCountResponse> getFollowCount(
        violet.action.common.proto_gen.action.MGetFollowCountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_FOLLOW_COUNT, getCallOptions()), request);
    }
  }

  private static final int METHODID_FOLLOW = 0;
  private static final int METHODID_UNFOLLOW = 1;
  private static final int METHODID_MIS_FOLLOWING = 2;
  private static final int METHODID_MIS_FOLLOWER = 3;
  private static final int METHODID_GET_FOLLOWING_LIST = 4;
  private static final int METHODID_GET_FOLLOWER_LIST = 5;
  private static final int METHODID_GET_FRIEND_LIST = 6;
  private static final int METHODID_GET_FOLLOW_COUNT = 7;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ActionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ActionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FOLLOW:
          serviceImpl.follow((violet.action.common.proto_gen.action.FollowRequest) request,
              (io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.FollowResponse>) responseObserver);
          break;
        case METHODID_UNFOLLOW:
          serviceImpl.unfollow((violet.action.common.proto_gen.action.FollowRequest) request,
              (io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.FollowResponse>) responseObserver);
          break;
        case METHODID_MIS_FOLLOWING:
          serviceImpl.mIsFollowing((violet.action.common.proto_gen.action.MIsFollowRequest) request,
              (io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.MIsFollowResponse>) responseObserver);
          break;
        case METHODID_MIS_FOLLOWER:
          serviceImpl.mIsFollower((violet.action.common.proto_gen.action.MIsFollowRequest) request,
              (io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.MIsFollowResponse>) responseObserver);
          break;
        case METHODID_GET_FOLLOWING_LIST:
          serviceImpl.getFollowingList((violet.action.common.proto_gen.action.GetFollowListRequest) request,
              (io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.GetFollowListResponse>) responseObserver);
          break;
        case METHODID_GET_FOLLOWER_LIST:
          serviceImpl.getFollowerList((violet.action.common.proto_gen.action.GetFollowListRequest) request,
              (io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.GetFollowListResponse>) responseObserver);
          break;
        case METHODID_GET_FRIEND_LIST:
          serviceImpl.getFriendList((violet.action.common.proto_gen.action.GetFollowListRequest) request,
              (io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.GetFollowListResponse>) responseObserver);
          break;
        case METHODID_GET_FOLLOW_COUNT:
          serviceImpl.getFollowCount((violet.action.common.proto_gen.action.MGetFollowCountRequest) request,
              (io.grpc.stub.StreamObserver<violet.action.common.proto_gen.action.MGetFollowCountResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class ActionServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return violet.action.common.proto_gen.action.Action.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ActionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ActionServiceDescriptorSupplier())
              .addMethod(METHOD_FOLLOW)
              .addMethod(METHOD_UNFOLLOW)
              .addMethod(METHOD_MIS_FOLLOWING)
              .addMethod(METHOD_MIS_FOLLOWER)
              .addMethod(METHOD_GET_FOLLOWING_LIST)
              .addMethod(METHOD_GET_FOLLOWER_LIST)
              .addMethod(METHOD_GET_FRIEND_LIST)
              .addMethod(METHOD_GET_FOLLOW_COUNT)
              .build();
        }
      }
    }
    return result;
  }
}