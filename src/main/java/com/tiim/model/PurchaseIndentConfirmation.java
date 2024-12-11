package com.tiim.model;

public class PurchaseIndentConfirmation {
	
	
	private int isActive;
	
	private int purchaseIndentDetailId;
	private String toolinglotnumber;
	private String productName;
	private String drawingNo;
	private String typeOfTool;
	private String machineType;
	private String uom;
	private String toolingStatus;
	private String confirmation;
	private String poRefNo;
	private String supplierName;
	private String expectedDelDate;
	private int producedQty;
	
	
	public int getProducedQty() {
		return producedQty;
	}
	public void setProducedQty(int producedQty) {
		this.producedQty = producedQty;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getPurchaseIndentDetailId() {
		return purchaseIndentDetailId;
	}
	public void setPurchaseIndentDetailId(int purchaseIndentDetailId) {
		this.purchaseIndentDetailId = purchaseIndentDetailId;
	}

	public String getToolinglotnumber() {
		return toolinglotnumber;
	}
	public void setToolinglotnumber(String toolinglotnumber) {
		this.toolinglotnumber = toolinglotnumber;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDrawingNo() {
		return drawingNo;
	}
	public void setDrawingNo(String drawingNo) {
		this.drawingNo = drawingNo;
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
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getToolingStatus() {
		return toolingStatus;
	}
	public void setToolingStatus(String toolingStatus) {
		this.toolingStatus = toolingStatus;
	}
	public String getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	public String getPoRefNo() {
		return poRefNo;
	}
	public void setPoRefNo(String poRefNo) {
		this.poRefNo = poRefNo;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getExpectedDelDate() {
		return expectedDelDate;
	}
	public void setExpectedDelDate(String expectedDelDate) {
		this.expectedDelDate = expectedDelDate;
	}
	
	
	
}
