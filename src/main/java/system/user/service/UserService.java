package system.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.user.mapper.UserMapper;

/**
 * user的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("userService")
public class UserService<T> extends BaseService<T>{

	@Autowired
	private UserMapper<T> userMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return userMapper;
	}
	

}
