package violet.action.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import violet.action.common.pojo.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user values (null,#{userId},#{username},#{avatar},#{password})")
    boolean createUser(User user);

    @Select("select * from user where user_id = #{userId}")
    User selectByUserId(@Param("userId") Long userId);

    @Select("select * from user where username = #{username}")
    User selectByUserName(@Param("username") String username);
}
