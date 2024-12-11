package com.tiim.model;

public class Role {

	private int roleId;
	private String roleName;
	private int isActive;
	private String searchRole;
	
	public String getSearchRole() {
		return searchRole;
	}
	public void setSearchRole(String searchRole) {
		this.searchRole = searchRole;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
