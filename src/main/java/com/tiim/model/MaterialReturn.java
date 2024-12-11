package com.tiim.model;



public class MaterialReturn {
	private int returnNo;
	private String returnDate;
	private int materialCode;
	private String lotNumber;
	private String materialName;
	private int materialQty;
	private String uom;
	private String returnBy;
	private String receivedBy;
	private String remark;
	private String materialType;
	private int toolIssueNo;
	private int issuedQty;

	/**
	 * @return the issuedQty
	 */
	public int getIssuedQty() {
		return issuedQty;
	}
	/**
	 * @param issuedQty the issuedQty to set
	 */
	public void setIssuedQty(int issuedQty) {
		this.issuedQty = issuedQty;
	}

	/**
	 * @return the materialCode
	 */
	public int getMaterialCode() {
		return materialCode;
	}
	/**
	 * @param materialCode the materialCode to set
	 */
	public void setMaterialCode(int materialCode) {
		this.materialCode = materialCode;
	}
	/**
	 * @return the lotNumber
	 */
	public String getLotNumber() {
		return lotNumber;
	}
	/**
	 * @param lotNumber the lotNumber to set
	 */
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	/**
	 * @return the materialName
	 */
	public String getMaterialName() {
		return materialName;
	}
	/**
	 * @param materialName the materialName to set
	 */
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	/**
	 * @return the materialQty
	 */
	public int getMaterialQty() {
		return materialQty;
	}
	/**
	 * @param materialQty the materialQty to set
	 */
	public void setMaterialQty(int materialQty) {
		this.materialQty = materialQty;
	}
	/**
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}
	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the materialType
	 */
	public String getMaterialType() {
		return materialType;
	}
	/**
	 * @param materialType the materialType to set
	 */
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	/**
	 * @return the retunNo
	 */
	public int getReturnNo() {
		return returnNo;
	}
	/**
	 * @param retunNo the retunNo to set
	 */
	public void setReturnNo(int returnNo) {
		this.returnNo = returnNo;
	}
	/**
	 * @return the returnDate
	 */
	public String getReturnDate() {
		return returnDate;
	}
	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	/**
	 * @return the returnBy
	 */
	public String getReturnBy() {
		return returnBy;
	}
	/**
	 * @param returnBy the returnBy to set
	 */
	public void setReturnBy(String returnBy) {
		this.returnBy = returnBy;
	}
	/**
	 * @return the receivedBy
	 */
	public String getReceivedBy() {
		return receivedBy;
	}
	/**
	 * @param receivedBy the receivedBy to set
	 */
	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}
	/**
	 * @return the toolIssueNo
	 */
	public int getToolIssueNo() {
		return toolIssueNo;
	}
	/**
	 * @param toolIssueNo the toolIssueNo to set
	 */
	public void setToolIssueNo(int toolIssueNo) {
		this.toolIssueNo = toolIssueNo;
	}

}
