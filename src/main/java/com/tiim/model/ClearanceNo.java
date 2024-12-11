package com.tiim.model;

import java.util.Date;

public class ClearanceNo {

	private int clearanceNo;
	private String lotNumber;
	private Date clearanceDate;
	private String productName;
	private String batchNumber;
	private int batchQty;
	private int approvalflag;
	private Date approveddate;
	private String approvedby;
	private long toolingLife;
	private long nextDueQty;
	private long issueId;
	
	
	public long getIssueId() {
		return issueId;
	}
	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}
	public long getToolingLife() {
		return toolingLife;
	}
	public void setToolingLife(long toolingLife) {
		this.toolingLife = toolingLife;
	}
	
	public long getNextDueQty() {
		return nextDueQty;
	}
	public void setNextDueQty(long nextDueQty) {
		this.nextDueQty = nextDueQty;
	}
	/**
	 * @return the clearanceNo
	 */
	public int getClearanceNo() {
		return clearanceNo;
	}
	/**
	 * @param clearanceNo the clearanceNo to set
	 */
	public void setClearanceNo(int clearanceNo) {
		this.clearanceNo = clearanceNo;
	}

	/**
	 * @return the clearanceDate
	 */
	public Date getClearanceDate() {
		return clearanceDate;
	}
	/**
	 * @param clearanceDate the clearanceDate to set
	 */
	public void setClearanceDate(Date clearanceDate) {
		this.clearanceDate = clearanceDate;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}
	/**
	 * @param batchNumber the batchNumber to set
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	/**
	 * @return the batchQty
	 */
	public int getBatchQty() {
		return batchQty;
	}
	/**
	 * @param batchQty the batchQty to set
	 */
	public void setBatchQty(int batchQty) {
		this.batchQty = batchQty;
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
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
}
