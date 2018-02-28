package system.attendance.model;

import basecore.model.BaseModel;
import system.user.model.ClassModel;

/**
 * 考勤表的model
 * @author 李昌鹏
 */
public class WorkAttendanceModel extends BaseModel{

	private String userCode;
	private String oneCheck;
	private String twoCheck;
	private String threeCheck;
	private String fourCheck;
	private String fiveCheck;
	private String sixCheck;
	private String score;
	private String time;
	
	private ClassModel cClass =new ClassModel();			//根据老师的code，班级code查询用
	
	private String uTrueName;	//真实名字
	
	
	public String getuTrueName() {
		return uTrueName;
	}
	public void setuTrueName(String uTrueName) {
		this.uTrueName = uTrueName;
	}
	public ClassModel getcClass() {
		return cClass;
	}
	public void setcClass(ClassModel cClass) {
		this.cClass = cClass;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getOneCheck() {
		return oneCheck;
	}
	public void setOneCheck(String oneCheck) {
		this.oneCheck = oneCheck;
	}
	public String getTwoCheck() {
		return twoCheck;
	}
	public void setTwoCheck(String twoCheck) {
		this.twoCheck = twoCheck;
	}
	public String getThreeCheck() {
		return threeCheck;
	}
	public void setThreeCheck(String threeCheck) {
		this.threeCheck = threeCheck;
	}
	public String getFourCheck() {
		return fourCheck;
	}
	public void setFourCheck(String fourCheck) {
		this.fourCheck = fourCheck;
	}
	public String getFiveCheck() {
		return fiveCheck;
	}
	public void setFiveCheck(String fiveCheck) {
		this.fiveCheck = fiveCheck;
	}
	public String getSixCheck() {
		return sixCheck;
	}
	public void setSixCheck(String sixCheck) {
		this.sixCheck = sixCheck;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
