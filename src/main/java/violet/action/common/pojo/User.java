package violet.action.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import violet.action.common.proto_gen.action.UserInfo;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private Long userId;
    private String username;
    private String avatar;
    private String password;

    public UserInfo toProto() {
        return UserInfo.newBuilder()
                .setUserId(this.userId)
                .setUsername(this.username)
                .setAvatar(this.avatar)
                .build();
    }
}