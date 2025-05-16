package violet.action.common.service;

import violet.action.common.proto_gen.action.*;

public interface DiggService {
    DiggResponse digg(DiggRequest req) throws Exception;

    DiggResponse cancelDigg(DiggRequest req) throws Exception;

    GetDiggListByUserResponse getDiggListByUser(GetDiggListByUserRequest req) throws Exception;

    //JSONObject getDiggListByEntity(JSONObject req) throws Exception;
    //JSONObject getDiggCountByUser(JSONObject req) throws Exception;
    MGetDiggCountByEntityResponse mGetDiggCountByEntity(MGetDiggCountByEntityRequest req) throws Exception;

    MHasDiggResponse mHasDigg(MHasDiggRequest req) throws Exception;
}
