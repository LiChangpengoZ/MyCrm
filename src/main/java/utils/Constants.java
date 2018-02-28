package utils;

import java.util.ArrayList;
import java.util.List;

import system.user.model.DictionaryModel;

/**
 * 常量类
 * @author 李昌鹏
 */
public class Constants {

	public static List<DictionaryModel> KQ_LIST=new ArrayList<DictionaryModel>();	//考勤的集合
	public static List<DictionaryModel> QJ_LIST=new ArrayList<DictionaryModel>();	//请假的集合
	public static List<DictionaryModel> ZY_LIST=new ArrayList<DictionaryModel>();	//作业的集合
	
	public static String SELECT_MYSELF;			//查询自己
	public static String SELECT_CLASS;			//查询班级
	public static String SELECT_ALL;			//查询全部
	
	public static Integer PAGE_NUMBER;			//第几页
	public static Integer ROWS_NUMBER;			//有几行
	
	public static Integer NOW_CLASS_Y;			//当前班
	public static Integer NOW_CLASS_N;			//不是当前班
	
	public static Integer DELETE_Y;				//删除
	public static Integer DELETE_N;				//未删除
	
	public static Integer EFFECT_Y;				//有效
	public static Integer EFFECT_N;				//无效
	
	public static String LEAVE_SP;			//请假审批
	public static String LEAVE_BH;			//请假驳回
	public static String LEAVE_JSTG;			//请假教师通过
	public static String LEAVE_JWTG;			//请假教务通过
	
	public static String GRADE_F;		//未做放弃
	public static String GRADE_E;		//差距较大
	public static String GRADE_D;		//一般情况
	public static String GRADE_C;		//较好完成
	public static String GRADE_B;		//全部完成
	public static String GRADE_A;		//扩展掌握
	
}
