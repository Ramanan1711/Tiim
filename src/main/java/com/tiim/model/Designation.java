package com.tiim.model;

public class Designation {
	
	private int designationId;
	private String designationname;
	private String desigcode;
	private String action;
	private String searchDesignation;
	private int isActive;
	private int delStatus;
	
		
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getSearchDesignation() {
		return searchDesignation;
	}
	public void setSearchDesignation(String searchDesignation) {
		this.searchDesignation = searchDesignation;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getDesignationId() {
		return designationId;
	}
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}
	public String getDesignationname() {
		return designationname;
	}
	public void setDesignationname(String designationname) {
		this.designationname = designationname;
	}
	public String getDesigcode() {
		return desigcode;
	}
	public void setDesigcode(String desigcode) {
		this.desigcode = desigcode;
	}
	
	

}
