package system.work.model;

import java.util.ArrayList;
import java.util.List;

import basecore.model.BaseModel;
import system.user.model.ClassModel;

/**
 * 布置作业的model
 * @author 李昌鹏
 */
public class PutWorkModel extends BaseModel{

	private String time;
	private String teacherCode;
	private String tm;
	private String gradeStandard;	//评分标准
	private String type;			//作业的类型
	
	ClassModel cClass=new ClassModel();		//班级的model
	
	private String sStudentCode;			//学生code，查询用
	
	public ClassModel getcClass() {
		return cClass;
	}
	public void setcClass(ClassModel cClass) {
		this.cClass = cClass;
	}
	public String getsStudentCode() {
		return sStudentCode;
	}
	public void setsStudentCode(String sStudentCode) {
		this.sStudentCode = sStudentCode;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTeacherCode() {
		return teacherCode;
	}
	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}
	public String getTm() {
		return tm;
	}
	public void setTm(String tm) {
		this.tm = tm;
	}
	public String getGradeStandard() {
		return gradeStandard;
	}
	public void setGradeStandard(String gradeStandard) {
		this.gradeStandard = gradeStandard;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
