package violet.action.common.service.impl;

import com.alibaba.fastjson.JSON;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.service.vector.request.SearchReq;
import io.milvus.v2.service.vector.request.data.EmbeddedText;
import io.milvus.v2.service.vector.response.SearchResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.RelationMapper;
import violet.action.common.mapper.UserMapper;
import violet.action.common.pojo.User;
import violet.action.common.producer.KafkaProducer;
import violet.action.common.proto_gen.action.*;
import violet.action.common.proto_gen.common.BaseResp;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.service.UserService;
import violet.action.common.utils.SnowFlake;
import violet.action.common.utils.TimeUtil;
import violet.action.common.utils.UserDetailsImpl;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private SnowFlake userIdGenerator = new SnowFlake(0, 0);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private MilvusClientV2 milvusClient;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    @Qualifier("kvrocksTemplate")
    private RedisTemplate<String, String> kvrocksTemplate;

    private static final int PAGE_SIZE = 20;

    @Override
    public LoginResponse login(LoginRequest req) {
        LoginResponse.Builder resp = LoginResponse.newBuilder();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        User user = loginUser.getUser();
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).setUserId(user.getUserId()).build();
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
        RegisterResponse.Builder resp = RegisterResponse.newBuilder();
        String checkResult = checkRegisterParam(req.getUsername(), req.getPassword(), req.getConfirmPassword());
        if (checkResult != null) {
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Param_Error).setStatusMessage(checkResult).build();
            return resp.setBaseResp(baseResp).build();
        }
        String encodedPassword = passwordEncoder.encode(req.getPassword());
        User user = new User(null, userIdGenerator.nextId(), req.getUsername(), "", encodedPassword);
        if (!userMapper.createUser(user)) {
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
            return resp.setBaseResp(baseResp).build();
        }
        //relationMapper.save(user);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }

    @Override
    public GetUserInfosResponse getUserInfos(GetUserInfosRequest req) {
        GetUserInfosResponse.Builder resp = GetUserInfosResponse.newBuilder();
        List<Long> userIds = req.getUserIdsList();
        if (userIds.isEmpty()) {
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
            return resp.setBaseResp(baseResp).build();
        }
        List<User> users = userMapper.selectByUserIds(userIds);
        if (users.size() != userIds.size()) {
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Not_Found_Error).build();
            return resp.setBaseResp(baseResp).build();
        }
        List<UserInfo> userInfos = new ArrayList<>();
        for (User user : users) {
            userInfos.add(UserInfo.newBuilder().setUserId(user.getUserId()).setUsername(user.getUsername()).setAvatar(user.getAvatar()).build());
        }
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).addAllUserInfos(userInfos).build();
    }

    @Override
    public SearchUsersResponse searchUsers(SearchUsersRequest req) {
        SearchUsersResponse.Builder resp = SearchUsersResponse.newBuilder();
        int offset = (req.getPage() - 1) * PAGE_SIZE;
        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("drop_ratio_search", 0.2);
        List<List<SearchResp.SearchResult>> searchResults = milvusClient.search(SearchReq.builder()
                .collectionName("user")
                .data(Collections.singletonList(new EmbeddedText(req.getKeyword())))
                .annsField("username_embeddings")
                .offset(offset)
                .limit(PAGE_SIZE)
                .searchParams(searchParams)
                .outputFields(Arrays.asList("user_id", "username", "avatar"))
                .build()).getSearchResults();
        List<User> users = new ArrayList<>();
        if (!searchResults.isEmpty()) {
            users = searchResults.get(0).stream()
                    .map(searchResult -> new User(null,
                            ((Long) searchResult.getEntity().get("user_id")),
                            (String) searchResult.getEntity().get("username"),
                            (String) searchResult.getEntity().get("avatar"),
                            null))
                    .collect(Collectors.toList());
        }
        List<UserInfo> userInfos = users.stream().map(User::toProto).collect(Collectors.toList());
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).addAllUserInfos(userInfos).build();
    }

    @Override
    public ReportUserActionResponse reportUserAction(ReportUserActionRequest req) {
        ReportUserActionResponse.Builder resp = ReportUserActionResponse.newBuilder();
        kvrocksTemplate.opsForList().rightPush("action:" + req.getUserId() + ":" + TimeUtil.getNowDate(), JSON.toJSONString(req));
        kafkaProducer.sendMessage("action", JSON.toJSONString(req));
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }
}
