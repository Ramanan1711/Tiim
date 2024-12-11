package com.tiim.model;

import java.util.Date;
import java.util.List;

public class ScreenApproval {
	
	private int approvalId;
	private String screenName;
	private int transactionId;
	private int levelOfApproval;
	private int approvalFlag;
	private String approvedBy;
	private Date approvedDate;
	private List<ScreenUrl> lstTransactionId;
	private int noOfLevel;
	
	
	public int getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(int approvalId) {
		this.approvalId = approvalId;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getLevelOfApproval() {
		return levelOfApproval;
	}
	public void setLevelOfApproval(int levelOfApproval) {
		this.levelOfApproval = levelOfApproval;
	}
	public int getApprovalFlag() {
		return approvalFlag;
	}
	public void setApprovalFlag(int approvalFlag) {
		this.approvalFlag = approvalFlag;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	public int getNoOfLevel() {
		return noOfLevel;
	}
	public void setNoOfLevel(int noOfLevel) {
		this.noOfLevel = noOfLevel;
	}
	public List<ScreenUrl> getLstTransactionId() {
		return lstTransactionId;
	}
	public void setLstTransactionId(List<ScreenUrl> lstTransactionId) {
		this.lstTransactionId = lstTransactionId;
	}
	
}
