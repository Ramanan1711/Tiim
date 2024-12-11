package com.tiim.model;

import java.util.Date;

public class CleaningSop {

	private int[] cleaningId;
	private int serialNo;
	private String cleaningtype;
	private String description;
	private String[] cleaningprocess;
	private String action;
	private int approvalflag;
	private Date approveddate;
	private String approvedby;
	private int  alertWeeks;
	/**
	 * @return the cleaningId
	 */
	public int[] getCleaningId() {
		return cleaningId;
	}
	/**
	 * @param cleaningId the cleaningId to set
	 */
	public void setCleaningId(int[] cleaningId) {
		this.cleaningId = cleaningId;
	}
	/**
	 * @return the serialNo
	 */
	public int getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return the cleaningtype
	 */
	public String getCleaningtype() {
		return cleaningtype;
	}
	/**
	 * @param cleaningtype the cleaningtype to set
	 */
	public void setCleaningtype(String cleaningtype) {
		this.cleaningtype = cleaningtype;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the cleaningprocess
	 */
	public String[] getCleaningprocess() {
		return cleaningprocess;
	}
	/**
	 * @param cleaningprocess the cleaningprocess to set
	 */
	public void setCleaningprocess(String[] cleaningprocess) {
		this.cleaningprocess = cleaningprocess;
	}
	public int getAlertWeeks() {
		return alertWeeks;
	}
	public void setAlertWeeks(int alertWeeks) {
		this.alertWeeks = alertWeeks;
	}
	
	
	
}
