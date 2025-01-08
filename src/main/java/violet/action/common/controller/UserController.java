package violet.action.common.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import violet.action.common.service.UserService;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/user/register/")
    public JSONObject register(@RequestParam Map<String,String> map){
        String username=map.get("username");
        String password=map.get("password");
        String confirmPassword=map.get("confirmPassword");
        return userService.register(username,password,confirmPassword);
    }

    @PostMapping("/api/user/login/")
    public JSONObject login(@RequestParam Map<String,String> map){
        String username=map.get("username");
        String password=map.get("password");
        return userService.login(username,password);
    }

    @GetMapping("/api/user/get_info/")
    public JSONObject getUserInfo(){
        return userService.getUserInfo();
    }
}
