package system.work.model;

import basecore.model.BaseModel;
import system.user.model.ClassModel;
import system.user.model.UserModel;

/**
 * 项目的model
 * @author 李昌鹏
 */
public class ProjectModel extends BaseModel{

	private String projectName;
	private String userCode;
	private String allScore;
	private String studentScore;
	private String teacherScore;
	private String grade;
	private String time;
	private String studentGrade;

	private ClassModel cClass=new ClassModel();
	private UserModel uUser=new UserModel();
	
	
	public ClassModel getcClass() {
		return cClass;
	}
	public void setcClass(ClassModel cClass) {
		this.cClass = cClass;
	}
	public String getStudentGrade() {
		return studentGrade;
	}
	public void setStudentGrade(String studentGrade) {
		this.studentGrade = studentGrade;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public UserModel getuUser() {
		return uUser;
	}
	public void setuUser(UserModel uUser) {
		this.uUser = uUser;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getAllScore() {
		return allScore;
	}
	public void setAllScore(String allScore) {
		this.allScore = allScore;
	}
	public String getStudentScore() {
		return studentScore;
	}
	public void setStudentScore(String studentScore) {
		this.studentScore = studentScore;
	}
	public String getTeacherScore() {
		return teacherScore;
	}
	public void setTeacherScore(String teacherScore) {
		this.teacherScore = teacherScore;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}

}
