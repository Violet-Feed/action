package violet.action.common.mapper;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import violet.action.common.pojo.Entity;

import java.util.List;
import java.util.Map;

@Repository
public interface DiggMapper {

    @Query("MATCH (u:user {userId:{0}}) MATCH (e:entity {entityType:{1},entityId:{2}}) MERGE (u)-[d:digg]->(e) ON CREATE SET d.timestamp=datetime()")
    void digg(Long userId, Integer entityType, Long entityId);

    @Query("MATCH (u:user {userId:{0}})-[d:digg]->(e:entity {entityType:{1},entityId:{2}}) DELETE d")
    void cancelDigg(Long userId, Integer entityType, Long entityId);

    @Query("MATCH (u:user {userId:{0}})-[d:digg]->(e:entity {entityType:{1}}) RETURN e ORDER BY d.timestamp DESC SKIP {2} LIMIT {3}")
    List<Entity> getDiggListByUser(Long userId, Integer entityType, int skip, int limit);

    @Query("UNWIND {1} AS id MATCH (u:user)-[:digg]->(e:entity {entityType:{0},entityId:id}) RETURN e.entityId AS entityId,COUNT(u) AS count")
    List<Map<String, Object>> mGetDiggCountByEntity(Integer entityType, List<Long> entityIds);

    @Query("UNWIND {2} AS id MATCH (u:user {userId:{0}})-[d:digg]->(e:entity {entityType:{1},entityId:id}) RETURN e.entityId AS entityId,COUNT(d)>0 AS hasDigg")
    List<Map<String, Object>> mHasDigg(Long userId, Integer entityType, List<Long> entityIds);
}
