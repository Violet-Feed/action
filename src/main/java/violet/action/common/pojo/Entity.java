package violet.action.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entity {
    private Long id;
    private Integer entityType;
    private Long entityId;
    private String extra;
}
