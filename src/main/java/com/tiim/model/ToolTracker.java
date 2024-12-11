package com.tiim.model;

import java.util.List;

public class ToolTracker {
	
	private List<ToolingReceiptNote> toolingReceiptNotes;
	private List<ToolingRequest> toolingRequests;
	private List<ToolingInspection> toolingInspections;
	private List<ToolingRequestNote> toolingRequestNotes;
	private List<ToolingIssueNote> toolingIssueNotes;
	public List<ToolingReceiptNote> getToolingReceiptNotes() {
		return toolingReceiptNotes;
	}
	public void setToolingReceiptNotes(List<ToolingReceiptNote> toolingReceiptNotes) {
		this.toolingReceiptNotes = toolingReceiptNotes;
	}
	public List<ToolingRequest> getToolingRequests() {
		return toolingRequests;
	}
	public void setToolingRequests(List<ToolingRequest> toolingRequests) {
		this.toolingRequests = toolingRequests;
	}
	public List<ToolingInspection> getToolingInspections() {
		return toolingInspections;
	}
	public void setToolingInspections(List<ToolingInspection> toolingInspections) {
		this.toolingInspections = toolingInspections;
	}
	public List<ToolingRequestNote> getToolingRequestNotes() {
		return toolingRequestNotes;
	}
	public void setToolingRequestNotes(List<ToolingRequestNote> toolingRequestNotes) {
		this.toolingRequestNotes = toolingRequestNotes;
	}
	public List<ToolingIssueNote> getToolingIssueNotes() {
		return toolingIssueNotes;
	}
	public void setToolingIssueNotes(List<ToolingIssueNote> toolingIssueNotes) {
		this.toolingIssueNotes = toolingIssueNotes;
	}
	
	

}
