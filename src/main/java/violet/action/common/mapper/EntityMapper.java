package violet.action.common.mapper;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import violet.action.common.pojo.Entity;

@Repository
public interface EntityMapper extends Neo4jRepository<Entity, Long> {
}
