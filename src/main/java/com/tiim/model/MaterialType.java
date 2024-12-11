package com.tiim.model;

public class MaterialType {
	
	private int materialTypeId;
	private String materialTypeName;
	private int isActive;
	private String searchMaterialType;
	
	public int getMaterialTypeId() {
		return materialTypeId;
	}
	public void setMaterialTypeId(int materialTypeId) {
		this.materialTypeId = materialTypeId;
	}
	public String getMaterialTypeName() {
		return materialTypeName;
	}
	public void setMaterialTypeName(String materialTypeName) {
		this.materialTypeName = materialTypeName;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getSearchMaterialType() {
		return searchMaterialType;
	}
	public void setSearchMaterialType(String searchMaterialType) {
		this.searchMaterialType = searchMaterialType;
	}
	
	

}
