package com.tiim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiim.dao.UserDetailsDao;
import com.tiim.model.UserDetails;

@Service
public class UserDetailsService {


	
	@Autowired
	UserDetailsDao userDetailsDao; 
	
	public String addUserDetails(UserDetails userDetail, int userId)
	{
		return userDetailsDao.addUser(userDetail, userId);
	}
	
	public String updateUserDetails(UserDetails userDetail, int userId)
	{
		return userDetailsDao.updateUserDetails(userDetail, userId);
	}
	
	public String deleteUserDetails(int userId, int sesuserId)
	{
		return userDetailsDao.deleteUserDetail(userId, sesuserId);
	}
	
	public List<UserDetails> getUserDetails(String searchUserDetails)
	{
		return userDetailsDao.getUserDetails(searchUserDetails);
	}
	
	public UserDetails getUserDetails(int userDetailId)
	{
		return userDetailsDao.getUserDetails(userDetailId);
	}
	
	public String changeUserDetailsStatus(int userDetailId)
	{
		return userDetailsDao.changeUserDetailsStatus(userDetailId);
	}
	
	public List<String> getBranchList()
	{
		return userDetailsDao.getBranch();
	}
	
}
