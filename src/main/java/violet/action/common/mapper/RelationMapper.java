package violet.action.common.mapper;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import violet.action.common.pojo.User;

import java.util.List;

@Repository
public interface RelationMapper extends Neo4jRepository<User,Long> {
    @Query("MATCH (a:user {userId: {0}}), (b:user {userId: {1}}) CREATE (a)-[:follow]->(b)")
    void follow(long fromUserId,long toUserId);

    @Query("MATCH (a:user {userId: {0}})-[r:follow]->(b:user {userId: {1}}) DELETE r")
    void unfollow(long fromUserId, long toUserId);

    @Query("MATCH (a:user {userId: {0}})-[:follow]->(b:user) RETURN b")
    List<User> getFollowingList(long userId);

    @Query("MATCH (a:user)-[:follow]->(b:user {userId: {0}}) RETURN a")
    List<User> getFollowedList(long userId);

    @Query("MATCH (a:user {userId: {0}})-[:follow]->(b:user)-[:follow]->(a) RETURN b")
    List<User> getFriendList(long userId);
}
