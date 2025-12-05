package violet.action.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.CommentMapper;
import violet.action.common.proto_gen.action.*;
import violet.action.common.proto_gen.common.BaseResp;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.service.CommentService;
import violet.action.common.utils.SnowFlake;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final SnowFlake commentIdGenerator = new SnowFlake(0, 0);
    private final SnowFlake replyIdGenerator = new SnowFlake(0, 0);
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    @Qualifier("kvrocksTemplate")
    private RedisTemplate<String, String> kvrocksTemplate;

    private static final int PAGE_SIZE = 10;

    @Override
    public CreateCommentResponse createComment(CreateCommentRequest req) throws Exception {
        CreateCommentResponse.Builder resp = CreateCommentResponse.newBuilder();
        long commentId = commentIdGenerator.nextId();
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
        kvrocksTemplate.opsForValue().set(key, JSONObject.toJSONString(commentData));
        commentMapper.createComment(req.getEntityType(), req.getEntityId(), commentId);
        kvrocksTemplate.opsForValue().increment("comment_count:" + req.getEntityType() + ":" + req.getEntityId(), 1);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setCommentId(commentId).setBaseResp(baseResp).build();
    }

    @Override
    public CreateCommentReplyResponse createCommentReply(CreateCommentReplyRequest req) throws Exception {
        CreateCommentReplyResponse.Builder resp = CreateCommentReplyResponse.newBuilder();
        long replyId = replyIdGenerator.nextId();
        CommentData commentData = CommentData.newBuilder()
                .setUserId(req.getUserId())
                .setCommentId(replyId)
                .setContentType(req.getContentType())
                .setContent(req.getContent())
                .setLevel(2)
                .setParentId(req.getParentId())
                .setParentType("comment")
                .setSibId(req.getSibId())
                .setSibUserId(req.getSibUserId())
                .setCreateTime(System.currentTimeMillis())
                .build();
        String key = String.format("reply:%d", replyId);
        kvrocksTemplate.opsForValue().set(key, JSONObject.toJSONString(commentData));
        commentMapper.createReply(req.getParentId(), replyId);
        //todo: increment comment count
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setCommentId(replyId).setBaseResp(baseResp).build();
    }

    @Override
    public GetCommentListResponse getCommentList(GetCommentListRequest req) throws Exception {
        GetCommentListResponse.Builder resp = GetCommentListResponse.newBuilder();
        int offset = (req.getPage() - 1) * PAGE_SIZE;
        List<Long> commentIds = commentMapper.getCommentList(req.getEntityType(), req.getEntityId(), offset, PAGE_SIZE);
        List<String> keys = commentIds.stream().map(id -> "comment:" + id).collect(Collectors.toList());
        List<String> results = kvrocksTemplate.opsForValue().multiGet(keys);
        List<CommentData> comments = new ArrayList<>();
        for (String result : results) {
            if (result != null) {
                CommentData commentData = JSONObject.parseObject(result, CommentData.class);
                comments.add(commentData);
            }
        }
        //todo: digg count & is digg & reply count
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).addAllCommentList(comments).build();
    }

    @Override
    public GetCommentReplyListResponse getCommentReplyList(GetCommentReplyListRequest req) throws Exception {
        GetCommentReplyListResponse.Builder resp = GetCommentReplyListResponse.newBuilder();
        int offset = (req.getPage() - 1) * PAGE_SIZE;
        List<Long> replyIds = commentMapper.getReplyList(req.getCommentId(), offset, PAGE_SIZE);
        List<String> keys = replyIds.stream().map(id -> "reply:" + id).collect(Collectors.toList());
        List<String> results = kvrocksTemplate.opsForValue().multiGet(keys);
        List<CommentData> replies = new ArrayList<>();
        for (String result : results) {
            if (result != null) {
                CommentData commentData = JSONObject.parseObject(result, CommentData.class);
                replies.add(commentData);
            }
        }
        //todo: digg count & is digg
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).addAllCommentList(replies).build();
    }

    @Override
    public GetCommentCountResponse getCommentCount(GetCommentCountRequest req) throws Exception {
        GetCommentCountResponse.Builder resp = GetCommentCountResponse.newBuilder();
        String key = "comment_count:" + req.getEntityType() + ":" + req.getEntityId();
        String countStr = kvrocksTemplate.opsForValue().get(key);
        long count = countStr == null ? 0L : Long.parseLong(countStr);
        //Long count = commentMapper.getCommentCount(req.getEntityType(), req.getEntityId());
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).setCommentCount(count).build();
    }
}
