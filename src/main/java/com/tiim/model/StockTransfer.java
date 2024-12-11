package com.tiim.model;

public class StockTransfer {
	
	private int stockTransferId;
	private String stockDate;
	private String fromBranch;
	private String toBranch;
	private int isActive;
	
	private int stockTransferDetailId[];
	private String toolinglotnumber[];
	private String toolinglotnumber1;
	private String productName;
	private String drawingNo;
	private String typeOfTool;
	private String machineType;
	private String uom;
	private long stockQty;
	private long transferQty[];
	private long transferQty1;
	private String searchTransferId;
	private int stockTransferDetailId1;
	
	
	
	public String[] getToolinglotnumber() {
		return toolinglotnumber;
	}
	public int getStockTransferDetailId1() {
		return stockTransferDetailId1;
	}
	public void setStockTransferDetailId1(int stockTransferDetailId1) {
		this.stockTransferDetailId1 = stockTransferDetailId1;
	}
	
	public String getToolinglotnumber1() {
		return toolinglotnumber1;
	}
	public void setToolinglotnumber1(String toolinglotnumber1) {
		this.toolinglotnumber1 = toolinglotnumber1;
	}
	public void setToolinglotnumber(String[] toolinglotnumber) {
		this.toolinglotnumber = toolinglotnumber;
	}
	public String getSearchTransferId() {
		return searchTransferId;
	}
	public void setSearchTransferId(String searchTransferId) {
		this.searchTransferId = searchTransferId;
	}
	public int getStockTransferId() {
		return stockTransferId;
	}
	public void setStockTransferId(int stockTransferId) {
		this.stockTransferId = stockTransferId;
	}
	public String getStockDate() {
		return stockDate;
	}
	public void setStockDate(String stockDate) {
		this.stockDate = stockDate;
	}
	public String getFromBranch() {
		return fromBranch;
	}
	public void setFromBranch(String fromBranch) {
		this.fromBranch = fromBranch;
	}
	public String getToBranch() {
		return toBranch;
	}
	public void setToBranch(String toBranch) {
		this.toBranch = toBranch;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
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
	
	public int[] getStockTransferDetailId() {
		return stockTransferDetailId;
	}
	public void setStockTransferDetailId(int[] stockTransferDetailId) {
		this.stockTransferDetailId = stockTransferDetailId;
	}
	public long getStockQty() {
		return stockQty;
	}
	public void setStockQty(long stockQty) {
		this.stockQty = stockQty;
	}
	public long[] getTransferQty() {
		return transferQty;
	}
	public void setTransferQty(long[] transferQty) {
		this.transferQty = transferQty;
	}
	public long getTransferQty1() {
		return transferQty1;
	}
	public void setTransferQty1(long transferQty1) {
		this.transferQty1 = transferQty1;
	}
	
		
}
