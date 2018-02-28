package system.attendance.model;

import basecore.model.BaseModel;
import system.user.model.ClassModel;

/**
 * 请假的model
 * @author 李昌鹏
 */
public class LeaveModel extends BaseModel{

	private String userCode;
	private String why;
	private String time;
	private String beginTime;
	private String endTime;
	private String status;
	
	private ClassModel cClass =new ClassModel();			//根据老师的code，班级code查询用
	
	private String uTrueName;	//真实名字
	
	private String passOrBack;	//通过或者避驳回
	
	public String getPassOrBack() {
		return passOrBack;
	}
	public void setPassOrBack(String passOrBack) {
		this.passOrBack = passOrBack;
	}
	public ClassModel getcClass() {
		return cClass;
	}
	public void setcClass(ClassModel cClass) {
		this.cClass = cClass;
	}
	public String getuTrueName() {
		return uTrueName;
	}
	public void setuTrueName(String uTrueName) {
		this.uTrueName = uTrueName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getWhy() {
		return why;
	}
	public void setWhy(String why) {
		this.why = why;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
