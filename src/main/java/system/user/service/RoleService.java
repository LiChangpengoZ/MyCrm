package system.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.user.mapper.RoleMapper;

/**
 * 角色的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("roleService")
public class RoleService<T> extends BaseService<T>{

	@Autowired
	private RoleMapper<T> roleMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return roleMapper;
	}

}
