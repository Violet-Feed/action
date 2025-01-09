package violet.action.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.RelationMapper;
import violet.action.common.mapper.UserMapper;
import violet.action.common.pojo.User;
import violet.action.common.service.UserService;
import violet.action.common.utils.JwtUtil;
import violet.action.common.utils.SnowFlake;
import violet.action.common.utils.UserDetailsImpl;

@Service
public class UserServiceImpl implements UserService {
    private SnowFlake userIdGenerator=new SnowFlake(0,0);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public JSONObject register(String username, String password, String confirmPassword) {
        JSONObject resp=new JSONObject();
        if(username==null) {
            resp.put("message", "用户名不能为空");
            return resp;
        }
        username=username.trim();
        if(username.length()==0) {
            resp.put("message", "用户名不能为空");
            return resp;
        }
        if(username.length()>100){
            resp.put("message","用户名长度不能大于100");
            return resp;
        }
        if(password==null||confirmPassword==null){
            resp.put("message", "密码不能为空");
            return resp;
        }
        if(password.length()==0||confirmPassword.length()==0){
            resp.put("message", "密码不能为空");
            return resp;
        }
        if(password.length()>100){
            resp.put("message","密码长度不能大于100");
            return resp;
        }
        if(!password.equals(confirmPassword)){
            resp.put("message","两次输入的密码不一致");
            return resp;
        }
        User user=userMapper.selectByUserName(username);
        if(user!=null){
            resp.put("message","用户名已存在");
            return resp;
        }
        String encodedPassword=passwordEncoder.encode(password);
        user=new User(null,userIdGenerator.nextId(),username,"",encodedPassword);
        if(!userMapper.createUser(user)){
            resp.put("message","创建失败");
            return resp;
        }
        relationMapper.save(user);
        resp.put("message","success");
        return resp;
    }

    @Override
    public JSONObject login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(username,password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserDetailsImpl loginUser=(UserDetailsImpl)authenticate.getPrincipal();
        User user=loginUser.getUser();
        String jwt= JwtUtil.createJWT(user.getUserId().toString());
        JSONObject resp=new JSONObject();
        resp.put("message","success");
        resp.put("token",jwt);
        return resp;
    }

    @Override
    public JSONObject getUserInfo() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp = new JSONObject();
        resp.put("message", "success");
        resp.put("id", user.getId().toString());
        resp.put("userId", user.getUserId().toString());
        resp.put("username", user.getUsername());
        resp.put("avatar", user.getAvatar());
        return resp;
    }
}
