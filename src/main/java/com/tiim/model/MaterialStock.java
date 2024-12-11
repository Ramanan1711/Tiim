package com.tiim.model;

public class MaterialStock {
	
	private String toolingLotNumber;
	private String materialType;
	private int materialCode;
	private String materialName;
	private long stockQty;
	private String branch;
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getToolingLotNumber() {
		return toolingLotNumber;
	}
	public void setToolingLotNumber(String toolingLotNumber) {
		this.toolingLotNumber = toolingLotNumber;
	}
	
	public long getStockQty() {
		return stockQty;
	}
	public void setStockQty(long stockQty) {
		this.stockQty = stockQty;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public int getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(int materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
}
