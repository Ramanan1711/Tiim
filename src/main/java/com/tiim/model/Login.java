package com.tiim.model;

import java.sql.Date;


public class Login {

	private String username;
	private String password;
	private String role;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private int userId;
	private String errorMessage;
    private String confirmNewPassword;
    private String branchName;
    private Date passwordDate;
    private int passwordLock;
    private int loginFirstTime;
    
	public int getLoginFirstTime() {
		return loginFirstTime;
	}
	public void setLoginFirstTime(int loginFirstTime) {
		this.loginFirstTime = loginFirstTime;
	}
	public int getPasswordLock() {
		return passwordLock;
	}
	public void setPasswordLock(int passwordLock) {
		this.passwordLock = passwordLock;
	}
	public Date getPasswordDate() {
		return passwordDate;
	}
	public void setPasswordDate(Date passwordDate) {
		this.passwordDate = passwordDate;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
}
