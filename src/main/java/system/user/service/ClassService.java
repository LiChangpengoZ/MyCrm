package system.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.user.mapper.ClassMapper;

/**
 * 班级的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("classService")
public class ClassService<T> extends BaseService<T>{

	@Autowired
	private ClassMapper<T> classMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return classMapper;
	}

}
