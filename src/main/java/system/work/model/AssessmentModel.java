package system.work.model;

import basecore.model.BaseModel;

/**
 * 月度成绩排名表
 * @author 李昌鹏
 */
public class AssessmentModel extends BaseModel{

	private Integer ranking;
	private String score;
	private String userCode;
	private String userName;
	private String attScore;	//考勤分
	private String workScore;	//作业分
	private String dailyScore;	//项目分
	
	
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAttScore() {
		return attScore;
	}
	public void setAttScore(String attScore) {
		this.attScore = attScore;
	}
	public String getWorkScore() {
		return workScore;
	}
	public void setWorkScore(String workScore) {
		this.workScore = workScore;
	}
	public String getDailyScore() {
		return dailyScore;
	}
	public void setDailyScore(String dailyScore) {
		this.dailyScore = dailyScore;
	}
	
	

}
