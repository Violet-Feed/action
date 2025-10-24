package violet.action.common.mapper;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import violet.action.common.pojo.User;

import java.util.List;
import java.util.Map;

@Repository
public interface RelationMapper extends Neo4jRepository<User, Long> {
    @Query("MATCH (a:User {user_id: $fromUserId}), (b:User {user_id: $toUserId}) CREATE (a)-[:follow]->(b)")
    void follow(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);

    @Query("MATCH (a:User {user_id: $fromUserId})-[r:follow]->(b:User {user_id: $toUserId}) DELETE r")
    void unfollow(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);

    @Query("MATCH (a:User {user_id: $userId})-[:follow]->(b:User) RETURN b")
    List<User> getFollowingList(@Param("userId") Long userId);

    @Query("MATCH (a:User)-[:follow]->(b:User {user_id: $userId}) RETURN a")
    List<User> getFollowerList(@Param("userId") Long userId);

    @Query("MATCH (a:User {user_id: $userId})-[:follow]-(b:User) RETURN b")
    List<User> getFriendList(@Param("userId") Long userId);

//    @Query("MATCH (a:user {userId: {0}})-[:follow]->(b:user) RETURN Count(*)")
//    Long getFollowingCount(Long userId);
//
//    @Query(" MATCH (a:user)-[:follow]->(b:user {userId: {0}}) RETURN Count(*)")
//    Long getFollowerCount(Long userId);
    @Query("UNWIND $userIds AS userId MATCH (a:User {user_id: userId})-[:follow]->(b:User) RETURN a.user_id AS userId, COUNT(b) AS count")
    List<Map<String, Object>> mGetFollowingCount(@Param("userIds") List<Long> userIds);
}
