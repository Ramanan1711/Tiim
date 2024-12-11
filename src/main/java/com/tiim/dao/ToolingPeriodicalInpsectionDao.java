package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.tiim.model.Stock;
import com.tiim.model.ToolingIssueNote;
import com.tiim.model.ToolingPeriodicInspection;
import com.tiim.util.TiimUtil;

@Repository
public class ToolingPeriodicalInpsectionDao {

	@Autowired
	DataSource datasource;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	TransactionHistoryDao historyDao;
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ProductDao productDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	java.util.Date cDate = new java.util.Date();
	
	public int getIntialValue()
	{
		int requestNo = 0;
		requestNo = getMaxOriginalId();
		requestNo++;
		/*Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(requestNo) requestno FROM tooling_periodical_inspection_request");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				requestNo = rs.getInt("requestno");
			}
			requestNo++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in delete the periodic_inspection_report in reportNo and periodic_inspection_report_detail table : "+ex.getMessage());
			}
		}
		*/
		return requestNo;	
	}
	
	public String addPeriodicalInspection(ToolingPeriodicInspection periodicalInspection, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		int toolingInspectionId = 0;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into tooling_periodical_inspection_request(requestdate, requestby,branchname, originalid)"
						+ "values(?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
				//,Statement.RETURN_GENERATED_KEYS
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(1, date);
				pstmt.setString(2, periodicalInspection.getRequestedBy());
				pstmt.setString(3, periodicalInspection.getBranchName());
				int originalId = getMaxOriginalId();
				originalId++;
				pstmt.setInt(4, originalId);
				periodicalInspection.setOriginalId(originalId);
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					toolingInspectionId = rs.getInt(1);
				}
				periodicalInspection.setRequestNo(toolingInspectionId);
				
				periodicalInspection.setRevisionNumber(1);
				//updateOriginalId(periodicalInspection);
				msg = addToolingPeriodicInspectionDetail(periodicalInspection);
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("periodicRequest.page", null,null));
				history.setDescription(messageSource.getMessage("periodicRequest.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling periodical inspection detail in tooling_periodical_inspection_request table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling periodical inspection detail in tooling_periodical_inspection_request table : "+ex.getMessage());
			}
		}
		return msg;
	
	}

	public String addToolingPeriodicInspectionDetail(ToolingPeriodicInspection periodicInspection)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		String dueQty = "";
		String stockId = "";
		try
		{
			con = datasource.getConnection();
			for(int i = 0; i< periodicInspection.getStockId().length;i++)
			{
				stockId = periodicInspection.getStockId()[i];
				if(stockId == null || "".equals(stockId))
				{
					stockId = "0";
				}
				pstmt1 = con.prepareStatement("select toolinglotnumber, typeoftool, productname, drawingno, UOM, machinetype from stock where stockId = ? ");
				pstmt1.setString(1, stockId);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())
				{
					try
					{
						pstmt = con.prepareStatement("insert into tooling_periodical_inspection_request_detail(requestno, toolinglotnumber, typeoftool, productname ,drawingno ,UOM ,"
								+ " tabletproducedqty , toolingstatus , toolingdueqty , machinetype, toolingrequestqty, originalid, revisionNumber) "
								+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
						//,Statement.RETURN_GENERATED_KEYS
						
						pstmt.setInt(1, periodicInspection.getRequestNo());
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("toolinglotnumber")));
						pstmt.setString(3, TiimUtil.ValidateNull(rs.getString("typeoftool")));
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("productname")));
						pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(6, TiimUtil.ValidateNull(rs.getString("UOM")));
						pstmt.setLong(7, periodicInspection.getTabProducedQty()[i]);
						pstmt.setString(8, TiimUtil.ValidateNull(periodicInspection.getToolingStatus()[i]));
						
						periodicInspection.setLotNumber(rs.getString("toolinglotnumber"));
						periodicInspection.setTypeOfTool(rs.getString("typeoftool"));
						periodicInspection.setProductName(rs.getString("productname"));
						periodicInspection.setDrawingNo(rs.getString("drawingno"));
						periodicInspection.setUom(rs.getString("UOM"));
						periodicInspection.setMachineType(rs.getString("machinetype"));
						   if(periodicInspection.getToolingDueQty()[i] != null && !"".equals(periodicInspection.getToolingDueQty()[i]))
						   {
							   dueQty = periodicInspection.getToolingDueQty()[i];
						   }
						   else
						   {
							   dueQty = "0";
						   }
						pstmt.setString(9, dueQty);
						//pstmt.setString(10, TiimUtil.ValidateNull(periodicInspection.getToolingHistory()[i]));
						pstmt.setString(10, TiimUtil.ValidateNull(rs.getString("machinetype")));
						pstmt.setInt(11, periodicInspection.getRequestQuantity());
						pstmt.setInt(12, periodicInspection.getOriginalId());
						pstmt.setInt(13, periodicInspection.getRevisionNumber());
						pstmt.executeUpdate();
						storeTransaction(periodicInspection, i, "Sub");
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when storing the data to tooling_periodical_inspection_request_detail: "+ex.getMessage());
					}
					finally
					{
						try
						{
							if(pstmt != null)
							{
								pstmt.close();
							}
						}catch(Exception ex)
						{
							System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_periodical_inspection_request_detail table : "+ex.getMessage());
						}
					}
				}
				msg = "Saved Successfully";
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in tooling_periodical_inspection_request_detail table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt1 != null)
				{
					pstmt1.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "+ex.getMessage());
			}
		}
		return msg;
	 }

	public String updatePeriodicInspection(ToolingPeriodicInspection periodicInspection, int userId)
	{
		getRequestVersions(periodicInspection);
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String msg = "";
		int toolingInspectionId = 0;
		try
		{
			storeTransaction(getPeriodicInspectionRequestDetail(periodicInspection.getRequestNo()),0,"Add");
			con = datasource.getConnection();
			pstmt = con.prepareStatement("insert into tooling_periodical_inspection_request(requestdate, requestby,branchname, originalid, revisionNumber)"
					+ "values(?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			//,Statement.RETURN_GENERATED_KEYS
			Calendar calendar = Calendar.getInstance();
		    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			pstmt.setDate(1, date);
			pstmt.setString(2, periodicInspection.getRequestedBy());
			pstmt.setString(3, periodicInspection.getBranchName());
			pstmt.setInt(4, periodicInspection.getOriginalId());
			pstmt.setInt(5, periodicInspection.getRevisionNumber());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next())
			{
				toolingInspectionId = rs.getInt(1);
			}
			periodicInspection.setRequestNo(toolingInspectionId);
			
			msg = addToolingPeriodicInspectionDetail(periodicInspection);
							
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("periodicRequest.page", null,null));
				history.setDescription(messageSource.getMessage("periodicRequest.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in tooling_receiving_inspection table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}
				
				if(pstmt2 != null)
				{
					pstmt2.close();
				}
				
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in updating detail in tooling_receiving_inspection table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updatePeriodicInspectionDetail(ToolingPeriodicInspection periodicInspection)
	{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String dueQty = "";
		String msg = "";
		String stockId = "";
		try
		{
			con = datasource.getConnection();
			for(int i = 0; i< periodicInspection.getStockId().length;i++)
			{
				stockId = periodicInspection.getStockId()[i];
				if(stockId == null || "".equals(stockId))
				{
					stockId = "0";
				}
				pstmt1 = con.prepareStatement("select toolinglotnumber, typeoftool, productname, drawingno, UOM, machinetype from stock where stockId = ? ");
				pstmt1.setString(1, stockId);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())
				{
					try
					{
						pstmt = con.prepareStatement("insert into tooling_periodical_inspection_request_detail(requestno, toolinglotnumber, machinetype, productname ,drawingno ,UOM ,"
								+ " tabletproducedqty , toolingstatus , toolingdueqty , typeoftool, toolingrequestqty) "
								+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setInt(1, periodicInspection.getRequestNo());
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("toolinglotnumber")));
						pstmt.setString(3, TiimUtil.ValidateNull(rs.getString("machinetype")));
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("productname")));
						pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(6, TiimUtil.ValidateNull(rs.getString("UOM")));
						pstmt.setLong(7, periodicInspection.getTabProducedQty()[i]);
						pstmt.setString(8, TiimUtil.ValidateNull(periodicInspection.getToolingStatus()[i]));
						
						periodicInspection.setLotNumber(rs.getString("toolinglotnumber"));
						periodicInspection.setTypeOfTool(rs.getString("typeoftool"));
						periodicInspection.setProductName(rs.getString("productname"));
						periodicInspection.setDrawingNo(rs.getString("drawingno"));
						periodicInspection.setUom(rs.getString("UOM"));
						periodicInspection.setMachineType(rs.getString("machinetype"));
						
						   if(periodicInspection.getToolingDueQty()[i] != null && !"".equals(periodicInspection.getToolingDueQty()[i]))
						   {
							   dueQty = periodicInspection.getToolingDueQty()[i];
						   }
						   else
						   {
							   dueQty = "0";
						   }
						pstmt.setString(9, dueQty);
						//pstmt.setString(10, TiimUtil.ValidateNull(periodicInspection.getToolingHistory()[i]));
						pstmt.setString(10, TiimUtil.ValidateNull(rs.getString("typeoftool")));
						pstmt.setInt(11, periodicInspection.getRequestQuantity());
						pstmt.executeUpdate();
						
						storeTransaction(periodicInspection, i, "Sub");
						
						/*pstmt = con.prepareStatement("update tooling_periodical_inspection_request_detail set requestno = ?, toolinglotnumber = ?, toolingname = ?, productname = ? ,drawingno = ? , UOM = ? ,"
								+ " tabletproducedqty = ? , toolingstatus = ? , toolingdueqty = ? ,toolinghistory = ?, toolingserialnumber = ? "
								+ " where requestdetailid = ?");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setInt(1, periodicInspection.getRequestNo());
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("toolinglotnumber")));
						pstmt.setString(3, TiimUtil.ValidateNull(rs.getString("toolingname")));
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("productname")));
						pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(6, TiimUtil.ValidateNull(rs.getString("UOM")));
						pstmt.setInt(7, rs.getInt("tabletproducedqty"));
						pstmt.setString(8, periodicInspection.getToolingStatus()[i]);
						pstmt.setInt(9, periodicInspection.getInspectionDueQty()[i]);
						pstmt.setString(10, periodicInspection.getToolingHistory()[i]);
						pstmt.setString(11, periodicInspection.getToolingSerialNo()[i]);
						pstmt.setInt(12, periodicInspection.getToolingDetailId()[i]);
						pstmt.executeUpdate();*/
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when storing & updating the data to tooling_periodical_inspection_request_detail: "+ex.getMessage());
					}
					finally
					{
						try
						{
							if(pstmt != null)
							{
								pstmt.close();
							}
						}catch(Exception ex)
						{
							System.out.println("Exception when closing the connection in when updating tooling receipt note detail in tooling_periodical_inspection_request_detail table : "+ex.getMessage());
						}
					}
				}
				
				msg = "Saved Successfully";
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in tooling_periodical_inspection_request_detail table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt1 != null)
				{
					pstmt1.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "+ex.getMessage());
			}
		}
		return msg;
	 
	}
	
	public List<ToolingPeriodicInspection> getPeriodicInspection(String requestNo)
	{
		List<ToolingPeriodicInspection> lstPeriodicInspection = new ArrayList<ToolingPeriodicInspection>();

		ToolingPeriodicInspection objInspection = new ToolingPeriodicInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(requestNo != null && !"".equals(requestNo))
			{
			    pstmt = con.prepareStatement("select * from (Select requestno,isreport, requestdate, requestby, originalid "
			    		+ " from tooling_periodical_inspection_request Where requestno like '%"+requestNo+"%' order by requestno desc)  x group by originalid ");
			}
			else
			{
				pstmt = con.prepareStatement("select * from (Select requestno,isreport, requestdate, requestby, originalid "
			    		+ " from tooling_periodical_inspection_request order by requestno desc)  x group by originalid ");
			}
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingPeriodicInspection();
				objInspection.setRequestNo(rs.getInt("requestno"));
				objInspection.setReportStatus(rs.getInt("isreport"));
				objInspection.setRequestDate(sdf.format(sdfDB.parse(rs.getString("requestdate"))));
				objInspection.setRequestedBy(TiimUtil.ValidateNull(rs.getString("requestby")));
				objInspection.setOriginalId(rs.getInt("originalid"));
				lstPeriodicInspection.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspection : "+ex.getMessage());
			}
		}
		return lstPeriodicInspection;
	
	}
	
	public List<ToolingPeriodicInspection> getPeriodicInspectionDetail(int requestNo, String branch)
	{
		List<ToolingPeriodicInspection> lstPeriodicInspection = new ArrayList<ToolingPeriodicInspection>();

		ToolingPeriodicInspection objInspection = new ToolingPeriodicInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		String stockId = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select Period.requestdetailid, Period.requestno, Period.toolinglotnumber, Period.typeoftool, Period.productname, Period.drawingno," +
					"Period.UOM, Period.tabletproducedqty, Period.toolingstatus, Period.toolingdueqty, Period.toolinghistory, Period.machinetype, stock.stockId, toolingrequestqty, stock.stockqty stockqty " +
					"from tooling_periodical_inspection_request_detail Period " +
					"left outer join stock on stock.toolinglotnumber = Period.toolinglotnumber " +
					"where requestno = ? and stock.branch = ? ;");
			pstmt.setInt(1, requestNo);	
			pstmt.setString(2, branch);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingPeriodicInspection();
				objInspection.setRequestNo(rs.getInt("requestno"));
				objInspection.setToolingDetailId1(rs.getInt("requestdetailid"));
				objInspection.setLotNumber(rs.getString("toolinglotnumber"));
				objInspection.setTypeOfTool(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				objInspection.setProductName(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				objInspection.setDrawingNo(TiimUtil.ValidateNull(rs.getString("drawingno")).trim());
				objInspection.setUom(TiimUtil.ValidateNull(rs.getString("UOM")).trim());
				System.out.println(rs.getLong("tabletproducedqty"));
				objInspection.setTabProducedQty1(rs.getLong("tabletproducedqty"));
				objInspection.setToolingStatus1(TiimUtil.ValidateNull(rs.getString("toolingstatus")).trim());
				objInspection.setInspectionDueQty1(rs.getLong("toolingdueqty"));
				objInspection.setToolingHistory1(TiimUtil.ValidateNull(rs.getString("toolinghistory")).trim());
				objInspection.setMachineType(TiimUtil.ValidateNull(rs.getString("machinetype")).trim());
				objInspection.setRequestQuantity(rs.getInt("toolingrequestqty"));
				
				stockId = rs.getString("stockId");
				if(stockId == null || "".equals(stockId))
				{
					stockId = "0";
				}
				objInspection.setStockId1(stockId);
				objInspection.setStockQty(rs.getInt("stockqty"));
				
				lstPeriodicInspection.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspectionDetail : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspectionDetail : "+ex.getMessage());
			}
		}
		return lstPeriodicInspection;
	
	}
	
	public ToolingPeriodicInspection getPeriodicInspectionRequestDetail(int requestNo)
	{

		ToolingPeriodicInspection objInspection = new ToolingPeriodicInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		String stockId = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select Period.requestdetailid, Period.requestno, Period.toolinglotnumber, Period.typeoftool, Period.productname, Period.drawingno," +
					"Period.UOM, Period.tabletproducedqty, Period.toolingstatus, Period.toolingdueqty, Period.toolinghistory, Period.machinetype, stock.stockId, "
					+ " toolingrequestqty, stock.stockqty stockqty, stock.branch branch " +
					"from tooling_periodical_inspection_request_detail Period " +
					"left outer join stock on stock.toolinglotnumber = Period.toolinglotnumber " +
					"where requestno = ?;");
			pstmt.setInt(1, requestNo);		
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection.setRequestNo(rs.getInt("requestno"));
				objInspection.setToolingDetailId1(rs.getInt("requestdetailid"));
				objInspection.setLotNumber(rs.getString("toolinglotnumber"));
				objInspection.setTypeOfTool(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				objInspection.setProductName(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				objInspection.setDrawingNo(TiimUtil.ValidateNull(rs.getString("drawingno")).trim());
				objInspection.setUom(TiimUtil.ValidateNull(rs.getString("UOM")).trim());
				objInspection.setTabProducedQty1(rs.getLong("tabletproducedqty"));
				objInspection.setToolingStatus1(TiimUtil.ValidateNull(rs.getString("toolingstatus")).trim());
				objInspection.setInspectionDueQty1(rs.getLong("toolingdueqty"));
				objInspection.setToolingHistory1(TiimUtil.ValidateNull(rs.getString("toolinghistory")).trim());
				objInspection.setMachineType(TiimUtil.ValidateNull(rs.getString("machinetype")).trim());
				objInspection.setRequestQuantity(rs.getInt("toolingrequestqty"));
				objInspection.setBranchName(rs.getString("branch"));
				System.out.println("branch: "+objInspection.getBranchName());
				stockId = rs.getString("stockId");
				if(stockId == null || "".equals(stockId))
				{
					stockId = "0";
				}
				objInspection.setStockId1(stockId);
				objInspection.setStockQty(rs.getInt("stockqty"));
							
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspectionRequestDetail : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspectionDetail : "+ex.getMessage());
			}
		}
		return objInspection;
	
	}
	
	public ToolingPeriodicInspection editPeriodicInspection(int requestNo)
	{
		ToolingPeriodicInspection objInspection = new ToolingPeriodicInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select requestno, requestdate, requestby "
			    		+ " from tooling_periodical_inspection_request where requestno = ?");
			pstmt.setInt(1, requestNo);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				objInspection = new ToolingPeriodicInspection();
				objInspection.setRequestNo(rs.getInt("requestno"));
				objInspection.setRequestDate(rs.getString("requestdate"));
				objInspection.setRequestedBy(rs.getString("requestby"));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspection : "+ex.getMessage());
			}
		}
		return objInspection;
	
	}
	
	public ToolingPeriodicInspection editPeriodicInspectionSearch(int requestNo)
	{
		ToolingPeriodicInspection objInspection = new ToolingPeriodicInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select requestno, requestdate, requestby, originalid "
			    		+ " from tooling_periodical_inspection_request where requestno = ?");
			pstmt.setInt(1, requestNo);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				objInspection = new ToolingPeriodicInspection();
				objInspection.setRequestNo(rs.getInt("originalid"));
				objInspection.setRequestDate(rs.getString("requestdate"));
				objInspection.setRequestedBy(rs.getString("requestby"));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspection : "+ex.getMessage());
			}
		}
		return objInspection;
	
	}
	
	public String deletePeriodicInspection(int originalId, int requestNo, int userId, String branchName)
	{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			ToolingPeriodicInspection obj = getPeriodicInspectionRequestDetail(requestNo);
			obj.setBranchName(branchName);
			storeTransaction(obj,0,"Add");
			
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from tooling_periodical_inspection_request where originalId = ?");
			pstmt.setInt(1, originalId);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement("delete from tooling_periodical_inspection_request_detail where originalId = ?");
			pstmt.setInt(1, originalId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("periodicRequest.page", null,null));
			history.setDescription(messageSource.getMessage("periodicRequest.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the detail in tooling_periodical_inspection_request_detail and tooling_periodical_inspection_request table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in delete the detail in tooling_receipt_product and tooling_receipt_note table : "+ex.getMessage());
			}
		}
		
		return msg;
	}

	public List<ToolingPeriodicInspection> getAutoToolingPeriodicalRequestNote(String requestNo, String approvalFlag)
	{
		List<ToolingPeriodicInspection> lstRequestNote = new ArrayList<ToolingPeriodicInspection>();

		ToolingPeriodicInspection objInspection = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if("1".equalsIgnoreCase(approvalFlag))
			{
				pstmt = con.prepareStatement("select * from (Select requestno, requestdate, requestby, originalid from tooling_periodical_inspection_request where requestno like '"+requestNo+"%'"
						+ " and a.approvalflag = ? and originalid not in(SELECT originalid FROM periodic_inspection_report) order by requestno desc)  x group by originalid");
				pstmt.setInt(1, 1);
			}else
			{
				pstmt = con.prepareStatement("select * from (Select requestno, requestdate, requestby, originalid from tooling_periodical_inspection_request where requestno like '"+requestNo+"%'"
						+ " and originalid not in(SELECT originalid FROM periodic_inspection_report) order by requestno desc)  x group by originalid");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingPeriodicInspection();
				objInspection.setRequestNo(rs.getInt("requestno"));
				objInspection.setRequestDate(rs.getString("requestdate"));
				objInspection.setRequestedBy(rs.getString("requestby"));
				lstRequestNote.add(objInspection);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the auto complete tooling_periodical_inspection_request list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the auto complete tooling_periodical_inspection_request list : "+ex.getMessage());
			}
		}
		return lstRequestNote;	
	}
	
	public List<ToolingPeriodicInspection> getToolingPeriodicalRequestDetail(String approvalFlag)
	{
		List<ToolingPeriodicInspection> lstRequestNote = new ArrayList<ToolingPeriodicInspection>();

		ToolingPeriodicInspection objInspection = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if("1".equalsIgnoreCase(approvalFlag))
			{
				pstmt = con.prepareStatement("select * from (Select a.requestno requestno, requestdate, requestby, machinetype, productname, drawingno, typeoftool, a.originalid originalid from tooling_periodical_inspection_request a, tooling_periodical_inspection_request_detail b where a.requestno = b.requestno and "
						+ " a.approvalflag = ? and   a.originalid not in(SELECT requestNo FROM periodic_inspection_report) order by a.requestno desc)  x group by originalid");
				pstmt.setInt(1, 1);
			}else
			{
				pstmt = con.prepareStatement("select * from (Select a.requestno requestno, requestdate, requestby, machinetype, productname, drawingno, typeoftool, a.originalid originalid from tooling_periodical_inspection_request a, tooling_periodical_inspection_request_detail b where a.requestno = b.requestno and "
						+ "  a.originalid not in(SELECT requestNo FROM periodic_inspection_report) order by a.requestno desc)  x group by originalid");
				
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingPeriodicInspection();
				objInspection.setOriginalId(rs.getInt("originalid"));
				objInspection.setRequestNo(rs.getInt("requestno"));
				objInspection.setRequestDate(rs.getString("requestdate"));
				objInspection.setRequestedBy(rs.getString("requestby"));
				objInspection.setMachineType(rs.getString("machinetype"));
				objInspection.setProductName(rs.getString("productname"));
				objInspection.setDrawingNo(rs.getString("drawingno"));
				objInspection.setTypeOfTool(rs.getString("typeoftool"));
				objInspection.setUploadPath(productDao.getProductUploadedPath(rs.getString("productname"), rs.getString("drawingno"), rs.getString("machinetype"), rs.getString("typeoftool")));
				
				lstRequestNote.add(objInspection);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the auto complete tooling_periodical_inspection_request list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the auto complete tooling_periodical_inspection_request list : "+ex.getMessage());
			}
		}
		return lstRequestNote;	
	}
	
	public List<ToolingPeriodicInspection> getPendingPeriodicalRequestNote()
	{
		List<ToolingPeriodicInspection> lstRequestNote = new ArrayList<ToolingPeriodicInspection>();

		ToolingPeriodicInspection objInspection = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select * from (Select requestno, requestdate, requestby, originalid from tooling_periodical_inspection_request where "
					+ " approvalflag = 1 and originalid not in(SELECT requestNo FROM periodic_inspection_report) order by requestno desc)  x group by originalid ");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingPeriodicInspection();
				objInspection.setRequestNo(rs.getInt("originalid"));
				objInspection.setRequestDate(sdf.format(sdfDB.parse(rs.getString("requestdate"))));
				objInspection.setRequestedBy(rs.getString("requestby"));
				objInspection.setOriginalId(rs.getInt("originalid"));
				
				lstRequestNote.add(objInspection);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the auto complete tooling_periodical_inspection_request list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the auto complete tooling_periodical_inspection_request list : "+ex.getMessage());
			}
		}
		return lstRequestNote;	
	}
	
	public List<Integer> getAutoPeriodicInspection(String inspectionId)
	{
		List<Integer> lstInspection = new ArrayList<Integer>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			
			//pstmt = con.prepareStatement("Select toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno from tooling_receipt_note where grnno LIKE '"+grn+"%' order by grnno");
			pstmt = con.prepareStatement("select a.requestno requestno from tooling_periodical_inspection_request a, tooling_periodical_inspection_request_detail b  where b.toolingstatus = 'Damaged' and a.requestno  LIKE '"+inspectionId+"%' and a.requestno = b.requestno;");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				lstInspection.add(rs.getInt("requestno"));
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting the getAutoPeriodicInspection : "+e.getMessage());
		}
		return lstInspection;
	}
	
	private void storeTransaction(ToolingPeriodicInspection periodicInspection, int index, String flag)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		long returnQty = 0;
		try
		{
			con = datasource.getConnection();
			{
				
				{
					try
					{
						pstmt = con.prepareStatement("insert into transaction(productname, drawingno, toolinglotnumber, UOM, stockqty,machinename, "
								+ " transactiondate, Source )"
								+ "values(?, ?, ?, ?, ?, ?, ?, ?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(periodicInspection.getProductName()));
						pstmt.setString(2, TiimUtil.ValidateNull(periodicInspection.getDrawingNo()));
						pstmt.setString(3, periodicInspection.getLotNumber());
						pstmt.setString(4, TiimUtil.ValidateNull(periodicInspection.getUom()));
						if("Sub".equalsIgnoreCase(flag))
						{
							returnQty = periodicInspection.getRequestQuantity();
						}else
						{
							returnQty = -(periodicInspection.getRequestQuantity());
						}
						
						pstmt.setLong(5, returnQty);
						pstmt.setString(6, TiimUtil.ValidateNull(periodicInspection.getMachineType()));
						pstmt.setString(7, sdf.format(cDate));
						pstmt.setString(8, "PeriodicInspection");
						pstmt.executeUpdate();
					}catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when storing the data to transaction in Periodic Inspection report: "+ex.getMessage());
					}finally
					{
						try
						{
							if(pstmt != null)
							{
								pstmt.close();
							}
						}catch(Exception ex)
						{
							System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "+ex.getMessage());
						}
					}
				}
			}
			//storeStock(periodicInspection, index, flag);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in tooling_receiving_inspection_details table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt1 != null)
				{
					pstmt1.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "+ex.getMessage());
			}
		}

	
	}
	
	private void storeStock(ToolingPeriodicInspection periodicInspection, int index, String flag)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		long totalStock = 0;
		long returnQty = 0;
		Stock stock = new Stock();
		try
		{
			con = datasource.getConnection();
			{
				
				{
					try
					{
						pstmt = con.prepareStatement("SELECT stockQty, stockId FROM stock where toolinglotnumber = ? and typeoftool = ? and productname = ? and drawingno = ? and machinetype = ?");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(periodicInspection.getLotNumber()));
						pstmt.setString(2, TiimUtil.ValidateNull(periodicInspection.getTypeOfTool()));
						pstmt.setString(3, periodicInspection.getProductName());
						pstmt.setString(4, TiimUtil.ValidateNull(periodicInspection.getDrawingNo()));
						pstmt.setString(5, periodicInspection.getMachineType());
						returnQty = periodicInspection.getRequestQuantity();
						/*if("Sub".equalsIgnoreCase(flag))
						{
							returnQty = periodicInspection.getRequestQuantity();
						}else
						if(toolingReturnNote.getReturnQty()[index] != null && !"".equals(toolingReturnNote.getReturnQty()[index]))
						{
							returnQty = Integer.parseInt(periodicInspection.getRequestQuantity());
						}*/
						
						stock.setToolingLotNumber(periodicInspection.getLotNumber());
						stock.setTypeOfTool(TiimUtil.ValidateNull(periodicInspection.getTypeOfTool()).trim());
						stock.setProductName(TiimUtil.ValidateNull(periodicInspection.getProductName()).trim());
						stock.setDrawingNo(TiimUtil.ValidateNull(periodicInspection.getDrawingNo()).trim());
						stock.setMachineName(periodicInspection.getMachineType());
						stock.setUom(periodicInspection.getUom());
						stock.setStockQty(returnQty);
						//System.out.println(periodicInspection.getBranchName());
						stock.setBranch(periodicInspection.getBranchName());
						ResultSet rs1 = pstmt.executeQuery();
						if(rs1.next()){
							System.out.println(flag+", "+returnQty+", "+rs1.getInt("stockQty"));
							if("Add".equalsIgnoreCase(flag))
							{
								totalStock = rs1.getInt("stockQty") + returnQty;
							}
							else
							{
								totalStock = rs1.getInt("stockQty") - returnQty;
							}
							stock.setStockId(rs1.getInt("stockId"));
							stock.setStockQty(totalStock);
							stockDao.updateStock(stock);
						}
						else
						{
							stockDao.storeStock(stock);
						}
						
					}catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when storing the data to transaction: "+ex.getMessage());
					}finally
					{
						try
						{
							if(pstmt != null)
							{
								pstmt.close();
							}
						}catch(Exception ex)
						{
							System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "+ex.getMessage());
						}
					}
				}
				
				msg = "Saved Successfully";
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in tooling_receiving_inspection_details table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt1 != null)
				{
					pstmt1.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "+ex.getMessage());
			}
		}
	}
	
	public void updateOriginalId(ToolingPeriodicInspection periodicInspection)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_periodical_inspection_request set originalid = ? "
						+ " where requestno = ?");

				pstmt.setInt(1, periodicInspection.getOriginalId());
				pstmt.setInt(2, periodicInspection.getRequestNo());
				pstmt.executeUpdate();
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in ToolingPeriodic Request updateOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in ToolingPeriodic request updateOriginalId : "+ex.getMessage());
			}
		}
	}
	
	/**
	 * Get original Id from tooling_receiving_request
	 * @param requestId
	 * @return
	 */
	private void getRequestVersions(ToolingPeriodicInspection periodicInspection)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("select originalId, revisionNumber from tooling_periodical_inspection_request "
						+ " where requestno = ?");

				pstmt.setInt(1, periodicInspection.getRequestNo());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					periodicInspection.setOriginalId(rs.getInt("originalId"));
					int revisionNumber = rs.getInt("revisionNumber");
					revisionNumber++;
					periodicInspection.setRevisionNumber(revisionNumber);
				}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in ToolingPeriodic request updateOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in ToolingPeriodic request updateOriginalId : "+ex.getMessage());
			}
		}
	
	}
	/**
	 * Get original Id from tooling_receiving_request
	 * @param requestId
	 * @return
	 */
	private int getMaxOriginalId()
	{
		int originalId = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("select max(originalid) originalid from tooling_periodical_inspection_request");
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					originalId = rs.getInt("originalid");
				}
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in tooling_periodical_inspection_request getMaxOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in tooling_periodical_inspection_request getMaxOriginalId : "+ex.getMessage());
			}
		}
	
		return originalId;
	}
}

