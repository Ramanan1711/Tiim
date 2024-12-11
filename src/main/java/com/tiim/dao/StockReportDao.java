package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.Stock;

@Repository
public class StockReportDao {

	@Autowired
	DataSource datasource;
	
	public List<Stock> getStockReport(Stock stock)
	{
		List<Stock> lststocks = new ArrayList<Stock>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
		/*	String query = "select a.toolinglotnumber, a.typeoftool, a.productname, a.drawingno, a.machinetype, a.stockqty, a.UOM from stock as a"
					+ " where productname = '"+stock.getProductName()+"'";*/

			String query ="select  toolinglotnumber,typeoftool,productname, drawingno,  machinetype,stockqty,UOM,group_concat(serialnumber) as rejectSerialNumber from stock as a, serial_number_details as b where a.toolinglotnumber=b.lotnumber and approved=2 and productname= '"+stock.getProductName()+"'  group by toolinglotnumber";
			
			if(stock.getMachineName() != null && !stock.getMachineName().equals("") )
			{
				query = query + " and machinetype = '"+stock.getMachineName()+"' ";
			}
			if(stock.getTypeOfTool() != null && !stock.getTypeOfTool().equals(""))
			{
				query = query + " and typeoftool = '"+stock.getTypeOfTool()+"' ";
			}
			if(stock.getToolingLotNumber() != null && stock.getToolingLotNumber() != "")
			{
				query = query + " and toolinglotnumber = '"+stock.getToolingLotNumber() +"' ";
			}
			System.out.println("query: "+query);
			
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				Stock obj = new Stock();
				obj.setToolingLotNumber(rs.getString("toolinglotnumber"));
				obj.setProductName(rs.getString("productname"));
				obj.setMachineName(rs.getString("machinetype"));
				obj.setDrawingNo(rs.getString("drawingno"));
				obj.setTypeOfTool(rs.getString("typeoftool"));
				obj.setStockQty(rs.getInt("stockqty"));
				obj.setUom(rs.getString("UOM"));
				obj.setRejectSerialNumber(rs.getString("rejectSerialNumber"));
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
	
	public Stock getStockQuantity(String lotNumber, String branch){
		Stock obj = new Stock();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			String query = "select toolinglotnumber, typeoftool, productname, drawingno, machinetype, stockqty, UOM from stock where toolinglotnumber = ? and branch = ? ";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, lotNumber);
			pstmt.setString(2, branch);
			rs = pstmt.executeQuery();
			while(rs.next())
			{

			
				obj.setToolingLotNumber(rs.getString("toolinglotnumber"));
				obj.setProductName(rs.getString("productname"));
				obj.setMachineName(rs.getString("machinetype"));
				obj.setDrawingNo(rs.getString("drawingno"));
				obj.setTypeOfTool(rs.getString("typeoftool"));
				obj.setStockQty(rs.getInt("stockqty"));
				obj.setUom(rs.getString("UOM"));
			
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in stockDao getStockQuantity : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  stockDao getStockQuantity  : "+ex.getMessage());
			}
		}
	
		return obj;
	}
	
	
	public int inspectionQuantity(String lotNumber){
		int inspectionQty = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			String query = "SELECT max(revisionnumber), acceptedQty FROM tooling_receiving_inspection_details where lotnumber = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				inspectionQty = rs.getInt("acceptedQty");
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in stockDao inspectionQuantity : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  stockDao inspectionQuantity  : "+ex.getMessage());
			}
		}
	
		return inspectionQty;
	}
	
	public int productIssueQuantity(String lotNumber){
		int inspectionQty = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			String query = "select max(versionnumber), issuedqty from tooling_issue_note_detail where toolinglotnumber = ? "
					+ " and originalId not in (select issueNo from tooling_production_return_note)";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				inspectionQty = rs.getInt("issuedqty");
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in stockDao productIssueQuantity : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  stockDao productIssueQuantity  : "+ex.getMessage());
			}
		}
	
		return inspectionQty;
	}
	
	public int productReturnQuantity(String lotNumber){
		int inspectionQty = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			String query = "select max(revisionnumber),goodqty from tooling_production_return_note_detail where toolinglotnumber = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				inspectionQty = rs.getInt("goodqty");
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in stockDao productReturnQuantity : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  stockDao productReturnQuantity  : "+ex.getMessage());
			}
		}
	
		return inspectionQty;
	}
	
	public int periodicRequestQuantity(String lotNumber){
		int inspectionQty = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			String query = "SELECT max(revisionnumber), toolingrequestqty FROM tooling_periodical_inspection_request_detail where toolinglotnumber = ? and "
					+ " originalId not in (select requestNo from periodic_inspection_report)";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				inspectionQty = rs.getInt("toolingrequestqty");
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in stockDao periodicRequestQuantity : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  stockDao periodicRequestQuantity  : "+ex.getMessage());
			}
		}
	
		return inspectionQty;
	}
	
	
	public int periodicIssueQuantity(String lotNumber){
		int inspectionQty = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			String query = "SELECT max(revisionnumber),goodQty FROM periodic_inspection_report_detail where toolinglotnumber = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				inspectionQty = rs.getInt("goodQty");
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
	
		return inspectionQty;
	}
	
	public List<Stock> getAvailableStock(String branchName)
	{

		Stock obj = new Stock();
		List<Stock> lstStock = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			String query = "select toolinglotnumber, typeoftool, productname, drawingno, machinetype, stockqty, UOM from stock where stockqty > 0 and branch = ? ";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, branchName);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				obj = new Stock();
				obj.setToolingLotNumber(rs.getString("toolinglotnumber"));
				obj.setProductName(rs.getString("productname"));
				obj.setMachineName(rs.getString("machinetype"));
				obj.setDrawingNo(rs.getString("drawingno"));
				obj.setTypeOfTool(rs.getString("typeoftool"));
				obj.setStockQty(rs.getInt("stockqty"));
				obj.setUom(rs.getString("UOM"));
				lstStock.add(obj);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in stockDao getAvailableStock : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  stockDao getAvailableStock  : "+ex.getMessage());
			}
		}
	
		return lstStock;
	
	}
}
