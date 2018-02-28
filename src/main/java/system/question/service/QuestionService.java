package system.question.service;

import org.springframework.beans.factory.annotation.Autowired;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.question.mapper.QuestionMapper;

/**
 * 学生回答问卷的service
 * @author 李昌鹏
 * @param <T>
 */
public class QuestionService<T> extends BaseService<T>{

	@Autowired
	private QuestionMapper<T> questionMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return questionMapper;
	}

}
