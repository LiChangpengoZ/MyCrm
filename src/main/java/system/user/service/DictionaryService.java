package system.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.user.mapper.DictionaryMapper;

/**
 * 字典表的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("dictionaryService")
public class DictionaryService<T> extends BaseService<T>{

	@Autowired
	private DictionaryMapper<T> dictionaryMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return dictionaryMapper;
	}

}
