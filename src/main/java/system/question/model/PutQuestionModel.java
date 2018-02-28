package system.question.model;

import basecore.model.BaseModel;

/**
 * 创建问卷的model
 * @author 李昌鹏
 */
public class PutQuestionModel extends BaseModel{

	private String questionName;
	private String userCode;
	
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	
}
