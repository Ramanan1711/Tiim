package com.tiim.model;

public class SupplierReturn {
	
	private String toolingLotNumber;
	private String productName;
	private String drawingNo;
	private String typeOfTool;
	private String machineType;
	private String uom;
	private int transactionDetailId;
	private int transactionId;
	private int receivedQuantity;
	private int requestId;
	private String requestDate;
	private int returnId;
	private int returnDetailId;
	private String branch;
	private String returnDate;
	private String searchReturntNo;
	private String returnNoteDate;
	private String returnType;
	
	
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getReturnNoteDate() {
		return returnNoteDate;
	}
	public void setReturnNoteDate(String returnNoteDate) {
		this.returnNoteDate = returnNoteDate;
	}
	public String getSearchReturntNo() {
		return searchReturntNo;
	}
	public void setSearchReturntNo(String searchReturntNo) {
		this.searchReturntNo = searchReturntNo;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public int getReturnId() {
		return returnId;
	}
	public void setReturnId(int returnId) {
		this.returnId = returnId;
	}
	public int getReturnDetailId() {
		return returnDetailId;
	}
	public void setReturnDetailId(int returnDetailId) {
		this.returnDetailId = returnDetailId;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public int getReceivedQuantity() {
		return receivedQuantity;
	}
	public void setReceivedQuantity(int receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	public int getTransactionDetailId() {
		return transactionDetailId;
	}
	public void setTransactionDetailId(int transactionDetailId) {
		this.transactionDetailId = transactionDetailId;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
		
	public String getToolingLotNumber() {
		return toolingLotNumber;
	}
	public void setToolingLotNumber(String toolingLotNumber) {
		this.toolingLotNumber = toolingLotNumber;
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

}
