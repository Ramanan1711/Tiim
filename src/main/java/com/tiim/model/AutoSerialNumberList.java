package com.tiim.model;

import java.util.List;

public class AutoSerialNumberList {

	private String insType;
	private String insNo;
	private String lotNumber;
	private List<String> acceptedQty;
	private List<String> rejectedQty;
	private int totalQty;
	
	public String getInsType() {
		return insType;
	}
	public void setInsType(String insType) {
		this.insType = insType;
	}
	public String getInsNo() {
		return insNo;
	}
	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public List<String> getAcceptedQty() {
		return acceptedQty;
	}
	public void setAcceptedQty(List<String> acceptedQty) {
		this.acceptedQty = acceptedQty;
	}
	public List<String> getRejectedQty() {
		return rejectedQty;
	}
	public void setRejectedQty(List<String> rejectedQty) {
		this.rejectedQty = rejectedQty;
	}
	public int getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
	}
	
}
