package com.tiim.model;

public class Stock {
	
	private int stockId;
	private String toolingLotNumber;
	private String typeOfTool;
	private String productName;
	private String drawingNo;
	private String uom;
	private int tabletProducedQty;
	private String machineName;
	private long stockQty;
	private String branch;
	private String uploadPath;
	private int goodQty;
	private long damagedQty;
	private String rejectSerialNumber;
	
	public String getRejectSerialNumber() {
		return rejectSerialNumber;
	}
	public void setRejectSerialNumber(String rejectSerialNumber) {
		this.rejectSerialNumber = rejectSerialNumber;
	}
	public int getTabletProducedQty() {
		return tabletProducedQty;
	}
	public void setTabletProducedQty(int tabletProducedQty) {
		this.tabletProducedQty = tabletProducedQty;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	
	public String getToolingLotNumber() {
		return toolingLotNumber;
	}
	public void setToolingLotNumber(String toolingLotNumber) {
		this.toolingLotNumber = toolingLotNumber;
	}
	public String getTypeOfTool() {
		return typeOfTool;
	}
	public void setTypeOfTool(String typeOfTool) {
		this.typeOfTool = typeOfTool;
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
	
	
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public long getStockQty() {
		return stockQty;
	}
	public void setStockQty(long stockQty) {
		this.stockQty = stockQty;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public int getGoodQty() {
		return goodQty;
	}
	public void setGoodQty(int goodQty) {
		this.goodQty = goodQty;
	}
	public long getDamagedQty() {
		return damagedQty;
	}
	public void setDamagedQty(long damagedQty) {
		this.damagedQty = damagedQty;
	}
	
	
}
