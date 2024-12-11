package com.tiim.model;

import java.sql.Date;

public class DestructionNote {

	private int destructionNo;
	private String destructionDate;
	private String action;
	private int approvalflag;
	private Date approveddate;
	private String approvedby;
	private String uploadedPath;
	
	
	public String getUploadedPath() {
		return uploadedPath;
	}
	public void setUploadedPath(String uploadedPath) {
		this.uploadedPath = uploadedPath;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the approvalflag
	 */
	public int getApprovalflag() {
		return approvalflag;
	}
	/**
	 * @param approvalflag the approvalflag to set
	 */
	public void setApprovalflag(int approvalflag) {
		this.approvalflag = approvalflag;
	}
	/**
	 * @return the approveddate
	 */
	public Date getApproveddate() {
		return approveddate;
	}
	/**
	 * @param approveddate the approveddate to set
	 */
	public void setApproveddate(Date approveddate) {
		this.approveddate = approveddate;
	}
	/**
	 * @return the approvedby
	 */
	public String getApprovedby() {
		return approvedby;
	}
	/**
	 * @param approvedby the approvedby to set
	 */
	public void setApprovedby(String approvedby) {
		this.approvedby = approvedby;
	}
	/**
	 * @return the destructionNote
	 */
	public int getDestructionNo() {
		return destructionNo;
	}
	/**
	 * @param destructionNote the destructionNote to set
	 */
	public void setDestructionNo(int destructionNo) {
		this.destructionNo = destructionNo;
	}
	/**
	 * @return the destructionDate
	 */
	public String getDestructionDate() {
		return destructionDate;
	}
	/**
	 * @param destructionDate the destructionDate to set
	 */
	public void setDestructionDate(String destructionDate) {
		this.destructionDate = destructionDate;
	}
}
