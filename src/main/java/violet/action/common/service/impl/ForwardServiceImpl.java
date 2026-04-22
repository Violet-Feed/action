package violet.action.common.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import violet.action.common.proto_gen.action.ForwardRequest;
import violet.action.common.proto_gen.action.ForwardResponse;
import violet.action.common.proto_gen.action.GetForwardCountRequest;
import violet.action.common.proto_gen.action.GetForwardCountResponse;
import violet.action.common.proto_gen.common.BaseResp;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.proto_gen.im.*;
import violet.action.common.service.ForwardService;

@Slf4j
@Service
public class ForwardServiceImpl implements ForwardService {
    @GrpcClient("im")
    private IMServiceGrpc.IMServiceBlockingStub imStub;
    @Autowired
    @Qualifier("kvrocksTemplate")
    private RedisTemplate<String, String> kvrocksTemplate;

    @Override
    public ForwardResponse forward(ForwardRequest req) throws Exception {
        ForwardResponse.Builder resp = ForwardResponse.newBuilder();
        //todo:群聊信息
        SendMessageRequest sendMessageRequest = SendMessageRequest.newBuilder()
                .setSenderType(SenderType.User_VALUE)
                .setSenderId(req.getUserId())
                .setConShortId(req.getConShortId())
                .setConId("")
                .setConType(0)
                .setClientMsgId(0)
                .setMsgType(MessageType.Forward_VALUE)
                .setMsgContent(String.valueOf(req.getEntityId()))
                .build();
        SendMessageResponse sendMessageResponse = imStub.sendMessage(sendMessageRequest);
        if (sendMessageResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
            log.error("[forward] SendMessage rpc err, err = {}", sendMessageResponse.getBaseResp());
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
            return resp.setBaseResp(baseResp).build();
        }
        String key = "forward_count:" + req.getEntityType() + ":" + req.getEntityId();
        kvrocksTemplate.opsForValue().increment(key, 1);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }

    @Override
    public GetForwardCountResponse getForwardCount(GetForwardCountRequest req) throws Exception {
        GetForwardCountResponse.Builder resp = GetForwardCountResponse.newBuilder();
        String key = "forward_count:" + req.getEntityType() + ":" + req.getEntityId();
        String countStr = kvrocksTemplate.opsForValue().get(key);
        long count = countStr == null ? 0L : Long.parseLong(countStr);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).setForwardCount(count).build();
    }
}
