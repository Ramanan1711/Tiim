package com.tiim.model;

import java.sql.Date;

public class LotToolDestruction {

	private int destructionNo;
	private String lotDestructionDate;
	private String action;
	private int approvalflag;
	private Date approveddate;
	private String approvedby;
	private String uploadedPath;
	private int lotDestruction;
	private String destroyedBy;
	private String lotNo;
	private String punch;
	private String serailNo;
	private String rejectedAt;
	private String remarks;

	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
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
	public String getLotDestructionDate() {
		return lotDestructionDate;
	}
	/**
	 * @param destructionDate the destructionDate to set
	 */
	public void setLotDestructionDate(String lotDestructionDate) {
		this.lotDestructionDate = lotDestructionDate;
	}
	/**
	 * @return the lotDestruction
	 */
	public int getLotDestruction() {
		return lotDestruction;
	}
	/**
	 * @param lotDestruction the lotDestruction to set
	 */
	public void setLotDestruction(int lotDestruction) {
		this.lotDestruction = lotDestruction;
	}
	/**
	 * @return the destroyedBy
	 */
	public String getDestroyedBy() {
		return destroyedBy;
	}
	/**
	 * @param destroyedBy the destroyedBy to set
	 */
	public void setDestroyedBy(String destroyedBy) {
		this.destroyedBy = destroyedBy;
	}
	/**
	 * @return the lotNo
	 */
	public String getLotNo() {
		return lotNo;
	}
	/**
	 * @param lotNo the lotNo to set
	 */
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	/**
	 * @return the punch
	 */
	public String getPunch() {
		return punch;
	}
	/**
	 * @param punch the punch to set
	 */
	public void setPunch(String punch) {
		this.punch = punch;
	}
	/**
	 * @return the serailNo
	 */
	
	/**
	 * @return the rejectedAt
	 */
	public String getRejectedAt() {
		return rejectedAt;
	}
	public String getSerailNo() {
		return serailNo;
	}
	public void setSerailNo(String serailNo) {
		this.serailNo = serailNo;
	}
	/**
	 * @param rejectedAt the rejectedAt to set
	 */
	public void setRejectedAt(String rejectedAt) {
		this.rejectedAt = rejectedAt;
	}
}
