package violet.action.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node("entity")
public class Entity {
    @Id
    @GeneratedValue
    private Long id;
    private Integer entityType;
    private Long entityId;
    private String extra;
}
