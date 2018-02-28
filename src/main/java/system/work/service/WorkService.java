package system.work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.work.mapper.WorkMapper;

/**
 * 作业的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("workService")
public class WorkService<T> extends BaseService<T>{

	@Autowired
	private WorkMapper<T> WorkMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return WorkMapper;
	}

}
