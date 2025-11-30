package violet.action.common.mapper;

import violet.action.common.pojo.Entity;

import java.util.List;

public interface CommentMapper {
    void createComment(String entityType, Long entityId, Integer commentType, Long commentId);

    List<Entity> getCommentList(String entityType, Long entityId, int skip, int limit);

    Long getCommentCount(String entityType, Long entityId);
}
