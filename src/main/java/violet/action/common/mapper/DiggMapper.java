package violet.action.common.mapper;

import java.util.List;
import java.util.Map;

public interface DiggMapper {
    void digg(Long userId, String entityType, Long entityId);

    void cancelDigg(Long userId, String entityType, Long entityId);

    List<Long> getDiggListByUser(Long userId, String entityType, int skip, int limit);

    Map<Long, Long> mGetDiggCountByEntity(String entityType, List<Long> entityIds);

    Map<Long, Boolean> mIsDigg(Long userId, String entityType, List<Long> entityIds);
}
