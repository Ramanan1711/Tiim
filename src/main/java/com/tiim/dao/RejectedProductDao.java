package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.RejectedProduct;

@Repository
public class RejectedProductDao {

	@Autowired
	DataSource datasource;
	
	public void addRejectedProduct(RejectedProduct reject)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into rejected_product(productname, machineName,typeOfTool, drawingNo, toolinglotnumber, toolingId, source, serialnumber)"
						+ "values(?, ?, ?, ?, ?, ?, ?, ?)");

				pstmt.setString(1, reject.getProductName());
				pstmt.setString(2, reject.getMachineName());
				pstmt.setString(3, reject.getTypeOfTool());
				pstmt.setString(4, reject.getDrawingNumber());
				pstmt.setString(5, reject.getLotNumber());
				pstmt.setInt(6, reject.getToolingId());
				pstmt.setString(7, reject.getSource());
				pstmt.setString(8, reject.getSerialNumber());
				pstmt.executeUpdate();
				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the addRejectedProduct : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in addRejectedProduct : "+ex.getMessage());
			}
		}
	}
	
	public List<RejectedProduct> getRejectedProductDetail()
	{
		List<RejectedProduct> lstRejectProduct = new ArrayList<RejectedProduct>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		RejectedProduct rejectProduct = new RejectedProduct();
		try
		{
			con = datasource.getConnection();
			
				pstmt = con.prepareStatement("SELECT productname, machineName,typeOfTool, drawingNo, toolinglotnumber, toolingId, source, serialnumber FROM rejected_product");

			rs = pstmt.executeQuery();
			while(rs.next())
			{
				rejectProduct = new RejectedProduct();
				rejectProduct.setProductName(rs.getString("productname"));
				rejectProduct.setMachineName(rs.getString("machineName"));
				rejectProduct.setTypeOfTool(rs.getString("typeOfTool"));
				rejectProduct.setDrawingNumber(rs.getString("drawingNo"));
				rejectProduct.setToolingId(rs.getInt("toolingId"));
				rejectProduct.setLotNumber(rs.getString("toolinglotnumber"));
				rejectProduct.setSerialNumber(rs.getString("serialnumber"));
				rejectProduct.setSource(rs.getString("source"));
				lstRejectProduct.add(rejectProduct);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting the getExpiryProductDetail List : "+e.getMessage());
		}
		return lstRejectProduct;
	}
	
	public void deleteRejectProduct(String lotNumber)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("delete from rejected_product where toolinglotnumber = ?");
				pstmt.setString(1, lotNumber);
				pstmt.executeUpdate();
				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when deleting the deleteRejectProduct : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in deleteRejectProduct : "+ex.getMessage());
			}
		}
	
	}
}
