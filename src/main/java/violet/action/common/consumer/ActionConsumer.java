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
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.proto_gen.im.*;
import violet.action.common.utils.TimeUtil;

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
        Action action = new Action(event.getUserId(), event.getActionType(), event.getCreationList(), event.getTimestamp());
        kvrocksTemplate.opsForList().rightPush("action:" + action.getUserId() + ":" + TimeUtil.getNowDate(), JSON.toJSONString(action));

        //按理应该放在另一个消费者组
        if (event.getActionType() == ActionType.Click_VALUE) {
            return;
        }
        if (event.getUserId() == event.getPayload().getAuthorId()) {
            return;
        }
        long creationId = Long.parseLong(event.getCreationList());
        int noticeType;
        String aggField = "";
        switch (event.getActionType()) {
            case ActionType.Digg_VALUE:
                noticeType = NoticeType.Digg_VALUE;
                aggField = noticeType + ":" + creationId;
                break;
            case ActionType.DiggComment_VALUE:
                noticeType = NoticeType.DiggComment_VALUE;
                aggField = noticeType + ":" + event.getPayload().getOpCommentId();
                break;
            case ActionType.CreateComment_VALUE:
                noticeType = NoticeType.CreateComment_VALUE;
                break;
            case ActionType.CreateReply_VALUE:
                noticeType = NoticeType.CreateReply_VALUE;
                break;
            default:
                return;
        }
        SendNoticeRequest sendNoticeRequest = SendNoticeRequest.newBuilder()
                .setUserId(event.getPayload().getAuthorId())
                .setGroup(NoticeGroup.Action_Group_VALUE)
                .setNoticeType(noticeType)
                .setNoticeContent(JSON.toJSONString(event.getPayload()))
                .setSenderId(event.getUserId())
                .setRefId(creationId)
                .setAggField(aggField)
                .build();
        SendNoticeResponse sendNoticeResponse = imStub.sendNotice(sendNoticeRequest);
        if (sendNoticeResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
            log.error("[ActionConsumer] send action notice failed, resp = {}", sendNoticeResponse.getBaseResp());
        }
    }
}
