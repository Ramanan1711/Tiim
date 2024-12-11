package com.tiim.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class ToolingReceiptNote {

	private int toolingReceiptId;
	private int grnNo;
	private String grnDate;
	private String po;
	private String supplierCode;
	private String supplierName;
	private String productSerialNo;
	
	private int toolingProductId;
	private String toolingCodeNumber;
	private String productName;
	private String drawingNo;
	private String strength;
	private String machineType;
	private String typeOfTool;
	private String mocPunches;
	private String mocDies;
	private String shape;
	private String dimensions;
	private String breakLineUpper;
	private String breakLineLower;
	private String embosingUpper;
	private String embosingLower;
	private String laserMarking;
	private String hardCromePlating;
	private String dustCapGroove;
	private long poQuantity;
	private long receivedQuantity;
	private String toolingLotNumber;
	private int isActive;
	private String SearchProduct;
	private String searchToolingReceiptNote;
	private String uom;
	private int receiptStatus;
	private String branchName;
	
	private String dqDocument;
	private String mocNumber;
	private String inspectionReportNumber;

	
	private String toolingCodeNumberPO;
	private String productNamePO;
	private String drawingNoPO;
	private String machineTypePO;
	private String typeOfToolPO;
	private String mocPunchesPO;
	private String mocDiesPO;
	private String shapePO;
	private String dimensionsPO;
	private String breakLineUpperPO;
	private String breakLineLowerPO;
	private String embosingUpperPO;
	private String embosingLowerPO;
	private String laserMarkingPO;
	private String hardCromePlatingPO;
	private String dustCapGroovePO;
	
	private String storageLocation;
	private String mocUploadImage;
	private String dqUploadImage;
	private String inspectionUploadImage;
	private int upperQnty;
	private int lowerQnty;
	private int dieQnty;
	private String cleaningSOP;
	private long toolingLife;
	private long serviceIntervalQnty;
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
	public long getToolingLife() {
		return toolingLife;
	}
	public void setToolingLife(long toolingLife) {
		this.toolingLife = toolingLife;
	}
	public long getServiceIntervalQnty() {
		return serviceIntervalQnty;
	}
	public void setServiceIntervalQnty(long serviceIntervalQnty) {
		this.serviceIntervalQnty = serviceIntervalQnty;
	}
	private CommonsMultipartFile mocFile;
	private CommonsMultipartFile dqFile;
	private CommonsMultipartFile inspFile;
	
	//private Date expiryDate;
	//private String expiryDates;
	private Date manDate;
	private String manDates;
	private String uploadPath;
	private int minAcceptedQty;
	
	
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getManDates() {
		return manDates;
	}
	public void setManDates(String manDates) {
		this.manDates = manDates;
	}

	/*
	 * public String getExpiryDates() { return expiryDates; } public void
	 * setExpiryDates(String expiryDates) { this.expiryDates = expiryDates;
	 * 
	 * }
	 */
	public Date getManDate() {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		if(manDates != null)
		{
		String newDate = null;
		java.util.Date dtDob = new java.util.Date(manDates);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		
		try {
			java.util.Date date = formatter.parse(sdf.format(dtDob));
			setManDate(new java.sql.Date(date.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		}
		return manDate;
	}
	public void setManDate(Date manDate) {
		this.manDate = manDate;
	}

	/*
	 * public Date getExpiryDate() { SimpleDateFormat formatter = new
	 * SimpleDateFormat("dd-MM-yyyy");
	 * System.out.println("expiryDates: "+expiryDates);
	 * 
	 * String newDate = null; java.util.Date dtDob = new
	 * java.util.Date(expiryDates); SimpleDateFormat sdf = new
	 * SimpleDateFormat("dd-MM-yyyy");
	 * 
	 * 
	 * try { java.util.Date date = formatter.parse(sdf.format(dtDob));
	 * System.out.println(date); setExpiryDate(new java.sql.Date(date.getTime())); }
	 * catch (ParseException e) { // TODO Auto-generated catch block
	 * 
	 * e.printStackTrace(); } return expiryDate; } public void setExpiryDate(Date
	 * expiryDate) { this.expiryDate = expiryDate; }
	 */
	public String getToolingCodeNumberPO() {
		return toolingCodeNumberPO;
	}
	public void setToolingCodeNumberPO(String toolingCodeNumberPO) {
		this.toolingCodeNumberPO = toolingCodeNumberPO;
	}
	public String getProductNamePO() {
		return productNamePO;
	}
	public void setProductNamePO(String productNamePO) {
		this.productNamePO = productNamePO;
	}
	public String getDrawingNoPO() {
		return drawingNoPO;
	}
	public void setDrawingNoPO(String drawingNoPO) {
		this.drawingNoPO = drawingNoPO;
	}
	public String getMachineTypePO() {
		return machineTypePO;
	}
	public void setMachineTypePO(String machineTypePO) {
		this.machineTypePO = machineTypePO;
	}
	public String getTypeOfToolPO() {
		return typeOfToolPO;
	}
	public void setTypeOfToolPO(String typeOfToolPO) {
		this.typeOfToolPO = typeOfToolPO;
	}
	public String getMocPunchesPO() {
		return mocPunchesPO;
	}
	public void setMocPunchesPO(String mocPunchesPO) {
		this.mocPunchesPO = mocPunchesPO;
	}
	public String getMocDiesPO() {
		return mocDiesPO;
	}
	public void setMocDiesPO(String mocDiesPO) {
		this.mocDiesPO = mocDiesPO;
	}
	public String getShapePO() {
		return shapePO;
	}
	public void setShapePO(String shapePO) {
		this.shapePO = shapePO;
	}
	public String getDimensionsPO() {
		return dimensionsPO;
	}
	public void setDimensionsPO(String dimensionsPO) {
		this.dimensionsPO = dimensionsPO;
	}
	public String getBreakLineUpperPO() {
		return breakLineUpperPO;
	}
	public void setBreakLineUpperPO(String breakLineUpperPO) {
		this.breakLineUpperPO = breakLineUpperPO;
	}
	public String getBreakLineLowerPO() {
		return breakLineLowerPO;
	}
	public void setBreakLineLowerPO(String breakLineLowerPO) {
		this.breakLineLowerPO = breakLineLowerPO;
	}
	public String getEmbosingUpperPO() {
		return embosingUpperPO;
	}
	public void setEmbosingUpperPO(String embosingUpperPO) {
		this.embosingUpperPO = embosingUpperPO;
	}
	public String getEmbosingLowerPO() {
		return embosingLowerPO;
	}
	public void setEmbosingLowerPO(String embosingLowerPO) {
		this.embosingLowerPO = embosingLowerPO;
	}
	public String getLaserMarkingPO() {
		return laserMarkingPO;
	}
	public void setLaserMarkingPO(String laserMarkingPO) {
		this.laserMarkingPO = laserMarkingPO;
	}
	public String getHardCromePlatingPO() {
		return hardCromePlatingPO;
	}
	public void setHardCromePlatingPO(String hardCromePlatingPO) {
		this.hardCromePlatingPO = hardCromePlatingPO;
	}
	public String getDustCapGroovePO() {
		return dustCapGroovePO;
	}
	public void setDustCapGroovePO(String dustCapGroovePO) {
		this.dustCapGroovePO = dustCapGroovePO;
	}
	public String getToolingLotNumberPO() {
		return toolingLotNumberPO;
	}
	public void setToolingLotNumberPO(String toolingLotNumberPO) {
		this.toolingLotNumberPO = toolingLotNumberPO;
	}
	public String getUomPO() {
		return uomPO;
	}
	public void setUomPO(String uomPO) {
		this.uomPO = uomPO;
	}
	public String getDqDocumentPO() {
		return dqDocumentPO;
	}
	public void setDqDocumentPO(String dqDocumentPO) {
		this.dqDocumentPO = dqDocumentPO;
	}
	public String getMocNumberPO() {
		return mocNumberPO;
	}
	public void setMocNumberPO(String mocNumberPO) {
		this.mocNumberPO = mocNumberPO;
	}
	public String getInspectionReportNumberPO() {
		return inspectionReportNumberPO;
	}
	public void setInspectionReportNumberPO(String inspectionReportNumberPO) {
		this.inspectionReportNumberPO = inspectionReportNumberPO;
	}
	private String toolingLotNumberPO;
	private String uomPO;
	
	private String dqDocumentPO;
	private String mocNumberPO;
	private String inspectionReportNumberPO;
	
		
	public String getToolingCodeNumber() {
		return toolingCodeNumber;
	}
	public void setToolingCodeNumber(String toolingCodeNumber) {
		this.toolingCodeNumber = toolingCodeNumber;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getInspectionReportNumber() {
		return inspectionReportNumber;
	}
	public void setInspectionReportNumber(String inspectionReportNumber) {
		this.inspectionReportNumber = inspectionReportNumber;
	}
	public String getDqDocument() {
		return dqDocument;
	}
	public void setDqDocument(String dqDocument) {
		this.dqDocument = dqDocument;
	}
	public String getMocNumber() {
		return mocNumber;
	}
	public void setMocNumber(String mocNumber) {
		this.mocNumber = mocNumber;
	}
	public int getReceiptStatus() {
		return receiptStatus;
	}
	public void setReceiptStatus(int receiptStatus) {
		this.receiptStatus = receiptStatus;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public int getToolingProductId() {
		return toolingProductId;
	}
	public void setToolingProductId(int toolingProductId) {
		this.toolingProductId = toolingProductId;
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
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
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
	public String getMocPunches() {
		return mocPunches;
	}
	public void setMocPunches(String mocPunches) {
		this.mocPunches = mocPunches;
	}
	public String getMocDies() {
		return mocDies;
	}
	public void setMocDies(String mocDies) {
		this.mocDies = mocDies;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	public String getBreakLineUpper() {
		return breakLineUpper;
	}
	public void setBreakLineUpper(String breakLineUpper) {
		this.breakLineUpper = breakLineUpper;
	}
	public String getBreakLineLower() {
		return breakLineLower;
	}
	public void setBreakLineLower(String breakLineLower) {
		this.breakLineLower = breakLineLower;
	}
	public String getEmbosingUpper() {
		return embosingUpper;
	}
	public void setEmbosingUpper(String embosingUpper) {
		this.embosingUpper = embosingUpper;
	}
	public String getEmbosingLower() {
		return embosingLower;
	}
	public void setEmbosingLower(String embosingLower) {
		this.embosingLower = embosingLower;
	}
	public String getLaserMarking() {
		return laserMarking;
	}
	public void setLaserMarking(String laserMarking) {
		this.laserMarking = laserMarking;
	}
	public String getHardCromePlating() {
		return hardCromePlating;
	}
	public void setHardCromePlating(String hardCromePlating) {
		this.hardCromePlating = hardCromePlating;
	}
	public String getDustCapGroove() {
		return dustCapGroove;
	}
	public void setDustCapGroove(String dustCapGroove) {
		this.dustCapGroove = dustCapGroove;
	}
	

	public long getPoQuantity() {
		return poQuantity;
	}
	public void setPoQuantity(long poQuantity) {
		this.poQuantity = poQuantity;
	}
	public long getReceivedQuantity() {
		return receivedQuantity;
	}
	public void setReceivedQuantity(long receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	public String getToolingLotNumber() {
		return toolingLotNumber;
	}
	public void setToolingLotNumber(String toolingLotNumber) {
		this.toolingLotNumber = toolingLotNumber;
	}
	public int getToolingReceiptId() {
		return toolingReceiptId;
	}
	public void setToolingReceiptId(int toolingReceiptId) {
		this.toolingReceiptId = toolingReceiptId;
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
	public String getPo() {
		return po;
	}
	public void setPo(String po) {
		this.po = po;
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
	public String getProductSerialNo() {
		return productSerialNo;
	}
	public void setProductSerialNo(String productSerialNo) {
		this.productSerialNo = productSerialNo;
	}
	
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
	
	
	public String getSearchToolingReceiptNote() {
		return searchToolingReceiptNote;
	}
	public void setSearchToolingReceiptNote(String searchToolingReceiptNote) {
		this.searchToolingReceiptNote = searchToolingReceiptNote;
	}
	public String getSearchProduct() {
		return SearchProduct;
	}
	public void setSearchProduct(String searchProduct) {
		SearchProduct = searchProduct;
	}
	public String getStorageLocation() {
		return storageLocation;
	}
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}
	public String getMocUploadImage() {
		return mocUploadImage;
	}
	public void setMocUploadImage(String mocUploadImage) {
		this.mocUploadImage = mocUploadImage;
	}
	public String getDqUploadImage() {
		return dqUploadImage;
	}
	public void setDqUploadImage(String dqUploadImage) {
		this.dqUploadImage = dqUploadImage;
	}
	public String getInspectionUploadImage() {
		return inspectionUploadImage;
	}
	public void setInspectionUploadImage(String inspectionUploadImage) {
		this.inspectionUploadImage = inspectionUploadImage;
	}
	public int getUpperQnty() {
		return upperQnty;
	}
	public void setUpperQnty(int upperQnty) {
		this.upperQnty = upperQnty;
	}
	public int getLowerQnty() {
		return lowerQnty;
	}
	public void setLowerQnty(int lowerQnty) {
		this.lowerQnty = lowerQnty;
	}
	
	public String getCleaningSOP() {
		return cleaningSOP;
	}
	public void setCleaningSOP(String cleaningSOP) {
		this.cleaningSOP = cleaningSOP;
	}
	public int getDieQnty() {
		return dieQnty;
	}
	public void setDieQnty(int dieQnty) {
		this.dieQnty = dieQnty;
	}
	public CommonsMultipartFile getMocFile() {
		return mocFile;
	}
	public void setMocFile(CommonsMultipartFile mocFile) {
		this.mocFile = mocFile;
	}
	public CommonsMultipartFile getDqFile() {
		return dqFile;
	}
	public void setDqFile(CommonsMultipartFile dqFile) {
		this.dqFile = dqFile;
	}
	public CommonsMultipartFile getInspFile() {
		return inspFile;
	}
	public void setInspFile(CommonsMultipartFile inspFile) {
		this.inspFile = inspFile;
	}
	public int getMinAcceptedQty() {
		return minAcceptedQty;
	}
	public void setMinAcceptedQty(int minAcceptedQty) {
		this.minAcceptedQty = minAcceptedQty;
	}

}
