package system.user.model;

import java.util.ArrayList;
import java.util.List;

import basecore.model.BaseModel;

/**
 * 班级的model
 * @author 李昌鹏
 */
public class ClassModel extends BaseModel{

	private String className;
	private String teacherCode;
	
	private List<UserModel> uUserList=new ArrayList<UserModel>();
	
	public List<UserModel> getuUserList() {
		return uUserList;
	}
	public void setuUserList(List<UserModel> uUserList) {
		this.uUserList = uUserList;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTeacherCode() {
		return teacherCode;
	}
	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}
	
}
