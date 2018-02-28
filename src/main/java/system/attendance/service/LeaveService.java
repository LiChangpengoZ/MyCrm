package system.attendance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.attendance.mapper.LeaveMapper;

/**
 * 请假的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("leaveService")
public class LeaveService<T> extends BaseService<T>{

	@Autowired
	private LeaveMapper<T> leaveMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return leaveMapper;
	}

}
