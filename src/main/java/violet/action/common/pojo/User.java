package violet.action.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Node("User")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Property("user_id")
    private Long userId;
    private String username;
    private String avatar;
    @Transient
    private String password;
}