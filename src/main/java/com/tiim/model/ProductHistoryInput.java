package com.tiim.model;

import java.sql.Date;

public class ProductHistoryInput {

	private String allTransaction;
	private String receivedTransaction;
	private String productionTransaction;
	private String periodicTransaction;
	private String fromDate;
	private String toDate;
	
	private Date fromDate1;
	private Date toDate1;
	
	private String lotNumber;
	public String getAllTransaction() {
		return allTransaction;
	}
	public void setAllTransaction(String allTransaction) {
		this.allTransaction = allTransaction;
	}
	public String getReceivedTransaction() {
		return receivedTransaction;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public Date getFromDate1() {
		return fromDate1;
	}
	public void setFromDate1(Date fromDate1) {
		this.fromDate1 = fromDate1;
	}
	public Date getToDate1() {
		return toDate1;
	}
	public void setToDate1(Date toDate1) {
		this.toDate1 = toDate1;
	}
	public void setReceivedTransaction(String receivedTransaction) {
		this.receivedTransaction = receivedTransaction;
	}
	public String getProductionTransaction() {
		return productionTransaction;
	}
	public void setProductionTransaction(String productionTransaction) {
		this.productionTransaction = productionTransaction;
	}
	public String getPeriodicTransaction() {
		return periodicTransaction;
	}
	public void setPeriodicTransaction(String periodicTransaction) {
		this.periodicTransaction = periodicTransaction;
	}
	
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
}
