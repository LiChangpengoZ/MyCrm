package system.work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.work.mapper.ProjectMapper;

/**
 * 项目的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("projectService")
public class ProjectService<T> extends BaseService<T>{

	@Autowired
	private ProjectMapper<T> projectMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return projectMapper;
	}

}
