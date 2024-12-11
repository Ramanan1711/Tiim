package com.tiim.model;

public class Param {
	  private String value;
	  private String label;
	  private String id;

	  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Param(String value, String label) {
	    this.value = value;
	    this.label = label;
	  }
	
	public Param(String value, String label,String id) {
	    this.value = value;
	    this.label = label;
	    this.id = id;
	  }

	  public String getLabel() {
	    return label;
	  }

	  public String getValue() {
	    return value;
	  }
	  
	  @Override
	    public String toString() {
	        return "Param [id=" + id + ", label=" + label + ", value="+ value +"]";
	    }
}
