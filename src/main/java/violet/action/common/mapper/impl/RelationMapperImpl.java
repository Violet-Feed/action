package violet.action.common.mapper.impl;

import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import violet.action.common.mapper.RelationMapper;
import violet.action.common.pojo.User;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class RelationMapperImpl implements RelationMapper {
    @Autowired
    private Session session;


    @Override
    public void createUser(User user) {
        String vid = String.valueOf(user.getUserId());
        String nGQL = String.format(
                "INSERT VERTEX IF NOT EXISTS user(user_id, username, avatar) " +
                        "VALUES \"%s\":(%d, \"%s\", \"%s\")",
                vid,
                user.getUserId(),
                escapeString(user.getUsername()),
                escapeString(user.getAvatar())
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("createUser failed, userId: {}, error: {}", user.getUserId(), resultSet.getErrorMessage());
                throw new RuntimeException("createUser failed: " + resultSet.getErrorMessage());
            }
        } catch (IOErrorException e) {
            log.error("createUser failed, userId: {}", user.getUserId(), e);
            throw new RuntimeException(e);
        }
    }

    private String escapeString(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\"", "\\\"");
    }

    @Override
    public void follow(Long fromUserId, Long toUserId) {
        String fromVid = String.valueOf(fromUserId);
        String toVid = String.valueOf(toUserId);
        String nGQL = String.format(
                "INSERT EDGE IF NOT EXISTS follow (ts) " +
                        "VALUES \"%s\"->\"%s\":(%d);",
                fromVid, toVid, System.currentTimeMillis()
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("follow failed, fromUserId: {}, toUserId: {}, error: {}", fromUserId, toUserId, resultSet.getErrorMessage());
                throw new RuntimeException("follow failed: " + resultSet.getErrorMessage());
            }
        } catch (IOErrorException e) {
            log.error("follow failed, fromUserId: {}, toUserId: {}", fromUserId, toUserId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unfollow(Long fromUserId, Long toUserId) {
        String fromVid = String.valueOf(fromUserId);
        String toVid = String.valueOf(toUserId);
        String nGQL = String.format(
                "DELETE EDGE follow \"%s\" -> \"%s\";",
                fromVid, toVid
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("unfollow failed, fromUserId: {}, toUserId: {}, error: {}", fromUserId, toUserId, resultSet.getErrorMessage());
                throw new RuntimeException("unfollow failed: " + resultSet.getErrorMessage());
            }
        } catch (IOErrorException e) {
            log.error("unfollow failed, fromUserId: {}, toUserId: {}", fromUserId, toUserId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getFollowingList(Long userId) {
        String vid = String.valueOf(userId);
        String nGQL = String.format(
                "MATCH (a:user)-[:follow]->(b:user) " +
                        "WHERE id(a) == \"%s\" " +
                        "RETURN b.user.user_id AS user_id, b.user.username AS username, b.user.avatar AS avatar",
                vid
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("getFollowingList failed, userId: {}, error: {}", userId, resultSet.getErrorMessage());
                throw new RuntimeException("getFollowingList failed: " + resultSet.getErrorMessage());
            }
            return parseUsers(resultSet);
        } catch (IOErrorException | UnsupportedEncodingException e) {
            log.error("getFollowingList failed, userId: {}", userId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getFollowerList(Long userId) {
        String vid = String.valueOf(userId);
        String nGQL = String.format(
                "MATCH (a:user)-[:follow]->(b:user) " +
                        "WHERE id(b) == \"%s\" " +
                        "RETURN a.user.user_id AS user_id, a.user.username AS username, a.user.avatar AS avatar",
                vid
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("getFollowerList failed, userId: {}, error: {}", userId, resultSet.getErrorMessage());
                throw new RuntimeException("getFollowerList failed: " + resultSet.getErrorMessage());
            }
            return parseUsers(resultSet);
        } catch (IOErrorException | UnsupportedEncodingException e) {
            log.error("getFollowerList failed, userId: {}", userId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getFriendList(Long userId) {
        String vid = String.valueOf(userId);
        String nGQL = String.format(
                "MATCH (u1:user)-[:follow]->(u2:user)-[:follow]->(u1) " +
                        "WHERE id(u1) == \"%s\" " +
                        "RETURN u2.user.user_id AS user_id, u2.user.username AS username, u2.user.avatar AS avatar",
                vid
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("getFriendList failed, userId: {}, error: {}", userId, resultSet.getErrorMessage());
                throw new RuntimeException("getFriendList failed: " + resultSet.getErrorMessage());
            }
            return parseUsers(resultSet);
        } catch (IOErrorException | UnsupportedEncodingException e) {
            log.error("getFriendList failed, userId: {}", userId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, Map<String, String>> mGetFollowingMap(List<Long> userIds) {
        Map<Long, Map<String, String>> followingMaps = new HashMap<>();
        String idList = userIds.stream()
                .map(id -> "\"" + id + "\"")
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        String nGQL = String.format(
                "UNWIND [%s] AS vid " +
                        "MATCH (a:user)-[:follow]->(b:user) " +
                        "WHERE id(a) == vid " +
                        "RETURN a.user.user_id AS from_id, b.user.user_id AS to_id",
                idList
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("mGetFollowingMap failed, userIds: {}, error: {}", userIds, resultSet.getErrorMessage());
                throw new RuntimeException("mGetFollowingMap failed: " + resultSet.getErrorMessage());
            }
            for (int i = 0; i < resultSet.rowsSize(); i++) {
                ResultSet.Record record = resultSet.rowValues(i);
                long fromId = record.get("from_id").asLong();
                long toId = record.get("to_id").asLong();
                followingMaps.computeIfAbsent(fromId, k -> new HashMap<>()).put(Long.toString(toId), "1");
            }
            return followingMaps;
        } catch (IOErrorException e) {
            log.error("mGetFollowingMap failed, userIds: {}", userIds, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, Long> mGetFollowingCount(List<Long> userIds) {
        String idList = userIds.stream()
                .map(id -> "\"" + id + "\"")
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        String nGQL = String.format(
                "UNWIND [%s] AS vid " +
                        "MATCH (a:user)-[:follow]->(b:user) " +
                        "WHERE id(a) == vid " +
                        "RETURN a.user.user_id AS userId, COUNT(b) AS count",
                idList
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("mGetFollowingCount failed, userIds: {}, error: {}", userIds, resultSet.getErrorMessage());
                throw new RuntimeException("mGetFollowingCount failed: " + resultSet.getErrorMessage());
            }
            return parseCount(resultSet);
        } catch (IOErrorException e) {
            log.error("mGetFollowingCount failed, userIds: {}", userIds, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, Long> mGetFollowerCount(List<Long> userIds) {
        String idList = userIds.stream()
                .map(id -> "\"" + id + "\"")
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        String nGQL = String.format(
                "UNWIND [%s] AS vid " +
                        "MATCH (a:user)-[:follow]->(b:user) " +
                        "WHERE id(b) == vid " +
                        "RETURN b.user.user_id AS userId, COUNT(a) AS count",
                idList
        );
        try {
            ResultSet resultSet = session.execute(nGQL);
            if (!resultSet.isSucceeded()) {
                log.error("mGetFollowerCount failed, userIds: {}, error: {}", userIds, resultSet.getErrorMessage());
                throw new RuntimeException("mGetFollowerCount failed: " + resultSet.getErrorMessage());
            }
            return parseCount(resultSet);
        } catch (IOErrorException e) {
            log.error("mGetFollowerCount failed, userIds: {}", userIds, e);
            throw new RuntimeException(e);
        }
    }

    private List<User> parseUsers(ResultSet resultSet) throws UnsupportedEncodingException {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < resultSet.rowsSize(); i++) {
            ResultSet.Record record = resultSet.rowValues(i);
            User user = new User();
            user.setUserId(record.get("user_id").asLong());
            user.setUsername(record.get("username").asString());
            user.setAvatar(record.get("avatar").asString());
            users.add(user);
        }
        return users;
    }

    private Map<Long, Long> parseCount(ResultSet resultSet) {
        Map<Long, Long> result = new HashMap<>();
        for (int i = 0; i < resultSet.rowsSize(); i++) {
            ResultSet.Record record = resultSet.rowValues(i);
            long userId = record.get("userId").asLong();
            long count = record.get("count").asLong();
            result.put(userId, count);
        }
        return result;
    }
}
