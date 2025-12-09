package violet.action.common.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import violet.action.common.proto_gen.action.ForwardRequest;
import violet.action.common.proto_gen.action.ForwardResponse;
import violet.action.common.proto_gen.common.BaseResp;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.proto_gen.im.IMServiceGrpc;
import violet.action.common.service.ForwardService;

@Slf4j
@Service
public class ForwardServiceImpl implements ForwardService {
    @GrpcClient("im")
    private IMServiceGrpc.IMServiceBlockingStub imStub;

    @Override
    public ForwardResponse forward(ForwardRequest req) throws Exception {
        ForwardResponse.Builder resp = ForwardResponse.newBuilder();
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }
}
