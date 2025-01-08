package violet.action.common.service;

import com.alibaba.fastjson.JSONObject;

public interface UserService {
    JSONObject register(String username,String password,String confirmPassword);
    JSONObject login(String username,String password);
    JSONObject getUserInfo();
}
