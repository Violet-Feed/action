package violet.action.common.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import violet.action.common.proto_gen.action.UserInfo;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private Long userId;
    private String username;
    private String avatar;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date modifyTime;
    private Integer status;
    private String extra;

    public UserInfo toProto() {
        return UserInfo.newBuilder()
                .setUserId(this.userId)
                .setUsername(this.username)
                .setAvatar(this.avatar == null ? "" : this.avatar)
                .setCreateTime(this.createTime == null ? 0 : this.createTime.getTime())
                .setModifyTime(this.modifyTime == null ? 0 : this.modifyTime.getTime())
                .setStatus(this.status == null ? 0 : this.status)
                .setExtra(this.extra == null ? "" : this.extra)
                .build();
    }
}