package com.tiim.model;

public class ProductHistory {
	
	private int id;
	private int revisionNumber;
	private String productName;
	private String typeOfTool;
	private String machineType;
	private String drawingNo;
	private String lotNumber;
	private String reportDate;
	private String source;
	private String punchSetNo;
	private int compForce;
	
	public String getPunchSetNo() {
		return punchSetNo;
	}
	public void setPunchSetNo(String punchSetNo) {
		this.punchSetNo = punchSetNo;
	}
	public int getCompForce() {
		return compForce;
	}
	public void setCompForce(int compForce) {
		this.compForce = compForce;
	}
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRevisionNumber() {
		return revisionNumber;
	}
	public void setRevisionNumber(int revisionNumber) {
		this.revisionNumber = revisionNumber;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTypeOfTool() {
		return typeOfTool;
	}
	public void setTypeOfTool(String typeOfTool) {
		this.typeOfTool = typeOfTool;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String getDrawingNo() {
		return drawingNo;
	}
	public void setDrawingNo(String drawingNo) {
		this.drawingNo = drawingNo;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}
