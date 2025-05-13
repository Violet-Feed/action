package violet.action.common.mapper;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import violet.action.common.pojo.User;

import java.util.List;
import java.util.Map;

@Repository
public interface DiggMapper extends Neo4jRepository<User,Long> {
    @Query("MATCH (u:user {userId: {0}}), (e) WHERE labels(e)[0] = {1} AND id(e) = {2} CREATE (u)-[d:DIGG {timestamp: datetime()}]->(e)")
    void digg(Long userId, String entityType, Long entityId);

    @Query("MATCH (u:User {userId: {0}})-[d:DIGG]->(e) WHERE labels(e)[0] = {1} AND id(e) = {2} DELETE d")
    void cancelDigg(Long userId, String entityType, Long entityId);

    @Query("MATCH (u:user {userId: {0}})-[d:DIGG]->(e) WHERE labels(e)[0] = {1} RETURN properties(e) AS properties ORDER BY d.timestamp DESC SKIP {2} LIMIT {3}")
    List<Map<String, Object>> getDiggListByUser(Long userId, String entityType, int skip, int limit);

    @Query("UNWIND {1} AS entityId MATCH (u:user)-[:digg]->(e) WHERE labels(e)[0] = {0} AND id(e) = entityId RETURN e.id AS entityId, COUNT(u) AS count")
    List<Map<String, Object>> mGetDiggCountByEntity(String entityType, List<Long> entityIds);

    @Query("UNWIND {2} AS entityId MATCH (u:user {userId: {0}})-[d:digg]->(e) WHERE labels(e)[0] = {1} AND id(e) = entityId RETURN e.id AS entityId, COUNT(d) > 0 AS hasDigg")
    List<Map<String, Object>> mHasDigg(Long userId, String entityType, List<Long> entityIds);
}
