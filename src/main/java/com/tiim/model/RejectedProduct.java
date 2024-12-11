package com.tiim.model;

public class RejectedProduct {

	private int rejectedId;
	private int toolingId;
	private String productName;
	private String typeOfTool;
	private String machineName;
	private String drawingNumber;
	private String source;
	private String lotNumber;
	private String serialNumber;
	
	
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public int getRejectedId() {
		return rejectedId;
	}
	public void setRejectedId(int rejectedId) {
		this.rejectedId = rejectedId;
	}
	public int getToolingId() {
		return toolingId;
	}
	public void setToolingId(int toolingId) {
		this.toolingId = toolingId;
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
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getDrawingNumber() {
		return drawingNumber;
	}
	public void setDrawingNumber(String drawingNumber) {
		this.drawingNumber = drawingNumber;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	
}
