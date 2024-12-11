package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GetMinAcceptedQty {
	
	@Autowired
	DataSource datasource;

	public int getMinAcceptedQty1(String productName, String drawingNo, String machineType, String typeOfTool)
	{
		int minAcceptedQty = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select minAcceptedQty from mst_product where productname = ? and drawingno = ? and machinetype = ? and typeoftool = ?");
			pstmt.setString(1, productName);
			pstmt.setString(2, drawingNo);
			pstmt.setString(3, machineType);
			pstmt.setString(4, typeOfTool);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				minAcceptedQty = rs.getInt("minAcceptedQty");
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getMinAcceptedQty : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getMinAcceptedQty : "+ex.getMessage());
			}
		}	
		return minAcceptedQty;
	
	}
	
	public int getMinAcceptedQty(String lotNumber)
	{
		int minAcceptedQty = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select minacceptedQty from tooling_receipt_product where toolinglotnumber = ?");
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				minAcceptedQty = rs.getInt("minacceptedQty");
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getMinAcceptedQty : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getMinAcceptedQty : "+ex.getMessage());
			}
		}	
		return minAcceptedQty;
	
	}
}
