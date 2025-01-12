package violet.action.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "user")
public class UserEs{
    @Field(name = "user_id",type = FieldType.Long)
    private Long userId;
    @Field(name = "username",type = FieldType.Text)
    private String username;
    @Field(name = "avatar",type = FieldType.Keyword)
    private String avatar;
}
