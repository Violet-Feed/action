package violet.action.common.mapper.impl;

import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import violet.action.common.mapper.DiggMapper;
import violet.action.common.pojo.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class DiggMapperImpl implements DiggMapper {
    @Autowired
    private Session session;

    @Override
    public void digg(Long userId, Integer entityType, Long entityId) {
        String nGQL = String.format(
                "MATCH (u:user {userId: %d}) " +
                        "MATCH (e:entity {entityType: %d, entityId: %d}) " +
                        "CREATE (u)-[:digg {timestamp: datetime()}]->(e)",
                userId, entityType, entityId
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("digg failed, userId: {}, entityType: {}, entityId: {}, error: {}", userId, entityType, entityId, resultSet.getErrorMessage());
                throw new RuntimeException("digg failed: " + resultSet.getErrorMessage());
            }
        } catch (IOErrorException e) {
            log.error("digg failed, userId: {}, entityType: {}, entityId: {}", userId, entityType, entityId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cancelDigg(Long userId, Integer entityType, Long entityId) {
        String nGQL = String.format(
                "MATCH (u:user {userId: %d})-[d:digg]->(e:entity {entityType: %d, entityId: %d}) " +
                        "DELETE d",
                userId, entityType, entityId
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("cancelDigg failed, userId: {}, entityType: {}, entityId: {}, error: {}", userId, entityType, entityId, resultSet.getErrorMessage());
                throw new RuntimeException("cancelDigg failed: " + resultSet.getErrorMessage());
            }
        } catch (IOErrorException e) {
            log.error("cancelDigg failed, userId: {}, entityType: {}, entityId: {}", userId, entityType, entityId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Entity> getDiggListByUser(Long userId, Integer entityType, int skip, int limit) {
        String nGQL = String.format(
                "MATCH (u:user {userId: %d})-[d:digg]->(e:entity {entityType: %d}) " +
                        "RETURN e.entityId AS entityId, e.entityType AS entityType " +
                        "ORDER BY d.timestamp DESC " +
                        "SKIP %d LIMIT %d",
                userId, entityType, skip, limit
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("getDiggListByUser failed, userId: {}, entityType: {}, skip: {}, limit: {}, error: {}", userId, entityType, skip, limit, resultSet.getErrorMessage());
                throw new RuntimeException("getDiggListByUser failed: " + resultSet.getErrorMessage());
            }
            return parseEntities(resultSet);
        } catch (IOErrorException e) {
            log.error("getDiggListByUser failed, userId: {}, entityType: {}, skip: {}, limit: {}", userId, entityType, skip, limit, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> mGetDiggCountByEntity(Integer entityType, List<Long> entityIds) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (entityIds == null || entityIds.isEmpty()) {
            return list;
        }
        String idList = entityIds.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        String nGQL = String.format(
                "UNWIND [%s] AS id " +
                        "MATCH (u:user)-[:digg]->(e:entity {entityType: %d, entityId: id}) " +
                        "RETURN e.entityId AS entityId, COUNT(u) AS count",
                idList, entityType
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("mGetDiggCountByEntity failed, entityType: {}, entityIds: {}, error: {}", entityType, entityIds, resultSet.getErrorMessage());
                throw new RuntimeException("mGetDiggCountByEntity failed: " + resultSet.getErrorMessage());
            }
            for (int i = 0; i < resultSet.rowsSize(); i++) {
                ResultSet.Record record = resultSet.rowValues(i);
                Map<String, Object> map = new HashMap<>();
                map.put("entityId", record.get("entityId").asLong());
                map.put("count", record.get("count").asLong());
                list.add(map);
            }
            return list;
        } catch (IOErrorException e) {
            log.error("mGetDiggCountByEntity failed, entityType: {}, entityIds: {}", entityType, entityIds, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> mHasDigg(Long userId, Integer entityType, List<Long> entityIds) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (entityIds == null || entityIds.isEmpty()) {
            return list;
        }
        String idList = entityIds.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        String nGQL = String.format(
                "UNWIND [%s] AS id " +
                        "MATCH (u:user {userId: %d})-[d:digg]->(e:entity {entityType: %d, entityId: id}) " +
                        "RETURN e.entityId AS entityId, COUNT(d) > 0 AS hasDigg",
                idList, userId, entityType
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("mHasDigg failed, userId: {}, entityType: {}, entityIds: {}, error: {}", userId, entityType, entityIds, resultSet.getErrorMessage());
                throw new RuntimeException("mHasDigg failed: " + resultSet.getErrorMessage());
            }
            for (int i = 0; i < resultSet.rowsSize(); i++) {
                ResultSet.Record record = resultSet.rowValues(i);
                Map<String, Object> map = new HashMap<>();
                map.put("entityId", record.get("entityId").asLong());
                map.put("hasDigg", record.get("hasDigg").asBoolean());
                list.add(map);
            }
            return list;
        } catch (IOErrorException e) {
            log.error("mHasDigg failed, userId: {}, entityType: {}, entityIds: {}", userId, entityType, entityIds, e);
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
