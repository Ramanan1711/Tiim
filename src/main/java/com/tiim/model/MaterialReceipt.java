package com.tiim.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class MaterialReceipt {

	private int materialGrnNo;
	private String materialGrnDate;
	private String dcNo;
	private String supplierCode;
	private String supplierName;
	private String billNo;
	private String dcDate;
	private String billDate;

	private int materialCode;
	private String lotNumber;
	private String materialName;
	private int materialQty;
	private String uom;
	private String mgfDate;
	private String receivedBy;
	private String remark;
	private String branch;

	/**
	 * @return the materialGrnNo
	 */
	public int getMaterialGrnNo() {
		return materialGrnNo;
	}
	/**
	 * @param materialGrnNo the materialGrnNo to set
	 */
	public void setMaterialGrnNo(int materialGrnNo) {
		this.materialGrnNo = materialGrnNo;
	}
	/**
	 * @return the materialGrnDate
	 */
	public String getMaterialGrnDate() {
		return materialGrnDate;
	}
	/**
	 * @param materialGrnDate the materialGrnDate to set
	 */
	public void setMaterialGrnDate(String materialGrnDate) {
		this.materialGrnDate = materialGrnDate;
	}
	/**
	 * @return the dcNo
	 */
	public String getDcNo() {
		return dcNo;
	}
	/**
	 * @param dcNo the dcNo to set
	 */
	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}
	/**
	 * @return the supplierCode
	 */
	public String getSupplierCode() {
		return supplierCode;
	}
	/**
	 * @param supplierCode the supplierCode to set
	 */
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	/**
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}
	/**
	 * @param supplierName the supplierName to set
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	/**
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * @param billNo the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	/**
	 * @return the dcDate
	 */
	public String getDcDate() {
		return dcDate;
	}
	/**
	 * @param dcDate the dcDate to set
	 */
	public void setDcDate(String dcDate) {
		this.dcDate = dcDate;
	}
	/**
	 * @return the billDate
	 */
	public String getBillDate() {
		return billDate;
	}
	/**
	 * @param billDate the billDate to set
	 */
	public void setBillDate(String billDate) {
		this.billDate = billDate;
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
	 * @return the mgfDate
	 */
	public String getMgfDate() {
		return mgfDate;
	}
	/**
	 * @param mgfDate the mgfDate to set
	 */
	public void setMgfDate(String mgfDate) {
		this.mgfDate = mgfDate;
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
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}
	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

}
