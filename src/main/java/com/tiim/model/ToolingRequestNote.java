package com.tiim.model;

public class ToolingRequestNote {
	
	private int requestId;
	private String requestDate;
	private String requestBy;
	
	private String[] requestDetailId;
	private String[] productName;
	private String[] drawingNo;
	private String[] machingType;
	private String[] UOM;
	private String[] batchQty;
	private String[] requestQty;
	private String[] inStock;
	private String[] underInspection;
	private String[] typeOfTool;
	private String[] toolingLotNumber;
	private String[] batchnos;
	private String[] batchProd;
	private String[] market;
	private String dustCup;
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
	private int requestDetailId1;
	private String productName1;
	private String drawingNo1;
	private String machingType1;
	private String UOM1;
	private String toolingLotNumber1;
	private String batchQty1;
	private String requestQty1;
	private String inStock1;
	private String underInspection1;
	private String searchRequestBy;
	private String typeOfTool1;
	private String branchName;
	private String lastInspectionDate1;
	private long nextDueQty;
	private int originalId;
	private int revisionNumber;
	private String uploadPath;
	private int reportStatus;
	private String batchnos1;
	private String batchProd1;
	private String market1;
	
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
	private String dustCup1;

	public String[] getBatchnos() {
		return batchnos;
	}
	public String[] getBatchProd() {
		return batchProd;
	}
	public void setBatchProd(String[] batchProd) {
		this.batchProd = batchProd;
	}
	public String getBatchProd1() {
		return batchProd1;
	}
	public void setBatchProd1(String batchProd1) {
		this.batchProd1 = batchProd1;
	}
	public void setBatchnos(String[] batchnos) {
		this.batchnos = batchnos;
	}
	
	
	public String[] getMarket() {
		return market;
	}
	public void setMarket(String[] market) {
		this.market = market;
	}
	public String getBatchnos1() {
		return batchnos1;
	}
	public void setBatchnos1(String batchnos1) {
		this.batchnos1 = batchnos1;
	}

	
	public String getMarket1() {
		return market1;
	}
	public void setMarket1(String market1) {
		this.market1 = market1;
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
	public long getNextDueQty() {
		return nextDueQty;
	}
	public void setNextDueQty(long nextDueQty) {
		this.nextDueQty = nextDueQty;
	}
	public String getLastInspectionDate1() {
		return lastInspectionDate1;
	}
	public void setLastInspectionDate1(String lastInspectionDate1) {
		this.lastInspectionDate1 = lastInspectionDate1;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String[] getTypeOfTool() {
		return typeOfTool;
	}
	public void setTypeOfTool(String[] typeOfTool) {
		this.typeOfTool = typeOfTool;
	}
	public String getTypeOfTool1() {
		return typeOfTool1;
	}
	public void setTypeOfTool1(String typeOfTool1) {
		this.typeOfTool1 = typeOfTool1;
	}
	public String getSearchRequestBy() {
		return searchRequestBy;
	}
	public void setSearchRequestBy(String searchRequestBy) {
		this.searchRequestBy = searchRequestBy;
	}
	public int getRequestDetailId1() {
		return requestDetailId1;
	}
	public void setRequestDetailId1(int requestDetailId1) {
		this.requestDetailId1 = requestDetailId1;
	}
	/*public String[] getToolingLotNumber() {
		return toolingLotNumber;
	}*/
	/*public void setToolingLotNumber(String[] toolingLotNumber) {
		this.toolingLotNumber = toolingLotNumber;
	}*/
	public String getToolingLotNumber1() {
		return toolingLotNumber1;
	}
	public void setToolingLotNumber1(String toolingLotNumber1) {
		this.toolingLotNumber1 = toolingLotNumber1;
	}
	public String getProductName1() {
		return productName1;
	}
	public void setProductName1(String productName1) {
		this.productName1 = productName1;
	}
	public String getDrawingNo1() {
		return drawingNo1;
	}
	public void setDrawingNo1(String drawingNo1) {
		this.drawingNo1 = drawingNo1;
	}
	public String getMachingType1() {
		return machingType1;
	}
	public void setMachingType1(String machingType1) {
		this.machingType1 = machingType1;
	}
	public String getUOM1() {
		return UOM1;
	}
	public void setUOM1(String uOM1) {
		UOM1 = uOM1;
	}
	

	
	public String getBatchQty1() {
		return batchQty1;
	}
	public void setBatchQty1(String batchQty1) {
		this.batchQty1 = batchQty1;
	}
	public String getRequestQty1() {
		return requestQty1;
	}
	public void setRequestQty1(String requestQty1) {
		this.requestQty1 = requestQty1;
	}
	public String getInStock1() {
		return inStock1;
	}
	public void setInStock1(String inStock1) {
		this.inStock1 = inStock1;
	}
	public String getUnderInspection1() {
		return underInspection1;
	}
	public void setUnderInspection1(String underInspection1) {
		this.underInspection1 = underInspection1;
	}
	private int isActive;
	
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
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
	public String getRequestBy() {
		return requestBy;
	}
	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}
	public String[] getRequestDetailId() {
		return requestDetailId;
	}
	public void setRequestDetailId(String[] requestDetailId) {
		this.requestDetailId = requestDetailId;
	}
	public String[] getProductName() {
		return productName;
	}
	public void setProductName(String[] productName) {
		this.productName = productName;
	}
	public String[] getDrawingNo() {
		return drawingNo;
	}
	public void setDrawingNo(String[] drawingNo) {
		this.drawingNo = drawingNo;
	}
	public String[] getMachingType() {
		return machingType;
	}
	public void setMachingType(String[] machingType) {
		this.machingType = machingType;
	}
	public String[] getUOM() {
		return UOM;
	}
	public void setUOM(String[] uOM) {
		UOM = uOM;
	}
	public String[] getBatchQty() {
		return batchQty;
	}
	public void setBatchQty(String[] batchQty) {
		this.batchQty = batchQty;
	}
	public String[] getRequestQty() {
		return requestQty;
	}
	public void setRequestQty(String[] requestQty) {
		this.requestQty = requestQty;
	}
	public String[] getInStock() {
		return inStock;
	}
	public void setInStock(String[] inStock) {
		this.inStock = inStock;
	}
	public String[] getUnderInspection() {
		return underInspection;
	}
	public void setUnderInspection(String[] underInspection) {
		this.underInspection = underInspection;
	}
	public int getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String[] getToolingLotNumber() {
		// TODO Auto-generated method stub
		return toolingLotNumber;
	}
	public void setToolingLotNumber(String[] toolingLotNumber) {
		// TODO Auto-generated method stub
		this.toolingLotNumber= toolingLotNumber;
	}
	public void SetMarket1(String market1) {
		// TODO Auto-generated method stub
		this.market1= market1;
	}
	
		
	}

	

