package  com.lotus.backstage.user.mapper;

import com.lotus.backstage.user.model.User;

public interface UserMapper {
  

    int insert(User record);

    int insertSelective(User record);

    int deleteByPrimaryKey(Integer id);
    
    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

	User selectUser(User user);
    
}