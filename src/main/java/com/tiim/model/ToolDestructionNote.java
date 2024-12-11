package com.tiim.model;

import java.sql.Date;

public class ToolDestructionNote {
	
	private int id;
	private int serialId;
	private String lotNumber;
	private String destructionBy;
	private Date creationDate;
	private String serialNumber;
	private int rejectedQty;
	private String module;
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getRejectedQty() {
		return rejectedQty;
	}
	public void setRejectedQty(int rejectedQty) {
		this.rejectedQty = rejectedQty;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSerialId() {
		return serialId;
	}
	public void setSerialId(int serialId) {
		this.serialId = serialId;
	}
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public String getDestructionBy() {
		return destructionBy;
	}
	public void setDestructionBy(String destructionBy) {
		this.destructionBy = destructionBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
