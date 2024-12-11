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
import com.tiim.model.Product;
import com.tiim.model.RejectedProduct;
import com.tiim.model.Stock;
import com.tiim.model.ToolSerialNumber;
import com.tiim.model.ToolingPeriodicInspectionReport;
import com.tiim.model.ToolingReturnNote;
import com.tiim.util.TiimUtil;

@Repository
public class ToolingPeriodicInspectionReportDao {

	@Autowired
	DataSource datasource;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	GetMinAcceptedQty minAcceptedQty;
	
	@Autowired
	RejectedProductDao rejectedProductDao;
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	ToolingReceiptNoteDao receiptDao;
	
	@Autowired
	ToolSerialNumberDao toolSerialNumberDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	java.util.Date cDate = new java.util.Date();
	
	public int getIntialValue()
	{
		int reportNo = 0;
		reportNo = getMaxOriginalId();
		reportNo++;
		/*Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(reportno) reportno FROM periodic_inspection_report");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				reportNo = rs.getInt("reportno");
			}
			reportNo++;
			
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
		}*/
		
		return reportNo;	
	}
	
	public String addPeriodicInspectionReport(ToolingPeriodicInspectionReport periodicReport, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		int toolingReportId = 0;
		try
		{			
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into periodic_inspection_report(reportdate, requestNo, requestdate, requestedBy, reportBy, branchname, originalid)"
						+ "values(?, ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
				//,Statement.RETURN_GENERATED_KEYS
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(1, date);
				pstmt.setInt(2, periodicReport.getRequestNo());
				pstmt.setString(3, sdfDB.format(sdf.parse(periodicReport.getRequestDate())));
				pstmt.setString(4, periodicReport.getRequestedBy());
				pstmt.setString(5, periodicReport.getReportedBy());
				pstmt.setString(6, periodicReport.getBranchName());
				int originalId = getMaxOriginalId();
				originalId++;
				pstmt.setInt(7, originalId);
				periodicReport.setOriginalId(originalId);
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					toolingReportId = rs.getInt(1);
				}
				periodicReport.setReportNo(toolingReportId);
				
				periodicReport.setRevisionNumber(1);
				//updateOriginalId(periodicReport);
				msg = addPeriodicInspectionReportDetail(periodicReport);
				updatePeriodicRequestStatus(periodicReport,1);
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("periodicReport.page", null,null));
				history.setDescription(messageSource.getMessage("periodicReport.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling periodical inspection detail in periodic_inspection_report table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling periodical inspection detail in periodic_inspection_report table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String addPeriodicInspectionReportDetail(ToolingPeriodicInspectionReport periodicReport)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		String intervalQty = "";
		String damagedQty = "";
		String goodQty = "";
		String status = "";
		try
		{
			con = datasource.getConnection();
			for(int i = 0; i< periodicReport.getToolingDetailId().length;i++)
			{
				pstmt1 = con.prepareStatement("Select requestdetailid, requestno, toolinglotnumber, typeoftool, productname, drawingno, UOM, tabletproducedqty,"
						+ " toolingstatus, machinetype, toolingrequestqty  from tooling_periodical_inspection_request_detail Where requestdetailid = ?");
				pstmt1.setInt(1, periodicReport.getToolingDetailId()[i]);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())
				{
					try
					{
						//int acceptedQty = minAcceptedQty.getMinAcceptedQty(rs.getString("productname"), rs.getString("drawingno"), rs.getString("machinetype"), rs.getString("typeoftool"));
						int acceptedQty = minAcceptedQty.getMinAcceptedQty(rs.getString("toolinglotnumber"));
						goodQty = TiimUtil.ValidateNull(periodicReport.getGoodIntervalQty()[i]);
						Stock stock = stockDao.getStock(rs.getString("toolinglotnumber"), periodicReport.getBranchName());
						if(goodQty == null || "".equals(goodQty))
						{
							goodQty = "0";
						}
						if(Integer.parseInt(goodQty) >= acceptedQty)
						{
							status = "Good";
						}else
						if(stock != null && stock.getStockQty() > 0)
						{
							if(stock.getStockQty() >= acceptedQty)
							{
								status = "Good";
							}else
							{
								status = "Damaged";
							}
						}
						else 
						{
								status = "Damaged";
						}
						
						
						
						
						pstmt = con.prepareStatement("insert into periodic_inspection_report_detail(reportNo, toolinglotnumber, typeoftool, productname ,drawingno ,UOM ,"
								+ " tabletproducedqty , toolingstatus , intervalqty ,toolingstatusafterinspection, toolcondition,machinetype, remark, requestdetailid, "
								+ " toolingrequestqty, damagedqty,goodqty ,revisionNumber, originalid, damagedserialnumber) "
								+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setInt(1, periodicReport.getReportNo());
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("toolinglotnumber")));
						pstmt.setString(3, TiimUtil.ValidateNull(rs.getString("typeoftool")));
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("productname")));
						pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(6, TiimUtil.ValidateNull(rs.getString("UOM")));
						pstmt.setLong(7, rs.getLong("tabletproducedqty"));
						pstmt.setString(8, TiimUtil.ValidateNull(rs.getString("toolingstatus")));
						
						periodicReport.setLotNumber(rs.getString("toolinglotnumber"));
						periodicReport.setTypeOfTool(rs.getString("typeoftool"));
						periodicReport.setProductName(rs.getString("productname"));
						periodicReport.setDrawingNo(rs.getString("drawingno"));
						periodicReport.setUom(rs.getString("UOM"));
						periodicReport.setMachineType(rs.getString("machinetype"));
						periodicReport.setToolingRequestQty(rs.getInt("toolingrequestqty"));
						intervalQty = periodicReport.getIntervalQty()[i];
						if(intervalQty == null || "".equals(intervalQty))
						{
							intervalQty = "0";
						}
						pstmt.setString(9, intervalQty);
						pstmt.setString(10, status);
						//pstmt.setString(10, TiimUtil.ValidateNull(periodicReport.getInspectionStatusAfterInspection()[i]).trim());
						if(periodicReport.getCondition() != null && periodicReport.getCondition().length > 0 && periodicReport.getCondition().length == (i+1)  )
							pstmt.setString(11, TiimUtil.ValidateNull(periodicReport.getCondition()[i]).trim());
						else
							pstmt.setString(11, "");
						pstmt.setString(12, TiimUtil.ValidateNull(rs.getString("machinetype")).trim());
						if(periodicReport.getRemark() != null && periodicReport.getRemark().length > 0 && periodicReport.getRemark().length == (i+1)  )
						{
							pstmt.setString(13, TiimUtil.ValidateNull(periodicReport.getRemark()[i]).trim());
						}else
						{
							pstmt.setString(13, "");
						}
						pstmt.setInt(14, periodicReport.getToolingDetailId()[i]);
						
						pstmt.setInt(15, rs.getInt("toolingrequestqty"));
						damagedQty = TiimUtil.ValidateNull(periodicReport.getDamagedIntervalQty()[i]);
						if(damagedQty == null || "".equals(damagedQty))
						{
							damagedQty = "0";
						}
						pstmt.setString(16, damagedQty);
						pstmt.setString(17, goodQty);
						pstmt.setInt(18, periodicReport.getRevisionNumber());
						pstmt.setInt(19, periodicReport.getOriginalId());
						pstmt.setString(20, periodicReport.getDamagedSerialNumber());
						pstmt.executeUpdate();
						
						addSerialNumber(periodicReport, Integer.parseInt(damagedQty), Integer.parseInt(goodQty));

						Product product = productDao.getProductIntervalQnty(rs.getString("productname"), rs.getString("drawingno"));
					//	long toolLife = Integer.parseInt(product.getToolingLife()) * Integer.parseInt(goodQty);
						long intervalQnty = Integer.parseInt(product.getServiceIntervalQty()) * Integer.parseInt(goodQty);
						receiptDao.updateReceiptIntervalQnty(rs.getString("toolinglotnumber"), intervalQnty);
						
						if(status.equalsIgnoreCase("Good"))
						{
								storeTransaction(periodicReport, i, "Add");
						}else
						{
							RejectedProduct reject = new RejectedProduct();
							reject.setDrawingNumber(periodicReport.getDrawingNo());
							reject.setMachineName(periodicReport.getMachineType());
							reject.setProductName(periodicReport.getProductName());
							reject.setTypeOfTool(periodicReport.getTypeOfTool());
							reject.setToolingId(periodicReport.getReportNo());
							reject.setLotNumber(periodicReport.getLotNumber());
							reject.setSerialNumber(periodicReport.getDamagedSerialNumber());
							reject.setSource("Tool Periodic Inspection Report");
							rejectedProductDao.addRejectedProduct(reject);
						}
						
					}catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when storing the data to periodic_inspection_report_detail: "+ex.getMessage());
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
							System.out.println("Exception when closing the connection in tooling receipt note detail in periodic_inspection_report_detail table : "+ex.getMessage());
						}
					}
				}
				
				msg = "Saved Successfully";
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in periodic_inspection_report_detail table : "+ex.getMessage());
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
	
	public String updatePeriodicInspectionReport(ToolingPeriodicInspectionReport periodicReport, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		int toolingReportId = 0;
		ToolingPeriodicInspectionReport obj = getPeriodicReportDetail(periodicReport.getReportNo());
		obj.setBranchName(periodicReport.getBranchName());
		if(obj.getInspectionStatusAfterInspection1().equalsIgnoreCase("Good"))
		{
			storeTransaction(obj, 0, "Sub");
		}
		else
		{
			rejectedProductDao.deleteRejectProduct(obj.getLotNumber());
		}
		try
		{			
			getRequestVersions(periodicReport);
			con = datasource.getConnection();
			pstmt = con.prepareStatement("insert into periodic_inspection_report(reportdate, requestNo, requestdate, requestedBy, reportBy, branchname, revisionNumber, originalid)"
					+ "values(?, ?, ?, ?, ?, ?,?, ?)",Statement.RETURN_GENERATED_KEYS);
			//,Statement.RETURN_GENERATED_KEYS
			Calendar calendar = Calendar.getInstance();
		    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			pstmt.setDate(1, date);
			pstmt.setInt(2, periodicReport.getRequestNo());
			pstmt.setString(3, sdfDB.format(sdf.parse(periodicReport.getRequestDate())));
			pstmt.setString(4, periodicReport.getRequestedBy());
			pstmt.setString(5, periodicReport.getReportedBy());
			pstmt.setString(6, periodicReport.getBranchName());
			pstmt.setInt(7, periodicReport.getRevisionNumber());
			pstmt.setInt(8, periodicReport.getOriginalId());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next())
			{
				toolingReportId = rs.getInt(1);
			}
			periodicReport.setReportNo(toolingReportId);
				
			msg = addPeriodicInspectionReportDetail(periodicReport);
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("periodicReport.page", null,null));
				history.setDescription(messageSource.getMessage("periodicReport.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling periodical inspection detail in periodic_inspection_report table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling periodical inspection detail in periodic_inspection_report table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updatePeriodicInspectionReportOld(ToolingPeriodicInspectionReport periodicReport, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{			
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update periodic_inspection_report set reportdate = ?, requestNo = ?, requestdate = ?, requestedBy = ?, reportBy = ?"
						+ " where reportNo = ?");
				//,Statement.RETURN_GENERATED_KEYS
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(1, date);
				pstmt.setInt(2, periodicReport.getRequestNo());
				pstmt.setString(3, sdfDB.format(sdf.parse(periodicReport.getRequestDate())));
				pstmt.setString(4, TiimUtil.ValidateNull(periodicReport.getRequestedBy()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(periodicReport.getReportedBy()).trim());
				pstmt.setInt(6, periodicReport.getReportNo());
				pstmt.executeUpdate();
				periodicReport.setRequestNo(periodicReport.getReportNo());
				msg = updatedPeriodicInspectionReportDetail(periodicReport);
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("periodicReport.page", null,null));
				history.setDescription(messageSource.getMessage("periodicReport.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling periodical inspection detail in periodic_inspection_report table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling periodical inspection detail in periodic_inspection_report table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updatedPeriodicInspectionReportDetail(ToolingPeriodicInspectionReport periodicReport)
	{
		ToolingPeriodicInspectionReport obj = getPeriodicReportDetail(periodicReport.getReportNo());
		obj.setBranchName(periodicReport.getBranchName());
		storeTransaction(obj, 0, "Sub");
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		String intervalQty = "";
		String goodQty = "";
		String status = "";
		String damagedQty = "";
		try
		{
			con = datasource.getConnection();

			for(int i = 0; i< periodicReport.getToolingDetailId().length;i++)
			{
				pstmt1 = con.prepareStatement("Select requestdetailid, requestno, toolinglotnumber, typeoftool, productname, drawingno, UOM, tabletproducedqty,"
						+ " toolingstatus, machinetype  from tooling_periodical_inspection_request_detail Where requestdetailid = ?");
				pstmt1.setInt(1, periodicReport.getToolingDetailId()[i]);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())
				{
					try
					{
						//int acceptedQty = minAcceptedQty.getMinAcceptedQty(rs.getString("productname"), rs.getString("drawingno"), rs.getString("machinetype"), rs.getString("typeoftool"));
						int acceptedQty = minAcceptedQty.getMinAcceptedQty(rs.getString("toolinglotnumber"));
						goodQty = TiimUtil.ValidateNull(periodicReport.getGoodIntervalQty()[i]);
						if(goodQty == null || "".equals(goodQty))
						{
							goodQty = "0";
						}
						if(Integer.parseInt(goodQty) >= acceptedQty)
						{
							status = "Good";
						}else
						{
							status = "Damaged";
						}
						pstmt = con.prepareStatement("update periodic_inspection_report_detail set reportNo = ?, toolinglotnumber = ?, typeoftool = ?, productname = ? ,drawingno = ? ,UOM = ?,"
								+ " tabletproducedqty = ?, toolingstatus = ?, intervalqty =?, toolingstatusafterinspection = ?, toolcondition = ?,machinetype = ?, remark  = ?,damagedqty = ?,goodqty = ?, damagedserialnumber = ? "
								+ " where reportDetailNo = ?");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setInt(1, periodicReport.getReportNo());
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("toolinglotnumber")));
						pstmt.setString(3, TiimUtil.ValidateNull(rs.getString("typeoftool")));
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("productname")));
						pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(6, TiimUtil.ValidateNull(rs.getString("UOM")));
						pstmt.setLong(7, rs.getLong("tabletproducedqty"));
						pstmt.setString(8, TiimUtil.ValidateNull(rs.getString("toolingstatus")));
						pstmt.setString(9, rs.getString("damagedserialnumber"));
						
						periodicReport.setLotNumber(rs.getString("toolinglotnumber"));
						periodicReport.setTypeOfTool(rs.getString("typeoftool"));
						periodicReport.setProductName(rs.getString("productname"));
						periodicReport.setDrawingNo(rs.getString("drawingno"));
						periodicReport.setUom(rs.getString("UOM"));
						periodicReport.setMachineType(rs.getString("machinetype"));
						periodicReport.setToolingRequestQty(rs.getInt("toolingrequestqty"));
						
						intervalQty = TiimUtil.ValidateNull(periodicReport.getIntervalQty()[i]);
							if(intervalQty == null || "".equals(intervalQty))
							{
								intervalQty = "0";
							}
						pstmt.setString(9, intervalQty);
						pstmt.setString(10, TiimUtil.ValidateNull(periodicReport.getInspectionStatusAfterInspection()[i]).trim());
						if(periodicReport.getCondition() != null && periodicReport.getCondition().length > 0 && periodicReport.getCondition().length == (i+1) )
							pstmt.setString(11, TiimUtil.ValidateNull(periodicReport.getCondition()[i]).trim());
						else
							pstmt.setString(11, "");
						pstmt.setString(12, TiimUtil.ValidateNull(rs.getString("machinetype")));
						if(periodicReport.getRemark() != null && periodicReport.getRemark().length > 0 && periodicReport.getRemark().length == (i+1) )
							pstmt.setString(13, TiimUtil.ValidateNull(periodicReport.getRemark()[i]).trim());
						else
							pstmt.setString(13, "");
						pstmt.setInt(14, periodicReport.getReportDetailNo()[i]);
						damagedQty = TiimUtil.ValidateNull(periodicReport.getDamagedIntervalQty()[i]);
						if(damagedQty == null || "".equals(damagedQty))
						{
							damagedQty = "0";
						}
						pstmt.setString(15, damagedQty);
						pstmt.setString(16, goodQty);
					//	System.out.println("periodicReport.getReportDetailNo()[i]: "+periodicReport.getReportDetailNo()[i]);
						pstmt.executeUpdate();
						
						if(status.equalsIgnoreCase("Good"))
						{
								storeTransaction(periodicReport, i, "Add");
						}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when storing the data to periodic_inspection_report_detail: "+ex.getMessage());
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
							System.out.println("Exception when closing the connection in tooling receipt note detail in periodic_inspection_report_detail table : "+ex.getMessage());
						}
					}
				}
				msg = "Updated Successfully";
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in periodic_inspection_report_detail table : "+ex.getMessage());
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
	
	public List<ToolingPeriodicInspectionReport> getPeriodicInspectionReport(String reportNo)
	{
		List<ToolingPeriodicInspectionReport> lstPeriodicInspectionReport = new ArrayList<ToolingPeriodicInspectionReport>();

		ToolingPeriodicInspectionReport objInspection = new ToolingPeriodicInspectionReport();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(reportNo != null && !"".equals(reportNo))
			{
			    pstmt = con.prepareStatement("select * from (Select reportdate, requestNo, requestdate, requestedBy, reportBy, reportNo, originalid "
			    		+ " from periodic_inspection_report Where reportNo like '%"+reportNo+"%' order by reportNo desc)  x group by originalid");
			}
			else
			{
				pstmt = con.prepareStatement("select * from (Select reportdate, requestNo, requestdate, requestedBy, reportBy, reportNo, originalid "
			    		+ " from periodic_inspection_report order by reportNo desc)  x group by originalid");
			}
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingPeriodicInspectionReport();
				objInspection.setRequestNo(rs.getInt("requestNo"));
				objInspection.setRequestDate(sdf.format(sdfDB.parse(rs.getString("requestdate"))));
				objInspection.setRequestedBy(TiimUtil.ValidateNull(rs.getString("requestedBy")).trim());
				objInspection.setReportDate(sdf.format(sdfDB.parse(rs.getString("reportdate"))));
				objInspection.setReportedBy(TiimUtil.ValidateNull(rs.getString("reportBy")).trim());
				objInspection.setReportNo(rs.getInt("reportNo"));
				objInspection.setOriginalId(rs.getInt("originalid"));
				lstPeriodicInspectionReport.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspectionReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspectionReport : "+ex.getMessage());
			}
		}
		return lstPeriodicInspectionReport;
	}
	
	public List<ToolingPeriodicInspectionReport> getPeriodicInspectionReportSearch(String reportNo)
	{
		List<ToolingPeriodicInspectionReport> lstPeriodicInspectionReport = new ArrayList<ToolingPeriodicInspectionReport>();

		ToolingPeriodicInspectionReport objInspection = new ToolingPeriodicInspectionReport();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(reportNo != null && !"".equals(reportNo))
			{
			    pstmt = con.prepareStatement("select * from (Select reportdate, requestNo, requestdate, requestedBy, reportBy, reportNo, originalid "
			    		+ " from periodic_inspection_report Where originalid like '%"+reportNo+"%' order by reportNo desc)  x group by originalid");
			}
			else
			{
				pstmt = con.prepareStatement("select * from (Select reportdate, requestNo, requestdate, requestedBy, reportBy, reportNo, originalid "
			    		+ " from periodic_inspection_report order by reportNo desc)  x group by originalid");
			}
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingPeriodicInspectionReport();
				objInspection.setRequestNo(rs.getInt("requestNo"));
				objInspection.setRequestDate(sdf.format(sdfDB.parse(rs.getString("requestdate"))));
				objInspection.setRequestedBy(TiimUtil.ValidateNull(rs.getString("requestedBy")).trim());
				objInspection.setReportDate(sdf.format(sdfDB.parse(rs.getString("reportdate"))));
				objInspection.setReportedBy(TiimUtil.ValidateNull(rs.getString("reportBy")).trim());
				objInspection.setReportNo(rs.getInt("reportNo"));
				objInspection.setOriginalId(rs.getInt("originalid"));
				lstPeriodicInspectionReport.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspectionReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspectionReport : "+ex.getMessage());
			}
		}
		return lstPeriodicInspectionReport;
	}
	
	
	public List<ToolingPeriodicInspectionReport> getPeriodicInspectionReport()
	{
		List<ToolingPeriodicInspectionReport> lstPeriodicInspectionReport = new ArrayList<ToolingPeriodicInspectionReport>();

		ToolingPeriodicInspectionReport objInspection = new ToolingPeriodicInspectionReport();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select * from (Select reportdate, requestNo, requestdate, requestedBy, reportBy, reportNo, originalid "
			    		+ " from periodic_inspection_report order by reportNo desc )  x group by originalid");
			//pstmt.setInt(1, reportNo);
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingPeriodicInspectionReport();
				objInspection.setRequestNo(rs.getInt("requestNo"));
				objInspection.setRequestDate(sdf.format(sdfDB.parse(rs.getString("requestdate"))));
				objInspection.setRequestedBy(TiimUtil.ValidateNull(rs.getString("requestedBy")).trim());
				objInspection.setReportDate(sdf.format(sdfDB.parse(rs.getString("reportdate"))));
				objInspection.setReportedBy(TiimUtil.ValidateNull(rs.getString("reportBy")).trim());
				objInspection.setReportNo(rs.getInt("reportNo"));
				lstPeriodicInspectionReport.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspectionReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspectionReport : "+ex.getMessage());
			}
		}
		return lstPeriodicInspectionReport;
	
	}
	
	public List<ToolingPeriodicInspectionReport> getPeriodicInspectionReportDetail(int reportNo)
	{
		List<ToolingPeriodicInspectionReport> lstPeriodicInspectionReport = new ArrayList<ToolingPeriodicInspectionReport>();

		ToolingPeriodicInspectionReport objInspection = new ToolingPeriodicInspectionReport();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select reportNo, toolinglotnumber, typeoftool, productname ,drawingno ,UOM , "
					+ "  tabletproducedqty , toolingstatus , intervalqty ,toolingstatusafterinspection, toolcondition,machinetype, reportDetailNo, remark, "
					+ " requestdetailid, toolingrequestqty, goodqty, damagedqty, damagedserialnumber "
			    		+ " from periodic_inspection_report_detail where reportNo = ?");
			pstmt.setInt(1, reportNo);
			System.out.println("reportNo: "+reportNo);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingPeriodicInspectionReport();
				objInspection.setReportNo(rs.getInt("reportNo"));
				objInspection.setLotNumber(rs.getString("toolinglotnumber"));
				objInspection.setTypeOfTool(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				objInspection.setProductName(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				objInspection.setDrawingNo(TiimUtil.ValidateNull(rs.getString("drawingno")).trim());
				objInspection.setUom(TiimUtil.ValidateNull(rs.getString("UOM")).trim());
				objInspection.setProducedQuantity(rs.getLong("tabletproducedqty"));
				objInspection.setToolingStatus(TiimUtil.ValidateNull(rs.getString("toolingstatus")).trim());
				objInspection.setIntervalQty1(rs.getInt("intervalqty"));
				objInspection.setInspectionStatusAfterInspection1(TiimUtil.ValidateNull(rs.getString("toolingstatusafterinspection")).trim());
				objInspection.setCondition1(TiimUtil.ValidateNull(rs.getString("toolcondition")).trim());
				objInspection.setMachineType(TiimUtil.ValidateNull(rs.getString("machinetype")).trim());
				objInspection.setReportDetailNo1(rs.getInt("reportDetailNo"));
				objInspection.setRemark1(TiimUtil.ValidateNull(rs.getString("remark")).trim());
				objInspection.setRequestDetailId(rs.getString("requestdetailid"));
				objInspection.setRequestQuantity(rs.getInt("toolingrequestqty"));
				objInspection.setGoodIntervalQty1(rs.getLong("goodqty"));
				objInspection.setDamagedIntervalQty1(rs.getLong("damagedqty"));
				objInspection.setDamagedSerialNumber(rs.getString("damagedserialnumber"));
				lstPeriodicInspectionReport.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspectionReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspectionReport : "+ex.getMessage());
			}
		}
		return lstPeriodicInspectionReport;
	}
	
	public List<ToolingPeriodicInspectionReport> getRejectPeriodicInspectionReportDetail(String drawingNumber)
	{
		List<ToolingPeriodicInspectionReport> lstPeriodicInspectionReport = new ArrayList<ToolingPeriodicInspectionReport>();

		ToolingPeriodicInspectionReport objInspection = new ToolingPeriodicInspectionReport();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			
			String query = "Select a.reportNo reportNo, branchname, reportdate, toolinglotnumber, typeoftool, productname ,drawingno ,UOM ,   tabletproducedqty , toolingstatus ,"
					+ " intervalqty ,toolingstatusafterinspection, toolcondition,machinetype, reportDetailNo, remark, requestdetailid, toolingrequestqty,damagedserialnumber  "
					+ " from periodic_inspection_report_detail a, periodic_inspection_report b where b.reportNo = a.reportNo and toolingstatus = ? ";
			
			if(drawingNumber != null && "".equals(drawingNumber))
			{
				query = query + "and drawingno = ?";
			}
				
			pstmt = con.prepareStatement(query);
					
			pstmt.setString(1, "Damaged");
			if(drawingNumber != null && "".equals(drawingNumber))
			{
				pstmt.setString(2,drawingNumber );
			}
			
			if(drawingNumber != null && "".equals(drawingNumber))
			{
				query = query + "and drawingno = ?";
			}
				
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingPeriodicInspectionReport();
				objInspection.setReportNo(rs.getInt("reportNo"));
				objInspection.setRequestDate(rs.getString("reportdate"));
				objInspection.setBranchName(rs.getString("branchname"));
				objInspection.setLotNumber(rs.getString("toolinglotnumber"));
				objInspection.setTypeOfTool(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				objInspection.setProductName(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				objInspection.setDrawingNo(TiimUtil.ValidateNull(rs.getString("drawingno")).trim());
				objInspection.setUom(TiimUtil.ValidateNull(rs.getString("UOM")).trim());
				objInspection.setProducedQuantity(rs.getLong("tabletproducedqty"));
				objInspection.setToolingStatus(TiimUtil.ValidateNull(rs.getString("toolingstatus")).trim());
				objInspection.setIntervalQty1(rs.getInt("intervalqty"));
				objInspection.setInspectionStatusAfterInspection1(TiimUtil.ValidateNull(rs.getString("toolingstatusafterinspection")).trim());
				objInspection.setCondition1(TiimUtil.ValidateNull(rs.getString("toolcondition")).trim());
				objInspection.setMachineType(TiimUtil.ValidateNull(rs.getString("machinetype")).trim());
				objInspection.setReportDetailNo1(rs.getInt("reportDetailNo"));
				objInspection.setRemark1(TiimUtil.ValidateNull(rs.getString("remark")).trim());
				objInspection.setRequestDetailId(rs.getString("requestdetailid"));
				objInspection.setRequestQuantity(rs.getInt("toolingrequestqty"));
				objInspection.setDamagedSerialNumber(rs.getString("damagedserialnumber"));
				lstPeriodicInspectionReport.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspectionReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspectionReport : "+ex.getMessage());
			}
		}
		return lstPeriodicInspectionReport;
	}
	
	public ToolingPeriodicInspectionReport getPeriodicReportDetail(int reportNo)
	{
		ToolingPeriodicInspectionReport objInspection = new ToolingPeriodicInspectionReport();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select reportNo, toolinglotnumber, typeoftool, productname ,drawingno ,UOM , "
					+ "  tabletproducedqty , toolingstatus , intervalqty ,toolingstatusafterinspection, toolcondition,machinetype, reportDetailNo, remark, requestdetailid, toolingrequestqty,goodqty, damagedqty,damagedserialnumber "
			    		+ " from periodic_inspection_report_detail where reportNo = ?");
			pstmt.setInt(1, reportNo);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection.setReportNo(rs.getInt("reportNo"));
				objInspection.setLotNumber(rs.getString("toolinglotnumber"));
				objInspection.setTypeOfTool(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				objInspection.setProductName(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				objInspection.setDrawingNo(TiimUtil.ValidateNull(rs.getString("drawingno")).trim());
				objInspection.setUom(TiimUtil.ValidateNull(rs.getString("UOM")).trim());
				objInspection.setProducedQuantity(rs.getLong("tabletproducedqty"));
				objInspection.setToolingStatus(TiimUtil.ValidateNull(rs.getString("toolingstatus")).trim());
				objInspection.setIntervalQty1(rs.getInt("intervalqty"));
				objInspection.setInspectionStatusAfterInspection1(TiimUtil.ValidateNull(rs.getString("toolingstatusafterinspection")).trim());
				objInspection.setCondition1(TiimUtil.ValidateNull(rs.getString("toolcondition")).trim());
				objInspection.setMachineType(TiimUtil.ValidateNull(rs.getString("machinetype")).trim());
				objInspection.setReportDetailNo1(rs.getInt("reportDetailNo"));
				objInspection.setRemark1(TiimUtil.ValidateNull(rs.getString("remark")).trim());
				objInspection.setRequestDetailId(rs.getString("requestdetailid"));
				objInspection.setToolingRequestQty(rs.getInt("toolingrequestqty"));
				String strGoodQty[] = {rs.getString("goodqty")};
				objInspection.setDamagedSerialNumber(rs.getString("damagedserialnumber"));
				objInspection.setGoodIntervalQty(strGoodQty);
	
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspectionReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspectionReport : "+ex.getMessage());
			}
		}
		return objInspection;
	}
	
	public ToolingPeriodicInspectionReport editPeriodicInspectionReport(int reportNo)
	{
		ToolingPeriodicInspectionReport objInspection = new ToolingPeriodicInspectionReport();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select reportdate, requestNo, requestdate, requestedBy, reportBy, reportNo "
			    		+ " from periodic_inspection_report where reportNo = ?");
			pstmt.setInt(1, reportNo);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingPeriodicInspectionReport();
				objInspection.setRequestNo(rs.getInt("requestNo"));
				objInspection.setRequestDate(sdf.format(sdfDB.parse(rs.getString("requestdate"))));
				objInspection.setRequestedBy(TiimUtil.ValidateNull(rs.getString("requestedBy")).trim());
				objInspection.setReportDate(sdf.format(sdfDB.parse(rs.getString("reportdate"))));
				objInspection.setReportedBy(TiimUtil.ValidateNull(rs.getString("reportBy")).trim());
				objInspection.setReportNo(rs.getInt("reportNo"));			
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspectionReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspectionReport : "+ex.getMessage());
			}
		}
		return objInspection;
	
	}
	
	public ToolingPeriodicInspectionReport getPeriodicInspectionReport(int reportNo)
	{
		ToolingPeriodicInspectionReport objInspection = new ToolingPeriodicInspectionReport();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select reportdate, requestNo, requestdate, requestedBy, reportBy, reportNo "
			    		+ " from periodic_inspection_report where originalId = ?");
			pstmt.setInt(1, reportNo);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingPeriodicInspectionReport();
				objInspection.setRequestNo(rs.getInt("requestNo"));
				objInspection.setRequestDate(sdf.format(sdfDB.parse(rs.getString("requestdate"))));
				objInspection.setRequestedBy(TiimUtil.ValidateNull(rs.getString("requestedBy")).trim());
				objInspection.setReportDate(sdf.format(sdfDB.parse(rs.getString("reportdate"))));
				objInspection.setReportedBy(TiimUtil.ValidateNull(rs.getString("reportBy")).trim());
				objInspection.setReportNo(rs.getInt("reportNo"));			
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getPeriodicInspectionReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getPeriodicInspectionReport : "+ex.getMessage());
			}
		}
		return objInspection;
	
	}
	
	public String deletePeriodicInspection(int originalId, int requestNo, String branch, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{

			ToolingPeriodicInspectionReport obj = getPeriodicReportDetail(requestNo);
			obj.setBranchName(branch);
			if(obj.getToolingStatus().equalsIgnoreCase("Good"))
			{
				storeTransaction(obj, 0, "Sub");
			}
			
			con = datasource.getConnection();
			updatePeriodicRequestStatus(getPeriodicInspectionReport(originalId), 0);
			pstmt = con.prepareStatement("delete from periodic_inspection_report where originalid = ?");
			pstmt.setInt(1, originalId);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement("delete from periodic_inspection_report_detail where originalid = ?");
			pstmt.setInt(1, originalId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("periodicReport.page", null,null));
			history.setDescription(messageSource.getMessage("periodicReport.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the detail in periodic_inspection_report and periodic_inspection_report_detail table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in delete the periodic_inspection_report in reportNo and periodic_inspection_report_detail table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	private void storeTransaction(ToolingPeriodicInspectionReport periodicReport, int index, String flag)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			{
				/*pstmt1 = con.prepareStatement("Select requestdetailid, requestno, toolinglotnumber, typeoftool, productname, drawingno, UOM, tabletproducedqty,"
						+ " toolingstatus, machinetype  from tooling_periodical_inspection_request_detail Where requestdetailid = ?");
				pstmt1.setInt(1, periodicReport.getToolingDetailId()[index]);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())*/
				{
					try
					{
						pstmt = con.prepareStatement("insert into transaction(productname, drawingno, toolinglotnumber, UOM, stockqty,machinename, "
								+ " transactiondate, Source )"
								+ "values(?, ?, ?, ?, ?, ?, ?, ?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(periodicReport.getProductName()));
						pstmt.setString(2, TiimUtil.ValidateNull(periodicReport.getDrawingNo()));
						pstmt.setString(3, periodicReport.getLotNumber());
						pstmt.setString(4, TiimUtil.ValidateNull(periodicReport.getUom()));
						if("Add".equalsIgnoreCase(flag))
						{
							//pstmt.setLong(5, periodicReport.getToolingRequestQty());
							pstmt.setLong(5, Long.parseLong(periodicReport.getGoodIntervalQty()[index]));
						}else
						{
							pstmt.setLong(5, -Long.parseLong(periodicReport.getGoodIntervalQty()[index]));
						}
						pstmt.setString(6, TiimUtil.ValidateNull(periodicReport.getMachineType()));
						pstmt.setString(7, sdf.format(cDate));
						pstmt.setString(8, "PeriodicInspectionReport");
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
				
				msg = "Saved Successfully";
			}
			storeStock(periodicReport, index, flag);
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
	
	private void storeStock(ToolingPeriodicInspectionReport periodicReport, int index, String flag)
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
						pstmt.setString(1, TiimUtil.ValidateNull(periodicReport.getLotNumber()));
						pstmt.setString(2, TiimUtil.ValidateNull(periodicReport.getTypeOfTool()));
						pstmt.setString(3, periodicReport.getProductName());
						pstmt.setString(4, TiimUtil.ValidateNull(periodicReport.getDrawingNo()));
						pstmt.setString(5, periodicReport.getMachineType());
						returnQty = periodicReport.getToolingRequestQty();
						long damagedQty = returnQty - Integer.parseInt(periodicReport.getGoodIntervalQty()[index]);
						//returnQty = Long.parseLong(periodicReport.getGoodIntervalQty()[index]);
						/*if("Sub".equalsIgnoreCase(flag))
						{
							returnQty = periodicInspection.getRequestQuantity();
						}else
						if(toolingReturnNote.getReturnQty()[index] != null && !"".equals(toolingReturnNote.getReturnQty()[index]))
						{
							returnQty = Integer.parseInt(periodicInspection.getRequestQuantity());
						}*/
						
						stock.setToolingLotNumber(periodicReport.getLotNumber());
						stock.setTypeOfTool(TiimUtil.ValidateNull(periodicReport.getTypeOfTool()).trim());
						stock.setProductName(TiimUtil.ValidateNull(periodicReport.getProductName()).trim());
						stock.setDrawingNo(TiimUtil.ValidateNull(periodicReport.getDrawingNo()).trim());
						stock.setMachineName(periodicReport.getMachineType());
						stock.setUom(periodicReport.getUom());
						stock.setStockQty(returnQty);
						stock.setBranch(periodicReport.getBranchName());
						stock.setGoodQty(Integer.parseInt(periodicReport.getGoodIntervalQty()[index]));
						stock.setDamagedQty(damagedQty);
						ResultSet rs1 = pstmt.executeQuery();
						if(rs1.next()){
							System.out.println(flag+", "+returnQty+", "+rs1.getInt("stockQty"));
							totalStock = rs1.getInt("stockQty") - periodicReport.getDamagedIntervalQty1();
							
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
	
	public void updateOriginalId(ToolingPeriodicInspectionReport periodicInspection)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update periodic_inspection_report set originalid = ? "
						+ " where reportNo = ?");

				pstmt.setInt(1, periodicInspection.getOriginalId());
				pstmt.setInt(2, periodicInspection.getReportNo());
				pstmt.executeUpdate();
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in ToolingPeriodic report updateOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in ToolingPeriodic report updateOriginalId : "+ex.getMessage());
			}
		}
	}
	
	/**
	 * Get original Id from tooling_receiving_request
	 * @param requestId
	 * @return
	 */
	private void getRequestVersions(ToolingPeriodicInspectionReport periodicInspection)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("select originalid, revisionNumber from periodic_inspection_report "
						+ " where reportNo = ?");

				pstmt.setInt(1, periodicInspection.getReportNo());
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
	
	public boolean isIntegratedWithPeriodicRequest(int originalId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				/*pstmt = con.prepareStatement("SELECT reportNo FROM periodic_inspection_report where requestNo in "
						+ " (SELECT requestNo FROM tooling_periodical_inspection_request where originalId = ?)");*/
				
				pstmt = con.prepareStatement("SELECT reportNo FROM periodic_inspection_report where requestNo = ?");
				pstmt.setInt(1, originalId);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					return true;
				}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when ToolingPeriodicInspectionReport  isIntegratedWithPeriodicRequest method : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in ToolingPeriodicInspectionReport isIntegratedWithPeriodicRequest : "+ex.getMessage());
			}
		}
	
	
		return false;
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
				pstmt = con.prepareStatement("select max(originalid) originalid from periodic_inspection_report");
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					originalId = rs.getInt("originalid");
				}
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in periodic_inspection_report getMaxOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in periodic_inspection_report getMaxOriginalId : "+ex.getMessage());
			}
		}
	
		return originalId;
	}

	private void updatePeriodicRequestStatus(ToolingPeriodicInspectionReport periodicReport, int status)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_periodical_inspection_request set isreport = ? where originalId = ?");
				pstmt.setInt(1, status);
				pstmt.setInt(2, periodicReport.getRequestNo());
				pstmt.executeUpdate();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in updatePeriodicRequestStatus  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling receipt note detail in updateToolingRequestStatus table : "+ex.getMessage());
			}
		}
	
	}
	
	private void addSerialNumber(ToolingPeriodicInspectionReport periodicReport, int damagedQty, int goodQty) {
		ToolSerialNumber toolSerialNumber = new ToolSerialNumber();
		toolSerialNumber.setLotNumber(periodicReport.getToolingLotNumber());
		toolSerialNumber.setModule("Tooling Periodic Inspection");
		toolSerialNumber.setAcceptQty(goodQty);
		toolSerialNumber.setRejectQty(damagedQty);
		toolSerialNumber.setSerialNumber(periodicReport.getDamagedSerialNumber());
		toolSerialNumberDao.addSerialNumber(toolSerialNumber);
	}
}



