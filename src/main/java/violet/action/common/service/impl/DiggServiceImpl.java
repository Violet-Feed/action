package violet.action.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.DiggMapper;
import violet.action.common.proto_gen.action.*;
import violet.action.common.proto_gen.common.BaseResp;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.service.DiggService;

@Service
public class DiggServiceImpl implements DiggService {
    @Autowired
    private DiggMapper diggMapper;

    @Override
    public DiggResponse digg(DiggRequest req) throws Exception {
        DiggResponse.Builder resp = DiggResponse.newBuilder();
        diggMapper.digg(req.getUserId(), req.getEntityType(), req.getEntityId());
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }

    @Override
    public DiggResponse cancelDigg(DiggRequest req) throws Exception {
        DiggResponse.Builder resp = DiggResponse.newBuilder();
        diggMapper.cancelDigg(req.getUserId(), req.getEntityType(), req.getEntityId());
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }

    @Override
    public GetDiggListByUserResponse getDiggListByUser(GetDiggListByUserRequest req) throws Exception {
        GetDiggListByUserResponse.Builder resp = GetDiggListByUserResponse.newBuilder();
        diggMapper.getDiggListByUser(req.getUserId(), req.getEntityType(), (int) ((req.getPage() - 1) * 20), 20);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }

    @Override
    public MGetDiggCountByEntityResponse mGetDiggCountByEntity(MGetDiggCountByEntityRequest req) throws Exception {
        MGetDiggCountByEntityResponse.Builder resp = MGetDiggCountByEntityResponse.newBuilder();
        diggMapper.mGetDiggCountByEntity(req.getEntityType(), req.getEntityIdsList());
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }

    @Override
    public MHasDiggResponse mHasDigg(MHasDiggRequest req) throws Exception {
        MHasDiggResponse.Builder resp = MHasDiggResponse.newBuilder();
        diggMapper.mHasDigg(req.getUserId(), req.getEntityType(), req.getEntityIdsList());
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }
}
