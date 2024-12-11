package com.tiim.model;

public class ToolingRequest {

	private int toolingRequestId;
	private String inspectionReportNo;
	private String inspectionReportDate;
	private int grnNo;
	private String grnDate;
	private String supplierCode;
	private String supplierName;
	private int toolingReceiptId;
	
	private int toolingRequestDetailId[];
	private String toolingname;
	private String poNumber;
	private String poDate;
	private String drawingNo;
	private String lotNumber;
	private long receivedQuantity;
	private String uom;
	//private String[] inspectionStatus;
	private String[] remarks;
	private int[] toolingProductId;
	
	private String machineType;
	private String typeOfTool;
	
	//private String inspectionStatus1;
	private String remarks1;
	private int toolingProductId1;
	private int toolingRequestDetailId1;
	private String toolingSearch;
	private String branchName;
	private int originalId;
	private int revisionNumber;
	private String uploadPath;
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
	private int reportStatus;
	
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public int getRevisionNumber() {
		return revisionNumber;
	}
	public void setRevisionNumber(int revisionNumber) {
		this.revisionNumber = revisionNumber;
	}
	public int getOriginalId() {
		return originalId;
	}
	public void setOriginalId(int originalId) {
		this.originalId = originalId;
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
	
	public String getTypeOfTool() {
		return typeOfTool;
	}
	public void setTypeOfTool(String typeOfTool) {
		this.typeOfTool = typeOfTool;
	}
	public String getToolingSearch() {
		return toolingSearch;
	}
	public void setToolingSearch(String toolingSearch) {
		this.toolingSearch = toolingSearch;
	}
	public int getToolingRequestDetailId1() {
		return toolingRequestDetailId1;
	}
	public void setToolingRequestDetailId1(int toolingRequestDetailId1) {
		this.toolingRequestDetailId1 = toolingRequestDetailId1;
	}
	/*public String getInspectionStatus1() {
		return inspectionStatus1;
	}
	public void setInspectionStatus1(String inspectionStatus1) {
		this.inspectionStatus1 = inspectionStatus1;
	}*/
	public String getRemarks1() {
		return remarks1;
	}
	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}
	public int getToolingProductId1() {
		return toolingProductId1;
	}
	public void setToolingProductId1(int toolingProductId1) {
		this.toolingProductId1 = toolingProductId1;
	}
	public int[] getToolingRequestDetailId() {
		return toolingRequestDetailId;
	}
	public void setToolingRequestDetailId(int[] toolingRequestDetailId) {
		this.toolingRequestDetailId = toolingRequestDetailId;
	}
	public String getToolingname() {
		return toolingname;
	}
	public void setToolingname(String toolingname) {
		this.toolingname = toolingname;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
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
	
	/*public String[] getInspectionStatus() {
		return inspectionStatus;
	}
	public void setInspectionStatus(String[] inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}*/
	public String[] getRemarks() {
		return remarks;
	}
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	public int[] getToolingProductId() {
		return toolingProductId;
	}
	public void setToolingProductId(int[] toolingProductId) {
		this.toolingProductId = toolingProductId;
	}
	public int getToolingRequestId() {
		return toolingRequestId;
	}
	public void setToolingRequestId(int toolingRequestId) {
		this.toolingRequestId = toolingRequestId;
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
	public int getGrnNo() {
		return grnNo;
	}
	public void setGrnNo(int grnNo) {
		this.grnNo = grnNo;
	}
	public String getGrnDate() {
		return grnDate;
	}
	public void setGrnDate(String grnDate) {
		this.grnDate = grnDate;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public int getToolingReceiptId() {
		return toolingReceiptId;
	}
	public void setToolingReceiptId(int toolingReceiptId) {
		this.toolingReceiptId = toolingReceiptId;
	}
	public int getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}
}
