package system.attendance.mapper;

import basecore.mapper.BaseMapper;

/**
 * 考勤的mapper
 * @author 李昌鹏
 * @param <T>
 */
public interface WorkAttendanceMapper<T> extends BaseMapper<T>{

	/**
	 * 插入的时候根据用户的code和时间查重
	 * @param t
	 * @return
	 */
	Integer selectCountByUserCodeAndTime(T t);
	
}
