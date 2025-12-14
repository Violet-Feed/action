package violet.action.common.producer;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import violet.action.common.proto_gen.action.*;
import violet.action.common.proto_gen.aigc.AigcServiceGrpc;
import violet.action.common.proto_gen.aigc.GetCreationByIdRequest;
import violet.action.common.proto_gen.aigc.GetCreationByIdResponse;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.service.CommentService;
import violet.action.common.utils.SnowFlake;

@Slf4j
@Component
public class ActionMqPublisher {
    @GrpcClient("aigc")
    private AigcServiceGrpc.AigcServiceBlockingStub aigcStub;
    @Autowired
    private CommentService commentService;
    @Autowired
    private KafkaProducer kafkaProducer;
    private final SnowFlake actionIdGenerator = new SnowFlake(0, 0);
    private final String ACTION_TOPIC = "action";

    @Async("mqExecutor")
    public void publishClickEvent(ReportClickRequest req) {
        try {
            long actionId = actionIdGenerator.nextId();
            long timestamp = System.currentTimeMillis();
            ClickPayload payload = ClickPayload.newBuilder()
                    .addAllCreationIds(req.getCreationIdsList())
                    .build();
            ActionEvent event = ActionEvent.newBuilder()
                    .setActionId(actionId)
                    .setActionType(ActionType.Click_VALUE)
                    .setUserId(req.getUserId())
                    .setTimestamp(timestamp)
                    .setClickPayload(payload)
                    .build();
            kafkaProducer.sendMessage(ACTION_TOPIC, JSON.toJSONString(event));
        } catch (Exception ex) {
            log.error("[publishClickEvent] err", ex);
        }
    }

    @Async("mqExecutor")
    public void publishDiggEvent(DiggRequest req) {
        try {
            long actionId = actionIdGenerator.nextId();
            long timestamp = System.currentTimeMillis();
            if (EntityType.Creation.name().equals(req.getEntityType())) {
                GetCreationByIdRequest getCreationByIdRequest = GetCreationByIdRequest.newBuilder()
                        .setCreationId(req.getEntityId())
                        .build();
                GetCreationByIdResponse getCreationByIdResponse = aigcStub.getCreationById(getCreationByIdRequest);
                if (getCreationByIdResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
                    log.error("[publishDiggEvent] getCreationById err, err = {}", getCreationByIdResponse.getBaseResp());
                    return;
                }
                long ownerId = getCreationByIdResponse.getCreation().getUserId();
                DiggPayload payload = DiggPayload.newBuilder()
                        .setCreationId(req.getEntityId())
                        .setOwnerId(ownerId)
                        .build();
                ActionEvent event = ActionEvent.newBuilder()
                        .setActionId(actionId)
                        .setActionType(ActionType.Digg_VALUE)
                        .setUserId(req.getUserId())
                        .setTimestamp(timestamp)
                        .setDiggPayload(payload)
                        .build();
                kafkaProducer.sendMessage(ACTION_TOPIC, JSON.toJSONString(event));
            } else if (EntityType.Comment.name().equals(req.getEntityType())) {
                GetCommentByIdRequest getCommentByIdRequest = GetCommentByIdRequest.newBuilder()
                        .setCommentId(req.getEntityId())
                        .build();
                GetCommentByIdResponse getCommentByIdResponse = commentService.getCommentById(getCommentByIdRequest);
                if (getCommentByIdResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
                    log.error("[publishDiggEvent] getCommentById err, err = {}", getCommentByIdResponse.getBaseResp());
                    return;
                }
                CommentData comment = getCommentByIdResponse.getComment();
                long creationId = comment.getEntityId();
                long ownerId = comment.getUserId();
                long parentCommentId = comment.getLevel() == 1 ? req.getEntityId() : comment.getParentId();
                DiggCommentPayload payload = DiggCommentPayload.newBuilder()
                        .setCreationId(creationId)
                        .setCommentId(req.getEntityId())
                        .setOwnerId(ownerId)
                        .setParentCommentId(parentCommentId)
                        .build();
                ActionEvent event = ActionEvent.newBuilder()
                        .setActionId(actionId)
                        .setActionType(ActionType.DiggComment_VALUE)
                        .setUserId(req.getUserId())
                        .setTimestamp(timestamp)
                        .setDiggCommentPayload(payload)
                        .build();
                kafkaProducer.sendMessage(ACTION_TOPIC, JSON.toJSONString(event));
            }

        } catch (Exception ex) {
            log.error("[publishDiggEvent] err", ex);
        }
    }

    @Async("mqExecutor")
    public void publishCreateCommentEvent(CreateCommentRequest req, Long commentId) {
        try {
            long actionId = actionIdGenerator.nextId();
            long timestamp = System.currentTimeMillis();
            if (EntityType.Creation.name().equals(req.getEntityType())) {
                GetCreationByIdRequest getCreationByIdRequest = GetCreationByIdRequest.newBuilder()
                        .setCreationId(req.getEntityId())
                        .build();
                GetCreationByIdResponse getCreationByIdResponse = aigcStub.getCreationById(getCreationByIdRequest);
                if (getCreationByIdResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
                    log.error("[publishCreateCommentEvent] getCreationById err, err = {}", getCreationByIdResponse.getBaseResp());
                    return;
                }
                long ownerId = getCreationByIdResponse.getCreation().getUserId();
                CreateCommentPayload payload = CreateCommentPayload.newBuilder()
                        .setCreationId(req.getEntityId())
                        .setOwnerId(ownerId)
                        .setCommentId(commentId)
                        .setCommentContentType(req.getContentType())
                        .setCommentContent(req.getContent())
                        .build();
                ActionEvent event = ActionEvent.newBuilder()
                        .setActionId(actionId)
                        .setActionType(ActionType.CreateComment_VALUE)
                        .setUserId(req.getUserId())
                        .setTimestamp(timestamp)
                        .setCreateCommentPayload(payload)
                        .build();
                kafkaProducer.sendMessage(ACTION_TOPIC, JSON.toJSONString(event));
            }
        } catch (Exception ex) {
            log.error("[publishCreateCommentEvent] err", ex);
        }
    }

    @Async("mqExecutor")
    public void publishCreateReplyEvent(CreateCommentReplyRequest req, Long replyId) {
        try {
            long actionId = actionIdGenerator.nextId();
            long timestamp = System.currentTimeMillis();
            if (EntityType.Creation.name().equals(req.getEntityType())) {
                long commentId, ownerId;
                if (req.getSibId() == 0) {
                    commentId = req.getParentId();
                    GetCommentByIdRequest getCommentByIdRequest = GetCommentByIdRequest.newBuilder()
                            .setCommentId(req.getParentId())
                            .build();
                    GetCommentByIdResponse getCommentByIdResponse = commentService.getCommentById(getCommentByIdRequest);
                    if (getCommentByIdResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
                        log.error("[publishCreateReplyEvent] getCommentById err, err = {}", getCommentByIdResponse.getBaseResp());
                        return;
                    }
                    ownerId = getCommentByIdResponse.getComment().getUserId();
                } else {
                    commentId = req.getSibId();
                    ownerId = req.getSibUserId();
                }
                CreateReplyPayload payload = CreateReplyPayload.newBuilder()
                        .setCreationId(req.getEntityId())
                        .setCommentId(commentId)
                        .setOwnerId(ownerId)
                        .setReplyId(replyId)
                        .setReplyContentType(req.getContentType())
                        .setReplyContent(req.getContent())
                        .setParentCommentId(req.getParentId())
                        .build();
                ActionEvent event = ActionEvent.newBuilder()
                        .setActionId(actionId)
                        .setActionType(ActionType.CreateReply_VALUE)
                        .setUserId(req.getUserId())
                        .setTimestamp(timestamp)
                        .setCreateReplyPayload(payload)
                        .build();
                kafkaProducer.sendMessage(ACTION_TOPIC, JSON.toJSONString(event));
            }
        } catch (Exception ex) {
            log.error("[publishCreateReplyEvent] err", ex);
        }
    }

    @Async("mqExecutor")
    public void publishFollowEvent(FollowRequest req) {
        try {
            long actionId = actionIdGenerator.nextId();
            long timestamp = System.currentTimeMillis();
            FollowPayload payload = FollowPayload.newBuilder()
                    .setToUserId(req.getToUserId())
                    .build();
            ActionEvent event = ActionEvent.newBuilder()
                    .setActionId(actionId)
                    .setActionType(ActionType.Follow_VALUE)
                    .setUserId(req.getFromUserId())
                    .setTimestamp(timestamp)
                    .setFollowPayload(payload)
                    .build();
            kafkaProducer.sendMessage(ACTION_TOPIC, JSON.toJSONString(event));
        } catch (Exception ex) {
            log.error("[publishFollowEvent] err", ex);
        }
    }
}
