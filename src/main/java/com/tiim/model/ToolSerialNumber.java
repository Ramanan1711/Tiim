package com.tiim.model;

public class ToolSerialNumber {
	
	private Long serialId;
	private String lotNumber;
	private String module;
	private String serialNumber;
	private int acceptQty;
	private int rejectQty;
	private int totalRejectedQty;
	
	public int getTotalRejectedQty() {
		return totalRejectedQty;
	}
	public void setTotalRejectedQty(int totalRejectedQty) {
		this.totalRejectedQty = totalRejectedQty;
	}
	public Long getSerialId() {
		return serialId;
	}
	public void setSerialId(Long serialId) {
		this.serialId = serialId;
	}
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getAcceptQty() {
		return acceptQty;
	}
	public void setAcceptQty(int acceptQty) {
		this.acceptQty = acceptQty;
	}
	public int getRejectQty() {
		return rejectQty;
	}
	public void setRejectQty(int rejectQty) {
		this.rejectQty = rejectQty;
	}
	
	

}
