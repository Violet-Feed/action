package violet.action.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import violet.action.common.proto_gen.action.*;
import violet.action.common.proto_gen.aigc.AigcServiceGrpc;
import violet.action.common.proto_gen.aigc.Creation;
import violet.action.common.proto_gen.aigc.GetCreationByIdRequest;
import violet.action.common.proto_gen.aigc.GetCreationByIdResponse;
import violet.action.common.proto_gen.common.BaseResp;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.proto_gen.im.*;
import violet.action.common.service.ForwardService;
import violet.action.common.proto_gen.im.ForwardMessage;

@Slf4j
@Service
public class ForwardServiceImpl implements ForwardService {
    @GrpcClient("im")
    private IMServiceGrpc.IMServiceBlockingStub imStub;
    @GrpcClient("aigc")
    private AigcServiceGrpc.AigcServiceBlockingStub aigcStub;
    @Autowired
    @Qualifier("kvrocksTemplate")
    private RedisTemplate<String, String> kvrocksTemplate;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public ForwardResponse forward(ForwardRequest req) throws Exception {
        ForwardResponse.Builder resp = ForwardResponse.newBuilder();
        GetCreationByIdRequest getCreationByIdRequest = GetCreationByIdRequest.newBuilder()
                .setCreationId(req.getCreationId())
                .build();
        GetCreationByIdResponse getCreationByIdResponse = aigcStub.getCreationById(getCreationByIdRequest);
        if (getCreationByIdResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
            log.error("[forward] GetCreationById rpc err, err = {}", getCreationByIdResponse.getBaseResp());
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
            return resp.setBaseResp(baseResp).build();
        }
        Creation creation = getCreationByIdResponse.getCreation();
        GetUserInfosRequest getUserInfosRequest = GetUserInfosRequest.newBuilder()
                .addUserIds(creation.getUserId())
                .build();
        GetUserInfosResponse getUserInfosResponse = userService.getUserInfos(getUserInfosRequest);
        if (getUserInfosResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
            log.error("[forward] GetUserInfos rpc err, err = {}", getUserInfosResponse.getBaseResp());
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
            return resp.setBaseResp(baseResp).build();
        }
        UserInfo userInfo = getUserInfosResponse.getUserInfosList().get(0);
        ForwardMessage forwardMessage = ForwardMessage.newBuilder()
                .setCreationId(req.getCreationId())
                .setTitle(creation.getTitle())
                .setMaterialType(creation.getMaterialType())
                .setCoverUrl(creation.getCoverUrl())
                .setAuthorId(creation.getUserId())
                .setAuthorName(userInfo.getUsername())
                .setAuthorAvatarUrl(userInfo.getAvatar())
                .build();
        SendMessageRequest sendMessageRequest = SendMessageRequest.newBuilder()
                .setSenderType(SenderType.User_VALUE)
                .setSenderId(req.getUserId())
                .setConShortId(req.getConShortId())
                .setConId(req.getConId())
                .setConType(req.getConType())
                .setClientMsgId(0)
                .setMsgType(MessageType.Forward_VALUE)
                .setMsgContent(JSONObject.toJSONString(forwardMessage))
                .build();
        SendMessageResponse sendMessageResponse = imStub.sendMessage(sendMessageRequest);
        if (sendMessageResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
            log.error("[forward] SendMessage rpc err, err = {}", sendMessageResponse.getBaseResp());
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
            return resp.setBaseResp(baseResp).build();
        }
        String key = "forward_count:creation:" + req.getCreationId();
        kvrocksTemplate.opsForValue().increment(key, 1);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }

    @Override
    public GetForwardCountResponse getForwardCount(GetForwardCountRequest req) throws Exception {
        GetForwardCountResponse.Builder resp = GetForwardCountResponse.newBuilder();
        String key = "forward_count:creation:" + req.getCreationId();
        String countStr = kvrocksTemplate.opsForValue().get(key);
        long count = countStr == null ? 0L : Long.parseLong(countStr);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).setForwardCount(count).build();
    }
}
