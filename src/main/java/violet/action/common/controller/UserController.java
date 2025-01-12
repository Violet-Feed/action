package violet.action.common.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import violet.action.common.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/action/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register/")
    public JSONObject register(@RequestParam Map<String,String> map){
        String username=map.get("username");
        String password=map.get("password");
        String confirmPassword=map.get("confirmPassword");
        return userService.register(username,password,confirmPassword);
    }

    @PostMapping("/login/")
    public JSONObject login(@RequestParam Map<String,String> map){
        String username=map.get("username");
        String password=map.get("password");
        return userService.login(username,password);
    }

    @GetMapping("/get_info/")
    public JSONObject getUserInfo(){
        return userService.getUserInfo();
    }

    @GetMapping("/search/")
    public JSONObject searchUsers(@RequestParam Map<String,String> map){
        String term=map.get("term");
        return userService.searchUsers(term);
    }
}
