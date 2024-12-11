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
import com.tiim.model.ToolingInspection;
import com.tiim.model.ToolingReturnNote;
import com.tiim.util.TiimUtil;

@Repository
public class ToolingReturnDao {

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
	ToolSerialNumberDao toolSerialNumberDao;
	
	/*@Autowired
	ProductDao productDao;
	
	@Autowired
	ToolingReceiptNoteDao receiptDao;*/
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfDBFull = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
	java.util.Date cDate = new java.util.Date();
	
	public int getIntialValue()
	{
		int returnnoteId = 0;
		returnnoteId = getMaxOriginalId();
		returnnoteId++;
		/*Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(returnnoteid) returnnoteid FROM tooling_production_return_note");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				returnnoteId = rs.getInt("returnnoteid");
			}
			returnnoteId++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ToolingReturnDao  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in ToolingReturnDao getIntialValue : "+ex.getMessage());
			}
		}*/
		
		return returnnoteId;	
	}
	
	public String addToolingReturnNote(ToolingReturnNote toolingReturnNote, int userId, String approvalFlag)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";

		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into tooling_production_return_note(returndate, issueNo, issueDate, issuedBy, returnedby, branchname, originalid,cleaned_by )"
						+ "values(?, ?, ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
				//,Statement.RETURN_GENERATED_KEYS
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(1, date);
				pstmt.setInt(2, toolingReturnNote.getIssueId());
				pstmt.setString(3, sdfDB.format(sdf.parse(toolingReturnNote.getIssueDate())));
				pstmt.setString(4, TiimUtil.ValidateNull(toolingReturnNote.getIssueBy()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(toolingReturnNote.getReturnBy()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(toolingReturnNote.getBranchName()).trim());
				int originalId = getMaxOriginalId();
				originalId++;
				toolingReturnNote.setOriginalId(originalId);
				pstmt.setInt(7, originalId);
				pstmt.setString(8, toolingReturnNote.getCleanedBy());
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					toolingReturnNote.setReturnId(rs.getInt(1));
				}
				
				toolingReturnNote.setRevisionNumber(1);
				//updateOriginalId(toolingReturnNote);
				msg = addReturnDetail(toolingReturnNote, approvalFlag);
				msg = "Saved Successfully";
				updateToolingIssueStatus(toolingReturnNote, 1);
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("productionReturn.page", null,null));
				history.setDescription(messageSource.getMessage("productionReturn.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				updateClearanceNoStatus(toolingReturnNote.getToolingLotNumber1());
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the tooling return note initial value in getInitialValue table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling return note initial value in getInitialValue : "+ex.getMessage());
			}
		}
			
		return msg;
	}
	
	public void getMaxReturnId(int originalId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select max(returnnoteid) returnnoteid from tooling_production_return_note_detail where originalid = ?");
			pstmt.setInt(1, originalId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				int returnId = rs.getInt("returnnoteid");
				storeTransaction(getToolingReturnDetails(returnId), 0 , "Approve");
			}

		}catch(Exception ex)
		{
			System.out.println("Exception when getting the tooling_issue_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the tooling_issue_note list : "+ex.getMessage());
			}
		}
	
	}
	
	public String updateToolingReturnNote(ToolingReturnNote toolingReturnNote, String dbStatus, int userId, String approvalFlag)
	{
		getRequestVersions(toolingReturnNote);
		ToolingReturnNote obj = getToolingReturnDetails(toolingReturnNote.getReturnId());
		System.out.println("toolingReturnNote.getReturnId(): "+toolingReturnNote.getReturnId()+", "+obj.getToolingStatus1());
		if(obj.getToolingStatus1().equalsIgnoreCase("Good"))
		{
			storeTransaction(obj , 0, "Sub");
		}else
		{
			rejectedProductDao.deleteRejectProduct(obj.getToolingLotNumber1());
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("insert into tooling_production_return_note(returndate, issueNo, issueDate, issuedBy, returnedby, branchname, originalId, revisionNumber, cleaned_by )"
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			//,Statement.RETURN_GENERATED_KEYS
			Calendar calendar = Calendar.getInstance();
		    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			pstmt.setDate(1, date);
			pstmt.setInt(2, toolingReturnNote.getIssueId());
			pstmt.setString(3, sdfDB.format(sdf.parse(toolingReturnNote.getIssueDate())));
			pstmt.setString(4, TiimUtil.ValidateNull(toolingReturnNote.getIssueBy()).trim());
			pstmt.setString(5, TiimUtil.ValidateNull(toolingReturnNote.getReturnBy()).trim());
			pstmt.setString(6, TiimUtil.ValidateNull(toolingReturnNote.getBranchName()).trim());
		/*	int originalId = getMaxOriginalId();
			toolingReturnNote.setOriginalId(originalId);*/
			pstmt.setInt(7, toolingReturnNote.getOriginalId());
			pstmt.setInt(8, toolingReturnNote.getRevisionNumber());
			pstmt.setString(9, toolingReturnNote.getCleanedBy());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next())
			{
				toolingReturnNote.setReturnId(rs.getInt(1));
			}
	
			msg = addReturnDetail(toolingReturnNote, approvalFlag);
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("productionReturn.page", null,null));
				history.setDescription(messageSource.getMessage("productionReturn.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the tooling return note detail in tooling_production_return_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling return note detail in tooling_production_return_note table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String updateToolingReturnNoteOld(ToolingReturnNote toolingReturnNote, String dbStatus, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_production_return_note set returndate = ?, issueNo = ?, issueDate = ?, issuedBy = ?, returnedby = ?, cleaned_by=?"
						
						+ " where returnnoteid = ?");
				
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(1, date);
				pstmt.setInt(2, toolingReturnNote.getIssueId());
				pstmt.setString(3, sdfDB.format(sdf.parse(toolingReturnNote.getIssueDate())));
				pstmt.setString(4, TiimUtil.ValidateNull(toolingReturnNote.getIssueBy()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(toolingReturnNote.getReturnBy()).trim());
				
				pstmt.setInt(6, toolingReturnNote.getReturnId());
				pstmt.setString(7, toolingReturnNote.getCleanedBy());
				pstmt.executeUpdate();
				
				msg = updateToolingReturnDetail(toolingReturnNote);
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("productionReturn.page", null,null));
				history.setDescription(messageSource.getMessage("productionReturn.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the tooling return note detail in tooling_production_return_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling return note detail in tooling_production_return_note table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String addReturnDetail(ToolingReturnNote toolingReturnNote, String approvalFlag)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		String producedQty = "";				  
		String returnQty = "";
		String status = "";
		long totalStock = 0;
		try
		{
				con = datasource.getConnection();
			//	System.out.println("toolingReturnNote: "+toolingReturnNote.getToolingIssueDetailId().length);
				for(int i = 0; i < toolingReturnNote.getToolingIssueDetailId().length; i++)
				{
					pstmt1 = con.prepareStatement("Select toolingIssueDetailId, typeoftool, productname, machineName, drawingNo, batchQty,"
							+ " requestQty, toolinglotnumber, lastInspectionDate, nextDueQty, issuedQty, UOM from tooling_issue_note_detail"
							+ " where toolingIssueDetailId = ?");
					System.out.println(toolingReturnNote.getToolingIssueDetailId()[i]);
					pstmt1.setInt(1, toolingReturnNote.getToolingIssueDetailId()[i]);
					ResultSet rs = pstmt1.executeQuery();
					
					while(rs.next())
					{
						try
						{
							
							//int acceptedQty = minAcceptedQty.getMinAcceptedQty(rs.getString("productname"), rs.getString("drawingNo"), rs.getString("machineName"), rs.getString("typeoftool"));
							int acceptedQty = minAcceptedQty.getMinAcceptedQty(rs.getString("toolinglotnumber"));
							Stock stock = stockDao.getStock(rs.getString("toolinglotnumber"), toolingReturnNote.getBranchName());
							//System.out.println("within while loop..."+stock.getStockQty() );
							//if(toolingReturnNote.getToolingStatus()[i].equalsIgnoreCase("Good"))
							{
								//if(toolingReturnNote.getGoodQty() >= acceptedQty)
								if(stock != null && stock.getStockQty() > 0)
								{
									totalStock = stock.getStockQty() + toolingReturnNote.getGoodQty();
									if(totalStock >= acceptedQty)
									{
										status = "Good";
									}else
									{
										status = "Damaged";
										
									}
								}
								else 
								{
									if(toolingReturnNote.getGoodQty() >= acceptedQty)
									{
										status = "Good";
									}else
									{
										status = "Damaged";
										
									}
								}
									
								//toolingReturnNote.getToolingStatus()[i] = status;
							}
							
							pstmt = con.prepareStatement("insert into tooling_production_return_note_detail(returnnoteid, typeoftool, productname, machinetype, drawingNo, batchQty, "
									+ " producedqty, toolinglotnumber, lastInspectionDate, nextDueQty, issuedQty, UOM, returnedqty, toolingstatus, issueDetailId, batchnumber, originalId,"
									+ " revisionNumber,goodqty,damagedqty,damagedserialnumber, rejectedserialnumber, clearingsop)"
									+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
							pstmt.setInt(1, toolingReturnNote.getReturnId());
			
							pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
							pstmt.setString(3, TiimUtil.ValidateNull(rs.getString("productname")).trim());
							pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("machineName")).trim());
							pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("drawingNo")).trim());
							pstmt.setLong(6, rs.getLong("batchQty"));
							
							toolingReturnNote.setTypeOfTooling1(rs.getString("typeoftool"));
							toolingReturnNote.setProductName1(rs.getString("productname"));
							toolingReturnNote.setMachineName1(rs.getString("machineName"));
							toolingReturnNote.setDrawingNo1(rs.getString("drawingNo"));
							toolingReturnNote.setUOM1(rs.getString("UOM"));
							toolingReturnNote.setToolingLotNumber1(rs.getString("toolinglotnumber"));
							toolingReturnNote.setIssuedQty1(rs.getLong("issuedQty"));
							if(toolingReturnNote.getProducedQty()[i] != null && !"".equals(toolingReturnNote.getProducedQty()[i]))
								{
									producedQty = toolingReturnNote.getProducedQty()[i];
								}
								else
								{
									producedQty = "0";
								}

							pstmt.setString(7, producedQty);																																							  
							pstmt.setString(7, "0");
							pstmt.setString(8, rs.getString("toolinglotnumber"));
							pstmt.setDate(9, rs.getDate("lastInspectionDate"));
							pstmt.setLong(10, rs.getLong("nextDueQty"));
							pstmt.setLong(11, rs.getLong("issuedQty"));
							pstmt.setString(12, TiimUtil.ValidateNull(rs.getString("UOM")).trim());
								if(toolingReturnNote.getReturnQty()[i] != null && !"".equals(toolingReturnNote.getReturnQty()[i]))
								{
									returnQty = toolingReturnNote.getReturnQty()[i];
								}
								else
								{
									returnQty = "0";
								}
							pstmt.setString(13, returnQty);
							//pstmt.setString(14, TiimUtil.ValidateNull(toolingReturnNote.getToolingStatus()[i]).trim());
							pstmt.setString(14, TiimUtil.ValidateNull(status).trim());
							pstmt.setInt(15, toolingReturnNote.getToolingIssueDetailId()[i]);
							pstmt.setString(16, TiimUtil.ValidateNull(toolingReturnNote.getBatchNumber()[i]).trim());
							pstmt.setInt(17, toolingReturnNote.getOriginalId());
							pstmt.setInt(18, toolingReturnNote.getRevisionNumber());
							pstmt.setInt(19, toolingReturnNote.getGoodQty());
							pstmt.setInt(20, toolingReturnNote.getDamagedQty());
							
							if(toolingReturnNote.getDamagedQty() == 0 ) {
								pstmt.setString(21,"");
							}else {
								pstmt.setString(21, TiimUtil.ValidateNull(toolingReturnNote.getDamagedSerialNumber()).trim());
							}
							pstmt.setString(22, TiimUtil.ValidateNull(toolingReturnNote.getRejectedSerialNumber()).trim());
							pstmt.setString(23,  TiimUtil.ValidateNull(toolingReturnNote.getCleaningSOP()));
							pstmt.executeUpdate();
							addSerialNumber(toolingReturnNote);
							/*Product product = productDao.getProductIntervalQnty(rs.getString("productname"), rs.getString("drawingNo"));
							long toolLife = Integer.parseInt(product.getToolingLife()) * toolingReturnNote.getGoodQty();
							long intervalQnty = Integer.parseInt(product.getServiceIntervalQty()) * toolingReturnNote.getGoodQty();
							receiptDao.updateReceiptIntervalQnty(rs.getString("toolinglotnumber"), toolLife, intervalQnty);*/
							if(status.equalsIgnoreCase("Good"))
							{
								//if("0".equals(approvalFlag))
								{
									storeTransaction(toolingReturnNote, i, "Add");
								}
							}else
							{

								RejectedProduct reject = new RejectedProduct();
								reject.setDrawingNumber(toolingReturnNote.getDrawingNo1());
								reject.setMachineName(toolingReturnNote.getMachineName1());
								reject.setProductName(toolingReturnNote.getProductName1());
								reject.setTypeOfTool(toolingReturnNote.getTypeOfTooling1());
								reject.setToolingId(toolingReturnNote.getReturnId());
								reject.setLotNumber(toolingReturnNote.getToolingLotNumber1());
								reject.setSerialNumber(toolingReturnNote.getDamagedSerialNumber());
								reject.setSource("Tooling Inspection Report");
								rejectedProductDao.addRejectedProduct(reject);
							
							}
						}catch(Exception ex)
						{
							ex.printStackTrace();
							System.out.println("Exception when adding the tooling issue note detail in tooling_production_return_note_detail table : "+ex.getMessage());
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
							System.out.println("Exception when closing the connection in tooling issue note detail in tooling_production_return_note_detail table : "+ex.getMessage());
						}
					}
				}

				
				msg = "Saved Successfully";
			
		  }
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the tooling issue note detail in tooling_production_return_note_detail table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling issue note detail in tooling_production_return_note_detail table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateToolingReturnDetail(ToolingReturnNote toolingReturnNote)
	{
		storeTransaction(getToolingReturnDetails(toolingReturnNote.getReturnId()), 0, "Sub");
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		String producedQty = "";
		String returnQty = "";
		try
		{
				con = datasource.getConnection();
				for(int i = 0; i < toolingReturnNote.getToolingStatus().length; i++)
				{
					pstmt = con.prepareStatement("update tooling_production_return_note_detail set producedqty = ?, returnedqty = ?, toolingstatus = ?, batchnumber = ? "
							+ " where returnnotedetailid = ?");
					if(toolingReturnNote.getProducedQty()[i] != null && !"".equals(toolingReturnNote.getProducedQty()[i]))
						{
							producedQty = toolingReturnNote.getProducedQty()[i];
						}
						else
						{
							producedQty = "0";
						}
					pstmt.setString(1, toolingReturnNote.getProducedQty()[i]);																					
						pstmt.setString(1, toolingReturnNote.getProducedQty()[i]);
						if(toolingReturnNote.getReturnQty()[i] != null && !"".equals(toolingReturnNote.getReturnQty()[i]))
						{
							returnQty = toolingReturnNote.getReturnQty()[i];
						}
						else
						{
							returnQty = "0";
						}
					pstmt.setString(2, returnQty);
					pstmt.setString(3, TiimUtil.ValidateNull(toolingReturnNote.getToolingStatus()[i]).trim());
					pstmt.setString(4, TiimUtil.ValidateNull(toolingReturnNote.getBatchNumber()[i]).trim());
					pstmt.setInt(5, toolingReturnNote.getReturnDetailId()[i]);
					pstmt.executeUpdate();
					
					ToolingReturnNote objtoolingReturnNote = getToolingReturnDetails(toolingReturnNote.getReturnId());
					toolingReturnNote.setTypeOfTooling1(objtoolingReturnNote.getTypeOfTooling1());
					toolingReturnNote.setProductName1(objtoolingReturnNote.getProductName1());
					toolingReturnNote.setMachineName1(objtoolingReturnNote.getMachineName1());
					toolingReturnNote.setDrawingNo1(objtoolingReturnNote.getDrawingNo1());
					toolingReturnNote.setUOM1(objtoolingReturnNote.getUOM1());
					toolingReturnNote.setToolingLotNumber1(objtoolingReturnNote.getToolingLotNumber1());
					storeTransaction(toolingReturnNote, i, "Add");
				}
					
				
				msg = "Updated Successfully";
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling return note detail in tooling_production_return_note_detail table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling return note detail in tooling_production_return_note_detail table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public List<ToolingReturnNote> getToolingReturnNote(String issuedBy)
	{
		List<ToolingReturnNote> lstReturnNote = new ArrayList<ToolingReturnNote>();
		ToolingReturnNote returnNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(issuedBy == null || "".equals(issuedBy))
			{
				pstmt = con.prepareStatement("select * from (Select returnnoteid, returndate, issueNo, issueDate, returnedby, issuedBy, customername, branchname, originalid, cleaned_by from "
						+ " tooling_production_return_note order by returnnoteid desc)  x group by originalid ");
			}else
			{
				pstmt = con.prepareStatement("select * from (Select returnnoteid, returndate, issueNo, issueDate, returnedby, issuedBy, customername, branchname, originalid, cleaned_by from "
						+ "  tooling_production_return_note where issuedBy like '%"+issuedBy+"%'  order by returnnoteid desc)  x group by originalid ");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				returnNote = new ToolingReturnNote();
				returnNote.setIssueId(rs.getInt("issueNo"));
				returnNote.setIssueDate(rs.getString("issueDate"));
				returnNote.setReturnId(rs.getInt("returnnoteid"));
				returnNote.setReturnDate(sdf.format(sdfDB.parse(rs.getString("returndate"))));
				returnNote.setReturnBy(TiimUtil.ValidateNull(rs.getString("returnedby")).trim());
				returnNote.setIssueBy(TiimUtil.ValidateNull(rs.getString("issuedBy")).trim());
				returnNote.setCustomerName(TiimUtil.ValidateNull(rs.getString("customername")).trim());
				returnNote.setBranchName(TiimUtil.ValidateNull(rs.getString("branchname")).trim());
				returnNote.setOriginalId(rs.getInt("originalid"));
				returnNote.setCleanedBy(rs.getString("cleaned_by"));
				lstReturnNote.add(returnNote);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the tooling_issue_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the tooling_issue_note list : "+ex.getMessage());
			}
		}
	
		return lstReturnNote;
	}
	
	public List<ToolingReturnNote> getToolingReturnNoteDetails(int toolingReturnId)
	{

		List<ToolingReturnNote> lstReturnNote = new ArrayList<ToolingReturnNote>();
		ToolingReturnNote returnNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT returnnotedetailid, returnnoteid, typeoftool, productname, machinetype, drawingNo, batchQty,"
					+ " producedqty, toolinglotnumber, lastInspectionDate, nextDueQty, issuedQty, UOM, returnedqty, toolingstatus,batchnumber,goodqty,damagedqty,damagedserialnumber,rejectedserialnumber,clearingsop  "
					+ " FROM tooling_production_return_note_detail where returnnoteid = ?");
			pstmt.setInt(1, toolingReturnId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				returnNote = new ToolingReturnNote();
				returnNote.setReturnDetailId1(rs.getInt("returnnotedetailid"));
				returnNote.setReturnId(rs.getInt("returnnoteid"));
				returnNote.setTypeOfTooling1(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				returnNote.setProductName1(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				returnNote.setMachineName1(TiimUtil.ValidateNull(rs.getString("machinetype")).trim());
				returnNote.setDrawingNo1(TiimUtil.ValidateNull(rs.getString("drawingNo")).trim());
				returnNote.setBatchQty1(rs.getLong("batchQty"));
				returnNote.setProducedQty1(rs.getLong("producedqty"));
				returnNote.setToolingLotNumber1(rs.getString("toolinglotnumber"));
				returnNote.setLastInspectionDate1(sdf.format(sdfDB.parse(rs.getString("lastInspectionDate"))));
				returnNote.setNextDueQty1(rs.getLong("nextDueQty"));
				returnNote.setIssuedQty1(rs.getLong("issuedQty"));
				returnNote.setUOM1(TiimUtil.ValidateNull(rs.getString("UOM")).trim());
				returnNote.setRequestQty1(rs.getLong("returnedqty"));
				returnNote.setToolingStatus1(TiimUtil.ValidateNull(rs.getString("toolingstatus")).trim());
				returnNote.setBatchNumber1(rs.getString("batchnumber"));
				returnNote.setGoodQty(rs.getInt("goodqty"));
				returnNote.setDamagedQty(rs.getInt("damagedqty"));
				returnNote.setDamagedSerialNumber(rs.getString("damagedserialnumber"));
				returnNote.setRejectedSerialNumber(rs.getString("rejectedserialnumber"));
				returnNote.setCleaningSOP(rs.getString("clearingsop"));
				lstReturnNote.add(returnNote);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the tooling_issue_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the tooling_issue_note list : "+ex.getMessage());
			}
		}
	
		return lstReturnNote;
	
	}
	
	public ToolingReturnNote getToolingReturnDetails(int toolingReturnId)
	{

		//List<ToolingReturnNote> lstReturnNote = new ArrayList<ToolingReturnNote>();
		ToolingReturnNote returnNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT returnnotedetailid, returnnoteid, typeoftool, productname, machinetype, drawingNo, batchQty,"
					+ " producedqty, toolinglotnumber, lastInspectionDate, nextDueQty, issuedQty, UOM, returnedqty, toolingstatus,batchnumber,goodqty,damagedqty,damagedserialnumber,rejectedserialnumber,clearingsop "
					+ " FROM tooling_production_return_note_detail where returnnoteid = ?");
			pstmt.setInt(1, toolingReturnId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				returnNote = new ToolingReturnNote();
				returnNote.setReturnDetailId1(rs.getInt("returnnotedetailid"));
				returnNote.setReturnId(rs.getInt("returnnoteid"));
				returnNote.setTypeOfTooling1(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				returnNote.setProductName1(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				returnNote.setMachineName1(TiimUtil.ValidateNull(rs.getString("machinetype")).trim());
				returnNote.setDrawingNo1(TiimUtil.ValidateNull(rs.getString("drawingNo")).trim());
				returnNote.setBatchQty1(rs.getLong("batchQty"));
				returnNote.setProducedQty1(rs.getLong("producedqty"));
				returnNote.setToolingLotNumber1(rs.getString("toolinglotnumber"));
				returnNote.setLastInspectionDate1(sdf.format(sdfDB.parse(rs.getString("lastInspectionDate"))));
				returnNote.setNextDueQty1(rs.getLong("nextDueQty"));
				returnNote.setIssuedQty1(rs.getLong("issuedQty"));
				returnNote.setUOM1(TiimUtil.ValidateNull(rs.getString("UOM")).trim());
				returnNote.setReturnQty1(rs.getLong("returnedqty"));
				returnNote.setToolingStatus1(TiimUtil.ValidateNull(rs.getString("toolingstatus")).trim());
				returnNote.setBatchNumber1(rs.getString("batchnumber"));
				returnNote.setGoodQty(rs.getInt("goodqty"));
				returnNote.setDamagedQty(rs.getInt("damagedqty"));
				returnNote.setDamagedSerialNumber(TiimUtil.ValidateNull(rs.getString("damagedserialnumber")).trim());
				returnNote.setRejectedSerialNumber(TiimUtil.ValidateNull(rs.getString("rejectedserialnumber")).trim());
				returnNote.setCleaningSOP(rs.getString("clearingsop"));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the tooling_issue_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the tooling_issue_note list : "+ex.getMessage());
			}
		}
	
		return returnNote;
	
	}
	
	public ToolingReturnNote editToolingReturnNote(int returnnoteid)
	{
		ToolingReturnNote returnNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select returnnoteid, returndate, issueNo, issueDate, returnedby, issuedBy, customername, branchname, cleaned_by from tooling_production_return_note where returnnoteid = ?");
			pstmt.setInt(1, returnnoteid);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				returnNote = new ToolingReturnNote();
				returnNote.setIssueId(rs.getInt("issueNo"));
				returnNote.setIssueDate(sdf.format(sdfDB.parse(rs.getString("issueDate"))));
				returnNote.setReturnId(rs.getInt("returnnoteid"));
				returnNote.setReturnDate(sdf.format(sdfDB.parse(rs.getString("returndate"))));
				returnNote.setReturnBy(TiimUtil.ValidateNull(rs.getString("returnedby")).trim());
				returnNote.setIssueBy(TiimUtil.ValidateNull(rs.getString("issuedBy")).trim());
				returnNote.setCustomerName(TiimUtil.ValidateNull(rs.getString("customername")).trim());
				returnNote.setBranchName(TiimUtil.ValidateNull(rs.getString("branchname")).trim());
				returnNote.setCleanedBy(rs.getString("cleaned_by"));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the edit tooling_issue_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the edit tooling_issue_note list : "+ex.getMessage());
			}
		}
	
		return returnNote;
	}
	
	public ToolingReturnNote getReturnNote(int returnnoteid)
	{
		ToolingReturnNote returnNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select returnnoteid, returndate, issueNo, issueDate, returnedby, issuedBy, customername, branchname,cleaned_by from tooling_production_return_note where originalId = ?");
			pstmt.setInt(1, returnnoteid);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				returnNote = new ToolingReturnNote();
				returnNote.setIssueId(rs.getInt("issueNo"));
				returnNote.setIssueDate(sdf.format(sdfDB.parse(rs.getString("issueDate"))));
				returnNote.setReturnId(rs.getInt("returnnoteid"));
				returnNote.setReturnDate(sdf.format(sdfDB.parse(rs.getString("returndate"))));
				returnNote.setReturnBy(TiimUtil.ValidateNull(rs.getString("returnedby")).trim());
				returnNote.setIssueBy(TiimUtil.ValidateNull(rs.getString("issuedBy")).trim());
				returnNote.setCustomerName(TiimUtil.ValidateNull(rs.getString("customername")).trim());
				returnNote.setBranchName(TiimUtil.ValidateNull(rs.getString("branchname")).trim());
				returnNote.setCleanedBy(rs.getString("cleaned_by"));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the edit tooling_issue_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the edit tooling_issue_note list : "+ex.getMessage());
			}
		}
	
		return returnNote;
	}
	
	public String deleteReturnNote(int originalId, int returnnoteid, int userId, String branchName)
	{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			ToolingReturnNote obj = getToolingReturnDetails(returnnoteid);
			obj.setBranchName(branchName);
			if(obj.getToolingStatus1().equalsIgnoreCase("Good"))
			{
				storeTransaction(obj, 0, "Sub");
			}
			
			updateToolingIssueStatus(getReturnNote(originalId), 0);
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from tooling_production_return_note_detail where originalId = ?");
			pstmt.setInt(1, originalId);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement("delete from tooling_production_return_note where originalId = ?");
			pstmt.setInt(1, originalId);
			pstmt.executeUpdate();
			

			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("productionReturn.page", null,null));
			history.setDescription(messageSource.getMessage("productionReturn.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
			msg = "Deleted Successfully";
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the detail in tooling_production_return_note and tooling_production_return_note_detail table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in delete the detail in tooling_production_return_note and tooling_production_return_note_detail table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	private void storeTransaction(ToolingReturnNote toolingReturnNote, int index, String flag)
	{
		System.out.print("Store Transaction:::::");
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		long returnQty = 0;
		try
		{
			con = datasource.getConnection();
			{
				/*pstmt1 = con.prepareStatement("Select toolingIssueDetailId, productname, machineName, drawingNo, batchQty,"
							+ " requestQty, toolinglotnumber, lastInspectionDate, nextDueQty, issuedQty, UOM from tooling_issue_note_detail"
							+ " where toolingIssueDetailId = ?");
				pstmt1.setInt(1, toolingReturnNote.getToolingIssueDetailId()[index]);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())*/
				System.out.println("toolingReturnNote.getProductName1(): "+toolingReturnNote.getProductName1());
				{
					try
					{
						pstmt = con.prepareStatement("insert into transaction(productname, drawingno, toolinglotnumber, UOM, stockqty,machinename, "
								+ " transactiondate, Source )"
								+ "values(?, ?, ?, ?, ?, ?, ?, ?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(toolingReturnNote.getProductName1()));
						pstmt.setString(2, TiimUtil.ValidateNull(toolingReturnNote.getDrawingNo1()));
						pstmt.setString(3, toolingReturnNote.getToolingLotNumber1());
						pstmt.setString(4, TiimUtil.ValidateNull(toolingReturnNote.getUOM1()));
						if("Sub".equalsIgnoreCase(flag))
						{
							returnQty = toolingReturnNote.getRequestQty1();
						}else if("Approve".equalsIgnoreCase(flag))
						{
							returnQty = toolingReturnNote.getGoodQty();
						}
						else
						if(toolingReturnNote.getReturnQty()[index] != null && !"".equals(toolingReturnNote.getReturnQty()[index]))
						{
							//returnQty = Long.parseLong(toolingReturnNote.getReturnQty()[index].trim());
							returnQty = toolingReturnNote.getGoodQty();
						}
						
						pstmt.setLong(5, returnQty);
						pstmt.setString(6, TiimUtil.ValidateNull(toolingReturnNote.getMachineName1()));
						pstmt.setString(7, sdf.format(cDate));
						pstmt.setString(8, "ToolingReturn");
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
			storeStock(toolingReturnNote, index, flag);
			
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
	
	private void storeStock(ToolingReturnNote toolingReturnNote, int index, String flag)
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
			//System.out.println("Stock table toolingReturnNote.getProductName1(): "+toolingReturnNote.getProductName1());
			con = datasource.getConnection();
			{
				/*pstmt1 = con.prepareStatement("Select toolingIssueDetailId, productname, machineName, drawingNo, batchQty,"
						+ " requestQty, toolinglotnumber, typeoftool, nextDueQty, issuedQty, UOM from tooling_issue_note_detail"
						+ " where toolingIssueDetailId = ?");
			pstmt1.setInt(1, toolingReturnNote.getToolingIssueDetailId()[index]);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())*/
				{
					try
					{
						pstmt = con.prepareStatement("SELECT stockQty, stockId FROM stock where toolinglotnumber = ? and typeoftool = ? and productname = ? and drawingno = ? and machinetype = ?");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(toolingReturnNote.getToolingLotNumber1()));
						pstmt.setString(2, TiimUtil.ValidateNull(toolingReturnNote.getTypeOfTooling1()));
						pstmt.setString(3, toolingReturnNote.getProductName1());
						pstmt.setString(4, TiimUtil.ValidateNull(toolingReturnNote.getDrawingNo1()));
						pstmt.setString(5, toolingReturnNote.getMachineName1());
						if("Sub".equalsIgnoreCase(flag))
						{
							//System.out.println("flag is sub: "+toolingReturnNote.getReturnQty1());
							//returnQty = toolingReturnNote.getReturnQty1();
							//returnQty = toolingReturnNote.getGoodQty();
							returnQty = toolingReturnNote.getIssuedQty1();
						}
						else if("Approve".equalsIgnoreCase(flag))
						{
							//returnQty = toolingReturnNote.getReturnQty1();
							//returnQty = toolingReturnNote.getGoodQty();
							returnQty = toolingReturnNote.getIssuedQty1();
						}else
						if(toolingReturnNote.getReturnQty()[index] != null && !"".equals(toolingReturnNote.getReturnQty()[index]))
						{
							//returnQty = Integer.parseInt(toolingReturnNote.getReturnQty()[index].trim());
							//returnQty = (toolingReturnNote.getGoodQty());
							returnQty = toolingReturnNote.getIssuedQty1();
						}
						
						stock.setToolingLotNumber(toolingReturnNote.getToolingLotNumber1());
						stock.setTypeOfTool(TiimUtil.ValidateNull(toolingReturnNote.getTypeOfTooling1()).trim());
						stock.setProductName(TiimUtil.ValidateNull(toolingReturnNote.getProductName1()).trim());
						stock.setDrawingNo(TiimUtil.ValidateNull(toolingReturnNote.getDrawingNo1()).trim());
						stock.setMachineName(toolingReturnNote.getMachineName1());
						stock.setUom(toolingReturnNote.getUOM1());
						stock.setStockQty(returnQty);
						stock.setGoodQty(toolingReturnNote.getGoodQty());
						stock.setDamagedQty(toolingReturnNote.getDamagedQty());
						stock.setBranch(toolingReturnNote.getBranchName());
						ResultSet rs1 = pstmt.executeQuery();
						if(rs1.next()){
							//System.out.println(flag+", "+returnQty+", "+rs1.getInt("stockQty"));
							/*
							 * if("Add".equalsIgnoreCase(flag) || "Approve".equalsIgnoreCase(flag)) {
							 * totalStock = rs1.getInt("stockQty") + returnQty; } else { totalStock =
							 * rs1.getInt("stockQty") - returnQty; }
							 */
							totalStock = rs1.getInt("stockQty") - toolingReturnNote.getDamagedQty();
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
	
	public void updateOriginalId(ToolingReturnNote returnNote)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_production_return_note set originalid = ? "
						+ " where returnnoteid = ?");

				pstmt.setInt(1, returnNote.getOriginalId());
				pstmt.setInt(2, returnNote.getReturnId());
				pstmt.executeUpdate();
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in ToolingReturnNote updateOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in ToolingReturnNote updateOriginalId : "+ex.getMessage());
			}
		}
	}
	
	/**
	 * Get original Id from tooling_receiving_request
	 * @param requestId
	 * @return
	 */
	private void getRequestVersions(ToolingReturnNote returnNote)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("select originalId, revisionNumber from tooling_production_return_note "
						+ " where returnnoteid = ?");
				System.out.println(returnNote.getReturnId());
				pstmt.setInt(1, returnNote.getReturnId());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					returnNote.setOriginalId(rs.getInt("originalId"));
					int revisionNumber = rs.getInt("revisionNumber");
					revisionNumber++;
					returnNote.setRevisionNumber(revisionNumber);
					System.out.println("rs.getInt(originalId): "+rs.getInt("originalId"));
				}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in ToolingReturnNote updateOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in ToolingReturnNote updateOriginalId : "+ex.getMessage());
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
				pstmt = con.prepareStatement("select max(originalid) originalid from tooling_production_return_note");
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					originalId = rs.getInt("originalid");
				}
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in tooling_production_return_note getMaxOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in tooling_issue_note getMaxOriginalId : "+ex.getMessage());
			}
		}
	
		return originalId;
	}
	
	private void updateToolingIssueStatus(ToolingReturnNote toolingReturnNote, int status)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_issue_note set isreport = ? where originalId = ?");
				pstmt.setInt(1, status);
				pstmt.setInt(2, toolingReturnNote.getIssueId());
				pstmt.executeUpdate();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in updateToolingIssueStatus  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling receipt note detail in updateToolingIssueStatus table : "+ex.getMessage());
			}
		}
	
	}
	private void updateClearanceNoStatus(String lotNumber)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update mst_clearance_no set isMoved =  1 where lotnumber = ? and isMoved = 0;");
				pstmt.setString(1, lotNumber);
				pstmt.executeUpdate();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception in updateClearanceNoStatus  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updateClearanceNoStatus table : "+ex.getMessage());
			}
		}
	
	
	}
	
	private void addSerialNumber(ToolingReturnNote toolingReturnNote) {
		ToolSerialNumber toolSerialNumber = new ToolSerialNumber();
		toolSerialNumber.setLotNumber(toolingReturnNote.getToolingLotNumber1());
		toolSerialNumber.setModule("Tool Return");
		toolSerialNumber.setAcceptQty(toolingReturnNote.getGoodQty());
		toolSerialNumber.setRejectQty(toolingReturnNote.getDamagedQty());
		toolSerialNumber.setSerialNumber(toolingReturnNote.getDamagedSerialNumber());
		toolSerialNumberDao.addSerialNumber(toolSerialNumber);
	}
}
