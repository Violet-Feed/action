package violet.action.common.producer;

import com.alibaba.fastjson2.JSON;
import com.google.protobuf.util.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import violet.action.common.proto_gen.action.*;
import violet.action.common.proto_gen.aigc.AigcServiceGrpc;
import violet.action.common.proto_gen.aigc.GetCreationByIdRequest;
import violet.action.common.proto_gen.aigc.GetCreationByIdResponse;
import violet.action.common.proto_gen.common.BaseResp;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.utils.SnowFlake;

import java.util.stream.Collectors;

@Slf4j
@Component
public class ActionMqPublisher {
    @GrpcClient("aigc")
    private AigcServiceGrpc.AigcServiceBlockingStub aigcStub;
    @Autowired
    @Qualifier("kvrocksTemplate")
    private RedisTemplate<String, String> kvrocksTemplate;
    @Autowired
    private KafkaProducer kafkaProducer;
    private final SnowFlake actionIdGenerator = new SnowFlake(0, 0);
    private final String ACTION_TOPIC = "action";

    @Async("mqExecutor")
    public void publishClickEvent(ReportClickRequest req) {
        try {
            long actionId = actionIdGenerator.nextId();
            long timestamp = System.currentTimeMillis();
            ActionEvent event = ActionEvent.newBuilder()
                    .setActionId(actionId)
                    .setActionType(ActionType.Click_VALUE)
                    .setUserId(req.getUserId())
                    .setTimestamp(timestamp)
                    .setCreationList(req.getCreationIdsList().stream().map(String::valueOf).collect(Collectors.joining(",")))
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
            if (EntityType.creation.name().equals(req.getEntityType())) {
                GetCreationByIdRequest getCreationByIdRequest = GetCreationByIdRequest.newBuilder()
                        .setCreationId(req.getEntityId())
                        .build();
                GetCreationByIdResponse getCreationByIdResponse = aigcStub.getCreationById(getCreationByIdRequest);
                if (getCreationByIdResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
                    log.error("[publishDiggEvent] getCreationById err, err = {}", getCreationByIdResponse.getBaseResp());
                    return;
                }
                long authorId = getCreationByIdResponse.getCreation().getUserId();
                ActionPayload payload = ActionPayload.newBuilder()
                        .setAuthorId(authorId)
                        .build();
                ActionEvent event = ActionEvent.newBuilder()
                        .setActionId(actionId)
                        .setActionType(ActionType.Digg_VALUE)
                        .setUserId(req.getUserId())
                        .setTimestamp(timestamp)
                        .setCreationList(String.valueOf(req.getEntityId()))
                        .setPayload(payload)
                        .build();
                kafkaProducer.sendMessage(ACTION_TOPIC, JSON.toJSONString(event));
            } else if (EntityType.comment.name().equals(req.getEntityType())) {
                GetCommentByIdRequest getCommentByIdRequest = GetCommentByIdRequest.newBuilder()
                        .setCommentId(req.getEntityId())
                        .build();
                GetCommentByIdResponse getCommentByIdResponse = getCommentById(getCommentByIdRequest);
                if (getCommentByIdResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
                    log.error("[publishDiggEvent] getCommentById err, err = {}", getCommentByIdResponse.getBaseResp());
                    return;
                }
                CommentData comment = getCommentByIdResponse.getComment();
                long creationId = comment.getEntityId();
                long authorId = comment.getUserId();
                long parentCommentId = comment.getLevel() == 1 ? req.getEntityId() : comment.getParentId();
                ActionPayload payload = ActionPayload.newBuilder()
                        .setAuthorId(authorId)
                        .setOpCommentId(req.getEntityId())
                        .setParentCommentId(parentCommentId)
                        .build();
                ActionEvent event = ActionEvent.newBuilder()
                        .setActionId(actionId)
                        .setActionType(ActionType.DiggComment_VALUE)
                        .setUserId(req.getUserId())
                        .setTimestamp(timestamp)
                        .setCreationList(String.valueOf(creationId))
                        .setPayload(payload)
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
            if (EntityType.creation.name().equals(req.getEntityType())) {
                GetCreationByIdRequest getCreationByIdRequest = GetCreationByIdRequest.newBuilder()
                        .setCreationId(req.getEntityId())
                        .build();
                GetCreationByIdResponse getCreationByIdResponse = aigcStub.getCreationById(getCreationByIdRequest);
                if (getCreationByIdResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
                    log.error("[publishCreateCommentEvent] getCreationById err, err = {}", getCreationByIdResponse.getBaseResp());
                    return;
                }
                long authorId = getCreationByIdResponse.getCreation().getUserId();
                ActionPayload payload = ActionPayload.newBuilder()
                        .setAuthorId(authorId)
                        .setCommentId(commentId)
                        .setContentType(req.getContentType())
                        .setContent(req.getContent())
                        .build();
                ActionEvent event = ActionEvent.newBuilder()
                        .setActionId(actionId)
                        .setActionType(ActionType.CreateComment_VALUE)
                        .setUserId(req.getUserId())
                        .setTimestamp(timestamp)
                        .setCreationList(String.valueOf(req.getEntityId()))
                        .setPayload(payload)
                        .build();
                kafkaProducer.sendMessage(ACTION_TOPIC, JSON.toJSONString(event));
            }
        } catch (Exception ex) {
            log.error("[publishCreateCommentEvent] err", ex);
        }
    }

    @Async("mqExecutor")
    public void publishCreateReplyEvent(CreateCommentReplyRequest req, Long commentId) {
        try {
            long actionId = actionIdGenerator.nextId();
            long timestamp = System.currentTimeMillis();
            if (EntityType.creation.name().equals(req.getEntityType())) {
                long opCommentId, authorId;
                if (req.getSibId() == 0) {
                    opCommentId = req.getParentId();
                    GetCommentByIdRequest getCommentByIdRequest = GetCommentByIdRequest.newBuilder()
                            .setCommentId(req.getParentId())
                            .build();
                    GetCommentByIdResponse getCommentByIdResponse = getCommentById(getCommentByIdRequest);
                    if (getCommentByIdResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
                        log.error("[publishCreateReplyEvent] getCommentById err, err = {}", getCommentByIdResponse.getBaseResp());
                        return;
                    }
                    authorId = getCommentByIdResponse.getComment().getUserId();
                } else {
                    opCommentId = req.getSibId();
                    authorId = req.getSibUserId();
                }
                ActionPayload payload = ActionPayload.newBuilder()
                        .setAuthorId(authorId)
                        .setOpCommentId(opCommentId)
                        .setCommentId(commentId)
                        .setContentType(req.getContentType())
                        .setContent(req.getContent())
                        .setParentCommentId(req.getParentId())
                        .build();
                ActionEvent event = ActionEvent.newBuilder()
                        .setActionId(actionId)
                        .setActionType(ActionType.CreateReply_VALUE)
                        .setUserId(req.getUserId())
                        .setTimestamp(timestamp)
                        .setCreationList(String.valueOf(req.getEntityId()))
                        .setPayload(payload)
                        .build();
                kafkaProducer.sendMessage(ACTION_TOPIC, JSON.toJSONString(event));
            }
        } catch (Exception ex) {
            log.error("[publishCreateReplyEvent] err", ex);
        }
    }

    public GetCommentByIdResponse getCommentById(GetCommentByIdRequest req) throws Exception {
        GetCommentByIdResponse.Builder resp = GetCommentByIdResponse.newBuilder();
        String key = "comment:" + req.getCommentId();
        String commentStr = kvrocksTemplate.opsForValue().get(key);
        CommentData.Builder builder = CommentData.newBuilder();
        JsonFormat.parser().ignoringUnknownFields().merge(commentStr, builder);
        CommentData commentData = builder.build();
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).setComment(commentData).build();
    }
}
