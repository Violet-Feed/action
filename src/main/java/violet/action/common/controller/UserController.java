package violet.action.common.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import violet.action.common.proto_gen.action.GetFollowListRequest;
import violet.action.common.proto_gen.action.GetUserInfosRequest;
import violet.action.common.proto_gen.action.LoginRequest;
import violet.action.common.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/action/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public JSONObject register(@RequestParam Map<String,String> map){
        String username=map.get("username");
        String password=map.get("password");
        String confirmPassword=map.get("confirmPassword");
        return userService.register(username,password,confirmPassword);
    }

    @PostMapping("/login")
    public JSONObject login(@RequestBody LoginRequest req){
        return userService.login(req);
    }

    @PostMapping("/get_infos")
    public JSONObject getUserInfos(@RequestBody GetUserInfosRequest req){
        return userService.getUserInfos(req);
    }

    @PostMapping("/search")
    public JSONObject searchUsers(@RequestParam Map<String,String> map){
        String keyword=map.get("keyword");
        return userService.searchUsers(keyword);
    }
}
