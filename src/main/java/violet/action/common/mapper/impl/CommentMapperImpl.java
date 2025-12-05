package violet.action.common.mapper.impl;

import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import violet.action.common.mapper.CommentMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    private Session session;

    @Override
    public void createComment(String entityType, Long entityId, Long commentId) {
        String targetVid = entityType + ":" + entityId;
        String commentVid = "comment:" + commentId;
        String nGQL = String.format(
                "INSERT VERTEX IF NOT EXISTS entity(`entity_type`, `entity_id`) " +
                        "VALUES \"%s\":(\"comment\", %d); " +
                        "INSERT EDGE IF NOT EXISTS comment(ts, digg) " +
                        "VALUES \"%s\"->\"%s\":(%d, 0);",
                commentVid, commentId,
                targetVid, commentVid, System.currentTimeMillis()
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("createComment failed, entityType: {}, entityId: {}, commentId: {}, error: {}", entityType, entityId, commentId, resultSet.getErrorMessage());
                throw new RuntimeException("createComment failed: " + resultSet.getErrorMessage());
            }
        } catch (IOErrorException e) {
            log.error("createComment failed, entityType: {}, entityId: {}, commentId: {}", entityType, entityId, commentId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createReply(Long commentId, Long replyId) {
        String commentVid = "comment:" + commentId;
        String replyVid = "reply:" + replyId;
        String nGQL = String.format(
                "INSERT VERTEX IF NOT EXISTS entity(`entity_type`, `entity_id`) " +
                        "VALUES \"%s\":(\"reply\", %d); " +
                        "INSERT EDGE IF NOT EXISTS reply(ts, digg) " +
                        "VALUES \"%s\"->\"%s\":(%d, 0);",
                replyVid, replyId,
                commentVid, replyVid, System.currentTimeMillis()
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("createReply failed, commentId: {}, replyId: {}, error: {}", commentId, replyId, resultSet.getErrorMessage());
                throw new RuntimeException("createComment failed: " + resultSet.getErrorMessage());
            }
        } catch (IOErrorException e) {
            log.error("createComment failed, commentId: {}, replyId: {}, error: {}", commentId, replyId, commentId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> getCommentList(String entityType, Long entityId, int skip, int limit) {
        String targetVid = entityType + ":" + entityId;
        String nGQL = String.format(
                "MATCH (e:entity)-[c:comment]->(cmt:entity) " +
                        "WHERE id(e) == \"%s\" " +
                        "RETURN " +
                        "cmt.entity.entity_id AS entityId, " +
                        "c.ts AS ts " +
                        "ORDER BY ts DESC " +
                        "SKIP %d LIMIT %d",
                targetVid, skip, limit
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("getCommentList failed, entityType: {}, entityId: {}, skip: {}, limit: {}, error: {}", entityType, entityId, skip, limit, resultSet.getErrorMessage());
                throw new RuntimeException("getCommentList failed: " + resultSet.getErrorMessage());
            }
            List<Long> commentIds = new ArrayList<>();
            for (int i = 0; i < resultSet.rowsSize(); i++) {
                ResultSet.Record record = resultSet.rowValues(i);
                commentIds.add(record.get("entityId").asLong());
            }
            return commentIds;
        } catch (IOErrorException e) {
            log.error("getCommentList failed, entityType: {}, entityId: {}, skip: {}, limit: {}", entityType, entityId, skip, limit, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> getReplyList(Long commentId, int skip, int limit) {
        String commentVid = "comment:" + commentId;
        String nGQL = String.format(
                "MATCH (cmt:entity)-[r:reply]->(rep:entity) " +
                        "WHERE id(cmt) == \"%s\" " +
                        "RETURN " +
                        "rep.entity.entity_id AS entityId, " +
                        "r.ts AS ts " +
                        "ORDER BY ts DESC " +
                        "SKIP %d LIMIT %d",
                commentVid, skip, limit
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("getReplyList failed, commentId: {}, skip: {}, limit: {}, error: {}", commentId, skip, limit, resultSet.getErrorMessage());
                throw new RuntimeException("getReplyList failed: " + resultSet.getErrorMessage());
            }
            List<Long> replyIds = new ArrayList<>();
            for (int i = 0; i < resultSet.rowsSize(); i++) {
                ResultSet.Record record = resultSet.rowValues(i);
                replyIds.add(record.get("entityId").asLong());
            }
            return replyIds;
        } catch (IOErrorException e) {
            log.error("getReplyList failed, commentId: {}, skip: {}, limit: {}", commentId, skip, limit, e);
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    @Override
    public Long getCommentCount(String entityType, Long entityId) {
        String targetVid = entityType + ":" + entityId;
        String nGQL = String.format(
                "MATCH (e:entity)-[c:comment]->() " +
                        "WHERE id(e) == \"%s\" " +
                        "RETURN COUNT(c) AS count",
                targetVid
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("getCommentCount failed, entityType: {}, entityId: {}, error: {}", entityType, entityId, resultSet.getErrorMessage());
                throw new RuntimeException("getCommentCount failed: " + resultSet.getErrorMessage());
            }
            if (resultSet.rowsSize() == 0) {
                return 0L;
            }
            ResultSet.Record record = resultSet.rowValues(0);
            return record.get("count").asLong();
        } catch (IOErrorException e) {
            log.error("getCommentCount failed, entityType: {}, entityId: {}", entityType, entityId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, Long> mGetReplyCount(List<Long> commentIds) {
        Map<Long, Long> result = new java.util.HashMap<>();
        if (commentIds == null || commentIds.isEmpty()) {
            return result;
        }
        for (Long id : commentIds) {
            result.put(id, 0L);
        }
        String vidList = commentIds.stream()
                .map(id -> String.format("\"comment:%d\"", id))
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        String nGQL = String.format(
                "UNWIND [%s] AS vid " +
                        "MATCH (cmt:entity)-[r:reply]->() " +
                        "WHERE id(cmt) == vid " +
                        "RETURN cmt.entity.entity_id AS commentId, COUNT(r) AS count",
                vidList
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("mGetReplyCount failed, commentIds: {}, error: {}", commentIds, resultSet.getErrorMessage());
                throw new RuntimeException("mGetReplyCount failed: " + resultSet.getErrorMessage());
            }
            for (int i = 0; i < resultSet.rowsSize(); i++) {
                ResultSet.Record record = resultSet.rowValues(i);
                long commentId = record.get("commentId").asLong();
                long count = record.get("count").asLong();
                result.put(commentId, count);
            }
            return result;
        } catch (IOErrorException e) {
            log.error("mGetReplyCount failed, commentIds: {}", commentIds, e);
            throw new RuntimeException(e);
        }
    }
}
