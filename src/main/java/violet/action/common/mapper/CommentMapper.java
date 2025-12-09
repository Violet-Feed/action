package violet.action.common.mapper;

import java.util.List;
import java.util.Map;

public interface CommentMapper {
    void createComment(String entityType, Long entityId, Long commentId);

    void createReply(Long commentId, Long replyId);

    Map<Long, Long> getCommentListByTime(String entityType, Long entityId, int skip, int limit);

    Map<Long, Long> getCommentListByDigg(String entityType, Long entityId, int skip, int limit);

    Map<Long, Long> getReplyList(Long commentId, int skip, int limit);

    Long getCommentCount(String entityType, Long entityId);

    Map<Long, Long> mGetReplyCount(List<Long> commentIds);

    void updateCommentDigg(String entityType, Long entityId, Long commentId, int delta);
}
