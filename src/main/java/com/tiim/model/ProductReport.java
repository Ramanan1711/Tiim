package com.tiim.model;

public class ProductReport {

	private String productName;
	private String machine;
	private String drawingNumber;
	private String typeOfTool;
	private String lotNumber;
	private int toolingLife;
	private int serviceIntervalQty;
	private int producedQty;
	private float lifePercentage;
	private long balanceProducedQty;
	private float producedQtyPercentage;
	private int percentage;
	private long nextDueQty;
	private String batchNumber;
		
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public long getNextDueQty() {
		return nextDueQty;
	}
	public void setNextDueQty(long nextDueQty) {
		this.nextDueQty = nextDueQty;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	public float getProducedQtyPercentage() {
		return producedQtyPercentage;
	}
	public void setProducedQtyPercentage(float producedQtyPercentage) {
		this.producedQtyPercentage = producedQtyPercentage;
	}
	public long getBalanceProducedQty() {
		return balanceProducedQty;
	}
	public void setBalanceProducedQty(long balanceProducedQty) {
		this.balanceProducedQty = balanceProducedQty;
	}
	public float getLifePercentage() {
		return lifePercentage;
	}
	public void setLifePercentage(float lifePercentage) {
		this.lifePercentage = lifePercentage;
	}
	public int getProducedQty() {
		return producedQty;
	}
	public void setProducedQty(int producedQty) {
		this.producedQty = producedQty;
	}
	public int getToolingLife() {
		return toolingLife;
	}
	public void setToolingLife(int toolingLife) {
		this.toolingLife = toolingLife;
	}
	public int getServiceIntervalQty() {
		return serviceIntervalQty;
	}
	public void setServiceIntervalQty(int serviceIntervalQty) {
		this.serviceIntervalQty = serviceIntervalQty;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getDrawingNumber() {
		return drawingNumber;
	}
	public void setDrawingNumber(String drawingNumber) {
		this.drawingNumber = drawingNumber;
	}
	public String getTypeOfTool() {
		return typeOfTool;
	}
	public void setTypeOfTool(String typeOfTool) {
		this.typeOfTool = typeOfTool;
	}
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
}
