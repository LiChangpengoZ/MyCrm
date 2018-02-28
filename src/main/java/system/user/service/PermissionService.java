package system.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.user.mapper.PermissionMapper;

/**
 * 权限的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("permissionService")
public class PermissionService<T> extends BaseService<T>{

	@Autowired
	private PermissionMapper<T> permissionMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return permissionMapper;
	}

}
