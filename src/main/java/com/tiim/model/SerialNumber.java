package com.tiim.model;

public class SerialNumber {

	private String lotNumber;
	private int approvedFlag;
	private String serialNumber;
	private int stockFlag;
	private int serialId;
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public int getApprovedFlag() {
		return approvedFlag;
	}
	public void setApprovedFlag(int approvedFlag) {
		this.approvedFlag = approvedFlag;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getStockFlag() {
		return stockFlag;
	}
	public void setStockFlag(int stockFlag) {
		this.stockFlag = stockFlag;
	}
	public int getSerialId() {
		return serialId;
	}
	public void setSerialId(int serialId) {
		this.serialId = serialId;
	}
	
	
}
