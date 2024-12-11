package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.Login;
import com.tiim.model.SecurityQuestion;
import com.tiim.util.Cryptography;
import com.tiim.util.TiimUtil;

@Repository
public class SecurityQuestionDao {

	@Autowired
	DataSource datasource;
	
	@Autowired
	LoginDao loginDao;
	
	public void addSecurityQuestion(SecurityQuestion question)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into securityAnswer(answer1, answer2, userId)"
						+ "values(?, ?, ?)");
				pstmt.setString(1, TiimUtil.ValidateNull(question.getQuestion1()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(question.getQuestion2()).trim());
				pstmt.setInt(3, question.getUserId());
				pstmt.executeUpdate();
				updateStatus(question.getUserId());
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the addSecurityQuestion : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in addSecurityQuestion : "+ex.getMessage());
			}
		}
	}
	
	public void updateStatus(int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update mst_user set isFirstTime = ? where userId = ?");
				pstmt.setInt(1, 1);
				pstmt.setInt(2, userId);
				pstmt.executeUpdate();
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the updateStatus : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updateStatus : "+ex.getMessage());
			}
		}
	}
	
	public String verifySecurityQuestion(String userName, SecurityQuestion question)
	{
		Login login = new Login();
		String password = "";
		try
		{
			
			int userId = getUserId(userName);
			if(userId > 0)
			{
				question.setUserId(userId);
				boolean isValid = checkQuestion(question);
				if(isValid)
				{
					password = RandomStringUtils.randomAlphanumeric(8);
					System.out.println("password: "+password);
					login.setUserId(userId);
					login.setPassword(password);
					login.setConfirmNewPassword(password);
					loginDao.changePassword(login);
					System.out.println("change pwd...");
				}
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when verifySecurityQuestion: "+ex.getMessage());
		}
		return password;
	}
	
	private boolean checkQuestion(SecurityQuestion question)
	{
		boolean returnValue = false;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select securityId from securityAnswer Where "
					+ " answer1 = ? and answer2 = ? and userId = ?");
			pstmt.setString(1, question.getQuestion1());
			pstmt.setString(2, question.getQuestion2());
			pstmt.setInt(3, question.getUserId());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				returnValue = true;
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting in checkQuestion  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in checkQuestion : "+ex.getMessage());
			}
		}
		
		return returnValue;
	}
	
	private int getUserId(String userName)
	{
		int userId = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select userId, username, password, role, firstname, lastname, isActive, branchname from mst_user Where username = ?");
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				userId = rs.getInt("userId");
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting in getUserId table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getUserId in mst_user table : "+ex.getMessage());
			}
		}
		return userId;	
	
	
	}
}
