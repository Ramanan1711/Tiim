package com.tiim.model;

public class MaterialIssue {

	private int issueNo;
	private String issueDate;
	private int materialCode;
	private String lotNumber;
	private String materialName;
	private int materialQty;
	private String uom;
	private String issuedBy;
	private String receivedBy;
	private String remark;
	private String materialType;
	private int toolRequestNo;
	private int stockQty;
	private int issuedQty;
	private int isReturned;


	/**
	 * @return the isReturned
	 */
	public int getIsReturned() {
		return isReturned;
	}
	/**
	 * @param isReturned the isReturned to set
	 */
	public void setIsReturned(int isReturned) {
		this.isReturned = isReturned;
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
	 * @return the issueNo
	 */
	public int getIssueNo() {
		return issueNo;
	}
	/**
	 * @param issueNo the issueNo to set
	 */
	public void setIssueNo(int issueNo) {
		this.issueNo = issueNo;
	}
	/**
	 * @return the issueDate
	 */
	public String getIssueDate() {
		return issueDate;
	}
	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	/**
	 * @return the issuedBy
	 */
	public String getIssuedBy() {
		return issuedBy;
	}
	/**
	 * @param issuedBy the issuedBy to set
	 */
	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
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
	 * @return the toolRequestNo
	 */
	public int getToolRequestNo() {
		return toolRequestNo;
	}
	/**
	 * @param toolRequestNo the toolRequestNo to set
	 */
	public void setToolRequestNo(int toolRequestNo) {
		this.toolRequestNo = toolRequestNo;
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
	 * @return the stockQty
	 */
	public int getStockQty() {
		return stockQty;
	}
	/**
	 * @param stockQty the stockQty to set
	 */
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}
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

}
