package system.work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.work.mapper.AssessmentMapper;

/**
 * 月度考核的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("assessmentService")
public class AssessmentService<T> extends BaseService<T>{

	@Autowired
	private AssessmentMapper<T> assessmentMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return assessmentMapper;
	}

}
