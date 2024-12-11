package com.tiim.model;

public class Plating {

	private int id;
	private String platingName;
	private int active;
	private String searchPlating;
	
	public String getSearchPlating() {
		return searchPlating;
	}
	public void setSearchPlating(String searchPlating) {
		this.searchPlating = searchPlating;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlatingName() {
		return platingName;
	}
	public void setPlatingName(String platingName) {
		this.platingName = platingName;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	
}
