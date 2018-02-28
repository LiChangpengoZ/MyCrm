package system.work.model;

import basecore.model.BaseModel;
import system.user.model.ClassModel;
import system.user.model.UserModel;

/**
 * 作业完成情况表
 * @author 李昌鹏
 */
public class WorkModel extends BaseModel{
	
	private String studentCode;
	private String putWorkCode;
	private String oneselfScore;
	private String oneselfGrade;
	private String teacherScore;
	private String teacherGrade;

	private ClassModel cClass=new ClassModel();
	private UserModel uUser=new UserModel();
	
	public ClassModel getcClass() {
		return cClass;
	}
	public void setcClass(ClassModel cClass) {
		this.cClass = cClass;
	}
	
	private PutWorkModel pPutWork=new PutWorkModel();
	
	public PutWorkModel getpPutWork() {
		return pPutWork;
	}
	public void setpPutWork(PutWorkModel pPutWork) {
		this.pPutWork = pPutWork;
	}
	public UserModel getuUser() {
		return uUser;
	}
	public void setuUser(UserModel uUser) {
		this.uUser = uUser;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	
	public String getPutWorkCode() {
		return putWorkCode;
	}
	public void setPutWorkCode(String putWorkCode) {
		this.putWorkCode = putWorkCode;
	}
	public String getOneselfScore() {
		return oneselfScore;
	}
	public void setOneselfScore(String oneselfScore) {
		this.oneselfScore = oneselfScore;
	}
	public String getOneselfGrade() {
		return oneselfGrade;
	}
	public void setOneselfGrade(String oneselfGrade) {
		this.oneselfGrade = oneselfGrade;
	}
	public String getTeacherScore() {
		return teacherScore;
	}
	public void setTeacherScore(String teacherScore) {
		this.teacherScore = teacherScore;
	}
	public String getTeacherGrade() {
		return teacherGrade;
	}
	public void setTeacherGrade(String teacherGrade) {
		this.teacherGrade = teacherGrade;
	}
	
	
}
