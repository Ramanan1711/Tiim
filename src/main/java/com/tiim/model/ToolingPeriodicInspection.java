package com.tiim.model;

public class ToolingPeriodicInspection {
	private int requestNo;
	private String requestDate;
	private String requestedBy;
	
	private int toolingDetailId[];
	private String machineType;
	private String productName;
	private String drawingNo;
	private String lotNumber;
	private long tabProducedQty[];
	private String uom;
	private String[] toolingLotNo;
	private String[] toolingStatus;
	private String[] toolingHistory;
	private String[] toolingDueQty;
	private String typeOfTool;	
	private int requestQuantity;
	private int stockQty;
	private int originalId;
	private int revisionNumber;
	private String uploadPath;
	private int reportStatus;
	
	private int approvedQty[];
	private int serialNumber1[];
	private int stockFlag;
	private int serialId[];
	
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
	public int getStockQty() {
		return stockQty;
	}
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}
	public int getRequestQuantity() {
		return requestQuantity;
	}
	public void setRequestQuantity(int requestQuantity) {
		this.requestQuantity = requestQuantity;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String getTypeOfTool() {
		return typeOfTool;
	}
	public void setTypeOfTool(String typeOfTool) {
		this.typeOfTool = typeOfTool;
	}
	private long tabProducedQty1;
	private String toolingStatus1;
	private String toolingHistory1;
	private long inspectionDueQty1;
	private String toolingSerialNo1;
	private int toolingDetailId1;
	private String stockId1;
	private String branchName;
	
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getStockId1() {
		return stockId1;
	}
	public void setStockId1(String stockId1) {
		this.stockId1 = stockId1;
	}
	private String searchRequestNo;
	
	public String getSearchRequestNo() {
		return searchRequestNo;
	}
	public void setSearchRequestNo(String searchRequestNo) {
		this.searchRequestNo = searchRequestNo;
	}
	private String[] stockId;
	
	
	public int getToolingDetailId1() {
		return toolingDetailId1;
	}
	public void setToolingDetailId1(int toolingDetailId1) {
		this.toolingDetailId1 = toolingDetailId1;
	}
	public int[] getToolingDetailId() {
		return toolingDetailId;
	}
	public void setToolingDetailId(int[] toolingDetailId) {
		this.toolingDetailId = toolingDetailId;
	}
	public String[] getStockId() {
		return stockId;
	}
	public void setStockId(String[] stockId) {
		this.stockId = stockId;
	}
	public int getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(int requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
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
	
	
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String[] getToolingStatus() {
		return toolingStatus;
	}
	public void setToolingStatus(String[] toolingStatus) {
		this.toolingStatus = toolingStatus;
	}
	public String[] getToolingHistory() {
		return toolingHistory;
	}
	public void setToolingHistory(String[] toolingHistory) {
		this.toolingHistory = toolingHistory;
	}
	
	
	public String[] getToolingDueQty() {
		return toolingDueQty;
	}
	public void setToolingDueQty(String[] toolingDueQty) {
		this.toolingDueQty = toolingDueQty;
	}
	
	public String getToolingStatus1() {
		return toolingStatus1;
	}
	public void setToolingStatus1(String toolingStatus1) {
		this.toolingStatus1 = toolingStatus1;
	}
	public String getToolingHistory1() {
		return toolingHistory1;
	}
	public void setToolingHistory1(String toolingHistory1) {
		this.toolingHistory1 = toolingHistory1;
	}
	
	public long[] getTabProducedQty() {
		return tabProducedQty;
	}
	public void setTabProducedQty(long[] tabProducedQty) {
		this.tabProducedQty = tabProducedQty;
	}
	public long getTabProducedQty1() {
		return tabProducedQty1;
	}
	public void setTabProducedQty1(long tabProducedQty1) {
		this.tabProducedQty1 = tabProducedQty1;
	}
	public long getInspectionDueQty1() {
		return inspectionDueQty1;
	}
	public void setInspectionDueQty1(long inspectionDueQty1) {
		this.inspectionDueQty1 = inspectionDueQty1;
	}
	public String getToolingSerialNo1() {
		return toolingSerialNo1;
	}
	public void setToolingSerialNo1(String toolingSerialNo1) {
		this.toolingSerialNo1 = toolingSerialNo1;
	}
	public String[] getToolingLotNo() {
		return toolingLotNo;
	}
	public void setToolingLotNo(String[] toolingLotNo) {
		this.toolingLotNo = toolingLotNo;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public int getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}
	public int[] getApprovedQty() {
		return approvedQty;
	}
	public void setApprovedQty(int[] approvedQty) {
		this.approvedQty = approvedQty;
	}
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
	public int[] getSerialId() {
		return serialId;
	}
	public void setSerialId(int[] serialId) {
		this.serialId = serialId;
	}
}
