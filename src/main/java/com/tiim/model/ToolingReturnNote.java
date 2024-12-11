package com.tiim.model;

public class ToolingReturnNote {

	private int issueId;
	private String issueDate;
	private String issueBy;
	private int returnId;
	private String returnDate;
	private String returnBy;
	private String customerName;
	private String branchName;
	private String cleanedBy;

	public String getCleanedBy() {
		return cleanedBy;
	}

	public void setCleanedBy(String cleanedBy) {
		this.cleanedBy = cleanedBy;
	}

	/*
	 * private String[] toolingName; private String[] productName; private String[]
	 * machineName; private String[] drawingNo; private int[] batchQty; private
	 * long[] toolingLotNumber; private String[] lastInspectionDate; private int[]
	 * nextDueQty; private int[] issuedQty; private String[] UOM;
	 */
	private int[] returnDetailId;
	private String[] producedQty;
	private String[] returnQty;
	private String[] toolingStatus;
	private String[] batchNumber;
	private String[] typeOfTooling;

	private int goodQty;
	private int damagedQty;
	private int returnDetailId1;
	private String batchNumber1;
	private long producedQty1;
	private long returnQty1;
	private String toolingStatus1;
	private int toolingIssueDetailId[];
	private String searchIssuedBy;
	private int originalId;
	private int revisionNumber;
	private String damagedSerialNumber;
	private String rejectedSerialNumber;

	public int getOriginalId() {
		return originalId;
	}

	public void setOriginalId(int originalId) {
		this.originalId = originalId;
	}

	public int getRevisionNumber() {
		return revisionNumber;
	}

	public void setRevisionNumber(int revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	public int getGoodQty() {
		return goodQty;
	}

	public void setGoodQty(int goodQty) {
		this.goodQty = goodQty;
	}

	public int getDamagedQty() {
		return damagedQty;
	}

	public void setDamagedQty(int damagedQty) {
		this.damagedQty = damagedQty;
	}

	public String[] getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String[] batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getBatchNumber1() {
		return batchNumber1;
	}

	public void setBatchNumber1(String batchNumber1) {
		this.batchNumber1 = batchNumber1;
	}

	public String getSearchIssuedBy() {
		return searchIssuedBy;
	}

	public void setSearchIssuedBy(String searchIssuedBy) {
		this.searchIssuedBy = searchIssuedBy;
	}

	public int[] getToolingIssueDetailId() {
		return toolingIssueDetailId;
	}

	public void setToolingIssueDetailId(int[] toolingIssueDetailId) {
		this.toolingIssueDetailId = toolingIssueDetailId;
	}

	public int[] getReturnDetailId() {
		return returnDetailId;
	}

	public void setReturnDetailId(int[] returnDetailId) {
		this.returnDetailId = returnDetailId;
	}

	public String[] getProducedQty() {
		return producedQty;
	}

	public void setProducedQty(String[] producedQty) {
		this.producedQty = producedQty;
	}

	public String[] getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(String[] returnQty) {
		this.returnQty = returnQty;
	}

	public String[] getToolingStatus() {
		return toolingStatus;
	}

	public void setToolingStatus(String[] toolingStatus) {
		this.toolingStatus = toolingStatus;
	}

	public int getReturnDetailId1() {
		return returnDetailId1;
	}

	public void setReturnDetailId1(int returnDetailId1) {
		this.returnDetailId1 = returnDetailId1;
	}

	public String getToolingStatus1() {
		return toolingStatus1;
	}

	public void setToolingStatus1(String toolingStatus1) {
		this.toolingStatus1 = toolingStatus1;
	}

	private String typeOfTooling1;
	private String productName1;
	private String machineName1;
	private String drawingNo1;
	private long batchQty1;
	private long requestQty1;
	private String toolingLotNumber1;
	private String lastInspectionDate1;
	private long nextDueQty1;
	private long issuedQty1;
	private String UOM1;
	private int requestDetailId1;
	private int issueDetailId1;
	private String cleaningSOP;

	public String getTypeOfTooling1() {
		return typeOfTooling1;
	}

	public void setTypeOfTooling1(String typeOfTooling1) {
		this.typeOfTooling1 = typeOfTooling1;
	}

	public String getProductName1() {
		return productName1;
	}

	public void setProductName1(String productName1) {
		this.productName1 = productName1;
	}

	public String getMachineName1() {
		return machineName1;
	}

	public void setMachineName1(String machineName1) {
		this.machineName1 = machineName1;
	}

	public String getDrawingNo1() {
		return drawingNo1;
	}

	public void setDrawingNo1(String drawingNo1) {
		this.drawingNo1 = drawingNo1;
	}

	public String getToolingLotNumber1() {
		return toolingLotNumber1;
	}

	public void setToolingLotNumber1(String toolingLotNumber1) {
		this.toolingLotNumber1 = toolingLotNumber1;
	}

	public String getLastInspectionDate1() {
		return lastInspectionDate1;
	}

	public void setLastInspectionDate1(String lastInspectionDate1) {
		this.lastInspectionDate1 = lastInspectionDate1;
	}

	public long getProducedQty1() {
		return producedQty1;
	}

	public void setProducedQty1(long producedQty1) {
		this.producedQty1 = producedQty1;
	}

	public long getReturnQty1() {
		return returnQty1;
	}

	public void setReturnQty1(long returnQty1) {
		this.returnQty1 = returnQty1;
	}

	public long getBatchQty1() {
		return batchQty1;
	}

	public void setBatchQty1(long batchQty1) {
		this.batchQty1 = batchQty1;
	}

	public long getRequestQty1() {
		return requestQty1;
	}

	public void setRequestQty1(long requestQty1) {
		this.requestQty1 = requestQty1;
	}

	public long getNextDueQty1() {
		return nextDueQty1;
	}

	public void setNextDueQty1(long nextDueQty1) {
		this.nextDueQty1 = nextDueQty1;
	}

	public long getIssuedQty1() {
		return issuedQty1;
	}

	public void setIssuedQty1(long issuedQty1) {
		this.issuedQty1 = issuedQty1;
	}

	public String getUOM1() {
		return UOM1;
	}

	public void setUOM1(String uOM1) {
		UOM1 = uOM1;
	}

	public int getRequestDetailId1() {
		return requestDetailId1;
	}

	public void setRequestDetailId1(int requestDetailId1) {
		this.requestDetailId1 = requestDetailId1;
	}

	public int getIssueDetailId1() {
		return issueDetailId1;
	}

	public void setIssueDetailId1(int issueDetailId1) {
		this.issueDetailId1 = issueDetailId1;
	}

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getIssueBy() {
		return issueBy;
	}

	public void setIssueBy(String issueBy) {
		this.issueBy = issueBy;
	}
	/*
	 * public String[] getToolingName() { return toolingName; } public void
	 * setToolingName(String[] toolingName) { this.toolingName = toolingName; }
	 * public String[] getProductName() { return productName; } public void
	 * setProductName(String[] productName) { this.productName = productName; }
	 * public String[] getMachineName() { return machineName; } public void
	 * setMachineName(String[] machineName) { this.machineName = machineName; }
	 * public String[] getDrawingNo() { return drawingNo; } public void
	 * setDrawingNo(String[] drawingNo) { this.drawingNo = drawingNo; } public int[]
	 * getBatchQty() { return batchQty; } public void setBatchQty(int[] batchQty) {
	 * this.batchQty = batchQty; }
	 * 
	 * public long[] getToolingLotNumber() { return toolingLotNumber; } public void
	 * setToolingLotNumber(long[] toolingLotNumber) { this.toolingLotNumber =
	 * toolingLotNumber; } public String[] getLastInspectionDate() { return
	 * lastInspectionDate; } public void setLastInspectionDate(String[]
	 * lastInspectionDate) { this.lastInspectionDate = lastInspectionDate; } public
	 * int[] getNextDueQty() { return nextDueQty; } public void setNextDueQty(int[]
	 * nextDueQty) { this.nextDueQty = nextDueQty; } public int[] getIssuedQty() {
	 * return issuedQty; } public void setIssuedQty(int[] issuedQty) {
	 * this.issuedQty = issuedQty; } public String[] getUOM() { return UOM; } public
	 * void setUOM(String[] uOM) { UOM = uOM; }
	 */

	public int getReturnId() {
		return returnId;
	}

	public void setReturnId(int returnId) {
		this.returnId = returnId;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getReturnBy() {
		return returnBy;
	}

	public void setReturnBy(String returnBy) {
		this.returnBy = returnBy;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String[] getTypeOfTooling() {
		return typeOfTooling;
	}

	public void setTypeOfTooling(String[] typeOfTooling) {
		this.typeOfTooling = typeOfTooling;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getDamagedSerialNumber() {
		return damagedSerialNumber;
	}

	public void setDamagedSerialNumber(String damagedSerialNumber) {
		this.damagedSerialNumber = damagedSerialNumber;
	}

	public String getRejectedSerialNumber() {
		return rejectedSerialNumber;
	}

	public void setRejectedSerialNumber(String rejectedSerialNumber) {
		this.rejectedSerialNumber = rejectedSerialNumber;
	}

	private int approvedQty[];
	private int serialNumber1[];
	private int stockFlag;
	private int serialId[];

	public int[] getSerialNumber1() {
		return serialNumber1;
	}

	public void setSerialNumber1(int[] serialNumber1) {
		this.serialNumber1 = serialNumber1;
	}

	public int getStockFlag() {
		return stockFlag;
	}

	public void setStockFlag(int stockFlag) {
		this.stockFlag = stockFlag;
	}

	public int[] getApprovedQty() {
		return approvedQty;
	}

	public void setApprovedQty(int[] approvedQty) {
		this.approvedQty = approvedQty;
	}

	public int[] getSerialId() {
		return serialId;
	}

	public void setSerialId(int[] serialId) {
		this.serialId = serialId;
	}

	public String getCleaningSOP() {
		return cleaningSOP;
	}

	public void setCleaningSOP(String cleaningSOP) {
		this.cleaningSOP = cleaningSOP;
	}

}
