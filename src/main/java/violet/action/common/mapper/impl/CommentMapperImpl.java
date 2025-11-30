package violet.action.common.mapper.impl;

import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import violet.action.common.mapper.CommentMapper;
import violet.action.common.pojo.Entity;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    private Session session;

    @Override
    public void createComment(String entityType, Long entityId, Integer commentType, Long commentId) {
        String targetVid = entityType + ":" + entityId;
        String commentTypeStr = String.valueOf(commentType);
        String commentVid = commentTypeStr + ":" + commentId;
        String nGQL = String.format(
                "INSERT VERTEX IF NOT EXISTS entity(`entity_type`, `entity_id`) " +
                        "VALUES \"%s\":(\"%s\", %d); " +
                        "INSERT EDGE IF NOT EXISTS comment(ts) " +
                        "VALUES \"%s\"->\"%s\":(%d);",
                commentVid, commentTypeStr, commentId,
                targetVid, commentVid, System.currentTimeMillis()
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("createComment failed, entityType: {}, entityId: {}, commentType: {}, commentId: {}, error: {}", entityType, entityId, commentType, commentId, resultSet.getErrorMessage());
                throw new RuntimeException("createComment failed: " + resultSet.getErrorMessage());
            }
        } catch (IOErrorException e) {
            log.error("createComment failed, entityType: {}, entityId: {}, commentType: {}, commentId: {}", entityType, entityId, commentType, commentId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Entity> getCommentList(String entityType, Long entityId, int skip, int limit) {
        String targetVid = entityType + ":" + entityId;
        String nGQL = String.format(
                "MATCH (e:entity)-[c:comment]->(cmt:entity) " +
                        "WHERE id(e) == \"%s\" " +
                        "RETURN " +
                        "cmt.entity_id AS entityId, " +
                        "cmt.entity_type AS entityType, " +
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
            return parseEntities(resultSet);
        } catch (IOErrorException e) {
            log.error("getCommentList failed, entityType: {}, entityId: {}, skip: {}, limit: {}", entityType, entityId, skip, limit, e);
            throw new RuntimeException(e);
        }
    }

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
                // 理论上 nebula 会返回一行 count=0，这里兜个底
                return 0L;
            }
            ResultSet.Record record = resultSet.rowValues(0);
            return record.get("count").asLong();
        } catch (IOErrorException e) {
            log.error("getCommentCount failed, entityType: {}, entityId: {}", entityType, entityId, e);
            throw new RuntimeException(e);
        }
    }

    private List<Entity> parseEntities(ResultSet resultSet) {
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < resultSet.rowsSize(); i++) {
            ResultSet.Record record = resultSet.rowValues(i);
            Entity entity = new Entity();
            entity.setEntityId(record.get("entityId").asLong());
            entity.setEntityType((int) record.get("entityType").asLong());
            entities.add(entity);
        }
        return entities;
    }
}
