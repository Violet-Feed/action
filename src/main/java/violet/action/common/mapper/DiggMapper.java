package violet.action.common.mapper;

import violet.action.common.pojo.Entity;

import java.util.List;
import java.util.Map;

public interface DiggMapper {
    void digg(Long userId, Integer entityType, Long entityId);

    void cancelDigg(Long userId, Integer entityType, Long entityId);

    List<Entity> getDiggListByUser(Long userId, Integer entityType, int skip, int limit);

    List<Map<String, Object>> mGetDiggCountByEntity(Integer entityType, List<Long> entityIds);

    List<Map<String, Object>> mHasDigg(Long userId, Integer entityType, List<Long> entityIds);
}
