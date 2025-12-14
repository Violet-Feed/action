package violet.action.common.consumer;

import com.alibaba.fastjson2.JSON;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import violet.action.common.pojo.Action;
import violet.action.common.proto_gen.action.ActionEvent;
import violet.action.common.proto_gen.action.ActionType;
import violet.action.common.proto_gen.action.EntityType;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.proto_gen.im.*;
import violet.action.common.utils.TimeUtil;

import java.util.stream.Collectors;

@Slf4j
@Component
public class ActionConsumer {
    @GrpcClient("im")
    private IMServiceGrpc.IMServiceBlockingStub imStub;
    @Autowired
    @Qualifier("kvrocksTemplate")
    private RedisTemplate<String, String> kvrocksTemplate;

    @KafkaListener(topics = "action", groupId = "action_consumer")
    public void listen(ConsumerRecord<String, String> record) {
        log.info("Received message: key = {}, value = {}, partition = {}, offset = {}", record.key(), record.value(), record.partition(), record.offset());
        ActionEvent.Builder builder = ActionEvent.newBuilder();
        try {
            JsonFormat.parser().ignoringUnknownFields().merge(record.value(), builder);
        } catch (InvalidProtocolBufferException e) {
            log.error("Failed to parse ActionEvent from JSON", e);
            throw new RuntimeException(e);
        }
        ActionEvent event = builder.build();
        Action action = new Action();
        action.setUserId(event.getUserId());
        action.setActionType(event.getActionType());
        action.setTimestamp(event.getTimestamp());
        String creationIds = null;
        String payload = null;
        long noticeUserId = 0;
        int noticeType = 0;
        String refType = null;
        long refId = 0;
        switch (event.getActionType()) {
            case ActionType.Click_VALUE:
                creationIds = event.getClickPayload().getCreationIdsList().stream().map(String::valueOf).collect(Collectors.joining(","));
                break;
            case ActionType.Digg_VALUE:
                creationIds = String.valueOf(event.getDiggPayload().getCreationId());
                payload = JSON.toJSONString(event.getDiggPayload());
                noticeUserId = event.getDiggPayload().getOwnerId();
                noticeType = NoticeType.Digg_VALUE;
                refType = EntityType.Creation.name();
                refId = event.getDiggPayload().getCreationId();
                break;
            case ActionType.DiggComment_VALUE:
                creationIds = String.valueOf(event.getDiggCommentPayload().getCommentId());
                payload = JSON.toJSONString(event.getDiggCommentPayload());
                noticeUserId = event.getDiggCommentPayload().getOwnerId();
                noticeType = NoticeType.DiggComment_VALUE;
                refType = EntityType.Comment.name();
                refId = event.getDiggCommentPayload().getCommentId();
                break;
            case ActionType.CreateComment_VALUE:
                creationIds = String.valueOf(event.getCreateCommentPayload().getCommentId());
                payload = JSON.toJSONString(event.getCreateCommentPayload());
                noticeUserId = event.getCreateCommentPayload().getOwnerId();
                noticeType = NoticeType.CreateComment_VALUE;
                refType = EntityType.Creation.name();
                refId = event.getCreateCommentPayload().getCreationId();
                break;
            case ActionType.CreateReply_VALUE:
                creationIds = String.valueOf(event.getCreateReplyPayload().getReplyId());
                payload = JSON.toJSONString(event.getCreateReplyPayload());
                noticeUserId = event.getCreateReplyPayload().getOwnerId();
                noticeType = NoticeType.CreateReply_VALUE;
                refType = EntityType.Comment.name();
                refId = event.getCreateReplyPayload().getCommentId();
                break;
            case ActionType.Follow_VALUE:
                payload = JSON.toJSONString(event.getFollowPayload());
                noticeUserId = event.getFollowPayload().getToUserId();
                noticeType = NoticeType.Follow_VALUE;
                break;
        }

        if (event.getActionType() != ActionType.Follow_VALUE) {
            action.setCreationIds(creationIds);
            kvrocksTemplate.opsForList().rightPush("action:" + action.getUserId() + ":" + TimeUtil.getNowDate(), JSON.toJSONString(action));
        }

        //按理应该放在另一个消费者组
        if (event.getActionType() == ActionType.Click_VALUE) {
            return;
        } else if (event.getActionType() == ActionType.Follow_VALUE) {
            SendNoticeRequest sendNoticeRequest = SendNoticeRequest.newBuilder()
                    .setUserId(noticeUserId)
                    .setGroup(NoticeGroup.Follow_Group_VALUE)
                    .setNoticeType(noticeType)
                    .setNoticeContent(payload)
                    .setSenderId(event.getUserId())
                    .build();
            SendNoticeResponse sendNoticeResponse = imStub.sendNotice(sendNoticeRequest);
            if (sendNoticeResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
                log.error("[ActionConsumer] send follow notice failed, resp = {}", sendNoticeResponse.getBaseResp());
            }
        } else {
            SendNoticeRequest sendNoticeRequest = SendNoticeRequest.newBuilder()
                    .setUserId(noticeUserId)
                    .setGroup(NoticeGroup.Action_Group_VALUE)
                    .setNoticeType(noticeType)
                    .setNoticeContent(payload)
                    .setSenderId(event.getUserId())
                    .setRefType(refType)
                    .setRefId(refId)
                    .build();
            SendNoticeResponse sendNoticeResponse = imStub.sendNotice(sendNoticeRequest);
            if (sendNoticeResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
                log.error("[ActionConsumer] send action notice failed, resp = {}", sendNoticeResponse.getBaseResp());
            }
        }
    }
}
