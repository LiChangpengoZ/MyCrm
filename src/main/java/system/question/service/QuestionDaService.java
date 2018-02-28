package system.question.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.question.mapper.QuestionDaMapper;

/**
 * 答案的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("questionDaService")
public class QuestionDaService<T> extends BaseService<T>{

	@Autowired
	private QuestionDaMapper<T> questionDaMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return questionDaMapper;
	}

}
