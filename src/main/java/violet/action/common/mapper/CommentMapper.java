package violet.action.common.mapper;

import violet.action.common.pojo.Entity;

import java.util.List;

public interface CommentMapper {
    void createComment(Integer entityType, Long entityId, Integer commentType, Long commentId);

    List<Entity> getCommentList(Integer entityType, Long entityId, int skip, int limit);

    Long getCommentCount(Integer entityType, Long entityId);
}
