package violet.action.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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
import violet.action.common.pojo.UserEs;
import violet.action.common.service.UserService;
import violet.action.common.utils.JwtUtil;
import violet.action.common.utils.SnowFlake;
import violet.action.common.utils.UserDetailsImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private SnowFlake userIdGenerator=new SnowFlake(0,0);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
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

    @Override
    public JSONObject searchUsers(String term) {
        SearchRequest request = new SearchRequest("user");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        MatchQueryBuilder query = QueryBuilders.matchQuery("username", term);
        builder.from(0);
        builder.size(10);
        builder.query(query);
        request.source(builder);
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        SearchHit[] hits = response.getHits().getHits();
        List<UserEs> userList= Arrays.stream(hits).map(o -> JSON.parseObject(o.getSourceAsString(), UserEs.class)).collect(Collectors.toList());
        JSONObject resp = new JSONObject();
        resp.put("message", "success");
        resp.put("userList", userList);
        return resp;
    }
}
