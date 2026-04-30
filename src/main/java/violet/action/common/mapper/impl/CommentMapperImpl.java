package violet.action.common.mapper.impl;

import com.vesoft.nebula.client.graph.data.ResultSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import violet.action.common.mapper.CommentMapper;
import violet.action.common.repository.NebulaManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    private NebulaManager nebulaManager;

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
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("createComment failed, entityType: {}, entityId: {}, commentId: {}, error: {}", entityType, entityId, commentId, resultSet.getErrorMessage());
            throw new RuntimeException("createComment failed: " + resultSet.getErrorMessage());
        }
    }

    @Override
    public void createReply(Long commentId, Long replyId) {
        String commentVid = "comment:" + commentId;
        String replyVid = "comment:" + replyId;
        String nGQL = String.format(
                "INSERT VERTEX IF NOT EXISTS entity(`entity_type`, `entity_id`) " +
                        "VALUES \"%s\":(\"reply\", %d); " +
                        "INSERT EDGE IF NOT EXISTS comment(ts, digg) " +
                        "VALUES \"%s\"->\"%s\":(%d, 0);",
                replyVid, replyId,
                commentVid, replyVid, System.currentTimeMillis()
        );
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("createReply failed, commentId: {}, replyId: {}, error: {}", commentId, replyId, resultSet.getErrorMessage());
            throw new RuntimeException("createComment failed: " + resultSet.getErrorMessage());
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        //这里感觉不删reply点是不是好一点
        String commentVid = "comment:" + commentId;
        String queryReplyNGQL = String.format(
                "MATCH (cmt:entity)-[c:comment]->(rep:entity) " +
                        "WHERE id(cmt) == \"%s\" " +
                        "AND rep.entity.entity_type == \"reply\" " +
                        "RETURN DISTINCT id(rep) AS replyVid",
                commentVid
        );
        ResultSet replyResultSet = nebulaManager.execute(queryReplyNGQL);
        if (!replyResultSet.isSucceeded()) {
            log.error("deleteComment query replies failed, commentId: {}, error: {}", commentId, replyResultSet.getErrorMessage());
            throw new RuntimeException("deleteComment query replies failed: " + replyResultSet.getErrorMessage());
        }

        List<String> vids = new ArrayList<>();
        vids.add(commentVid);
        try {
            for (int i = 0; i < replyResultSet.rowsSize(); i++) {
                ResultSet.Record record = replyResultSet.rowValues(i);
                String replyVid = record.get("replyVid").asString();
                if (replyVid != null && !replyVid.isEmpty()) {
                    vids.add(replyVid);
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error("deleteComment query replies failed, commentId: {}", commentId, e);
            throw new RuntimeException(e);
        }
        String vidList = vids.stream()
                .distinct()
                .map(vid -> String.format("\"%s\"", vid))
                .collect(Collectors.joining(", "));

        String deleteNGQL = String.format(
                "DELETE VERTEX %s WITH EDGE;",
                vidList
        );
        ResultSet deleteResultSet = nebulaManager.execute(deleteNGQL);
        if (!deleteResultSet.isSucceeded()) {
            log.error("deleteComment failed, commentId: {}, vids: {}, error: {}", commentId, vids, deleteResultSet.getErrorMessage());
            throw new RuntimeException("deleteComment failed: " + deleteResultSet.getErrorMessage());
        }
    }

    @Override
    public void deleteReply(Long replyId) {
        String replyVid = "comment:" + replyId;
        String nGQL = String.format(
                "DELETE VERTEX \"%s\" WITH EDGE;",
                replyVid
        );
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("deleteReply failed, replyId: {}, error: {}", replyId, resultSet.getErrorMessage());
            throw new RuntimeException("deleteReply failed: " + resultSet.getErrorMessage());
        }
    }

    @Override
    public Map<Long, Long> getCommentListByTime(String entityType, Long entityId, int skip, int limit) {
        String targetVid = entityType + ":" + entityId;
        String nGQL = String.format(
                "MATCH (e:entity)-[c:comment]->(cmt:entity) " +
                        "WHERE id(e) == \"%s\" " +
                        "RETURN " +
                        "cmt.entity.entity_id AS commentId, " +
                        "c.ts AS ts, " +
                        "c.digg AS diggCount " +
                        "ORDER BY ts DESC " +
                        "SKIP %d LIMIT %d",
                targetVid, skip, limit
        );
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("getCommentListByTime failed, entityType: {}, entityId: {}, skip: {}, limit: {}, error: {}", entityType, entityId, skip, limit, resultSet.getErrorMessage());
            throw new RuntimeException("getCommentListByTime failed: " + resultSet.getErrorMessage());
        }
        return parseCommentMetas(resultSet);
    }

    @Override
    public Map<Long, Long> getCommentListByDigg(String entityType, Long entityId, int skip, int limit) {
        String targetVid = entityType + ":" + entityId;
        String nGQL = String.format(
                "MATCH (e:entity)-[c:comment]->(cmt:entity) " +
                        "WHERE id(e) == \"%s\" " +
                        "RETURN " +
                        "cmt.entity.entity_id AS commentId, " +
                        "c.ts AS ts, " +
                        "c.digg AS diggCount " +
                        "ORDER BY diggCount DESC, ts DESC " +
                        "SKIP %d LIMIT %d",
                targetVid, skip, limit
        );
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("getCommentListByDigg failed, entityType: {}, entityId: {}, skip: {}, limit: {}, error: {}", entityType, entityId, skip, limit, resultSet.getErrorMessage());
            throw new RuntimeException("getCommentList failed: " + resultSet.getErrorMessage());
        }
        return parseCommentMetas(resultSet);
    }

    @Override
    public Map<Long, Long> getReplyList(Long commentId, int skip, int limit) {
        String commentVid = "comment:" + commentId;
        String nGQL = String.format(
                "MATCH (cmt:entity)-[c:comment]->(rep:entity) " +
                        "WHERE id(cmt) == \"%s\" " +
                        "RETURN " +
                        "rep.entity.entity_id AS commentId, " +
                        "c.digg AS diggCount " +
                        "ORDER BY diggCount DESC " +
                        "SKIP %d LIMIT %d",
                commentVid, skip, limit
        );
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("getReplyList failed, commentId: {}, skip: {}, limit: {}, error: {}", commentId, skip, limit, resultSet.getErrorMessage());
            throw new RuntimeException("getReplyList failed: " + resultSet.getErrorMessage());
        }
        return parseCommentMetas(resultSet);
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
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("getCommentCount failed, entityType: {}, entityId: {}, error: {}", entityType, entityId, resultSet.getErrorMessage());
            throw new RuntimeException("getCommentCount failed: " + resultSet.getErrorMessage());
        }
        if (resultSet.rowsSize() == 0) {
            return 0L;
        }
        ResultSet.Record record = resultSet.rowValues(0);
        return record.get("count").asLong();
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
                        "MATCH (cmt:entity)-[c:comment]->() " +
                        "WHERE id(cmt) == vid " +
                        "RETURN cmt.entity.entity_id AS commentId, COUNT(c) AS count",
                vidList
        );
        ResultSet resultSet = nebulaManager.execute(nGQL);
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
    }

    @Override
    public void updateCommentDigg(String entityType, Long entityId, Long commentId, int delta) {
        String srcVid = entityType + ":" + entityId;
        String dstVid = "comment:" + commentId;
        String nGQL = String.format(
                "UPDATE EDGE ON comment \"%s\"->\"%s\" " +
                        "SET digg = digg + (%d);",
                srcVid, dstVid, delta
        );
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("updateCommentDigg failed, commentId: {}, delta: {}, error: {}", commentId, delta, resultSet.getErrorMessage());
            throw new RuntimeException("updateCommentDigg failed: " + resultSet.getErrorMessage());
        }
    }

    private Map<Long, Long> parseCommentMetas(ResultSet resultSet) {
        Map<Long, Long> commentMetas = new LinkedHashMap<>();
        for (int i = 0; i < resultSet.rowsSize(); i++) {
            ResultSet.Record record = resultSet.rowValues(i);
            Long commentId = record.get("commentId").asLong();
            Long diggCount = record.get("diggCount").asLong();
            commentMetas.put(commentId, diggCount);
        }
        return commentMetas;
    }
}
