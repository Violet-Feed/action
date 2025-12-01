package violet.action.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.CommentMapper;
import violet.action.common.proto_gen.action.*;
import violet.action.common.proto_gen.common.BaseResp;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.service.CommentService;
import violet.action.common.utils.SnowFlake;

@Service
public class CommentServiceImpl implements CommentService {
    private SnowFlake commentIdGenerator = new SnowFlake(0, 0);
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public CreateCommentResponse createComment(CreateCommentRequest req) throws Exception {
        CreateCommentResponse.Builder resp = CreateCommentResponse.newBuilder();
        Long commentId = commentIdGenerator.nextId();
        CommentData commentData = CommentData.newBuilder()
                .setUserId(req.getUserId())
                .setCommentId(commentId)
                .setContentType(req.getContentType())
                .setContent(req.getContent())
                .setLevel(1)
                .setParentId(req.getEntityId())
                .setParentType(req.getEntityType())
                .setCreateTime(System.currentTimeMillis())
                .build();
        String key = String.format("comment:%d", commentId);
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(commentData));
        commentMapper.createComment(req.getEntityType(), req.getEntityId(), "comment", commentId);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setCommentId(commentId).setBaseResp(baseResp).build();
    }

    @Override
    public CreateCommentReplyResponse createCommentReply(CreateCommentReplyRequest req) throws Exception {
        CreateCommentReplyResponse.Builder resp = CreateCommentReplyResponse.newBuilder();
        Long commentId = commentIdGenerator.nextId();
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setCommentId(commentId).setBaseResp(baseResp).build();
    }

    @Override
    public GetCommentListResponse getCommentList(GetCommentListRequest req) throws Exception {
        GetCommentListResponse.Builder resp = GetCommentListResponse.newBuilder();
        commentMapper.getCommentList(req.getEntityType(), req.getEntityId(), (int) ((req.getPage() - 1) * 20), 20);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }

    @Override
    public GetCommentReplyListResponse getCommentReplyList(GetCommentReplyListRequest req) throws Exception {
        GetCommentReplyListResponse.Builder resp = GetCommentReplyListResponse.newBuilder();
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }

    @Override
    public GetCommentCountResponse getCommentCount(GetCommentCountRequest req) throws Exception {
        GetCommentCountResponse.Builder resp = GetCommentCountResponse.newBuilder();
        commentMapper.getCommentCount(req.getEntityType(), req.getEntityId());
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }
}
