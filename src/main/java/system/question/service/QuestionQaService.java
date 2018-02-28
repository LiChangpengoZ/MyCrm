package system.question.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.question.mapper.QuestionQaMapper;

/**
 * 问卷表的题目选项的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("questionQaService")
public class QuestionQaService<T> extends BaseService<T>{

	@Autowired
	private QuestionQaMapper<T> questionQaMapper;
 	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return questionQaMapper;
	}

	
}
