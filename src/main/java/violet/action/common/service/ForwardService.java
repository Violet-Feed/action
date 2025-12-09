package violet.action.common.service;

import violet.action.common.proto_gen.action.ForwardRequest;
import violet.action.common.proto_gen.action.ForwardResponse;

public interface ForwardService {
    ForwardResponse forward(ForwardRequest req) throws Exception;
}
