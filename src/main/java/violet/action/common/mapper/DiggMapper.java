package violet.action.common.mapper;

import violet.action.common.pojo.Entity;

import java.util.List;
import java.util.Map;

public interface DiggMapper {
    void digg(Long userId, String entityType, Long entityId);

    void cancelDigg(Long userId, String entityType, Long entityId);

    List<Entity> getDiggListByUser(Long userId, String entityType, int skip, int limit);

    List<Map<String, Object>> mGetDiggCountByEntity(String entityType, List<Long> entityIds);

    List<Map<String, Object>> mHasDigg(Long userId, String entityType, List<Long> entityIds);
}
