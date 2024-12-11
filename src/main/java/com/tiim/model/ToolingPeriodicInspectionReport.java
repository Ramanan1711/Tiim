package com.tiim.model;


public class ToolingPeriodicInspectionReport {
	
	private int requestNo;
	private String requestDate;
	private String requestedBy;
	private int reportNo;
	private String reportDate;
	private String reportedBy;
	
	private int toolingDetailId[];
	private String typeOfTool;
	private String productName;
	private String drawingNo;
	private String lotNumber;
	private long producedQuantity;
	private String uom;
	private String toolingStatus;
	private String machineType;	
	
	private int[] reportDetailNo;
	private String[] intervalQty;
	private String[] goodIntervalQty;
	private String[] damagedIntervalQty;
	private String[] inspectionStatusAfterInspection;
	private String[] condition;
	private String[] remark;
	
	private int reportDetailNo1;
	private long intervalQty1;
	private long damagedIntervalQty1;
	private long goodIntervalQty1;
	private String inspectionStatusAfterInspection1;
	private String condition1;
	private String remark1;
	private String searchReportNo;
	private String requestDetailId;
	private String branchName;
	
	private String toolingLotNumber;
	
	private int toolingRequestQty;
	private int requestQuantity;
	private int originalId;
	private int revisionNumber;
	
	private String damagedSerialNumber;
	
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
	public String[] getGoodIntervalQty() {
		return goodIntervalQty;
	}
	public void setGoodIntervalQty(String[] goodIntervalQty) {
		this.goodIntervalQty = goodIntervalQty;
	}
	public long getGoodIntervalQty1() {
		return goodIntervalQty1;
	}
	public void setGoodIntervalQty1(long goodIntervalQty1) {
		this.goodIntervalQty1 = goodIntervalQty1;
	}
	public String[] getDamagedIntervalQty() {
		return damagedIntervalQty;
	}
	public void setDamagedIntervalQty(String[] damagedIntervalQty) {
		this.damagedIntervalQty = damagedIntervalQty;
	}
	public long getDamagedIntervalQty1() {
		return damagedIntervalQty1;
	}
	public void setDamagedIntervalQty1(long damagedIntervalQty1) {
		this.damagedIntervalQty1 = damagedIntervalQty1;
	}
	public int getRequestQuantity() {
		return requestQuantity;
	}
	public void setRequestQuantity(int requestQuantity) {
		this.requestQuantity = requestQuantity;
	}
	public int getToolingRequestQty() {
		return toolingRequestQty;
	}
	public void setToolingRequestQty(int toolingRequestQty) {
		this.toolingRequestQty = toolingRequestQty;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getRequestDetailId() {
		return requestDetailId;
	}
	public void setRequestDetailId(String requestDetailId) {
		this.requestDetailId = requestDetailId;
	}
	public String getSearchReportNo() {
		return searchReportNo;
	}
	public void setSearchReportNo(String searchReportNo) {
		this.searchReportNo = searchReportNo;
	}
	public int[] getReportDetailNo() {
		return reportDetailNo;
	}
	public void setReportDetailNo(int[] reportDetailNo) {
		this.reportDetailNo = reportDetailNo;
	}
	public int getReportDetailNo1() {
		return reportDetailNo1;
	}
	public void setReportDetailNo1(int reportDetailNo1) {
		this.reportDetailNo1 = reportDetailNo1;
	}
	public String getToolingStatus() {
		return toolingStatus;
	}
	
	public int getReportNo() {
		return reportNo;
	}
	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getReportedBy() {
		return reportedBy;
	}
	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}
	public String[] getIntervalQty() {
		return intervalQty;
	}
	public void setIntervalQty(String[] intervalQty) {
		this.intervalQty = intervalQty;
	}
	public String[] getInspectionStatusAfterInspection() {
		return inspectionStatusAfterInspection;
	}
	public void setInspectionStatusAfterInspection(
			String[] inspectionStatusAfterInspection) {
		this.inspectionStatusAfterInspection = inspectionStatusAfterInspection;
	}
	public String[] getCondition() {
		return condition;
	}
	public void setCondition(String[] condition) {
		this.condition = condition;
	}
	public String[] getRemark() {
		return remark;
	}
	public void setRemark(String[] remark) {
		this.remark = remark;
	}
	
	public long getIntervalQty1() {
		return intervalQty1;
	}
	public void setIntervalQty1(long intervalQty1) {
		this.intervalQty1 = intervalQty1;
	}
	public void setProducedQuantity(long producedQuantity) {
		this.producedQuantity = producedQuantity;
	}
	public String getInspectionStatusAfterInspection1() {
		return inspectionStatusAfterInspection1;
	}
	public void setInspectionStatusAfterInspection1(
			String inspectionStatusAfterInspection1) {
		this.inspectionStatusAfterInspection1 = inspectionStatusAfterInspection1;
	}
	public String getCondition1() {
		return condition1;
	}
	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public void setToolingStatus(String toolingStatus) {
		this.toolingStatus = toolingStatus;
	}
		
	public int[] getToolingDetailId() {
		return toolingDetailId;
	}
	public void setToolingDetailId(int[] toolingDetailId) {
		this.toolingDetailId = toolingDetailId;
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
	
	public long getProducedQuantity() {
		return producedQuantity;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getDamagedSerialNumber() {
		return damagedSerialNumber;
	}
	public void setDamagedSerialNumber(String damagedSerialNumber) {
		this.damagedSerialNumber = damagedSerialNumber;
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
	public String getToolingLotNumber() {
		return toolingLotNumber;
	}
	public void setToolingLotNumber(String toolingLotNumber) {
		this.toolingLotNumber = toolingLotNumber;
	}
	
	
}
