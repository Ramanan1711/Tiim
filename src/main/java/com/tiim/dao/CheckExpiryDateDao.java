package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.ToolingRequestNote;
import com.tiim.util.TiimUtil;

@Repository
public class CheckExpiryDateDao {

	@Autowired
	DataSource datasource;
	
	public boolean isProductExpired(String lotNumber)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select toolingreceiptid from tooling_receipt_product "
					+ " where  CURDATE() <= expiryDate and toolinglotnumber = ?");
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				return false;
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the CheckExpiryDateDao : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in CheckExpiryDateDao : "+ex.getMessage());
			}
		}	
		return true;
	}
	
	public boolean isProductRejected(String lotNumber)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select toolinglotnumber from rejected_product where toolinglotnumber = ?");
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				return true;
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the isProductRejected : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in isProductRejected : "+ex.getMessage());
			}
		}	
		return false;
	}
}
