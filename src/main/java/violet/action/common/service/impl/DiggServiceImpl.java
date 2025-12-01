package violet.action.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.DiggMapper;
import violet.action.common.proto_gen.action.*;
import violet.action.common.proto_gen.common.BaseResp;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.service.DiggService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DiggServiceImpl implements DiggService {
    @Autowired
    private DiggMapper diggMapper;
    @Autowired
    @Qualifier("kvrocksTemplate")
    private RedisTemplate<String, String> kvrocksTemplate;

    private static final int PAGE_SIZE = 20;

    @Override
    public DiggResponse digg(DiggRequest req) throws Exception {
        DiggResponse.Builder resp = DiggResponse.newBuilder();
        diggMapper.digg(req.getUserId(), req.getEntityType(), req.getEntityId());
        kvrocksTemplate.opsForValue().increment("digg_count:" + req.getEntityType() + ":" + req.getEntityId(), 1);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }

    @Override
    public DiggResponse cancelDigg(DiggRequest req) throws Exception {
        DiggResponse.Builder resp = DiggResponse.newBuilder();
        diggMapper.cancelDigg(req.getUserId(), req.getEntityType(), req.getEntityId());
        kvrocksTemplate.opsForValue().increment("digg_count:" + req.getEntityType() + ":" + req.getEntityId(), -1);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }

    @Override
    public GetDiggListByUserResponse getDiggListByUser(GetDiggListByUserRequest req) throws Exception {
        GetDiggListByUserResponse.Builder resp = GetDiggListByUserResponse.newBuilder();
        int offset = (req.getPage() - 1) * PAGE_SIZE;
        List<Long> entityIds = diggMapper.getDiggListByUser(req.getUserId(), req.getEntityType(), offset, PAGE_SIZE);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.addAllEntityIds(entityIds).setBaseResp(baseResp).build();
    }

    @Override
    public MGetDiggCountByEntityResponse mGetDiggCountByEntity(MGetDiggCountByEntityRequest req) throws Exception {
        MGetDiggCountByEntityResponse.Builder resp = MGetDiggCountByEntityResponse.newBuilder();
        List<String> keys = req.getEntityIdsList().stream()
                .map(id -> "digg_count:" + req.getEntityType() + ":" + id)
                .collect(Collectors.toList());
        List<String> counts = kvrocksTemplate.opsForValue().multiGet(keys);
        Map<Long, Long> countMap = new HashMap<>();
        for (int i = 0; i < req.getEntityIdsList().size(); i++) {
            Long id = req.getEntityIdsList().get(i);
            String countStr = counts.get(i);
            Long count = countStr == null ? 0L : Long.parseLong(countStr);
            countMap.put(id, count);
        }
        //diggMapper.mGetDiggCountByEntity(req.getEntityType(), req.getEntityIdsList());
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.putAllEntityDiggCount(countMap).setBaseResp(baseResp).build();
    }

    @Override
    public MIsDiggResponse mIsDigg(MIsDiggRequest req) throws Exception {
        MIsDiggResponse.Builder resp = MIsDiggResponse.newBuilder();
        Map<Long, Boolean> isDiggMap = diggMapper.mIsDigg(req.getUserId(), req.getEntityType(), req.getEntityIdsList());
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.putAllIsDigg(isDiggMap).setBaseResp(baseResp).build();
    }
}
