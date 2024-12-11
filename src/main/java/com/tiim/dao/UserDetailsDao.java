package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.tiim.model.UserDetails;
import com.tiim.util.Cryptography;
import com.tiim.util.TiimUtil;

@Repository
public class UserDetailsDao {

	@Autowired
	DataSource datasource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	public String addUser(UserDetails userDetail, int userId)
	{
		Cryptography cryptography = new Cryptography();
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isUserExists(userDetail.getUserName()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into mst_user(username, password, role, firstname, lastname, isActive, branchname, passworddate, emailid) "
						+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, TiimUtil.ValidateNull(userDetail.getUserName()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(cryptography.encrypt(userDetail.getPassword().trim())));
				pstmt.setString(3, TiimUtil.ValidateNull(userDetail.getRole()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(userDetail.getFirstName()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(userDetail.getLastName()).trim());
				pstmt.setInt(6, 1);
				pstmt.setString(7, TiimUtil.ValidateNull(userDetail.getBranchName()).trim());
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(8, date);
				pstmt.setString(9, userDetail.getEmail());
				pstmt.executeUpdate();
				msg = "Saved Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("user.page", null,null));
				history.setDescription(messageSource.getMessage("user.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			}
			else
			{
				msg = "Already Exists";
			}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the user master detail in mst_user table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in user master detail in mst_user table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String updateUserDetails(UserDetails userDetail, int userId)
	{
		Cryptography cryptography = new Cryptography();
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isUsertExistsUpdate(userDetail.getUserId(), userDetail.getUserName()))
			{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update mst_user set username = ?, password = ?, role = ?, firstname = ?, lastname = ?, branchname = ?, passworddate = ?,  "
						+ " emailid = ? where userId = ?");
				pstmt.setString(1, TiimUtil.ValidateNull(userDetail.getUserName()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(cryptography.encrypt(userDetail.getPassword().trim())));
				pstmt.setString(3, TiimUtil.ValidateNull(userDetail.getRole()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(userDetail.getFirstName()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(userDetail.getLastName()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(userDetail.getBranchName()).trim());
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(7, date);
				pstmt.setString(8, userDetail.getEmail());
				pstmt.setInt(9, userDetail.getUserId());
				
				pstmt.executeUpdate();
				msg = "Updated Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("user.page", null,null));
				history.setDescription(messageSource.getMessage("user.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			}
			else
			{
				msg = "Already Exists";
			}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the User detail in mst_user table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in User detail in mst_user table : "+ex.getMessage());
			}
		}
		
		return msg;
	
	}
	
	public String deleteUserDetail(int userId, int sesuserId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from mst_user where userId = ?");
			pstmt.setInt(1, userId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("user.page", null,null));
			history.setDescription(messageSource.getMessage("user.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the User detail in mst_user table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in delete the User detail in mst_user table : "+ex.getMessage());
			}
		}
		
		return msg;
	
	}
	
	public List<UserDetails> getUserDetails(String searchUser)
	{
		List<UserDetails> lstUserDetails = new ArrayList<UserDetails>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		UserDetails userDetail;
		try
		{
			con = datasource.getConnection();
			if(searchUser != null && !"".equals(searchUser))
			{
			    pstmt = con.prepareStatement("Select userId, username, password, role, firstname, lastname, isActive, branchname, emailid from mst_user Where username like '%"+searchUser+"%' order by username");
			}
			else
			{
				pstmt = con.prepareStatement("Select userId, username, password, role, firstname, lastname, isActive, branchname, emailid from mst_user order by username");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				userDetail = new UserDetails();
				userDetail.setUserId(rs.getInt("userId"));
				userDetail.setUserName(rs.getString("username"));
				userDetail.setPassword("");
				userDetail.setRole(rs.getString("role"));
				userDetail.setFirstName(rs.getString("firstname"));
				userDetail.setLastName(rs.getString("lastname"));
				userDetail.setIsActive(rs.getInt("isActive"));
				userDetail.setBranchName(rs.getString("branchname"));
				userDetail.setEmail(rs.getString("emailid"));
				lstUserDetails.add(userDetail);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getUserDetails in mst_user table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in entire getUserDetails in mst_user table : "+ex.getMessage());
			}
		}
		return lstUserDetails;	
	
	}
	
	public UserDetails getUserDetails(int userId)
	{
		UserDetails userDetail = new UserDetails();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select userId, username, password, role, firstname, lastname, isActive, branchname, emailid from mst_user Where userId = ?");
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				userDetail.setUserId(rs.getInt("userId"));
				userDetail.setUserName(rs.getString("username"));
				userDetail.setPassword("");
				userDetail.setRole(rs.getString("role"));
				userDetail.setFirstName(rs.getString("firstname"));
				userDetail.setLastName(rs.getString("lastname"));
				userDetail.setIsActive(rs.getInt("isActive"));
				userDetail.setBranchName(rs.getString("branchname"));
				userDetail.setAction("");
				userDetail.setEmail(rs.getString("emailid"));
				userDetail.setSearchUserDetail("");
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the particular user details in mst_user table by using userId : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in  particular user details in mst_user table by using userId : "+ex.getMessage());
			}
		}
		return userDetail;
	}
	
	private boolean isUserExists(String userName)
	{

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_user where username = ?");
			 pstmt.setString(1, TiimUtil.ValidateNull(userName).trim());
			 rs = pstmt.executeQuery();
			 if(rs.next())
			 {
				 count = rs.getInt("Is_Exists");
			 }
			 
			 if(count > 0)
			 {
				 isExists = false;
			 }
		}
		catch(Exception e)
		{
			System.out.println("Exception while checking the userName exists in mst_user table when adding : "+e.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in userName master detail in mst_user table when adding : "+ex.getMessage());
			}
		}
		return isExists;
	
	}
	
	private boolean isUsertExistsUpdate(int userId, String userName)
	{

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_user WHERE  userId <> ? and  username = ?");
			 pstmt.setInt(1, userId);
			 pstmt.setString(2, TiimUtil.ValidateNull(userName).trim());
			 rs = pstmt.executeQuery();
			 if(rs.next())
			 {
				 count = rs.getInt("Is_Exists");
			 }
			 
			 if(count > 0)
			 {
				 isExists = false;
			 }
		}
		catch(Exception e)
		{
			System.out.println("Exception while checking the username exists in mst_user table when updating : "+e.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in username master detail in mst_user table when updating : "+ex.getMessage());
			}
		}
		return isExists;
	
	}
	
	public String changeUserDetailsStatus( int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try
		{
			   con = datasource.getConnection();
				pstmt = con.prepareStatement("Select isActive from mst_user where userId = ?");
				pstmt.setInt(1, userId);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					isActive = rs.getInt("isActive");
					if(isActive == 1)
					{
						isActive=0;
						msg = "Made InActive Successfully";
					}
					else
					{
						isActive=1;
						msg = "Made Active Successfully";	
					}
				}
				
				pstmt = con.prepareStatement("Update mst_user set isActive = ?  where userId = ?");
				pstmt.setInt(1, isActive);
				pstmt.setInt(2, userId);
				pstmt.executeUpdate();								
		}
		catch(Exception ex)
		{
			System.out.println("Exception when changing the status of User in mst_user table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in changing the status of User in mst_user table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<String> getBranch()
	{
		List<String> lstBranch = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select branchname from branch");
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				lstBranch.add(rs.getString("branchname"));
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getBranch in branch table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in getBranch table : "+ex.getMessage());
			}
		}
		return lstBranch;
	}
	
	/*public List<String> getUserName()
	{

		List<String> lstUserDetails = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select userId, username, password, role, firstname, lastname, isActive, branchname, emailid from mst_user order by username");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getUserDetails in mst_user table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in entire getUserDetails in mst_user table : "+ex.getMessage());
			}
		}
		return lstUserDetails;	
	
	
	}*/
}
