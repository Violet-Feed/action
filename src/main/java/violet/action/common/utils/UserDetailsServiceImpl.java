package violet.action.common.utils;

import violet.action.common.mapper.UserMapper;
import violet.action.common.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//用来接入数据库信息
        User user=userMapper.selectByUserName(username);
        if(user==null){
            throw new RuntimeException("用户不存在");
        }
        return new UserDetailsImpl(user);
    }
}
