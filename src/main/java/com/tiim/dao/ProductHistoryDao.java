package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.ProductHistory;
import com.tiim.model.ProductHistoryInput;

@Repository
public class ProductHistoryDao {

	@Autowired
	DataSource datasource;
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	public List<String> getLotNumber(String lotNumber)
	{
		List<String> lstlotNumber = new ArrayList<String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select distinct toolinglotnumber from tooling_receipt_product where  toolinglotnumber like '"+lotNumber+"%' order by toolinglotnumber");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				
				lstlotNumber.add(rs.getString("toolinglotnumber"));
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductHistoryDao getLotNumber : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductHistoryDao getLotNumber  : "+ex.getMessage());
			}
		}
	
		return lstlotNumber;
	}
	
	public List<ProductHistory> getProductHistoryFromReceivingRequest(ProductHistoryInput input)
	{
		List<ProductHistory> lstProductHistory = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			String query = "SELECT toolingname, drawingno, typeoftool, machinetype, b.revisionnumber revisionnumber,"
					+ " b.originalid originalid, a.inspectionreportdate inspectionreportdate, punchSetNo, compForce FROM "
					+ " tooling_receiving_request a, tooling_receiving_request_details b "
					+ " where lotnumber = ? and  b.originalId = a.originalid and b.revisionnumber = a.revisionnumber ";
			if(input.getFromDate1() != null && input.getToDate1() != null)
			{
				query = query + " and inspectionreportdata between ? and ?";
			}
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, input.getLotNumber());
			if(input.getFromDate1() != null && input.getToDate1() != null)
			{
				pstmt.setDate(2, input.getFromDate1());
				pstmt.setDate(3, input.getToDate1());
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ProductHistory product = new ProductHistory();
				product.setProductName(rs.getString("toolingname"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setDrawingNo(rs.getString("drawingno"));
				product.setCompForce(rs.getInt("compForce"));
				product.setPunchSetNo(rs.getString("punchSetNo"));
				product.setMachineType(rs.getString("machinetype"));
				product.setRevisionNumber(rs.getInt("revisionnumber"));
				product.setId(rs.getInt("originalid"));
				product.setReportDate(sdf.format(sdfDB.parse(rs.getString("inspectionreportdate"))));
				product.setLotNumber(input.getLotNumber());
				product.setSource("Receiving Inspection");
				lstProductHistory.add(product);
				System.out.println("product::: "+product.getDrawingNo());
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductHistoryDao getLotNumber : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductHistoryDao getProductHistoryFromReceivingInspection  : "+ex.getMessage());
			}
		}
		return lstProductHistory;
	}
	
	public List<ProductHistory> getProductHistoryFromReceivingInspection(ProductHistoryInput input)
	{
		List<ProductHistory> lstProductHistory = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			String query = "SELECT toolingname, drawingno, typeoftool, machinetype, b.revisionnumber revisionnumber,"
					+ " b.originalid originalid, a.requestdate requestdate, , punchSetNo, compForce FROM "
					+ " tooling_receiving_inspection a, tooling_receiving_inspection_details b "
					+ " where lotnumber = ? and  b.originalId = a.originalid and b.revisionnumber = a.revisionnumber ";
			if(input.getFromDate1() != null && input.getToDate1() != null)
			{
				query = query + " and requestdate between ? and ?";
			}
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, input.getLotNumber());
			if(input.getFromDate1() != null && input.getToDate1() != null)
			{
				pstmt.setDate(2, input.getFromDate1());
				pstmt.setDate(3, input.getToDate1());
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ProductHistory product = new ProductHistory();
				product.setProductName(rs.getString("toolingname"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setDrawingNo(rs.getString("drawingno"));
				product.setCompForce(rs.getInt("compForce"));
				product.setPunchSetNo(rs.getString("punchSetNo"));
				product.setMachineType(rs.getString("machinetype"));
				product.setRevisionNumber(rs.getInt("revisionnumber"));
				product.setId(rs.getInt("originalid"));
				product.setReportDate(sdf.format(sdfDB.parse(rs.getString("requestdate"))));
				product.setLotNumber(input.getLotNumber());
				product.setSource("Receiving Inspection");
				lstProductHistory.add(product);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductHistoryDao getLotNumber : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductHistoryDao getProductHistoryFromReceivingInspection  : "+ex.getMessage());
			}
		}
		return lstProductHistory;
	}
	
	public List<ProductHistory> getProductHistoryFromProductionIssue(ProductHistoryInput input)
	{
		List<ProductHistory> lstProductHistory = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			String query = "SELECT productName, machinename, drawingno, typeoftool, b.versionnumber versionnumber, b.originalid originalid, a.issueDate issueDate"
					+ " FROM tooling_issue_note a, tooling_issue_note_detail b where toolinglotnumber = ? and a.versionnumber = b.versionnumber and "
					+ "  a.originalid = b.originalid ";
			if(input.getFromDate1() != null && input.getToDate1() != null)
			{
				query = query + " and issueDate between ? and ?";
			}
			con = datasource.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, input.getLotNumber());
			if(input.getFromDate1() != null && input.getToDate1() != null)
			{
				pstmt.setDate(2, input.getFromDate1());
				pstmt.setDate(3, input.getToDate1());
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ProductHistory product = new ProductHistory();
				product.setProductName(rs.getString("productName"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setDrawingNo(rs.getString("drawingno"));
				product.setMachineType(rs.getString("machinename"));
				product.setRevisionNumber(rs.getInt("versionnumber"));
				product.setId(rs.getInt("originalid"));
				product.setReportDate(sdf.format(sdfDB.parse(rs.getString("issueDate"))));
				product.setLotNumber(input.getLotNumber());
				product.setSource("Production Issue");
				lstProductHistory.add(product);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductHistoryDao getProductHistoryFromProductionIssue : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductHistoryDao getProductHistoryFromProductionIssue  : "+ex.getMessage());
			}
		}
		return lstProductHistory;
	}
	
	public List<ProductHistory> getProductHistoryFromProductionReturn(ProductHistoryInput input)
	{
		List<ProductHistory> lstProductHistory = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			String query = "SELECT productName, machinetype, drawingno, typeoftool,b.revisionNumber revisionNumber, b.originalid originalid, returndate"
					+ " FROM tooling_production_return_note a, tooling_production_return_note_detail b where toolinglotnumber = ? and"
					+ " a.revisionNumber = b.revisionNumber and a.originalid = b.originalid ";
			if(input.getFromDate1() != null && input.getToDate1() != null)
			{
				query = query + " and returndate between ? and ?";
			}
			con = datasource.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, input.getLotNumber());
			if(input.getFromDate1() != null && input.getToDate1() != null)
			{
				pstmt.setDate(2, input.getFromDate1());
				pstmt.setDate(3, input.getToDate1());
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ProductHistory product = new ProductHistory();
				product.setProductName(rs.getString("productName"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setDrawingNo(rs.getString("drawingno"));
				product.setMachineType(rs.getString("machinetype"));
				product.setRevisionNumber(rs.getInt("revisionNumber"));
				product.setReportDate(sdf.format(sdfDB.parse(rs.getString("returndate"))));
				product.setId(rs.getInt("originalid"));
				product.setLotNumber(input.getLotNumber());
				product.setSource("Product Return");
				lstProductHistory.add(product);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductHistoryDao getProductHistoryFromProductionIssue : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductHistoryDao getProductHistoryFromProductionIssue  : "+ex.getMessage());
			}
		}
		return lstProductHistory;
	}
	
	public List<ProductHistory> getProductHistoryFromPeriodicInspection(ProductHistoryInput input)
	{
		List<ProductHistory> lstProductHistory = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			String query = "SELECT productName, machinetype, drawingno, typeoftool,b.revisionNumber revisionNumber, b.originalid originalid, requestdate"
					+ " FROM tooling_periodical_inspection_request a, tooling_periodical_inspection_request_detail b"
					+ "   where toolinglotnumber = ? and a.revisionNumber = b.revisionNumber and a.originalid = b.originalid ";
			
			if(input.getFromDate1() != null && input.getToDate1() != null)
			{
				query = query + " and requestdate between ? and ?";
			}
			con = datasource.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, input.getLotNumber());
			if(input.getFromDate1() != null && input.getToDate1() != null)
			{
				pstmt.setDate(2, input.getFromDate1());
				pstmt.setDate(3, input.getToDate1());
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ProductHistory product = new ProductHistory();
				product.setProductName(rs.getString("productName"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setDrawingNo(rs.getString("drawingno"));
				product.setMachineType(rs.getString("machinetype"));
				product.setRevisionNumber(rs.getInt("revisionNumber"));
				product.setId(rs.getInt("originalid"));
				product.setReportDate(sdf.format(sdfDB.parse(rs.getString("requestdate"))));
				product.setLotNumber(input.getLotNumber());
				product.setSource("Periodic Inspection");
				lstProductHistory.add(product);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductHistoryDao getProductHistoryFromPeriodicInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductHistoryDao getProductHistoryFromPeriodicInspection  : "+ex.getMessage());
			}
		}
		return lstProductHistory;
	}
	
	public List<ProductHistory> getProductHistoryFromPeriodicReport(ProductHistoryInput input)
	{
		List<ProductHistory> lstProductHistory = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			String query = "SELECT productName, machinetype, drawingno, typeoftool,b.revisionNumber revisionNumber, b.originalid originalid, reportdate"
					+ "   FROM periodic_inspection_report a,periodic_inspection_report_detail b where toolinglotnumber = ? "
					+ " and a.revisionNumber = b.revisionNumber and a.originalid = b.originalid ";
			if(input.getFromDate1() != null && input.getToDate1() != null)
			{
				query = query + " and reportdate between ? and ?";
			}
			con = datasource.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, input.getLotNumber());
			if(input.getFromDate1() != null && input.getToDate1() != null)
			{
				pstmt.setDate(2, input.getFromDate1());
				pstmt.setDate(3, input.getToDate1());
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ProductHistory product = new ProductHistory();
				product.setProductName(rs.getString("productName"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setDrawingNo(rs.getString("drawingno"));
				product.setMachineType(rs.getString("machinetype"));
				product.setRevisionNumber(rs.getInt("revisionNumber"));
				product.setId(rs.getInt("originalid"));
				product.setReportDate(sdf.format(sdfDB.parse(rs.getString("reportdate"))));
				product.setLotNumber(input.getLotNumber());
				product.setSource("Periodic Inspection Report");
				lstProductHistory.add(product);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductHistoryDao getProductHistoryFromPeriodicReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductHistoryDao getProductHistoryFromPeriodicReport  : "+ex.getMessage());
			}
		}
		return lstProductHistory;
	}
	
}
