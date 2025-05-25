package violet.action.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import violet.action.common.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user values (null,#{userId},#{username},#{avatar},#{password})")
    boolean createUser(User user);

    @Select("select * from user where user_id = #{userId}")
    User selectByUserId(@Param("userId") Long userId);

    @Select("<script>" +
            "SELECT * FROM user WHERE user_id IN " +
            "<foreach item='item' index='index' collection='userIds' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    List<User> selectByUserIds(@Param("userIds") List<Long> userIds);

    @Select("select * from user where username = #{username}")
    User selectByUserName(@Param("username") String username);
}
