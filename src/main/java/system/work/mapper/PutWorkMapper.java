package system.work.mapper;

import basecore.mapper.BaseMapper;

/**
 * 布置作业的mapper
 * @author 李昌鹏
 * @param <T>
 */
public interface PutWorkMapper<T> extends BaseMapper<T>{

	/**
	 * 关联查询数量
	 * @param t
	 * @return
	 */
	Integer selectCountJoin(T t);
	
}
