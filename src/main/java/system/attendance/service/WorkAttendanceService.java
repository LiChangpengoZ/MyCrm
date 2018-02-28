package system.attendance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import basecore.mapper.BaseMapper;
import basecore.service.BaseService;
import system.attendance.mapper.WorkAttendanceMapper;

/**
 * 考勤的service
 * @author 李昌鹏
 * @param <T>
 */
@Service("workAttendanceService")
public class WorkAttendanceService<T> extends BaseService<T>{

	@Autowired
	private WorkAttendanceMapper<T> workAttendanceMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return workAttendanceMapper;
	}
	
	/**
	 * 根据用户名和事件查重
	 * @param t
	 * @return
	 */
	public Integer selectCountByUserCodeAndTime(T t){
		return workAttendanceMapper.selectCountByUserCodeAndTime(t);
	}

}
