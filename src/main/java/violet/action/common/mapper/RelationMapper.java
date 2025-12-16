package violet.action.common.mapper;

import violet.action.common.pojo.User;

import java.util.List;
import java.util.Map;

public interface RelationMapper {
    void createUser(User user);

    void follow(Long fromUserId, Long toUserId);

    void unfollow(Long fromUserId, Long toUserId);

    List<User> getFollowingList(Long userId, int skip, int limit);

    List<User> getFollowerList(Long userId, int skip, int limit);

    List<User> getFriendList(Long userId, int skip, int limit);

    Map<Long, Map<String, String>> mGetFollowingMap(List<Long> userIds);

    Map<Long, Long> mGetFollowingCount(List<Long> userIds);

    Map<Long, Long> mGetFollowerCount(List<Long> userIds);
}
