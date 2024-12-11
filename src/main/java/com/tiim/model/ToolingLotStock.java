package com.tiim.model;

public class ToolingLotStock {
	
	private String product;
	private String drawingNo;
	private String machineType;
	private String toolType;
	private String lotNumber;
	private int orderQty;
	private int availableQty;
	private String missingSINo;
	private String location;
	private int tabletProducedQty;
	private int intervalQty;
	private int expiryDueQty;
	private String lotStatus;
	private int issueQty;
	private int returnQty;
	private int damagedQty;
	private String batchNumber;
	private int clearanceNumber;
	private int closingNumber;
	private String punchSetNo;
	private int compForce;
	private int issueId;
	private String issueBy;
	private String visualCheckBy;
	private long returnId;

	private String returnBy;
	private String cleanedBy;
		
	public int getIssueId() {
		return issueId;
	}
	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}
	public String getIssueBy() {
		return issueBy;
	}
	public void setIssueBy(String issueBy) {
		this.issueBy = issueBy;
	}
	public String getVisualCheckBy() {
		return visualCheckBy;
	}
	public void setVisualCheckBy(String visualCheckBy) {
		this.visualCheckBy = visualCheckBy;
	}
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
	public int getClearanceNumber() {
		return clearanceNumber;
	}
	public void setClearanceNumber(int clearanceNumber) {
		this.clearanceNumber = clearanceNumber;
	}
	public int getClosingNumber() {
		return closingNumber;
	}
	public void setClosingNumber(int closingNumber) {
		this.closingNumber = closingNumber;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public int getDamagedQty() {
		return damagedQty;
	}
	public void setDamagedQty(int damagedQty) {
		this.damagedQty = damagedQty;
	}
	public int getIssueQty() {
		return issueQty;
	}
	public void setIssueQty(int issueQty) {
		this.issueQty = issueQty;
	}
	public int getReturnQty() {
		return returnQty;
	}
	public void setReturnQty(int returnQty) {
		this.returnQty = returnQty;
	}
	public int getIntervalQty() {
		return intervalQty;
	}
	public void setIntervalQty(int intervalQty) {
		this.intervalQty = intervalQty;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getDrawingNo() {
		return drawingNo;
	}
	public void setDrawingNo(String drawingNo) {
		this.drawingNo = drawingNo;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String getToolType() {
		return toolType;
	}
	public void setToolType(String toolType) {
		this.toolType = toolType;
	}
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public int getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}
	public int getAvailableQty() {
		return availableQty;
	}
	public void setAvailableQty(int availableQty) {
		this.availableQty = availableQty;
	}
	public String getMissingSINo() {
		return missingSINo;
	}
	public void setMissingSINo(String missingSINo) {
		this.missingSINo = missingSINo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getTabletProducedQty() {
		return tabletProducedQty;
	}
	public void setTabletProducedQty(int tabletProducedQty) {
		this.tabletProducedQty = tabletProducedQty;
	}
	
	public int getExpiryDueQty() {
		return expiryDueQty;
	}
	public void setExpiryDueQty(int expiryDueQty) {
		this.expiryDueQty = expiryDueQty;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	
	public long getReturnId() {
		return returnId;
	}
	public void setReturnId(long returnId) {
		this.returnId = returnId;
	}
	public String getReturnBy() {
		return returnBy;
	}
	public void setReturnBy(String returnBy) {
		this.returnBy = returnBy;
	}
	public String getCleanedBy() {
		return cleanedBy;
	}
	public void setCleanedBy(String cleanedBy) {
		this.cleanedBy = cleanedBy;
	}

}
