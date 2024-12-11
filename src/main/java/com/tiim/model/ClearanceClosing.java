package com.tiim.model;

import java.util.Date;

public class ClearanceClosing {

	private int clearanceNo;
	private String lotNumber;
	private Date clearanceDate;
	private String productName;
	private String batchNumber;
	private String batchQty;
	private int closingNo;
	private int approvalflag;
	private Date approveddate;
	private String approvedby;
	private long currentBatchQty;
	
	public long getCurrentBatchQty() {
		return currentBatchQty;
	}
	public void setCurrentBatchQty(long currentBatchQty) {
		this.currentBatchQty = currentBatchQty;
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
	public String getBatchQty() {
		return batchQty;
	}
	/**
	 * @param batchQty the batchQty to set
	 */
	public void setBatchQty(String batchQty) {
		this.batchQty = batchQty;
	}
	/**
	 * @return the closingNo
	 */
	public int getClosingNo() {
		return closingNo;
	}
	/**
	 * @param closingNo the closingNo to set
	 */
	public void setClosingNo(int closingNo) {
		this.closingNo = closingNo;
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
