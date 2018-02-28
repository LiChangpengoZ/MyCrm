package system.question.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.question.mapper.PutQuestionMapper;

/**
 * 创建问卷的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("putQuestionService")
public class PutQuestionService<T> extends BaseService<T>{

	@Autowired
	private PutQuestionMapper<T> PutQuestionMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return PutQuestionMapper;
	}

}
