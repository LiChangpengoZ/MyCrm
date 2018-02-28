package system.user.model;

import java.util.List;

import basecore.model.BaseModel;

/**
 * user的model
 * @author 李昌鹏
 */
public class UserModel extends BaseModel{

	private String userName;
	private String password;
	private String trueName;
	private String roleCode;
	private String calssCode;
	
	private RoleModel rRole;	//角色表中的角色名
	
	private ClassModel cClass=new ClassModel();	//班级表中的班级名，班级code，老师code
	
	public ClassModel getcClass() {
		return cClass;
	}
	public void setcClass(ClassModel cClass) {
		this.cClass = cClass;
	}
	private List<PermissionModel> pPermissionList;	//角色权限集合
	
	public RoleModel getrRole() {
		return rRole;
	}
	public void setrRole(RoleModel rRole) {
		this.rRole = rRole;
	}
	public List<PermissionModel> getpPermissionList() {
		return pPermissionList;
	}
	public void setpPermissionList(List<PermissionModel> pPermissionList) {
		this.pPermissionList = pPermissionList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getCalssCode() {
		return calssCode;
	}
	public void setCalssCode(String calssCode) {
		this.calssCode = calssCode;
	}

	
}
