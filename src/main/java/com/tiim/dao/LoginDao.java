package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.tiim.model.Login;
import com.tiim.util.Cryptography;
import com.tiim.util.TiimUtil;


@Repository
public class LoginDao {
	
	@Autowired
	DataSource datasource;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	public boolean checkLoginCredential(Login login)
	{
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean returnValue = false;
			TransactionHistory history = new TransactionHistory();
			try
			{
				Cryptography cryptography = new Cryptography();
				String password = cryptography.encrypt(login.getPassword());
				System.out.println("password: "+password);
				history.setPageName(messageSource.getMessage("login.pagename", null,null));
				con = datasource.getConnection();
				pstmt = con.prepareStatement("select userid, username, role, firstname, lastname, branchname, passworddate, passwordlock, isFirstTime from mst_user where username = ? and password = ?");
				pstmt.setString(1, login.getUsername());
				pstmt.setString(2, password);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					login.setUsername(rs.getString("username"));
					login.setRole(rs.getString("role"));
					login.setUserId(rs.getInt("userId"));
					login.setFirstName(rs.getString("firstname"));
					login.setLastName(rs.getString("lastname"));
					login.setPasswordDate(rs.getDate("passworddate"));
					login.setBranchName(rs.getString("branchname"));
					login.setPasswordLock(rs.getInt("passwordlock"));
					login.setLoginFirstTime(rs.getInt("isFirstTime"));
					returnValue = true;
				}
				if(returnValue)
				{
					history.setDescription(messageSource.getMessage("login.success", null,null));
					history.setUserId(login.getUserId());
					historyDao.addHistory(history);
					
				}else
				{
					history.setDescription(messageSource.getMessage("login.fail", null,null));
					history.setUserId(0);
					historyDao.addHistory(history);
					
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when executing the login credential query: isValidUser: "+ex.getMessage());
			}finally
			{
				try
				{
					if(rs != null)
					{
						rs.close();
					}
					if (pstmt != null)
					{
						pstmt.close();
					}
					if(con != null)
					{
						con.close();
					}
				}catch(Exception ex)
				{
					System.out.println("Exception when close the connection objects in isValidUser: "+ex.getMessage());
				}
			}
			
			return returnValue;
		}
	
		public String changePassword(Login login)
		{
			Cryptography cryptography = new Cryptography();
			Connection con = null;
			PreparedStatement pstmt = null;
			String msg = "";
			try
			{
				
					con = datasource.getConnection(); 
					pstmt = con.prepareStatement("update mst_user set password = ?, passworddate = ? where userId = ?");
					pstmt.setString(1, TiimUtil.ValidateNull(cryptography.encrypt(login.getConfirmNewPassword()).trim()));
					Calendar calendar = Calendar.getInstance();
				    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
					pstmt.setDate(2, date);
					pstmt.setInt(3, login.getUserId());
					pstmt.executeUpdate();
					msg = "Changed Successfully";
					
					TransactionHistory history = new TransactionHistory();
					history.setPageName(messageSource.getMessage("changePassword.page", null,null));
					history.setDescription(messageSource.getMessage("changePassword.changed", null,null));
					history.setUserId(login.getUserId());
					historyDao.addHistory(history);
			}
			catch(Exception ex)
			{
				System.out.println("Exception when updating the user detail in mst_user table : "+ex.getMessage());
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
					System.out.println("Exception when closing the connection in user detail in mst_user table : "+ex.getMessage());
				}
			}
			
			return msg;
		
		}
		
		public String unlockAccount(Login login)
		{
			Cryptography cryptography = new Cryptography();
			Connection con = null;
			PreparedStatement pstmt = null;
			String msg = "";
			try
			{
				
					con = datasource.getConnection(); 
					pstmt = con.prepareStatement("update mst_user set password = ?, passworddate = ?, passwordlock = ? where username = ?");
					pstmt.setString(1, TiimUtil.ValidateNull(cryptography.encrypt(login.getConfirmNewPassword()).trim()));
					Calendar calendar = Calendar.getInstance();
				    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
					pstmt.setDate(2, date);
					pstmt.setInt(3, 0);
					pstmt.setString(4, login.getUsername());
					pstmt.executeUpdate();
					msg = "Changed Successfully";
					
					TransactionHistory history = new TransactionHistory();
					history.setPageName(messageSource.getMessage("userUnlock.page", null,null));
					history.setDescription(login.getUsername()+" "+messageSource.getMessage("userUnlock.unlocked", null,null));
					history.setUserId(0);
					historyDao.addHistory(history);
			}
			catch(Exception ex)
			{
				System.out.println("Exception when unlockAccount the user detail in mst_user table : "+ex.getMessage());
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
					System.out.println("Exception when closing the connection in user detail in mst_user table : "+ex.getMessage());
				}
			}
			
			return msg;
		
		}
		
		public String getPassword(Login login)
		{

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String password = new String();
			try
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("select userid, username, role, firstname, lastname, password from mst_user where userid = ?");
				pstmt.setString(1, login.getUsername());
				pstmt.setString(2, login.getPassword());
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					password = rs.getString("password");
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when executing the getPassword query: getPassword: "+ex.getMessage());
			}finally
			{
				try
				{
					if(rs != null)
					{
						rs.close();
					}
					if (pstmt != null)
					{
						pstmt.close();
					}
					if(con != null)
					{
						con.close();
					}
				}catch(Exception ex)
				{
					System.out.println("Exception when close the connection objects in getPassword: "+ex.getMessage());
				}
			}
			
			return password;
		}
	 public void lockPassword(Login login)
	 {
			Connection con = null;
			PreparedStatement pstmt = null;
			try
			{
					con = datasource.getConnection(); 
					pstmt = con.prepareStatement("update mst_user set passwordlock = ? where username = ?");
					pstmt.setInt(1, 1);
					
					pstmt.setString(2, login.getUsername());
					pstmt.executeUpdate();
					
					TransactionHistory history = new TransactionHistory();
					history.setPageName(messageSource.getMessage("userlock.page", null,null));
					history.setDescription(login.getUsername()+" "+messageSource.getMessage("userUnlock.lock", null,null));
					history.setUserId(0);
					historyDao.addHistory(history);
			}
			catch(Exception ex)
			{
				System.out.println("Exception when updating lockPassword : "+ex.getMessage());
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
					System.out.println("Exception when closing the connection in lockPassword in mst_user table : "+ex.getMessage());
				}
			}
	 }

}
