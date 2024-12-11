package com.tiim.model;

import java.util.Date;

public class ClearanceReport {



	private int clearanceNo;
	private String lotNumber;
	private Date clearanceDate;
	private String productName;
	private String batchNumber;
	private int batchQty;
	private Date closingDate;
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
	
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public Date getClosingDate() {
		return closingDate;
	}
	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}
	

}
