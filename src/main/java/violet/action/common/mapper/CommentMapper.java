package violet.action.common.mapper;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import violet.action.common.pojo.Entity;

import java.util.List;

@Repository
public interface CommentMapper extends Neo4jRepository<Entity, Long> {
    @Query("MATCH (e:entity {entityType:{0},entityId:{1}}) CREATE (cmt:entity {entityType:{2},entityId:{3}}) CREATE (e)-[c:comment {timestamp:datetime()}]->(cmt)")
    void createComment(Integer entityType, Long entityId, Integer commentType, Long commentId);

    @Query("MATCH (e:entity {entityType:{0},entityId:{1}})-[c:comment]->(cmt:entity) RETURN cmt ORDER BY c.timestamp DESC SKIP {2} LIMIT {3}")
    List<Entity> getCommentList(Integer entityType, Long entityId, int skip, int limit);

    @Query("MATCH (e:entity {entityType:{0},entityId:{1}})->[c:comment]->() RETURN COUNT(c)")
    Long getCommentCount(Integer entityType, Long entityId);
}
