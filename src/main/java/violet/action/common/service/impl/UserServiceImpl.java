package violet.action.common.service.impl;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.RelationMapper;
import violet.action.common.mapper.UserMapper;
import violet.action.common.pojo.User;
import violet.action.common.pojo.UserEs;
import violet.action.common.proto_gen.action.*;
import violet.action.common.proto_gen.common.BaseResp;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.service.UserService;
import violet.action.common.utils.SnowFlake;
import violet.action.common.utils.UserDetailsImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private SnowFlake userIdGenerator = new SnowFlake(0, 0);
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
    public LoginResponse login(LoginRequest req) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        User user = loginUser.getUser();
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        LoginResponse resp = LoginResponse.newBuilder().setBaseResp(baseResp).setUserId(user.getUserId()).build();
        return resp;
    }

    private String checkRegisterParam(String username, String password, String confirmPassword) {
        if (username == null || username.trim().isEmpty()) {
            return "用户名不能为空";
        }
        if (username.length() > 100) {
            return "用户名长度不能大于100";
        }
        if (password == null || confirmPassword == null || password.isEmpty() || confirmPassword.isEmpty()) {
            return "密码不能为空";
        }
        if (password.length() > 100) {
            return "密码长度不能大于100";
        }
        if (!password.equals(confirmPassword)) {
            return "两次输入的密码不一致";
        }
        User user = userMapper.selectByUserName(username);
        if (user != null) {
            return "用户名已存在";
        }
        return null;
    }

    @Override
    public RegisterResponse register(RegisterRequest req) {
        BaseResp baseResp;
        String checkResult = checkRegisterParam(req.getUsername(), req.getPassword(), req.getConfirmPassword());
        if (checkResult != null) {
            baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Param_Error).setStatusMessage(checkResult).build();
        } else {
            String encodedPassword = passwordEncoder.encode(req.getPassword());
            User user = new User(null, userIdGenerator.nextId(), req.getUsername(), "", encodedPassword);
            if (!userMapper.createUser(user)) {
                baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
            } else {
                relationMapper.save(user);
                baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
            }
        }
        RegisterResponse resp = RegisterResponse.newBuilder().setBaseResp(baseResp).build();
        return resp;
    }

    @Override
    public GetUserInfosResponse getUserInfos(GetUserInfosRequest req) {
        List<Long> userIds = req.getUserIdsList();
        List<User> users = userMapper.selectByUserIds(userIds);
        List<UserInfo> userInfos = new ArrayList<>();
        for (User user : users) {
            userInfos.add(UserInfo.newBuilder().setUserId(user.getUserId()).setUsername(user.getUsername()).setAvatar(user.getAvatar()).build());
        }
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        GetUserInfosResponse resp = GetUserInfosResponse.newBuilder().setBaseResp(baseResp).addAllUserInfos(userInfos).build();
        return resp;
    }

    @Override
    public SearchUsersResponse searchUsers(SearchUsersRequest req) {
        BaseResp baseResp;
        SearchRequest request = new SearchRequest("user");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        MatchQueryBuilder query = QueryBuilders.matchQuery("username", req.getKeyword());
        builder.from(0);
        builder.size(10);
        builder.query(query);
        request.source(builder);
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
            SearchUsersResponse resp = SearchUsersResponse.newBuilder().setBaseResp(baseResp).build();
            return resp;
        }
        SearchHit[] hits = response.getHits().getHits();
        List<UserEs> userList = Arrays.stream(hits).map(o -> JSON.parseObject(o.getSourceAsString(), UserEs.class)).collect(Collectors.toList());
        List<UserInfo> userInfos = new ArrayList<>();
        for (UserEs user : userList) {
            userInfos.add(UserInfo.newBuilder().setUserId(user.getUserId()).setUsername(user.getUsername()).setAvatar(user.getAvatar()).build());
        }
        baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        SearchUsersResponse resp = SearchUsersResponse.newBuilder().setBaseResp(baseResp).addAllUserInfos(userInfos).build();
        return resp;
    }
}
