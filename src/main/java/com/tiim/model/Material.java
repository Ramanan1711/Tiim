package com.tiim.model;

import java.util.Date;

public class Material {

	private int materialCode;
	private String materialType;
	private String materialName;
	private String materialQty;
	private String uom;
	private String action;
	private int approvalflag;
	private Date approveddate;
	private String approvedby;
	private String minStockLevel;
	private String reorderLevel;
	/**
	 * @return the materialcCode
	 */
	public int getMaterialCode() {
		return materialCode;
	}
	/**
	 * @param materialcCode the materialcCode to set
	 */
	public void setMaterialCode(int materialCode) {
		this.materialCode = materialCode;
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
	public String getMaterialQty() {
		return materialQty;
	}
	/**
	 * @param materialQty the materialQty to set
	 */
	public void setMaterialQty(String materialQty) {
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
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the approvalflag
	 */
	public int getApprovalflag() {
		return approvalflag;
	}
	/**
	 * @param approvalflag the approvalflag to set
	 */
	public void setApprovalflag(int approvalflag) {
		this.approvalflag = approvalflag;
	}
	/**
	 * @return the approveddate
	 */
	public Date getApproveddate() {
		return approveddate;
	}
	/**
	 * @param approveddate the approveddate to set
	 */
	public void setApproveddate(Date approveddate) {
		this.approveddate = approveddate;
	}
	/**
	 * @return the approvedby
	 */
	public String getApprovedby() {
		return approvedby;
	}
	/**
	 * @return the minStockLevel
	 */
	public String getMinStockLevel() {
		return minStockLevel;
	}
	/**
	 * @param minStockLevel the minStockLevel to set
	 */
	public void setMinStockLevel(String minStockLevel) {
		this.minStockLevel = minStockLevel;
	}
	/**
	 * @return the reorderLevel
	 */
	public String getReorderLevel() {
		return reorderLevel;
	}
	/**
	 * @param reorderLevel the reorderLevel to set
	 */
	public void setReorderLevel(String reorderLevel) {
		this.reorderLevel = reorderLevel;
	}
	/**
	 * @param approvedby the approvedby to set
	 */
	public void setApprovedby(String approvedby) {
		this.approvedby = approvedby;
	}
}
