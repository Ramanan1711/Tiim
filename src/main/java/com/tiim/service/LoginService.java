package com.tiim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiim.dao.LoginDao;
import com.tiim.model.Login;

@Service
public class LoginService {

	@Autowired
	LoginDao loginDao;
	
	public boolean checkLoginCredential(Login login)
	{
		return loginDao.checkLoginCredential(login);
	}
	
	public String changePassword(Login login)
	{
		return loginDao.changePassword(login);
	}
	
	public String getPassword(Login login)
	{
		return loginDao.getPassword(login);
	}
	
	public void lockPassword(Login login)
	{
		loginDao.lockPassword(login);
	}
	
	public String unlockAccount(Login login)
	{
		return loginDao.unlockAccount(login);
	}
}
