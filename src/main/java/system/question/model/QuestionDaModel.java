package system.question.model;

import basecore.model.BaseModel;

/**
 * 文件答案的code
 * @author 李昌鹏
 */
public class QuestionDaModel extends BaseModel{

	private String daName;
	private String questionCode;
	private String tmCode;
	private String userCode;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getDaName() {
		return daName;
	}
	public void setDaName(String daName) {
		this.daName = daName;
	}
	public String getQuestionCode() {
		return questionCode;
	}
	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}
	public String getTmCode() {
		return tmCode;
	}
	public void setTmCode(String tmCode) {
		this.tmCode = tmCode;
	}
	
	
	
}
