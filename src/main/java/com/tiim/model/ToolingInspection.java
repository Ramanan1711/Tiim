package com.tiim.model;


public class ToolingInspection {

	private int toolingInspectionId;
	private String inspectionReportNo;
	private String inspectionReportDate;
	private int requestId;
	private String requestDate;
	
	private int toolingInspectionDetailId[];
	private String toolingname;
	/*private String poNumber;
	private String poDate;*/
	private String drawingNo;
	private String lotNumber;
	private long receivedQuantity;
	private String uom;
	private String[] inspectionStatus;
	private String[] remarks;
	private int[] toolingRequestDetailId;
	private String machineType;
	private String typeOfTooling;
	private String branchName;
	private int rejectedQty;
	private int acceptedQty;
	private int originalId;
	private int revisionNumber;
	private String rejectedSerialNumber;	
	private int approvedQty[];
	private int serialNumber1[];
	private int stockFlag;
	private int serialId[];
	private String punchSetNo;
	private int compForce;
	
	
	public String getPunchSetNo() {
		return punchSetNo;
	}
	public void setPunchSetNo(String punchSetNo) {
		this.punchSetNo = punchSetNo;
	}
	public int getCompForce() {
		return compForce;
	}
	public void setCompForce(int compForce) {
		this.compForce = compForce;
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
	public int getRejectedQty() {
		return rejectedQty;
	}
	public void setRejectedQty(int rejectedQty) {
		this.rejectedQty = rejectedQty;
	}
	public int getAcceptedQty() {
		return acceptedQty;
	}
	public void setAcceptedQty(int acceptedQty) {
		this.acceptedQty = acceptedQty;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String getTypeOfTooling() {
		return typeOfTooling;
	}
	public void setTypeOfTooling(String typeOfTooling) {
		this.typeOfTooling = typeOfTooling;
	}
	private String inspectionStatus1;
	private String remarks1;
	private int toolingRequestDetailId1;
	private int toolingInspectionDetailId1;
	
	private String toolingSearch;
	
	public String getToolingSearch() {
		return toolingSearch;
	}
	public void setToolingSearch(String toolingSearch) {
		this.toolingSearch = toolingSearch;
	}
	public int getToolingInspectionDetailId1() {
		return toolingInspectionDetailId1;
	}
	public void setToolingInspectionDetailId1(int toolingInspectionDetailId1) {
		this.toolingInspectionDetailId1 = toolingInspectionDetailId1;
	}
	public String getInspectionStatus1() {
		return inspectionStatus1;
	}
	public void setInspectionStatus1(String inspectionStatus1) {
		this.inspectionStatus1 = inspectionStatus1;
	}
	public String getRemarks1() {
		return remarks1;
	}
	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}
	
	public int getToolingRequestDetailId1() {
		return toolingRequestDetailId1;
	}
	public void setToolingRequestDetailId1(int toolingRequestDetailId1) {
		this.toolingRequestDetailId1 = toolingRequestDetailId1;
	}
	public int[] getToolingInspectionDetailId() {
		return toolingInspectionDetailId;
	}
	public void setToolingInspectionDetailId(int[] toolingInspectionDetailId) {
		this.toolingInspectionDetailId = toolingInspectionDetailId;
	}
	public String getToolingname() {
		return toolingname;
	}
	public void setToolingname(String toolingname) {
		this.toolingname = toolingname;
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
	
	public long getReceivedQuantity() {
		return receivedQuantity;
	}
	public void setReceivedQuantity(long receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	public String[] getInspectionStatus() {
		return inspectionStatus;
	}
	public void setInspectionStatus(String[] inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}
	public String[] getRemarks() {
		return remarks;
	}
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	public int[] getToolingRequestDetailId() {
		return toolingRequestDetailId;
	}
	public void setToolingRequestDetailId(int[] toolingRequestDetailId) {
		this.toolingRequestDetailId = toolingRequestDetailId;
	}

	public int getToolingInspectionId() {
		return toolingInspectionId;
	}
	public void setToolingInspectionId(int toolingInspectionId) {
		this.toolingInspectionId = toolingInspectionId;
	}
	public String getInspectionReportNo() {
		return inspectionReportNo;
	}
	public void setInspectionReportNo(String inspectionReportNo) {
		this.inspectionReportNo = inspectionReportNo;
	}
	public String getInspectionReportDate() {
		return inspectionReportDate;
	}
	public void setInspectionReportDate(String inspectionReportDate) {
		this.inspectionReportDate = inspectionReportDate;
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
	public String getRejectedSerialNumber() {
		return rejectedSerialNumber;
	}
	public void setRejectedSerialNumber(String rejectedSerialNumber) {
		this.rejectedSerialNumber = rejectedSerialNumber;
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
}
