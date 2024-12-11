package com.tiim.model;

public class StockTransferIssue {

	private int stockTransferIssueId;
	private int stockTransferIssueDetailId[];
	private int stockTransferIssueDetailsId1;
	private int stockTransferId;
	private String stockDate;
	private String stockIssueDate;
	private String fromBranch;
	private String toBranch;
	private int isActive;
	
	private int stockTransferDetailId[];
	private int stockTransferDetailId1;
	private String toolinglotnumber;
	private String productName;
	private String drawingNo;
	private String typeOfTool;
	private String machineType;
	private String uom;
	private long stockQty;
	private long transferQty;
	private long stockIssueQty[];
	private long stockIssueQty1;
	private String searchTransferIssueId;
	
	public String getSearchTransferIssueId() {
		return searchTransferIssueId;
	}
	public void setSearchTransferIssueId(String searchTransferIssueId) {
		this.searchTransferIssueId = searchTransferIssueId;
	}
	public String getStockIssueDate() {
		return stockIssueDate;
	}
	public void setStockIssueDate(String stockIssueDate) {
		this.stockIssueDate = stockIssueDate;
	}
	public int getStockTransferIssueId() {
		return stockTransferIssueId;
	}
	public void setStockTransferIssueId(int stockTransferIssueId) {
		this.stockTransferIssueId = stockTransferIssueId;
	}
	public int[] getStockTransferIssueDetailId() {
		return stockTransferIssueDetailId;
	}
	public void setStockTransferIssueDetailId(int[] stockTransferIssueDetailId) {
		this.stockTransferIssueDetailId = stockTransferIssueDetailId;
	}
	public int getStockTransferIssueDetailsId1() {
		return stockTransferIssueDetailsId1;
	}
	public void setStockTransferIssueDetailsId1(int stockTransferIssueDetailsId1) {
		this.stockTransferIssueDetailsId1 = stockTransferIssueDetailsId1;
	}
	public int getStockTransferDetailId1() {
		return stockTransferDetailId1;
	}
	public void setStockTransferDetailId1(int stockTransferDetailId1) {
		this.stockTransferDetailId1 = stockTransferDetailId1;
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
	
	public long getStockQty() {
		return stockQty;
	}
	public void setStockQty(long stockQty) {
		this.stockQty = stockQty;
	}
	public long getTransferQty() {
		return transferQty;
	}
	public void setTransferQty(long transferQty) {
		this.transferQty = transferQty;
	}
	public long[] getStockIssueQty() {
		return stockIssueQty;
	}
	public void setStockIssueQty(long[] stockIssueQty) {
		this.stockIssueQty = stockIssueQty;
	}
	public long getStockIssueQty1() {
		return stockIssueQty1;
	}
	public void setStockIssueQty1(long stockIssueQty1) {
		this.stockIssueQty1 = stockIssueQty1;
	}
	public int[] getStockTransferDetailId() {
		return stockTransferDetailId;
	}
	public void setStockTransferDetailId(int[] stockTransferDetailId) {
		this.stockTransferDetailId = stockTransferDetailId;
	}
	public String getToolinglotnumber() {
		return toolinglotnumber;
	}
	public void setToolinglotnumber(String toolinglotnumber) {
		this.toolinglotnumber = toolinglotnumber;
	}
		
}
