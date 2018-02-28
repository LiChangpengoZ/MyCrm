package system.question.model;

import basecore.model.BaseModel;

/**
 * 学生回答问卷的model
 * @author 李昌鹏
 */
public class QuestionModel extends BaseModel{

	private String userCode;
	private String putQuestionCode;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPutQuestionCode() {
		return putQuestionCode;
	}
	public void setPutQuestionCode(String putQuestionCode) {
		this.putQuestionCode = putQuestionCode;
	}
	
	
}
