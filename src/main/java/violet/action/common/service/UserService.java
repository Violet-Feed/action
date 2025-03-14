package violet.action.common.service;

import com.alibaba.fastjson.JSONObject;
import violet.action.common.proto_gen.action.GetUserInfosRequest;
import violet.action.common.proto_gen.action.LoginRequest;

public interface UserService {
    JSONObject register(String username,String password,String confirmPassword);
    JSONObject login(LoginRequest req);
    JSONObject getUserInfos(GetUserInfosRequest req);
    JSONObject searchUsers(String keyword);
}
