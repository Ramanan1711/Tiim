package com.tiim.model;

;
public class ToolingIssueNote {
	
	public String getCheckedBy() {
		return checkedBy;
	}
	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}

	private int issueId;
	private String issueDate;
	private int requestNo;
	private String requestBy;
	private String requestDate;
	private String issueBy;
	private String checkedBy;
	private String branchName;
		
	/*private String[] toolingName;
	private String[] productName;
	private String[] machineName;
	private String[] drawingNo;
	private int[] batchQty;
	private int[] requestQty;*/
	private String[] toolingLotNumber;
	private String[] lastInspectionDate;
	private String[] nextDueQty;
	private String[] issuedQty;
	//private String[] UOM;
	private int[] requestDetailId;
	private int[] issueDetailId;
	
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
	private String serialNumber;
	
	//private int searchIssueId;
	private String searchRequestBy;
	
	private int originalId;
	private int revisionNumber;
	private String uploadPath;
	private int noOfDays;
	private int reportStatus;
	
	private String batchNumber;
	private String totalBatchQty;
	private int approvalFlag;
	private String dustCup;
	private String dustCup1;
	
		
	public String getDustCup() {
		return dustCup;
	}
	public void setDustCup(String dustCup) {
		this.dustCup = dustCup;
	}
	public String getDustCup1() {
		return dustCup1;
	}
	public void setDustCup1(String dustCup1) {
		this.dustCup1 = dustCup1;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
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
	public String getSearchRequestBy() {
		return searchRequestBy;
	}
	public void setSearchRequestBy(String searchRequestBy) {
		this.searchRequestBy = searchRequestBy;
	}
	/*public int getSearchIssueId() {
		return searchIssueId;
	}
	public void setSearchIssueId(int searchIssueId) {
		this.searchIssueId = searchIssueId;
	}*/
	
	public String getProductName1() {
		return productName1;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getTypeOfTooling1() {
		return typeOfTooling1;
	}
	public void setTypeOfTooling1(String typeOfTooling1) {
		this.typeOfTooling1 = typeOfTooling1;
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
	public int[] getIssueDetailId() {
		return issueDetailId;
	}
	public void setIssueDetailId(int[] issueDetailId) {
		this.issueDetailId = issueDetailId;
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
	public int getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(int requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestBy() {
		return requestBy;
	}
	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getIssueBy() {
		return issueBy;
	}
	public void setIssueBy(String issueBy) {
		this.issueBy = issueBy;
	}
	/*public String[] getToolingName() {
		return toolingName;
	}
	public void setToolingName(String[] toolingName) {
		this.toolingName = toolingName;
	}
	public String[] getProductName() {
		return productName;
	}
	public void setProductName(String[] productName) {
		this.productName = productName;
	}
	public String[] getMachineName() {
		return machineName;
	}
	public void setMachineName(String[] machineName) {
		this.machineName = machineName;
	}
	public String[] getDrawingNo() {
		return drawingNo;
	}
	public void setDrawingNo(String[] drawingNo) {
		this.drawingNo = drawingNo;
	}
	public int[] getBatchQty() {
		return batchQty;
	}
	public void setBatchQty(int[] batchQty) {
		this.batchQty = batchQty;
	}
	public int[] getRequestQty() {
		return requestQty;
	}
	public void setRequestQty(int[] requestQty) {
		this.requestQty = requestQty;
	}*/
	public String[] getToolingLotNumber() {
		return toolingLotNumber;
	}
	public void setToolingLotNumber(String[] toolingLotNumber) {
		this.toolingLotNumber = toolingLotNumber;
	}
	public String[] getLastInspectionDate() {
		return lastInspectionDate;
	}
	public void setLastInspectionDate(String[] lastInspectionDate) {
		this.lastInspectionDate = lastInspectionDate;
	}
	public String[] getNextDueQty() {
		return nextDueQty;
	}
	public void setNextDueQty(String[] nextDueQty) {
		this.nextDueQty = nextDueQty;
	}
	public String[] getIssuedQty() {
		return issuedQty;
	}
	public void setIssuedQty(String[] issuedQty) {
		this.issuedQty = issuedQty;
	}
	/*public String[] getUOM() {
		return UOM;
	}
	public void setUOM(String[] uOM) {
		UOM = uOM;
	}*/
	public int[] getRequestDetailId() {
		return requestDetailId;
	}
	public void setRequestDetailId(int[] requestDetailId) {
		this.requestDetailId = requestDetailId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	public int getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
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
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getTotalBatchQty() {
		return totalBatchQty;
	}
	public void setTotalBatchQty(String totalBatchQty) {
		this.totalBatchQty = totalBatchQty;
	}
	public int getApprovalFlag() {
		return approvalFlag;
	}
	public void setApprovalFlag(int approvalFlag) {
		this.approvalFlag = approvalFlag;
	}
}
