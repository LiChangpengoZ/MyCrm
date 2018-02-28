package system.user.model;

import java.util.List;

import basecore.model.BaseModel;

/**
 * 权限的model
 * @author 李昌鹏
 *
 */
public class PermissionModel extends BaseModel{

	private String permissionName;
	private String url;
	private String pcode;
	private String type;
	
	private List<PermissionModel> sonPerList;	//父权限包含子权限
	
	private Integer series;			//增删改查的权限
	
	
	public Integer getSeries() {
		return series;
	}
	public void setSeries(Integer series) {
		this.series = series;
	}
	public List<PermissionModel> getSonPerList() {
		return sonPerList;
	}
	public void setSonPerList(List<PermissionModel> sonPerList) {
		this.sonPerList = sonPerList;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
