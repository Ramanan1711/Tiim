package com.tiim.model;

public class Branch {

	private int branchId;
	private String branchName;
	private String branchCode;
	private String searchBranch;
	private int isActive;
	private String action;
	private String delStatus;
	
	
	
	public String getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getSearchBranch() {
		return searchBranch;
	}
	public void setSearchBranch(String searchBranch) {
		this.searchBranch = searchBranch;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	
	
}
