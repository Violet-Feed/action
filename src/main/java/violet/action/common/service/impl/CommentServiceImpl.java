package violet.action.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.util.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.CommentMapper;
import violet.action.common.producer.ActionMqPublisher;
import violet.action.common.proto_gen.action.*;
import violet.action.common.proto_gen.common.BaseResp;
import violet.action.common.proto_gen.common.StatusCode;
import violet.action.common.service.CommentService;
import violet.action.common.service.DiggService;
import violet.action.common.utils.SnowFlake;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    private final SnowFlake commentIdGenerator = new SnowFlake(0, 0);
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private DiggService diggService;
    @Autowired
    @Qualifier("kvrocksTemplate")
    private RedisTemplate<String, String> kvrocksTemplate;
    @Autowired
    private ActionMqPublisher actionMqPublisher;

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
                .setEntityType(req.getEntityType())
                .setEntityId(req.getEntityId())
                .setCreateTime(System.currentTimeMillis())
                .build();
        String key = String.format("comment:%d", commentId);
        kvrocksTemplate.opsForValue().set(key, JSONObject.toJSONString(commentData));
        commentMapper.createComment(req.getEntityType(), req.getEntityId(), commentId);
        kvrocksTemplate.opsForValue().increment("comment_count:" + req.getEntityType() + ":" + req.getEntityId(), 1);
        actionMqPublisher.publishCreateCommentEvent(req, commentId);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setCommentId(commentId).setBaseResp(baseResp).build();
    }

    @Override
    public CreateCommentReplyResponse createCommentReply(CreateCommentReplyRequest req) throws Exception {
        CreateCommentReplyResponse.Builder resp = CreateCommentReplyResponse.newBuilder();
        long commentId = commentIdGenerator.nextId();
        CommentData commentData = CommentData.newBuilder()
                .setUserId(req.getUserId())
                .setCommentId(commentId)
                .setContentType(req.getContentType())
                .setContent(req.getContent())
                .setLevel(2)
                .setParentId(req.getParentId())
                .setEntityType(req.getEntityType())
                .setEntityId(req.getEntityId())
                .setSibId(req.getSibId())
                .setSibUserId(req.getSibUserId())
                .setCreateTime(System.currentTimeMillis())
                .build();
        String key = String.format("comment:%d", commentId);
        kvrocksTemplate.opsForValue().set(key, JSONObject.toJSONString(commentData));
        commentMapper.createReply(req.getParentId(), commentId);
        kvrocksTemplate.opsForValue().increment("comment_count:" + req.getEntityType() + ":" + req.getEntityId(), 1);
        actionMqPublisher.publishCreateReplyEvent(req, commentId);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setCommentId(commentId).setBaseResp(baseResp).build();
    }

    @Override
    public GetCommentByIdResponse getCommentById(GetCommentByIdRequest req) throws Exception {
        GetCommentByIdResponse.Builder resp = GetCommentByIdResponse.newBuilder();
        String key = "comment:" + req.getCommentId();
        String commentStr = kvrocksTemplate.opsForValue().get(key);
        CommentData.Builder builder = CommentData.newBuilder();
        JsonFormat.parser().ignoringUnknownFields().merge(commentStr, builder);
        CommentData commentData = builder.build();
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).setComment(commentData).build();
    }

    @Override
    public GetCommentListResponse getCommentList(GetCommentListRequest req) throws Exception {
        GetCommentListResponse.Builder resp = GetCommentListResponse.newBuilder();
        int offset = (req.getPage() - 1) * PAGE_SIZE;
        Map<Long, Long> commentMetas;
        if ("time".equals(req.getSortType())) {
            commentMetas = commentMapper.getCommentListByTime(req.getEntityType(), req.getEntityId(), offset, PAGE_SIZE);
        } else {
            commentMetas = commentMapper.getCommentListByDigg(req.getEntityType(), req.getEntityId(), offset, PAGE_SIZE);
        }
        List<Long> commentIds = new ArrayList<>(commentMetas.keySet());
        List<String> keys = commentIds.stream().map(id -> "comment:" + id).collect(Collectors.toList());
        List<String> results = kvrocksTemplate.opsForValue().multiGet(keys);
        Map<Long, Long> replyCounts = commentMapper.mGetReplyCount(commentIds);
        List<CommentData> comments = new ArrayList<>();
        for (String result : results) {
            if (result != null) {
                CommentData.Builder builder = CommentData.newBuilder();
                JsonFormat.parser().ignoringUnknownFields().merge(result, builder);
                CommentData commentData = builder.build();
                commentData = commentData.toBuilder()
                        .setDiggCount(commentMetas.getOrDefault(commentData.getCommentId(), 0L))
                        .setReplyCount(replyCounts.getOrDefault(commentData.getCommentId(), 0L))
                        .build();
                comments.add(commentData);
            }
        }
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).addAllComments(comments).build();
    }

    @Override
    public GetCommentReplyListResponse getCommentReplyList(GetCommentReplyListRequest req) throws Exception {
        GetCommentReplyListResponse.Builder resp = GetCommentReplyListResponse.newBuilder();
        int offset = (req.getPage() - 1) * PAGE_SIZE;
        Map<Long, Long> commentMetas = commentMapper.getReplyList(req.getCommentId(), offset, PAGE_SIZE);
        List<Long> commentIds = new ArrayList<>(commentMetas.keySet());
        List<String> keys = commentIds.stream().map(id -> "comment:" + id).collect(Collectors.toList());
        List<String> results = kvrocksTemplate.opsForValue().multiGet(keys);
        List<CommentData> comments = new ArrayList<>();
        for (String result : results) {
            if (result != null) {
                CommentData.Builder builder = CommentData.newBuilder();
                JsonFormat.parser().ignoringUnknownFields().merge(result, builder);
                CommentData commentData = builder.build();
                commentData = commentData.toBuilder()
                        .setDiggCount(commentMetas.getOrDefault(commentData.getCommentId(), 0L))
                        .build();
                comments.add(commentData);
                ;
            }
        }
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).addAllComments(comments).build();
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

    @Override
    public DiggCommentResponse diggComment(DiggCommentRequest req) throws Exception {
        DiggCommentResponse.Builder resp = DiggCommentResponse.newBuilder();
        DiggRequest diggRequest = DiggRequest.newBuilder()
                .setUserId(req.getUserId())
                .setEntityType("comment")
                .setEntityId(req.getCommentId())
                .build();
        DiggResponse diggResponse = diggService.digg(diggRequest);
        if (diggResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
            log.error("[diggComment] Digg rpc err, err = {}", diggResponse.getBaseResp());
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
            return resp.setBaseResp(baseResp).build();
        }
        String commentStr = kvrocksTemplate.opsForValue().get("comment:" + req.getCommentId());
        CommentData.Builder builder = CommentData.newBuilder();
        JsonFormat.parser().ignoringUnknownFields().merge(commentStr, builder);
        CommentData comment = builder.build();
        String parentType = comment.getLevel() == 1 ? comment.getEntityType() : "comment";
        commentMapper.updateCommentDigg(parentType, comment.getParentId(), req.getCommentId(), 1);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }

    @Override
    public DiggCommentResponse cancelDiggComment(DiggCommentRequest req) throws Exception {
        DiggCommentResponse.Builder resp = DiggCommentResponse.newBuilder();
        DiggRequest diggRequest = DiggRequest.newBuilder()
                .setUserId(req.getUserId())
                .setEntityType("comment")
                .setEntityId(req.getCommentId())
                .build();
        DiggResponse diggResponse = diggService.cancelDigg(diggRequest);
        if (diggResponse.getBaseResp().getStatusCode() != StatusCode.Success) {
            log.error("[cancelDiggComment] CancelDigg rpc err, err = {}", diggResponse.getBaseResp());
            BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Server_Error).build();
            return resp.setBaseResp(baseResp).build();
        }
        String commentStr = kvrocksTemplate.opsForValue().get("comment:" + req.getCommentId());
        CommentData.Builder builder = CommentData.newBuilder();
        JsonFormat.parser().ignoringUnknownFields().merge(commentStr, builder);
        CommentData comment = builder.build();
        String parentType = comment.getLevel() == 1 ? comment.getEntityType() : "comment";
        commentMapper.updateCommentDigg(parentType, comment.getParentId(), req.getCommentId(), -1);
        BaseResp baseResp = BaseResp.newBuilder().setStatusCode(StatusCode.Success).build();
        return resp.setBaseResp(baseResp).build();
    }
}
