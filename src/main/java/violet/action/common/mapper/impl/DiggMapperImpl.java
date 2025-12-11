package violet.action.common.mapper.impl;

import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import violet.action.common.mapper.DiggMapper;
import violet.action.common.repository.NebulaManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class DiggMapperImpl implements DiggMapper {
    @Autowired
    private NebulaManager nebulaManager;

    @Override
    public void digg(Long userId, String entityType, Long entityId) {
        String userVid = String.valueOf(userId);
        String entityVid = entityType + ":" + entityId;
        String nGQL = String.format(
                "INSERT EDGE IF NOT EXISTS digg(ts) " +
                        "VALUES \"%s\"->\"%s\":(%d);",
                userVid, entityVid, System.currentTimeMillis()
        );
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("digg failed, userId: {}, entityType: {}, entityId: {}, error: {}", userId, entityType, entityId, resultSet.getErrorMessage());
            throw new RuntimeException("digg failed: " + resultSet.getErrorMessage());
        }
    }

    @Override
    public void cancelDigg(Long userId, String entityType, Long entityId) {
        String userVid = String.valueOf(userId);
        String entityVid = entityType + ":" + entityId;
        String nGQL = String.format(
                "DELETE EDGE digg \"%s\" -> \"%s\";",
                userVid, entityVid
        );
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("cancelDigg failed, userId: {}, entityType: {}, entityId: {}, error: {}", userId, entityType, entityId, resultSet.getErrorMessage());
            throw new RuntimeException("cancelDigg failed: " + resultSet.getErrorMessage());
        }
    }

    @Override
    public List<Long> getDiggListByUser(Long userId, String entityType, int skip, int limit) {
        String userVid = String.valueOf(userId);
        String nGQL = String.format(
                "MATCH (u:user)-[d:digg]->(e:entity) " +
                        "WHERE id(u) == \"%s\" AND e.entity.entity_type == \"%s\" " +
                        "RETURN e.entity.entity_id AS entityId, " +
                        "d.ts AS ts " +
                        "ORDER BY ts DESC " +
                        "SKIP %d LIMIT %d",
                userVid, entityType, skip, limit
        );
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("getDiggListByUser failed, userId: {}, entityType: {}, skip: {}, limit: {}, error: {}", userId, entityType, skip, limit, resultSet.getErrorMessage());
            throw new RuntimeException("getDiggListByUser failed: " + resultSet.getErrorMessage());
        }
        List<Long> entityIds = new ArrayList<>();
        for (int i = 0; i < resultSet.rowsSize(); i++) {
            ResultSet.Record record = resultSet.rowValues(i);
            entityIds.add(record.get("entityId").asLong());
        }
        return entityIds;
    }

    @Deprecated
    @Override
    public Map<Long, Long> mGetDiggCountByEntity(String entityType, List<Long> entityIds) {
        Map<Long, Long> result = new HashMap<>();
        if (entityIds == null || entityIds.isEmpty()) {
            return result;
        }
        for (Long id : entityIds) {
            result.put(id, 0L);
        }
        String vidList = entityIds.stream()
                .map(id -> String.format("\"%s:%d\"", entityType, id))
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        String nGQL = String.format(
                "UNWIND [%s] AS vid " +
                        "MATCH (u:user)-[:digg]->(e:entity) " +
                        "WHERE id(e) == vid " +
                        "RETURN e.entity.entity_id AS entityId, COUNT(u) AS count",
                vidList
        );
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("mGetDiggCountByEntity failed, entityType: {}, entityIds: {}, error: {}", entityType, entityIds, resultSet.getErrorMessage());
            throw new RuntimeException("mGetDiggCountByEntity failed: " + resultSet.getErrorMessage());
        }
        for (int i = 0; i < resultSet.rowsSize(); i++) {
            ResultSet.Record record = resultSet.rowValues(i);
            long entityId = record.get("entityId").asLong();
            long count = record.get("count").asLong();
            result.put(entityId, count);
        }
        return result;
    }

    @Override
    public Map<Long, Boolean> mIsDigg(Long userId, String entityType, List<Long> entityIds) {
        Map<Long, Boolean> result = new HashMap<>();
        if (entityIds == null || entityIds.isEmpty()) {
            return result;
        }
        for (Long id : entityIds) {
            result.put(id, false);
        }
        String userVid = String.valueOf(userId);
        String vidList = entityIds.stream()
                .map(id -> String.format("\"%s:%d\"", entityType, id))
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        String nGQL = String.format(
                "UNWIND [%s] AS vid " +
                        "MATCH (u:user)-[d:digg]->(e:entity) " +
                        "WHERE id(u) == \"%s\" AND id(e) == vid " +
                        "RETURN e.entity.entity_id AS entityId, COUNT(d) > 0 AS isDigg",
                vidList, userVid
        );
        ResultSet resultSet = nebulaManager.execute(nGQL);
        if (!resultSet.isSucceeded()) {
            log.error("mIsDigg failed, userId: {}, entityType: {}, entityIds: {}, error: {}", userId, entityType, entityIds, resultSet.getErrorMessage());
            throw new RuntimeException("mIsDigg failed: " + resultSet.getErrorMessage());
        }
        for (int i = 0; i < resultSet.rowsSize(); i++) {
            ResultSet.Record record = resultSet.rowValues(i);
            long entityId = record.get("entityId").asLong();
            boolean isDigg = record.get("isDigg").asBoolean();
            result.put(entityId, isDigg);
        }
        return result;
    }
}
