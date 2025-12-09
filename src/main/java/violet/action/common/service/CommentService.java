package violet.action.common.service;

import violet.action.common.proto_gen.action.*;

public interface CommentService {
    CreateCommentResponse createComment(CreateCommentRequest req) throws Exception;

    CreateCommentReplyResponse createCommentReply(CreateCommentReplyRequest req) throws Exception;

    GetCommentListResponse getCommentList(GetCommentListRequest req) throws Exception;

    GetCommentReplyListResponse getCommentReplyList(GetCommentReplyListRequest req) throws Exception;

    GetCommentCountResponse getCommentCount(GetCommentCountRequest req) throws Exception;

    DiggCommentResponse diggComment(DiggCommentRequest req) throws Exception;

    DiggCommentResponse cancelDiggComment(DiggCommentRequest req) throws Exception;
}
