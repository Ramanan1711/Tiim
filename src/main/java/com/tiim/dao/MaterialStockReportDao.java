package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.MaterialStock;

@Repository
public class MaterialStockReportDao {

	@Autowired
	DataSource datasource;
	
	public List<MaterialStock> getStockReport(MaterialStock stock)
	{
		List<MaterialStock> lststocks = new ArrayList<MaterialStock>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			
			String query = "SELECT ms.toolinglotnumber, ms.materialcode, ms.stockqty, m.materialName FROM material_stock ms, material_receipt_note m where ms.toolinglotnumber = m.lotNumber ";
			if(stock.getToolingLotNumber() != null && !stock.getToolingLotNumber().equals("") )
			{
				query = query + " and toolinglotnumber = '"+stock.getToolingLotNumber()+"' ";
			}
			else{
				if(stock.getMaterialCode() != 0)
				{
					query = query + " and ms.materialcode = '"+stock.getMaterialCode()+"' ";
				}
				else if(stock.getMaterialName() != null && stock.getMaterialName() != "")
				{
					MaterialStock matDtl = getLotNumberByMaterialType(stock.getMaterialName(), true);
					query = query + " and toolinglotnumber = '"+matDtl.getToolingLotNumber()+"' ";
				}
				else if(stock.getMaterialType() != null && stock.getMaterialType() != "")
				{
					MaterialStock matDtl = getLotNumberByMaterialType(stock.getMaterialType(), false);
					query = query + "and toolinglotnumber = '"+matDtl.getToolingLotNumber()+"' ";
				}
			}
			
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MaterialStock obj = new MaterialStock();
				obj.setToolingLotNumber(rs.getString("toolinglotnumber"));
				obj.setMaterialName(rs.getString("materialName"));
				obj.setStockQty(rs.getInt("stockqty"));
				lststocks.add(obj);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in stockDao getStockReport : "+ex.getMessage());
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
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in  stockDao getstock  : "+ex.getMessage());
			}
		}
	
		return lststocks;
	}
	
	public MaterialStock getLotNumberByMaterialType(String type, boolean option){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MaterialStock stock =  new MaterialStock();
		try
		{
			con = datasource.getConnection();
			String query = "SELECT lotnumber, materialname FROM material_issue ";
			if(option)
			{
				query = query + " where materialname = '"+type+"' ";
			}
			else
			{
				query = query + " where materialtype = '"+type+"' ";
			}
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, type);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				stock.setToolingLotNumber(rs.getString("lotnumber"));
				stock.setMaterialName(rs.getString("materialname"));
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in stockDao periodicIssueQuantity : "+ex.getMessage());
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
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in  stockDao periodicIssueQuantity  : "+ex.getMessage());
			}
		}
	
		return stock;
	}
	
	public List<String> getToolLotNumber(String lotNumber)
	{
		List<String> lstlotNumber = new ArrayList<String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT distinct toolinglotnumber FROM material_stock where toolinglotnumber like '"
			+lotNumber+"%' order by toolinglotnumber");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				
				lstlotNumber.add(rs.getString("toolinglotnumber"));
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductReportDao getLotNumber : "+ex.getMessage());
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
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in  ProductReportDao getLotNumber  : "+ex.getMessage());
			}
		}
	
		return lstlotNumber;
	}
}
