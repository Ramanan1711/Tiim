package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.tiim.model.ToolingRequest;
import com.tiim.util.TiimUtil;

@Repository
public class ToolingReceivingInspectionDao {

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
	SimpleDateFormat sdfDBFull = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
	java.util.Date cDate=new java.util.Date();
	
	public int getIntialValue()
	{
		int toolingInspectionId = 0;
		toolingInspectionId = getMaxOriginalId();
		toolingInspectionId++;
		/*Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(toolingInspectionid) toolingInspectionid FROM tooling_receiving_inspection");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				toolingInspectionId = rs.getInt("toolingInspectionid");
			}
			toolingInspectionId++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ToolingReceivingInspectionDao  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in ToolingReceivingInspectionDao  getIntialValue : "+ex.getMessage());
			}
		}*/
		
		return toolingInspectionId;	
	}
	
	public String addToolingReceivingInspection(ToolingInspection toolingInspection, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		int toolingInspectionId = 0;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into tooling_receiving_inspection(inspectionreportno, inspectionreportdate, requestId, requestDate, branchname, originalid)"
						+ "values(?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
				//,Statement.RETURN_GENERATED_KEYS
				pstmt.setString(1, toolingInspection.getInspectionReportNo());
				java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
				pstmt.setTimestamp(2, date);
				pstmt.setInt(3, toolingInspection.getRequestId());
				pstmt.setString(4, sdfDB.format(sdf.parse(toolingInspection.getRequestDate())));
				pstmt.setString(5, toolingInspection.getBranchName());
				int originalId = getMaxOriginalId();
				originalId++;
				toolingInspection.setOriginalId(originalId);
				pstmt.setInt(6, originalId);
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					toolingInspectionId = rs.getInt(1);
				}
				toolingInspection.setToolingInspectionId(toolingInspectionId);
				
				//updateOriginalId(toolingInspection);
				toolingInspection.setRevisionNumber(1);
				addToolingReceivingInspectionDetail(toolingInspection);
				updateReceivingRequestStatus(toolingInspection, 1);
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("inspectionReport.page", null,null));
				history.setDescription(messageSource.getMessage("inspectionReport.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				
				msg = "Saved Successfully";
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in tooling_receiving_inspection table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String addToolingReceivingInspectionDetail(ToolingInspection toolingInspection)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		String status = "";
		try
		{
			
			con = datasource.getConnection();
			for(int i = 0; i< toolingInspection.getToolingRequestDetailId().length;i++)
			{
				pstmt1 = con.prepareStatement("Select toolingname, ponumber, podate, drawingno, lotnumber, receivedquantity, UOM, remarks, "
						+ " toolingrequestid, toolingRequestdetailid, toolingProductId, typeoftool, machinetype, punchSetNo, compForce "
						+ " from tooling_receiving_request_details where toolingRequestdetailid = ?");
				
				pstmt1.setInt(1, toolingInspection.getToolingRequestDetailId()[i]);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())
				{
					try
					{
						//int acceptedQty = minAcceptedQty.getMinAcceptedQty(rs.getString("toolingname"), rs.getString("drawingno"), rs.getString("machinetype"), rs.getString("typeoftool"));
						int acceptedQty = minAcceptedQty.getMinAcceptedQty(rs.getString("lotnumber"));
						Stock stock = stockDao.getStock(rs.getString("lotnumber"), toolingInspection.getBranchName());
						
						
						pstmt = con.prepareStatement("insert into tooling_receiving_inspection_details(toolingname,  drawingno, lotnumber, receivedquantity, UOM, inspectionstatus, remarks, "
								+ " toolingInspectionid, toolingRequestdetailid, typeoftool, machinetype, acceptedQty, rejectedQty, originalid, revisionnumber,rejectedserialnumber,"
								+ " punchSetNo, compForce)"
								+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(rs.getString("toolingname")));
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(3, rs.getString("lotnumber"));
						pstmt.setLong(4, rs.getLong("receivedquantity"));
						pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("UOM")));
						
						if(toolingInspection.getAcceptedQty() >= acceptedQty)
						{
							status = "Accepted";
						}else
						{
							status = "Rejected";
							
						}
						/*if(stock != null)
						{ 
							System.out.println(stock.getStockQty());
							if(stock.getStockQty() >= toolingInspection.getAcceptedQty())
							{

								status = "Accepted";
							}else
							{
								status = "Rejected";
								
							}
						}else
						{
							if(toolingInspection.getAcceptedQty() >= acceptedQty)
							{
								status = "Accepted";
							}else
							{
								status = "Rejected";
								
							}
						}*/
						pstmt.setString(6, TiimUtil.ValidateNull(status));
						if(toolingInspection.getRemarks() != null && toolingInspection.getRemarks().length > 0 && toolingInspection.getRemarks().length == (i+1)  )
							pstmt.setString(7, TiimUtil.ValidateNull(toolingInspection.getRemarks()[i]));
						else
						pstmt.setString(7, "");
						
						pstmt.setInt(8, toolingInspection.getToolingInspectionId());
						pstmt.setInt(9, toolingInspection.getToolingRequestDetailId()[i]);
						pstmt.setString(10, TiimUtil.ValidateNull(rs.getString("typeoftool")));
						pstmt.setString(11, TiimUtil.ValidateNull(rs.getString("machinetype")));
						pstmt.setInt(12, toolingInspection.getAcceptedQty());
						pstmt.setInt(13, toolingInspection.getRejectedQty());
						pstmt.setInt(14, toolingInspection.getOriginalId());
						pstmt.setInt(15, toolingInspection.getRevisionNumber());
						if(toolingInspection.getRejectedQty() == 0) {
							pstmt.setString(16, "");
						}else {
							pstmt.setString(16, toolingInspection.getRejectedSerialNumber());
						}
						
						pstmt.setString(17, rs.getString("punchSetNo"));
						pstmt.setInt(18, rs.getInt("compForce"));
						pstmt.executeUpdate();
						
						addSerialNumber(toolingInspection);
												
						Product product = productDao.getProductIntervalQnty(rs.getString("toolingname"), rs.getString("drawingno"));
						//long toolLife = Integer.parseInt(product.getToolingLife()) * toolingInspection.getAcceptedQty();
						long toolLife = 0;
						long intervalQnty = Integer.parseInt(product.getServiceIntervalQty()) * toolingInspection.getAcceptedQty();
						receiptDao.updateReceiptIntervalQnty(rs.getString("lotnumber"), toolLife, intervalQnty);
						
						
						toolingInspection.setDrawingNo(rs.getString("drawingno"));
						toolingInspection.setToolingname(rs.getString("toolingname"));
						toolingInspection.setTypeOfTooling(rs.getString("typeoftool"));
						toolingInspection.setMachineType(rs.getString("machinetype"));
						toolingInspection.setLotNumber(rs.getString("lotnumber"));
						toolingInspection.setUom(rs.getString("UOM"));
						toolingInspection.setReceivedQuantity(rs.getLong("receivedquantity"));
						
						if(status.equalsIgnoreCase("Accepted"))
						{
							storeTransaction(toolingInspection, i, "Add");
						}
						else
						{
							RejectedProduct reject = new RejectedProduct();
							reject.setDrawingNumber(toolingInspection.getDrawingNo());
							reject.setMachineName(toolingInspection.getMachineType());
							reject.setProductName(toolingInspection.getToolingname());
							reject.setTypeOfTool(toolingInspection.getTypeOfTooling());
							reject.setToolingId(toolingInspection.getToolingInspectionId());
							reject.setLotNumber(toolingInspection.getLotNumber());
							reject.setSerialNumber(toolingInspection.getRejectedSerialNumber());
							reject.setSource("Tooling Inspection Report");
							rejectedProductDao.addRejectedProduct(reject);
						}
					}catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when storing the data to tooling_receiving_inspection_details: "+ex.getMessage());
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
		return msg;
	}
	
	public String updateToolingInspection(ToolingInspection toolingInspection, int userId)
	{
		getRequestVersions(toolingInspection);
		
		ToolingInspection obj = getIndividualInspectionDetails(toolingInspection.getToolingInspectionId());
		obj.setBranchName(toolingInspection.getBranchName());
		System.out.println("obj.getInspectionStatus1(): "+obj.getInspectionStatus1());
		if(obj.getInspectionStatus1().equalsIgnoreCase("Accepted"))
		{

			storeTransaction(obj, 0, "Sub");
		}else
		{
			rejectedProductDao.deleteRejectProduct(obj.getLotNumber());
		}
		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		int toolingInspectionId = 0;
		try
		{

			con = datasource.getConnection();
			pstmt = con.prepareStatement("insert into tooling_receiving_inspection(inspectionreportno, inspectionreportdate, requestId, requestDate, branchname, originalId, revisionnumber)"
					+ "values(?, ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			//,Statement.RETURN_GENERATED_KEYS
			pstmt.setString(1, toolingInspection.getInspectionReportNo());
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			pstmt.setTimestamp(2, date);
			pstmt.setInt(3, toolingInspection.getRequestId());
			pstmt.setString(4, sdfDB.format(sdf.parse(toolingInspection.getRequestDate())));
			pstmt.setString(5, toolingInspection.getBranchName());
			pstmt.setInt(6, toolingInspection.getOriginalId());
			pstmt.setInt(7, toolingInspection.getRevisionNumber());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next())
			{
				toolingInspectionId = rs.getInt(1);
			}
			toolingInspection.setToolingInspectionId(toolingInspectionId);
			
			
			addToolingReceivingInspectionDetail(toolingInspection);
			
				//updateToolingInspectionDetail(toolingInspection);
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("inspectionReport.page", null,null));
				history.setDescription(messageSource.getMessage("inspectionReport.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				msg = "Updated Successfully";
			
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
				}if(con != null)
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
	public String updateToolingInspectionOld(ToolingInspection toolingInspection, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_receiving_inspection set inspectionreportno = ?, inspectionreportdate = ?, requestId = ?, requestDate = ?"
						+ " where toolingInspectionid = ?");

				pstmt.setString(1, toolingInspection.getInspectionReportNo());
				
				java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
				pstmt.setTimestamp(2, date);
				
				pstmt.setInt(3, toolingInspection.getRequestId());
				pstmt.setString(4, sdfDB.format(sdf.parse(toolingInspection.getRequestDate())));
				pstmt.setInt(5, toolingInspection.getToolingInspectionId());
				pstmt.executeUpdate();
				
				updateToolingInspectionDetail(toolingInspection);
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("inspectionReport.page", null,null));
				history.setDescription(messageSource.getMessage("inspectionReport.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				msg = "Updated Successfully";
			
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
				}if(con != null)
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
	public String updateToolingInspectionDetail(ToolingInspection toolingInspection)
	{
		ToolingInspection obj = getIndividualInspectionDetails(toolingInspection.getToolingInspectionId());
		obj.setBranchName(toolingInspection.getBranchName());
		storeTransaction(obj, 0, "Sub");
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		String status = "";
		try
		{
			con = datasource.getConnection();
			for(int i = 0; i< toolingInspection.getToolingRequestDetailId().length;i++)
			{
				System.out.println(toolingInspection.getToolingRequestDetailId().length+", "+i);
				pstmt1 = con.prepareStatement("Select toolingname, ponumber, podate, drawingno, lotnumber, receivedquantity, UOM, remarks, "
						+ " toolingrequestid, toolingRequestdetailid, toolingProductId,typeoftool, machinetype, punchSetNo, compForce"
						+ " from tooling_receiving_request_details where toolingRequestdetailid = ?");
				
				pstmt1.setInt(1, toolingInspection.getToolingRequestDetailId()[i]);
				ResultSet rs = pstmt1.executeQuery();
				if(rs.next())
				{
					try
					{
						//int acceptedQty = minAcceptedQty.getMinAcceptedQty(rs.getString("toolingname"), rs.getString("drawingno"), rs.getString("machinetype"), rs.getString("typeoftool"));
						int acceptedQty = minAcceptedQty.getMinAcceptedQty(rs.getString("lotnumber"));
						pstmt = con.prepareStatement("update tooling_receiving_inspection_details set toolingname = ?, drawingno = ?, "
								+ " lotnumber = ?, receivedquantity = ?, UOM = ?, inspectionstatus = ?, remarks = ?, toolingInspectionid = ?,"
								+ " typeoftool = ?, machinetype = ? , acceptedQty = ?, rejectedQty = ?, rejectedserialnumber = ?, punchSetNo = ?, compForce = ? " 
								+ " where toolingInspectiondetailid = ?");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(rs.getString("toolingname")));
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(3, rs.getString("lotnumber"));
						pstmt.setLong(4, rs.getLong("receivedquantity"));
						pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("UOM")));
						if(toolingInspection.getAcceptedQty() >= acceptedQty)
						{
							status = "Accepted";
						}else
						{
							status = "Rejected";
							
						}
						pstmt.setString(6, status);
						//System.out.println(toolingInspection.getRemarks() != null && toolingInspection.getRemarks().length > 0 && (toolingInspection.getRemarks().length+1) == i );
						if(toolingInspection.getRemarks() != null && toolingInspection.getRemarks().length > 0 && toolingInspection.getRemarks().length == (i+1)  )
							pstmt.setString(7, TiimUtil.ValidateNull(toolingInspection.getRemarks()[i]));
						else
						pstmt.setString(7, "");
						pstmt.setInt(8, toolingInspection.getToolingInspectionId());
						pstmt.setString(9, TiimUtil.ValidateNull(rs.getString("typeoftool")));
						pstmt.setString(10, TiimUtil.ValidateNull(rs.getString("machinetype")));
						
						pstmt.setInt(11, toolingInspection.getAcceptedQty());
						pstmt.setInt(12, toolingInspection.getRejectedQty());
						pstmt.setString(13, toolingInspection.getRejectedSerialNumber());
						pstmt.setString(14, rs.getString("punchSetNo"));
						pstmt.setInt(15, rs.getInt("compForce"));
						pstmt.setInt(16, toolingInspection.getToolingInspectionDetailId()[i]);
						pstmt.executeUpdate();
						
						toolSerialNumberDao.deleteSerialNumber(toolingInspection.getLotNumber());
						addSerialNumber(toolingInspection);
					
						toolingInspection.setDrawingNo(rs.getString("drawingno"));
						toolingInspection.setToolingname(rs.getString("toolingname"));
						toolingInspection.setTypeOfTooling(rs.getString("typeoftool"));
						toolingInspection.setMachineType(rs.getString("machinetype"));
						toolingInspection.setLotNumber(rs.getString("lotnumber"));
						toolingInspection.setUom(rs.getString("UOM"));
						toolingInspection.setReceivedQuantity(rs.getLong("receivedquantity"));
						
						if(status.equalsIgnoreCase("Accepted"))
						{
							storeTransaction(toolingInspection, i, "Add");
						}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("Exception when updating the data to tooling_receiving_inspection_details: "+ex.getMessage());
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
							System.out.println("Exception when closing the connection in updating detail in tooling_receiving_inspection_details table : "+ex.getMessage());
						}
					}
				}
				
				msg = "Updated Successfully";
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
		return msg;
	
	}
	
	public String deleteReceiptInspection(int originalId, int toolingInspectionId, int userId, String branchName)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			ToolingInspection obj = getIndividualInspectionDetails(toolingInspectionId);
			obj.setBranchName(branchName);
			if(obj.getInspectionStatus1().equalsIgnoreCase("Accepted"))
			{
				storeTransaction(obj, 0, "Sub");
			}
			
			con = datasource.getConnection();
			updateReceivingRequestStatus(getToolingInspection(originalId), 0);
			
			pstmt = con.prepareStatement("delete from tooling_receiving_inspection_details where originalId = ?");
			pstmt.setInt(1, originalId);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement("delete from tooling_receiving_inspection where originalId = ?");
			pstmt.setInt(1, originalId);
			pstmt.executeUpdate();
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("inspectionReport.page", null,null));
			history.setDescription(messageSource.getMessage("inspectionReport.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
						
			msg = "Deleted Successfully";
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the detail in tooling_receipt_product and tooling_receipt_note table : "+ex.getMessage());
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
	
	public List<ToolingInspection> getToolingInspection(String searchInspection)
	{
		List<ToolingInspection> lstToolingInspection = new ArrayList<ToolingInspection>();

		ToolingInspection objInspection = new ToolingInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(searchInspection != null && !"".equals(searchInspection))
			{
			    pstmt = con.prepareStatement("select * from (Select toolingInspectionid, inspectionreportno, inspectionreportdate, requestId, requestDate, originalid "
			    		+ " from tooling_receiving_inspection Where toolingInspectionid like '%"+searchInspection+"%' order by toolingInspectionid desc)"
			    				+ "  x group by originalid");
			}
			else
			{
				pstmt = con.prepareStatement("select * from (Select toolingInspectionid, inspectionreportno, inspectionreportdate, requestId, requestDate, originalid"
						+ " from tooling_receiving_inspection order by toolingInspectionid desc)"
						+ " x group by originalid");
			}
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingInspection();
				objInspection.setToolingInspectionId(rs.getInt("toolingInspectionid"));
				objInspection.setInspectionReportNo(rs.getString("inspectionreportno"));
				objInspection.setInspectionReportDate(sdf.format(sdfDB.parse(rs.getString("inspectionreportdate"))));
				objInspection.setRequestId(rs.getInt("requestId"));
				objInspection.setRequestDate( sdf.format(sdfDB.parse(rs.getString("requestDate"))));
				objInspection.setOriginalId(rs.getInt("originalid"));
				lstToolingInspection.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getToolingInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getToolingInspection : "+ex.getMessage());
			}
		}
	
		return lstToolingInspection;
	}
	
	public List<ToolingInspection> getToolingInspectionSearch(String searchInspection)
	{
		List<ToolingInspection> lstToolingInspection = new ArrayList<ToolingInspection>();

		ToolingInspection objInspection = new ToolingInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(searchInspection != null && !"".equals(searchInspection))
			{
			    pstmt = con.prepareStatement("select * from (Select toolingInspectionid, inspectionreportno, inspectionreportdate, requestId, requestDate, originalid "
			    		+ " from tooling_receiving_inspection Where originalid like '%"+searchInspection+"%' order by toolingInspectionid desc)"
			    				+ "  x group by originalid");
			}
			else
			{
				pstmt = con.prepareStatement("select * from (Select toolingInspectionid, inspectionreportno, inspectionreportdate, requestId, requestDate, originalid"
						+ " from tooling_receiving_inspection order by toolingInspectionid desc)"
						+ " x group by originalid");
			}
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingInspection();
				objInspection.setToolingInspectionId(rs.getInt("toolingInspectionid"));
				objInspection.setInspectionReportNo(rs.getString("inspectionreportno"));
				objInspection.setInspectionReportDate(sdf.format(sdfDB.parse(rs.getString("inspectionreportdate"))));
				objInspection.setRequestId(rs.getInt("requestId"));
				objInspection.setRequestDate( sdf.format(sdfDB.parse(rs.getString("requestDate"))));
				objInspection.setOriginalId(rs.getInt("originalid"));
				lstToolingInspection.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getToolingInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getToolingInspection : "+ex.getMessage());
			}
		}
	
		return lstToolingInspection;
	}
	
	public ToolingInspection getToolingInspection(int toolingInspectionid)
	{
		ToolingInspection objInspection = new ToolingInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingInspectionid, inspectionreportno, inspectionreportdate, requestId, requestDate from tooling_receiving_inspection where originalId = ?");
			pstmt.setInt(1, toolingInspectionid);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingInspection();
				objInspection.setToolingInspectionId(rs.getInt("toolingInspectionid"));
				objInspection.setInspectionReportNo(TiimUtil.ValidateNull(rs.getString("inspectionreportno")));
				objInspection.setInspectionReportDate(sdf.format(sdfDB.parse(rs.getString("inspectionreportdate"))));
				objInspection.setRequestId(rs.getInt("requestId"));
				objInspection.setRequestDate(TiimUtil.ValidateNull( sdf.format(sdfDB.parse(rs.getString("requestDate")))));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the editToolingInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  editToolingInspection : "+ex.getMessage());
			}
		}
	
		return objInspection;
	}
	
	public ToolingInspection editToolingInspection(int toolingInspectionid)
	{
		ToolingInspection objInspection = new ToolingInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingInspectionid, inspectionreportno, inspectionreportdate, requestId, requestDate from tooling_receiving_inspection where toolingInspectionid = ?");
			pstmt.setInt(1, toolingInspectionid);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingInspection();
				objInspection.setToolingInspectionId(rs.getInt("toolingInspectionid"));
				objInspection.setInspectionReportNo(TiimUtil.ValidateNull(rs.getString("inspectionreportno")));
				objInspection.setInspectionReportDate(sdf.format(sdfDB.parse(rs.getString("inspectionreportdate"))));
				objInspection.setRequestId(rs.getInt("requestId"));
				objInspection.setRequestDate(TiimUtil.ValidateNull( sdf.format(sdfDB.parse(rs.getString("requestDate")))));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the editToolingInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  editToolingInspection : "+ex.getMessage());
			}
		}
	
		return objInspection;
	}
	
	public List<ToolingInspection> getToolingInspectionDetails(int toolingInspectionId)
	{
		List<ToolingInspection> lstToolingInspection = new ArrayList<ToolingInspection>();

		ToolingInspection objInspection = new ToolingInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingname, drawingno, lotnumber, receivedquantity, UOM, inspectionstatus, remarks, "
					+ " toolingInspectionid, toolingInspectiondetailid, toolingrequestdetailId, typeoftool, machinetype,acceptedQty, rejectedQty,"
					+ " rejectedserialnumber, punchSetNo, compForce"
					+ " from tooling_receiving_inspection_details where toolingInspectionid = ?");
			pstmt.setInt(1, toolingInspectionId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingInspection();
				objInspection.setToolingname(rs.getString("toolingname"));
				//objInspection.setPoNumber(rs.getString("ponumber"));
				//objInspection.setPoDate(sdf.format(sdfDB.parse(rs.getString("podate"))));
				objInspection.setDrawingNo(rs.getString("drawingno"));
				objInspection.setLotNumber(rs.getString("lotnumber"));				
				objInspection.setReceivedQuantity(rs.getLong("receivedquantity"));
				objInspection.setUom(rs.getString("UOM"));
				objInspection.setInspectionStatus1(rs.getString("inspectionstatus"));
				objInspection.setRemarks1(rs.getString("remarks"));
				objInspection.setToolingInspectionId(rs.getInt("toolingInspectionid"));
				objInspection.setToolingInspectionDetailId1(rs.getInt("toolingInspectiondetailid"));
				objInspection.setToolingRequestDetailId1(rs.getInt("toolingrequestdetailId"));
				objInspection.setTypeOfTooling(rs.getString("typeoftool"));
				objInspection.setMachineType(rs.getString("machinetype"));
				objInspection.setAcceptedQty(rs.getInt("acceptedQty"));
				objInspection.setRejectedQty(rs.getInt("rejectedQty"));
				objInspection.setRejectedSerialNumber(rs.getString("rejectedserialnumber"));
				objInspection.setPunchSetNo(rs.getString("punchSetNo"));
				objInspection.setCompForce(rs.getInt("compForce"));
				lstToolingInspection.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getToolingInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getToolingInspection : "+ex.getMessage());
			}
		}
	
		return lstToolingInspection;
	
	}
	
	public ToolingInspection getIndividualInspectionDetails(int toolingInspectionId)
	{
		ToolingInspection objInspection = new ToolingInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingname, drawingno, lotnumber, receivedquantity, UOM, inspectionstatus, remarks, "
					+ " toolingInspectionid, toolingInspectiondetailid, toolingrequestdetailId, typeoftool, machinetype,acceptedQty, rejectedQty,rejectedserialnumber, "
					+ " punchSetNo, compForce from tooling_receiving_inspection_details where toolingInspectionid = ?");
			pstmt.setInt(1, toolingInspectionId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection.setToolingname(rs.getString("toolingname"));
				//objInspection.setPoNumber(rs.getString("ponumber"));
				//objInspection.setPoDate(sdf.format(sdfDB.parse(rs.getString("podate"))));
				objInspection.setDrawingNo(rs.getString("drawingno"));
				objInspection.setLotNumber(rs.getString("lotnumber"));				
				objInspection.setReceivedQuantity(rs.getLong("receivedquantity"));
				objInspection.setUom(rs.getString("UOM"));
				objInspection.setInspectionStatus1(rs.getString("inspectionstatus"));
				objInspection.setRemarks1(rs.getString("remarks"));
				objInspection.setToolingInspectionId(rs.getInt("toolingInspectionid"));
				objInspection.setToolingInspectionDetailId1(rs.getInt("toolingInspectiondetailid"));
				objInspection.setToolingRequestDetailId1(rs.getInt("toolingrequestdetailId"));
				objInspection.setTypeOfTooling(rs.getString("typeoftool"));
				objInspection.setMachineType(rs.getString("machinetype"));
				objInspection.setAcceptedQty(rs.getInt("acceptedQty"));
				objInspection.setReceivedQuantity(rs.getInt("rejectedQty"));
				objInspection.setRejectedSerialNumber(rs.getString("rejectedserialnumber"));
				objInspection.setPunchSetNo(rs.getString("punchSetNo"));
				objInspection.setCompForce(rs.getInt("compForce"));
		
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getToolingInspection : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getToolingInspection : "+ex.getMessage());
			}
		}
	
		return objInspection;
	
	}
	
	public List<ToolingInspection> getRejectInspectionDetails(String drawingNumber)
	{
		List<ToolingInspection> lstToolingInspection = new ArrayList<ToolingInspection>();
		ToolingInspection objInspection = new ToolingInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			String query = "Select toolingname, branchname, drawingno, lotnumber, receivedquantity, UOM, inspectionstatus, remarks, "
					+ " a.toolingInspectionid toolingInspectionid, toolingInspectiondetailid, toolingrequestdetailId, typeoftool, machinetype, "
					+ " b.inspectionreportdate inspectionreportdate, punchSetNo, compForce  "
					+ " from tooling_receiving_inspection_details a, tooling_receiving_inspection b  "
					+ " where inspectionstatus = ? and a.toolingInspectionid = b.toolingInspectionid";
			
			if(drawingNumber != null && !"".equals(drawingNumber))
			{
				query = query + " and drawingno = ?";
			}
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "Rejected");
			if(drawingNumber != null && !"".equals(drawingNumber))
			{
				pstmt.setString(2, drawingNumber);
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingInspection();
				objInspection.setToolingname(rs.getString("toolingname"));
				objInspection.setBranchName(rs.getString("branchname"));
				//objInspection.setPoNumber(rs.getString("ponumber"));
				//objInspection.setPoDate(sdf.format(sdfDB.parse(rs.getString("podate"))));
				objInspection.setDrawingNo(rs.getString("drawingno"));
				objInspection.setLotNumber(rs.getString("lotnumber"));				
				objInspection.setReceivedQuantity(rs.getLong("receivedquantity"));
				objInspection.setUom(rs.getString("UOM"));
				objInspection.setInspectionStatus1(rs.getString("inspectionstatus"));
				objInspection.setRemarks1(rs.getString("remarks"));
				objInspection.setToolingInspectionId(rs.getInt("toolingInspectionid"));
				objInspection.setToolingInspectionDetailId1(rs.getInt("toolingInspectiondetailid"));
				objInspection.setToolingRequestDetailId1(rs.getInt("toolingrequestdetailId"));
				objInspection.setTypeOfTooling(rs.getString("typeoftool"));
				objInspection.setMachineType(rs.getString("machinetype"));
				objInspection.setRequestDate(rs.getString("inspectionreportdate"));
				objInspection.setPunchSetNo(rs.getString("punchSetNo"));
				objInspection.setCompForce(rs.getInt("compForce"));
				lstToolingInspection.add(objInspection);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getRejectInspectionDetails : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getRejectInspectionDetails : "+ex.getMessage());
			}
		}
	
		return lstToolingInspection;
	
	}
	
	private void storeTransaction(ToolingInspection toolingInspection, int index, String flag)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			{
				/*pstmt1 = con.prepareStatement("Select toolingname, ponumber, podate, drawingno, lotnumber, receivedquantity, UOM, remarks, "
						+ " toolingrequestid, toolingRequestdetailid, toolingProductId, machinetype from tooling_receiving_request_details where toolingRequestdetailid = ?");
				pstmt1.setInt(1, toolingInspection.getToolingRequestDetailId()[index]);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())*/
				{
					try
					{
						pstmt = con.prepareStatement("insert into transaction(productname, drawingno, toolinglotnumber, UOM, stockqty, machinename, "
								+ " transactiondate, Source )"
								+ "values(?, ?, ?, ?, ?, ?, ?, ?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(toolingInspection.getToolingname()));
						pstmt.setString(2, TiimUtil.ValidateNull(toolingInspection.getDrawingNo()));
						pstmt.setString(3, toolingInspection.getLotNumber());
						pstmt.setString(4, TiimUtil.ValidateNull(toolingInspection.getUom()));
						pstmt.setLong(5, toolingInspection.getAcceptedQty());
						pstmt.setString(6, toolingInspection.getMachineType());
						pstmt.setString(7, sdf.format(cDate));
						pstmt.setString(8, "ReceivingInspection");
						pstmt.executeUpdate();
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
			storeStock(toolingInspection, index, flag);
			
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
	
	/*private void storeStock(ToolingInspection toolingInspection, int index)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			{
				pstmt1 = con.prepareStatement("SELECT stockQty FROM stock where toolinglotnumber = '1234' and typeoftool='' and productname = '' and drawingno = '' and machinetype ='';");
				pstmt1.setInt(1, toolingInspection.getToolingRequestDetailId()[index]);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())
				{
					try
					{
						pstmt = con.prepareStatement("insert into transaction(productname, drawingno, toolinglotnumber, UOM, stockqty, machinename, "
								+ " transactiondate )"
								+ "values(?, ?, ?, ?, ?, ?, ?)");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(rs.getString("toolingname")));
						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("drawingno")));
						pstmt.setString(3, rs.getString("lotnumber"));
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("uom")));
						pstmt.setLong(5, rs.getLong("receivedquantity"));
						pstmt.setString(6, rs.getString("machinetype"));
						pstmt.setString(7, sdf.format(cDate));
						
						pstmt.executeUpdate();
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

	
	}*/
	
	private void storeStock(ToolingInspection toolingInspection, int index, String flag)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		long totalStock = 0;
		long receivedQty = 0;
		Stock stock = new Stock();
		try
		{
			con = datasource.getConnection();
			{
				/*pstmt1 = con.prepareStatement("Select toolingname, typeoftool, podate, drawingno, lotnumber, receivedquantity, UOM, remarks, "
						+ " toolingrequestid, toolingRequestdetailid, toolingProductId, machinetype from tooling_receiving_request_details where toolingRequestdetailid = ?");
				pstmt1.setInt(1, toolingInspection.getToolingRequestDetailId()[index]);
				ResultSet rs = pstmt1.executeQuery();
				while(rs.next())*/
				{
					try
					{
						pstmt = con.prepareStatement("SELECT stockQty, stockId FROM stock where toolinglotnumber = ? and typeoftool = ? and productname = ? and drawingno = ? and machinetype = ?");
						//,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(toolingInspection.getLotNumber()));
						pstmt.setString(2, TiimUtil.ValidateNull(toolingInspection.getTypeOfTooling()));
						pstmt.setString(3, toolingInspection.getToolingname());
						pstmt.setString(4, TiimUtil.ValidateNull(toolingInspection.getDrawingNo()));
						pstmt.setString(5, toolingInspection.getMachineType());
						receivedQty = toolingInspection.getAcceptedQty();
						stock.setUom(toolingInspection.getUom());
						stock.setToolingLotNumber(toolingInspection.getLotNumber());
						stock.setTypeOfTool(TiimUtil.ValidateNull(toolingInspection.getTypeOfTooling()).trim());
						stock.setProductName(TiimUtil.ValidateNull(toolingInspection.getToolingname()).trim());
						stock.setDrawingNo(TiimUtil.ValidateNull(toolingInspection.getDrawingNo()).trim());
						stock.setMachineName(toolingInspection.getMachineType());
						stock.setStockQty(receivedQty);
						stock.setBranch(toolingInspection.getBranchName());
						ResultSet rs1 = pstmt.executeQuery();
						if(rs1.next()){
							if("Add".equalsIgnoreCase(flag))
							{
								totalStock = rs1.getInt("stockQty") + receivedQty;
							}
							else
							{
								totalStock = rs1.getInt("stockQty") - receivedQty;
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
	
	public List<Integer> getReceivingInspection(String inspectionId)
	{
		List<Integer> lstInspection = new ArrayList<Integer>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			
			//pstmt = con.prepareStatement("Select toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno from tooling_receipt_note where grnno LIKE '"+grn+"%' order by grnno");
			pstmt = con.prepareStatement("SELECT a.toolingInspectionid  toolingInspectionid FROM tooling_receiving_inspection_details a, tooling_receiving_inspection b where inspectionstatus='Rejected' and a.toolingInspectionid LIKE '"+inspectionId+"%' and b.toolingInspectionid = a.toolingInspectionid");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				lstInspection.add(rs.getInt("toolingInspectionid"));
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting the getReceivingInspection : "+e.getMessage());
		}
		return lstInspection;
	}
	
	public void updateOriginalId(ToolingInspection toolingInspection)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_receiving_inspection set originalid = ? "
						+ " where toolingInspectionid = ?");

				pstmt.setInt(1, toolingInspection.getOriginalId());
				pstmt.setInt(2, toolingInspection.getToolingInspectionId());
				pstmt.executeUpdate();
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in ToolingInspection updateOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in ToolingInspection updateOriginalId : "+ex.getMessage());
			}
		}
	}
	
	/**
	 * Get original Id from tooling_receiving_request
	 * @param requestId
	 * @return
	 */
	private void getRequestVersions(ToolingInspection toolingInspection)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("select originalId, revisionnumber from tooling_receiving_inspection "
						+ " where toolingInspectionid = ?");

				pstmt.setInt(1, toolingInspection.getToolingInspectionId());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					toolingInspection.setOriginalId(rs.getInt("originalId"));
					int revisionNumber = rs.getInt("revisionnumber");
					revisionNumber++;
					toolingInspection.setRevisionNumber(revisionNumber);
				}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in ToolingInspection updateOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in ToolingInspection updateOriginalId : "+ex.getMessage());
			}
		}
	
	}
	
	public boolean isIntegratedWithRequest(int requestId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				/*pstmt = con.prepareStatement("select requestId from tooling_receiving_inspection where requestId in"
						+ " (SELECT toolingrequestId FROM tooling_receiving_request where originalId = ?)");*/
				pstmt = con.prepareStatement("select requestId from tooling_receiving_inspection where requestId = ?");
				pstmt.setInt(1, requestId);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					return true;
				}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when ToolingInspection  isIntegratedWithRequest method : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in ToolingInspection isIntegratedWithRequest : "+ex.getMessage());
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
				pstmt = con.prepareStatement("select max(originalid) originalid from tooling_receiving_inspection");
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					originalId = rs.getInt("originalid");
				}
				
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the tooling receipt note detail in tooling_receiving_inspection  getMaxOriginalId : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updating detail in tooling_receiving_inspection getMaxOriginalId : "+ex.getMessage());
			}
		}
	
		return originalId;
	}
	
	private void updateReceivingRequestStatus(ToolingInspection toolingInspection, int status)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_receiving_request set isreport = ? where originalId = ?");
				pstmt.setInt(1, status);
				pstmt.setInt(2, toolingInspection.getRequestId());
				pstmt.executeUpdate();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in updateReceivingRequestStatus  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling receipt note detail in updateReceivingRequestStatus table : "+ex.getMessage());
			}
		}
	
	}
	
	private void addSerialNumber(ToolingInspection toolingInspection) {
		ToolSerialNumber toolSerialNumber = new ToolSerialNumber();
		toolSerialNumber.setLotNumber(toolingInspection.getLotNumber());
		toolSerialNumber.setModule("Tool Receiving Inspection");
		toolSerialNumber.setAcceptQty(toolingInspection.getAcceptedQty());
		toolSerialNumber.setRejectQty(toolingInspection.getRejectedQty());
		toolSerialNumber.setSerialNumber(toolingInspection.getRejectedSerialNumber());
		toolSerialNumberDao.addSerialNumber(toolSerialNumber);
	}
}
