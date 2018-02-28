package system.work.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.work.mapper.PutWorkMapper;
import system.work.model.PutWorkModel;

/**
 * 布置作业 的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("putWorkService")
public class PutWorkService<T> extends BaseService<T>{

	@Autowired
	private PutWorkMapper<T> putWorkMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return putWorkMapper;
	}

	/**
	 * 关联查询数量
	 * @param putWorkTemp
	 * @return
	 */
	public Integer selectCountJoin(T t) {
		// TODO Auto-generated method stub
		return putWorkMapper.selectCountJoin(t);
	}
	
}
