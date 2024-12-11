package com.tiim.dao;

import java.sql.Connection;
import java.sql.Date;
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
import com.tiim.model.Stock;
import com.tiim.model.ToolingInspection;
import com.tiim.model.ToolingReceiptNote;
import com.tiim.util.TiimUtil;

@Repository
public class ToolingReceiptNoteDao {
	
	@Autowired
	DataSource datasource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	CheckExpiryDateDao expiryDao;
	
	@Autowired
	ProductDao productDao;
	
	public int getIntialValue()
	{
		int grnNo = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(toolingreceiptid) toolingreceiptid FROM tooling_receipt_note ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				grnNo = rs.getInt("toolingreceiptid");
			}
			grnNo++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ToolingReceiptNoteDao  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in ToolingReceiptNoteDao : "+ex.getMessage());
			}
		}
		
		return grnNo;	
	}
	
	public int getIntialLotNumberValue()
	{
		int lotNumber = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(toolingproductid) toolingproductid FROM tooling_receipt_product");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				lotNumber = rs.getInt("toolingproductid");
			}
			lotNumber++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in IntialLotNumberValue  getIntialLotNumberValue  : "+ex.getMessage());
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
				System.out.println("Exception in IntialLotNumberValue  getIntialLotNumberValue: "+ex.getMessage());
			}
		}
		
		return lotNumber;	
	}
	
	/*public String addToolingReceiptNote(ToolingReceiptNote toolReceipt)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		int toolingReceiptId = 0;
		try
		{
			if(isToolReceiptExists(toolReceipt.getGrnNo()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into tooling_receipt_note(grnno, grndate, po, suppliercode, suppliername, productserno)"
						+ "values(?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
				//,Statement.RETURN_GENERATED_KEYS
				pstmt.setInt(1, toolReceipt.getGrnNo());
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(2, date);
				pstmt.setString(3, TiimUtil.ValidateNull(toolReceipt.getPo()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(toolReceipt.getSupplierCode()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(toolReceipt.getSupplierName()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(toolReceipt.getProductSerialNo()).trim());
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					toolingReceiptId = rs.getInt(1);
				}
				toolReceipt.setToolingReceiptId(toolingReceiptId);
				addToolingReceiptProduct(toolReceipt);
				msg = "Saved Successfully";
			}
			else
			{
				msg = "Already Exists";
			}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the tooling receipt note detail in tooling_receipt_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return msg;
	}*/
	
	public String addToolingReceiptNote(ToolingReceiptNote toolReceipt, int userId)
	{
		//System.out.println("--Start--");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int toolingReceiptId = 0;
		try
		{
			con = datasource.getConnection();
			if(isToolReceiptExists(toolReceipt.getGrnNo()))
			{
				pstmt = con.prepareStatement("insert into tooling_receipt_note(grnno, grndate, po, suppliercode, suppliername, productserno, branchname)"
						+ "values(?, ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
				//,Statement.RETURN_GENERATED_KEYS
				pstmt.setInt(1, toolReceipt.getGrnNo());
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(2, date);
				pstmt.setString(3, TiimUtil.ValidateNull(toolReceipt.getPo()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(toolReceipt.getSupplierCode()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(toolReceipt.getSupplierName()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(toolReceipt.getProductSerialNo()).trim());
				pstmt.setString(7, TiimUtil.ValidateNull(toolReceipt.getBranchName()).trim());
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					toolingReceiptId = rs.getInt(1);
				}
				toolReceipt.setToolingReceiptId(toolingReceiptId);
				msg = addToolingReceiptProduct(toolReceipt);
				//addToolingReceiptProduct1(toolReceipt);
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("receipt.page", null,null));
				history.setDescription(messageSource.getMessage("receipt.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			}
			else
			{
				 pstmt = con.prepareStatement("SELECT toolingreceiptid FROM tooling_receipt_note WHERE grnno = ?");
				 pstmt.setInt(1, toolReceipt.getGrnNo());
				 rs = pstmt.executeQuery();
				 if(rs.next())
				 {
					 toolReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				 }
				 msg = addToolingReceiptProduct(toolReceipt);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in tooling_receipt_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	public String addToolingReceiptProduct(ToolingReceiptNote toolReceipt)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int toolingReceiptProdId = 0;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into tooling_receipt_product(toolingreceiptid, productname, drawingno, strength, machinetype, typeoftool,"
						+ "	mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
						+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber,"
						+ " toolingcodenumber, expiryDate, storagelocation, cleaningsop, dquploadimage, mocuploadimage, inspectionimageupload, "
						+ " uppperQty, lowerQty, dieQty,minacceptedQty, toolinglife, serviceintervalqty,mandate,punchSetNo,compForce)"
						+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
				System.out.println("getHardCromePlatingPO::::::::::::::  "+toolReceipt.getHardCromePlatingPO());
				pstmt.setInt(1, toolReceipt.getToolingReceiptId());
				pstmt.setString(2, toolReceipt.getProductNamePO());
				pstmt.setString(3, TiimUtil.ValidateNull(toolReceipt.getDrawingNoPO()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(toolReceipt.getStrength()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(toolReceipt.getMachineTypePO()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(toolReceipt.getTypeOfToolPO()).trim());
				
				pstmt.setString(7, TiimUtil.ValidateNull(toolReceipt.getMocPunchesPO()).trim());
				pstmt.setString(8, TiimUtil.ValidateNull(toolReceipt.getMocDiesPO()).trim());
				pstmt.setString(9, TiimUtil.ValidateNull(toolReceipt.getShapePO()).trim());
				pstmt.setString(10, TiimUtil.ValidateNull(toolReceipt.getDimensionsPO()).trim());
				pstmt.setString(11, TiimUtil.ValidateNull(toolReceipt.getBreakLineUpperPO()).trim());
				pstmt.setString(12, TiimUtil.ValidateNull(toolReceipt.getBreakLineLowerPO()).trim());
				pstmt.setString(13, TiimUtil.ValidateNull(toolReceipt.getEmbosingUpperPO()).trim());
				pstmt.setString(14, TiimUtil.ValidateNull(toolReceipt.getEmbosingLowerPO()).trim());
				pstmt.setString(15, TiimUtil.ValidateNull(toolReceipt.getLaserMarkingPO()).trim());
				pstmt.setString(16, TiimUtil.ValidateNull(toolReceipt.getHardCromePlatingPO()).trim());
				pstmt.setString(17, TiimUtil.ValidateNull(toolReceipt.getDustCapGroovePO()).trim());
				pstmt.setLong(18, toolReceipt.getPoQuantity());
				pstmt.setLong(19, toolReceipt.getReceivedQuantity());
				pstmt.setString(20, toolReceipt.getToolingLotNumberPO());
				pstmt.setString(21, toolReceipt.getUomPO());
				pstmt.setInt(22, toolReceipt.getReceiptStatus());
				pstmt.setString(23, toolReceipt.getMocNumber());
				pstmt.setString(24, toolReceipt.getDqDocument());
				pstmt.setString(25, toolReceipt.getInspectionReportNumber());
				pstmt.setString(26, toolReceipt.getToolingCodeNumberPO());
				pstmt.setDate(27, toolReceipt.getManDate());
				//pstmt.setString(27, toolReceipt.getExpiryDates());
				pstmt.setString(28, TiimUtil.ValidateNull(toolReceipt.getStorageLocation()));
				pstmt.setString(29, TiimUtil.ValidateNull(toolReceipt.getCleaningSOP()));
				pstmt.setString(30, TiimUtil.ValidateNull(toolReceipt.getDqUploadImage()));
				pstmt.setString(31, TiimUtil.ValidateNull(toolReceipt.getMocUploadImage()));
				pstmt.setString(32, TiimUtil.ValidateNull(toolReceipt.getInspectionUploadImage()));
				pstmt.setInt(33, toolReceipt.getUpperQnty());
				pstmt.setInt(34, toolReceipt.getLowerQnty());
				pstmt.setInt(35, toolReceipt.getDieQnty());
				pstmt.setInt(36, toolReceipt.getMinAcceptedQty());
				Product product = productDao.getProductIntervalQnty(toolReceipt.getProductNamePO(), toolReceipt.getDrawingNoPO());
				int toolingLife = Integer.parseInt(product.getToolingLife());
				int intervalQty = Integer.parseInt(product.getServiceIntervalQty());
				Long calculatedToolLife= toolingLife * toolReceipt.getReceivedQuantity();
				System.out.println("calculatedToolLife:  "+calculatedToolLife);
				pstmt.setLong(37, (calculatedToolLife));
				pstmt.setLong(38, (intervalQty * toolReceipt.getReceivedQuantity()));
				pstmt.setDate(39,  toolReceipt.getManDate());
				pstmt.setString(40, toolReceipt.getPunchSetNo());
				pstmt.setInt(41, toolReceipt.getCompForce());
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					toolingReceiptProdId = rs.getInt(1);
				}
				toolReceipt.setToolingProductId(toolingReceiptProdId);
				//updateToolingLogNumber(toolingReceiptProdId);
				msg = "Saved Successfully</>"+toolReceipt.getToolingReceiptId();
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling_receipt_product detail in tooling_receipt_product table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling product detail in tooling_receipt_product table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	private void updateToolingLogNumber(int toolingProductId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_receipt_product set toolinglotnumber = ? where toolingproductid = ?");
				pstmt.setInt(1, toolingProductId);
				pstmt.setInt(2, toolingProductId);
				pstmt.executeUpdate();
		
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updateToolingLogNumber : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in updateToolingLogNumber : "+ex.getMessage());
			}
		}
	
	}
	
	public String addToolingReceiptProduct1(ToolingReceiptNote toolReceipt)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into tooling_receipt_product1(toolingreceiptid, productname, drawingno, strength, machinetype, typeoftool,"
						+ "	mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
						+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber, expiryDate"
						+ " , punchSetNo, compForce)"
						+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?)");

				pstmt.setInt(1, toolReceipt.getToolingReceiptId());
				pstmt.setString(2, toolReceipt.getProductName());
				pstmt.setString(3, TiimUtil.ValidateNull(toolReceipt.getDrawingNo()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(toolReceipt.getStrength()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(toolReceipt.getMachineType()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(toolReceipt.getTypeOfTool()).trim());
				
				pstmt.setString(7, TiimUtil.ValidateNull(toolReceipt.getMocPunches()).trim());
				pstmt.setString(8, TiimUtil.ValidateNull(toolReceipt.getMocDies()).trim());
				pstmt.setString(9, TiimUtil.ValidateNull(toolReceipt.getShape()).trim());
				pstmt.setString(10, TiimUtil.ValidateNull(toolReceipt.getDimensions()).trim());
				pstmt.setString(11, TiimUtil.ValidateNull(toolReceipt.getBreakLineUpper()).trim());
				pstmt.setString(12, TiimUtil.ValidateNull(toolReceipt.getBreakLineLower()).trim());
				pstmt.setString(13, TiimUtil.ValidateNull(toolReceipt.getEmbosingUpper()).trim());
				pstmt.setString(14, TiimUtil.ValidateNull(toolReceipt.getEmbosingLower()).trim());
				pstmt.setString(15, TiimUtil.ValidateNull(toolReceipt.getLaserMarking()).trim());
				pstmt.setString(16, TiimUtil.ValidateNull(toolReceipt.getHardCromePlating()).trim());
				pstmt.setString(17, TiimUtil.ValidateNull(toolReceipt.getDustCapGroove()).trim());
				pstmt.setLong(18, toolReceipt.getPoQuantity());
				pstmt.setLong(19, toolReceipt.getReceivedQuantity());
				pstmt.setString(20, toolReceipt.getToolingLotNumber());
				pstmt.setString(21, toolReceipt.getUom());
				pstmt.setInt(22, toolReceipt.getReceiptStatus());
				pstmt.setString(23, toolReceipt.getMocNumber());
				pstmt.setString(24, toolReceipt.getDqDocument());
				pstmt.setString(25, toolReceipt.getInspectionReportNumber());
				pstmt.setDate(26, toolReceipt.getManDate());
				pstmt.setString(27, toolReceipt.getPunchSetNo());
				pstmt.setInt(28, toolReceipt.getCompForce());
				//pstmt.setString(26, toolReceipt.getExpiryDates());
				pstmt.executeUpdate();
				
				System.out.println("toolReceipt.getHardCromePlating(): "+toolReceipt.getHardCromePlating());
				msg = "Saved Successfully</>"+toolReceipt.getToolingReceiptId();
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling_receipt_product detail in tooling_receipt_product table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling product detail in tooling_receipt_product table : "+ex.getMessage());
			}
		}
		return msg;
	}
	
	private boolean isToolReceiptExists(int grnNo)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM tooling_receipt_note WHERE grnno = ?");
			 pstmt.setInt(1, grnNo);
			 rs = pstmt.executeQuery();
			 if(rs.next())
			 {
				 count = rs.getInt("Is_Exists");
			 }
			 
			 if(count > 0)
			 {
				 isExists = false;
			 }
		}
		catch(Exception e)
		{
			System.out.println("Exception while checking the grnno exists in isToolReceiptExists table when adding : "+e.getMessage());
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
				System.out.println("Exception when closing the connection in grnno master detail in isToolReceiptExists table when adding : "+ex.getMessage());
			}
		}
		return isExists;
	
	}
	
	public String updateToolingReceiptNote(ToolingReceiptNote toolReceipt, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_receipt_note set grnno = ?, grndate = ?, po = ?, suppliercode = ?, suppliername = ?, productserno = ? "
						+ "where toolingreceiptid = ?");
				//,Statement.RETURN_GENERATED_KEYS
				pstmt.setInt(1, toolReceipt.getGrnNo());
				//SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				/*try {

					date = (Date) formatter.parse(toolReceipt.getGrnDate());
					System.out.println(date);
					System.out.println(formatter.format(date));

				} catch (Exception e) {
					e.printStackTrace();
				}*/
				pstmt.setDate(2, date);
				pstmt.setString(3, TiimUtil.ValidateNull(toolReceipt.getPo()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(toolReceipt.getSupplierCode()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(toolReceipt.getSupplierName()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(toolReceipt.getProductSerialNo()).trim());
				pstmt.setInt(7, toolReceipt.getToolingReceiptId());
				pstmt.executeUpdate();
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("receipt.page", null,null));
				history.setDescription(messageSource.getMessage("receipt.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				updateToolingReceiptProduct(toolReceipt);
				updateToolingReceiptProduct1(toolReceipt);
				msg = "updated Successfully</>"+ toolReceipt.getToolingReceiptId();
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the tooling receipt note detail in tooling_receipt_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling receipt note detail in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return msg;
	
	}

	public String updateToolingReceiptProduct(ToolingReceiptNote toolReceipt)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_receipt_product set toolingreceiptid = ?, productname = ?, drawingno = ?, strength = ?, machinetype = ?, typeoftool = ?,"
						+ "	mocpunches = ?, mocdies = ?, shape = ?, dimensions = ?, breaklineupper = ?, breaklinelower = ?, embosingUpper = ?, embosingLower = ?, lasermarking = ?, hardcromeplating = ?,"
						+ " dustcapgroove = ?, poquantity = ?, receivedquantity = ?, toolinglotnumber = ?, uom = ?, receiptStatus = ?, mocNumber = ?, dqDocument = ?, inspectionReportNumber = ?,"
						+ " toolingcodenumber= ?, expiryDate = ?,  storagelocation = ?, cleaningsop = ?, dquploadimage = ?, mocuploadimage = ?, inspectionimageupload = ?,"
						+ " uppperQty = ?, lowerQty = ?, dieQty = ?, minacceptedQty = ?, toolinglife = ?, serviceintervalqty = ?, manDate = ?,punchSetNo=?, compForce=? "
						+ " where toolingproductid = ?");

				pstmt.setInt(1, toolReceipt.getToolingReceiptId());
				pstmt.setString(2, toolReceipt.getProductNamePO());
				pstmt.setString(3, TiimUtil.ValidateNull(toolReceipt.getDrawingNoPO()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(toolReceipt.getStrength()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(toolReceipt.getMachineTypePO()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(toolReceipt.getTypeOfToolPO()).trim());
				
				pstmt.setString(7, TiimUtil.ValidateNull(toolReceipt.getMocPunchesPO()).trim());
				pstmt.setString(8, TiimUtil.ValidateNull(toolReceipt.getMocDiesPO()).trim());
				pstmt.setString(9, TiimUtil.ValidateNull(toolReceipt.getShapePO()).trim());
				pstmt.setString(10, TiimUtil.ValidateNull(toolReceipt.getDimensionsPO()).trim());
				pstmt.setString(11, TiimUtil.ValidateNull(toolReceipt.getBreakLineUpperPO()).trim());
				pstmt.setString(12, TiimUtil.ValidateNull(toolReceipt.getBreakLineLowerPO()).trim());
				pstmt.setString(13, TiimUtil.ValidateNull(toolReceipt.getEmbosingUpperPO()).trim());
				pstmt.setString(14, TiimUtil.ValidateNull(toolReceipt.getEmbosingLowerPO()).trim());
				pstmt.setString(15, TiimUtil.ValidateNull(toolReceipt.getLaserMarkingPO()).trim());
				pstmt.setString(16, TiimUtil.ValidateNull(toolReceipt.getHardCromePlatingPO()).trim());
				pstmt.setString(17, TiimUtil.ValidateNull(toolReceipt.getDustCapGroovePO()).trim());
				pstmt.setLong(18, toolReceipt.getPoQuantity());
				pstmt.setLong(19, toolReceipt.getReceivedQuantity());
				pstmt.setString(20, toolReceipt.getToolingLotNumberPO());
				pstmt.setString(21, toolReceipt.getUomPO());
				pstmt.setInt(22, toolReceipt.getReceiptStatus());
				pstmt.setString(23, toolReceipt.getMocNumber());
				pstmt.setString(24, toolReceipt.getDqDocument());
				pstmt.setString(25, toolReceipt.getInspectionReportNumber());
				pstmt.setString(26, toolReceipt.getToolingCodeNumberPO());
				//pstmt.setDate(27, toolReceipt.getExpiryDate());
				pstmt.setString(28, toolReceipt.getStorageLocation());
				pstmt.setString(29, toolReceipt.getCleaningSOP());
				//System.out.println("toolReceipt.getReceivedQuantity(): "+toolReceipt.getDqUploadImage()+", "+TiimUtil.ValidateNull(toolReceipt.getDqUploadImage()));
				pstmt.setString(30, TiimUtil.ValidateNull(toolReceipt.getDqUploadImage()));
				pstmt.setString(31, TiimUtil.ValidateNull(toolReceipt.getMocUploadImage()));
				pstmt.setString(32, TiimUtil.ValidateNull(toolReceipt.getInspectionUploadImage()));
				pstmt.setInt(33, toolReceipt.getUpperQnty());
				pstmt.setInt(34, toolReceipt.getLowerQnty());
				pstmt.setInt(35, toolReceipt.getDieQnty());
				pstmt.setInt(36, toolReceipt.getMinAcceptedQty());
				Product product = productDao.getProductIntervalQnty(toolReceipt.getProductNamePO(), toolReceipt.getDrawingNoPO());
				//int toolingLife = Integer.parseInt(product.getToolingLife());
				int intervalQty = Integer.parseInt(product.getServiceIntervalQty());
				//pstmt.setLong(37, (toolingLife * toolReceipt.getReceivedQuantity()));
				pstmt.setLong(37, (toolReceipt.getReceivedQuantity()));
				pstmt.setLong(38, (intervalQty * toolReceipt.getReceivedQuantity()));
				pstmt.setDate(39, toolReceipt.getManDate());
				pstmt.setString(40,toolReceipt.getPunchSetNo());
				pstmt.setInt(41, toolReceipt.getCompForce());
				
				pstmt.setInt(42, toolReceipt.getToolingProductId());
				pstmt.executeUpdate();
				
				msg = "updated Successfully";
		
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the tooling_receipt_product detail in tooling_receipt_product table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling product detail in tooling_receipt_product table : "+ex.getMessage());
			}
		}
		return msg;

	}
	
	public String updateToolingReceiptProduct1(ToolingReceiptNote toolReceipt)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_receipt_product1 set toolingreceiptid = ?, productname = ?, drawingno = ?, strength = ?, machinetype = ?, typeoftool = ?,"
						+ "	mocpunches = ?, mocdies = ?, shape = ?, dimensions = ?, breaklineupper = ?, breaklinelower = ?, embosingUpper = ?, embosingLower = ?, lasermarking = ?, hardcromeplating = ?,"
						+ " dustcapgroove = ?, poquantity = ?, receivedquantity = ?, toolinglotnumber = ?, uom = ?, receiptStatus = ?, mocNumber = ?, dqDocument = ?, "
						+ " inspectionReportNumber = ?,  punchSetNo=?, compForce=? "
						+ " where toolingproductid = ?");

				pstmt.setInt(1, toolReceipt.getToolingReceiptId());
				pstmt.setString(2, toolReceipt.getProductName());
				pstmt.setString(3, TiimUtil.ValidateNull(toolReceipt.getDrawingNo()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(toolReceipt.getStrength()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(toolReceipt.getMachineType()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(toolReceipt.getTypeOfTool()).trim());
				
				pstmt.setString(7, TiimUtil.ValidateNull(toolReceipt.getMocPunches()).trim());
				pstmt.setString(8, TiimUtil.ValidateNull(toolReceipt.getMocDies()).trim());
				pstmt.setString(9, TiimUtil.ValidateNull(toolReceipt.getShape()).trim());
				pstmt.setString(10, TiimUtil.ValidateNull(toolReceipt.getDimensions()).trim());
				pstmt.setString(11, TiimUtil.ValidateNull(toolReceipt.getBreakLineUpper()).trim());
				pstmt.setString(12, TiimUtil.ValidateNull(toolReceipt.getBreakLineLower()).trim());
				pstmt.setString(13, TiimUtil.ValidateNull(toolReceipt.getEmbosingUpper()).trim());
				pstmt.setString(14, TiimUtil.ValidateNull(toolReceipt.getEmbosingLower()).trim());
				pstmt.setString(15, TiimUtil.ValidateNull(toolReceipt.getLaserMarking()).trim());
				pstmt.setString(16, TiimUtil.ValidateNull(toolReceipt.getHardCromePlating()).trim());
				pstmt.setString(17, TiimUtil.ValidateNull(toolReceipt.getDustCapGroove()).trim());
				pstmt.setLong(18, toolReceipt.getPoQuantity());
				pstmt.setLong(19, toolReceipt.getReceivedQuantity());
				pstmt.setString(20, toolReceipt.getToolingLotNumber());
				pstmt.setString(21, toolReceipt.getUom());
				pstmt.setInt(22, toolReceipt.getReceiptStatus());
				pstmt.setString(23, toolReceipt.getMocNumber());
				pstmt.setString(24, toolReceipt.getDqDocument());
				pstmt.setString(25, toolReceipt.getInspectionReportNumber());
				//pstmt.setDate(26, toolReceipt.getExpiryDate());
				pstmt.setString(26,toolReceipt.getPunchSetNo());
				pstmt.setInt(27, toolReceipt.getCompForce());
				pstmt.setInt(28, toolReceipt.getToolingProductId());
				
				pstmt.executeUpdate();
				
				msg = "updated Successfully";
		
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the tooling_receipt_product detail in tooling_receipt_product table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in tooling product detail in tooling_receipt_product table : "+ex.getMessage());
			}
		}
		return msg;

	}
	
	public String changeToolingReceiptNoteStatus(int toolingReceiptId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try
		{
			   con = datasource.getConnection();
				pstmt = con.prepareStatement("Select isActive from tooling_receipt_note where toolingreceiptid = ?");
				pstmt.setInt(1, toolingReceiptId);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					isActive = rs.getInt("isActive");
					if(isActive == 1)
					{
						isActive=0;
						msg = "Made InActive Successfully";
					}
					else
					{
						isActive=1;
						msg = "Made Active Successfully";	
					}
				}
				
				pstmt = con.prepareStatement("Update tooling_receipt_note set isActive = ?  where toolingreceiptid = ?");
				pstmt.setInt(1, isActive);
				pstmt.setInt(2, toolingReceiptId);
				pstmt.executeUpdate();								
		}
		catch(Exception ex)
		{
			System.out.println("Exception when changing the status of Tooling receipt in tooling_receipt_note table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connectin in changing the status of Tooling receipt in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		
		return msg;
	
	}
	
	public String updateToolingReceiptProductStatus(int toolingProductId, int toolingReceiptId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try
		{
			   con = datasource.getConnection();
			   if(toolingReceiptId > 0)
			   {
				   pstmt = con.prepareStatement("Select isActive from tooling_receipt_product where toolingreceiptid = ?");
				   pstmt.setInt(1, toolingReceiptId);
			   }else if(toolingProductId > 0)
			   {
				   pstmt = con.prepareStatement("Select isActive from tooling_receipt_product where toolingproductid = ?");
				   pstmt.setInt(1, toolingProductId);
			   }
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					isActive = rs.getInt("isActive");
					if(isActive == 1)
					{
						isActive=0;
						msg = "Made InActive Successfully";
					}
					else
					{
						isActive=1;
						msg = "Made Active Successfully";	
					}
				}
				if(toolingReceiptId > 0)
				{
					pstmt = con.prepareStatement("Update tooling_receipt_product set isActive = ?  where toolingreceiptid = ?");
					pstmt.setInt(1, isActive);
					pstmt.setInt(2, toolingReceiptId);
				}else if(toolingProductId > 0)
				{
					pstmt = con.prepareStatement("Update tooling_receipt_product set isActive = ?  where toolingproductid = ?");
					pstmt.setInt(1, isActive);
					pstmt.setInt(2, toolingProductId);
				}
				pstmt.executeUpdate();								
		}
		catch(Exception ex)
		{
			System.out.println("Exception when changing the status of Tooling receipt in tooling_receipt_note table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connectin in changing the status of Tooling receipt in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		
		return msg;
	
	}
	
	public List<ToolingReceiptNote> getToolingReceiptNoteDetails(String searchToolingReceipt)
	{
		List<ToolingReceiptNote> lstToolingReceiptProduct = new ArrayList<ToolingReceiptNote>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt;
		try
		{
			con = datasource.getConnection();
			if(searchToolingReceipt != null && !"".equals(searchToolingReceipt))
			{
			    pstmt = con.prepareStatement("Select toolingreceiptid, grnno, DATE_FORMAT(grndate,'%m/%d/%Y') AS grndate, po, suppliercode, suppliername, productserno from tooling_receipt_note Where grnno like '%"+searchToolingReceipt+"%' order by grnno desc");
			}
			else
			{
				pstmt = con.prepareStatement("Select toolingreceiptid, grnno, DATE_FORMAT(grndate,'%m/%d/%Y') AS grndate, po, suppliercode, suppliername, productserno from tooling_receipt_note order by grnno desc");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setGrnNo(rs.getInt("grnno"));
				toolingReceipt.setGrnDate(rs.getString("grndate"));
				toolingReceipt.setPo(rs.getString("po"));
				toolingReceipt.setSupplierCode(rs.getString("suppliercode"));
				toolingReceipt.setSupplierName(rs.getString("suppliername"));
				toolingReceipt.setProductSerialNo(rs.getString("productserno"));
				toolingReceipt.setIsActive(0);
				lstToolingReceiptProduct.add(toolingReceipt);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolingReceiptNoteDetails in tooling_receipt_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getToolingReceiptNoteDetails details in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return lstToolingReceiptProduct;	
	}
	
	public List<ToolingReceiptNote> getToolingReceiptProductDetails(String searchToolingProduct, int toolingReceiptId)
	{
		List<ToolingReceiptNote> lstToolingReceiptNote = new ArrayList<ToolingReceiptNote>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt;
		try
		{
			con = datasource.getConnection();
			if(searchToolingProduct != null && !"".equals(searchToolingProduct))
			{
			    pstmt = con.prepareStatement("Select toolingreceiptid, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber, toolinglife,serviceintervalqty, "
			    		+ " toolingcodenumber, expiryDate, storagelocation, cleaningsop, dquploadimage, mocuploadimage, inspectionimageupload, uppperQty,"
			    		+ " lowerQty, dieQty, minacceptedQty, mandate, punchSetNo, compForce "
			    		+ " from tooling_receipt_product Where toolingreceiptid = ?"
			    		+ " and productname like '%"+searchToolingProduct+"%' order by productname");
			}
			else
			{
				pstmt = con.prepareStatement("Select toolingreceiptid, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber,toolinglife,serviceintervalqty, "
			    		+ "  toolingcodenumber, expiryDate, storagelocation, cleaningsop, dquploadimage, mocuploadimage, inspectionimageupload, uppperQty,"
			    		+ " lowerQty, dieQty, minacceptedQty, mandate, punchSetNo, compForce  "
			    		+ " from tooling_receipt_product Where toolingreceiptid = ?  order by productname");
			}
			pstmt.setInt(1, toolingReceiptId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setToolingProductId(rs.getInt("toolingproductid"));
				toolingReceipt.setProductName(rs.getString("productname"));
				toolingReceipt.setDrawingNo(rs.getString("drawingno"));
				toolingReceipt.setStrength(rs.getString("strength"));
				toolingReceipt.setMachineType(rs.getString("machinetype"));
				toolingReceipt.setTypeOfTool(rs.getString("typeoftool"));
				toolingReceipt.setMocPunches(rs.getString("mocpunches"));
				toolingReceipt.setMocDies(rs.getString("mocdies"));
				toolingReceipt.setShape(rs.getString("shape"));
				toolingReceipt.setDimensions(rs.getString("dimensions"));
				toolingReceipt.setBreakLineUpper(rs.getString("breaklineupper"));
				toolingReceipt.setBreakLineLower(rs.getString("breaklinelower"));
				toolingReceipt.setEmbosingUpper(rs.getString("embosingUpper"));
				toolingReceipt.setEmbosingLower(rs.getString("embosingLower"));
				toolingReceipt.setLaserMarking(rs.getString("lasermarking"));
				toolingReceipt.setHardCromePlating(rs.getString("hardcromeplating"));
				toolingReceipt.setDustCapGroove(rs.getString("dustcapgroove"));
				toolingReceipt.setPoQuantity(rs.getLong("poquantity"));
				toolingReceipt.setReceivedQuantity(rs.getLong("receivedquantity"));
				toolingReceipt.setToolingLotNumber(rs.getString("toolinglotnumber"));			
				toolingReceipt.setUom(rs.getString("uom"));
				toolingReceipt.setMocNumber(rs.getString("mocNumber"));
				toolingReceipt.setDqDocument(rs.getString("dqDocument"));
				toolingReceipt.setInspectionReportNumber(rs.getString("inspectionReportNumber"));		
				toolingReceipt.setToolingCodeNumber(rs.getString("toolingcodenumber"));
				//toolingReceipt.setExpiryDate(rs.getDate("expiryDate"));
				toolingReceipt.setStorageLocation(rs.getString("storagelocation"));
				toolingReceipt.setMocUploadImage(rs.getString("mocuploadimage"));
				toolingReceipt.setDqUploadImage(rs.getString("dquploadimage"));
				toolingReceipt.setInspectionUploadImage(rs.getString("inspectionimageupload"));
				toolingReceipt.setUpperQnty(rs.getInt("uppperQty"));
				toolingReceipt.setLowerQnty(rs.getInt("lowerQty"));
				toolingReceipt.setDieQnty(rs.getInt("dieQty"));
				toolingReceipt.setCleaningSOP(rs.getString("cleaningsop"));
				toolingReceipt.setMinAcceptedQty(rs.getInt("minacceptedQty"));
				toolingReceipt.setManDate(rs.getDate("manDate"));
				toolingReceipt.setToolingLife(rs.getLong("toolinglife"));
				toolingReceipt.setServiceIntervalQnty(rs.getLong("serviceintervalqty"));
				toolingReceipt.setPunchSetNo(rs.getString("punchSetNo"));
				toolingReceipt.setCompForce(rs.getInt("compForce"));
				lstToolingReceiptNote.add(toolingReceipt);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolingReceiptNoteDetails in tooling_receipt_product table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getToolingReceiptNoteDetails details in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return lstToolingReceiptNote;	
	}
	
	public List<ToolingReceiptNote> getToolingRejectReceiptProductDetails(String drawingNumber)
	{
		System.out.println("getToolingRejectReceiptProductDetails...");
		List<ToolingReceiptNote> lstToolingReceiptNote = new ArrayList<ToolingReceiptNote>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt;
		try
		{
			con = datasource.getConnection();
			if(drawingNumber != null && !"".equals(drawingNumber))
			{
			    pstmt = con.prepareStatement("Select a.toolingreceiptid toolingreceiptid, branchname, grndate, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber, toolingcodenumber, expiryDate "
			    		+ " , storagelocation, cleaningsop, dquploadimage, mocuploadimage, inspectionimageupload, uppperQty, lowerQty, dieQty, minacceptedQty,"
			    		+ " mandate, punchSetNo, compForce  "
			    		+ " from tooling_receipt_product a , tooling_receipt_note b Where a.toolingreceiptid = b.toolingreceiptid and receiptStatus = ?"
			    		+ " and drawingno like '%"+drawingNumber+"%'  and b.toolingreceiptid not in (select requestno from suplier_return)  order by drawingno");
			}
			else
			{
				pstmt = con.prepareStatement("Select a.toolingreceiptid toolingreceiptid, grndate, branchname, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber, toolingcodenumber, expiryDate "
			    		+ " , storagelocation, cleaningsop, dquploadimage, mocuploadimage, inspectionimageupload, uppperQty, lowerQty, dieQty, minacceptedQty,"
			    		+ " mandate, punchSetNo, compForce  "
			    		+ " from tooling_receipt_product a, tooling_receipt_note b  Where a.toolingreceiptid = b.toolingreceiptid and receiptStatus = ?  "
			    		+ " and b.toolingreceiptid not in (select requestno from suplier_return) order by productname");
			}
			pstmt.setInt(1, 0);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setBranchName(rs.getString("branchname"));
				toolingReceipt.setGrnDate(rs.getString("grndate"));
				toolingReceipt.setToolingProductId(rs.getInt("toolingproductid"));
				toolingReceipt.setProductName(rs.getString("productname"));
				toolingReceipt.setDrawingNo(rs.getString("drawingno"));
				toolingReceipt.setStrength(rs.getString("strength"));
				toolingReceipt.setMachineType(rs.getString("machinetype"));
				toolingReceipt.setTypeOfTool(rs.getString("typeoftool"));
				toolingReceipt.setMocPunches(rs.getString("mocpunches"));
				toolingReceipt.setMocDies(rs.getString("mocdies"));
				toolingReceipt.setShape(rs.getString("shape"));
				toolingReceipt.setDimensions(rs.getString("dimensions"));
				toolingReceipt.setBreakLineUpper(rs.getString("breaklineupper"));
				toolingReceipt.setBreakLineLower(rs.getString("breaklinelower"));
				toolingReceipt.setEmbosingUpper(rs.getString("embosingUpper"));
				toolingReceipt.setEmbosingLower(rs.getString("embosingLower"));
				toolingReceipt.setLaserMarking(rs.getString("lasermarking"));
				toolingReceipt.setHardCromePlating(rs.getString("hardcromeplating"));
				toolingReceipt.setDustCapGroove(rs.getString("dustcapgroove"));
				toolingReceipt.setPoQuantity(rs.getLong("poquantity"));
				toolingReceipt.setReceivedQuantity(rs.getLong("receivedquantity"));
				toolingReceipt.setToolingLotNumber(rs.getString("toolinglotnumber"));			
				toolingReceipt.setUom(rs.getString("uom"));
				toolingReceipt.setMocNumber(rs.getString("mocNumber"));
				toolingReceipt.setDqDocument(rs.getString("dqDocument"));
				toolingReceipt.setInspectionReportNumber(rs.getString("inspectionReportNumber"));		
				toolingReceipt.setToolingCodeNumber(rs.getString("toolingcodenumber"));
				//toolingReceipt.setExpiryDate(rs.getDate("expiryDate"));
				toolingReceipt.setStorageLocation(rs.getString("storagelocation"));
				toolingReceipt.setMocUploadImage(rs.getString("mocuploadimage"));
				toolingReceipt.setDqUploadImage(rs.getString("dquploadimage"));
				toolingReceipt.setInspectionUploadImage(rs.getString("inspectionimageupload"));
				toolingReceipt.setUpperQnty(rs.getInt("uppperQty"));
				toolingReceipt.setLowerQnty(rs.getInt("lowerQty"));
				toolingReceipt.setDieQnty(rs.getInt("dieQty"));
				toolingReceipt.setCleaningSOP(rs.getString("cleaningsop"));
				toolingReceipt.setMinAcceptedQty(rs.getInt("minacceptedQty"));
				toolingReceipt.setManDate(rs.getDate("manDate"));
				toolingReceipt.setPunchSetNo(rs.getString("punchSetNo"));
				toolingReceipt.setCompForce(rs.getInt("compForce"));
				lstToolingReceiptNote.add(toolingReceipt);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolingReceiptNoteDetails in tooling_receipt_product table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getToolingReceiptNoteDetails details in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return lstToolingReceiptNote;	
	}
	
	public List<ToolingReceiptNote> getToolingReceiptProductDetails1(String searchToolingProduct, int toolingReceiptId)
	{
		List<ToolingReceiptNote> lstToolingReceiptNote = new ArrayList<ToolingReceiptNote>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt;
		try
		{
			con = datasource.getConnection();
			if(searchToolingProduct != null && !"".equals(searchToolingProduct))
			{
			    pstmt = con.prepareStatement("Select toolingreceiptid, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber,"
			    		+ " expiryDate,manDate, punchSetNo, compForce  "
			    		+ " from tooling_receipt_product1 Where toolingreceiptid = ?"
			    		+ " and productname like '%"+searchToolingProduct+"%' order by productname");
			}
			else
			{
				pstmt = con.prepareStatement("Select toolingreceiptid, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber,"
			    		+ " expiryDate,manDate, punchSetNo, compForce   "
			    		+ " from tooling_receipt_product1 Where toolingreceiptid = ?  order by productname");
			}
			pstmt.setInt(1, toolingReceiptId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setToolingProductId(rs.getInt("toolingproductid"));
				toolingReceipt.setProductName(rs.getString("productname"));
				toolingReceipt.setDrawingNo(rs.getString("drawingno"));
				toolingReceipt.setStrength(rs.getString("strength"));
				toolingReceipt.setMachineType(rs.getString("machinetype"));
				toolingReceipt.setTypeOfTool(rs.getString("typeoftool"));
				toolingReceipt.setMocPunches(rs.getString("mocpunches"));
				toolingReceipt.setMocDies(rs.getString("mocdies"));
				toolingReceipt.setShape(rs.getString("shape"));
				toolingReceipt.setDimensions(rs.getString("dimensions"));
				toolingReceipt.setBreakLineUpper(rs.getString("breaklineupper"));
				toolingReceipt.setBreakLineLower(rs.getString("breaklinelower"));
				toolingReceipt.setEmbosingUpper(rs.getString("embosingUpper"));
				toolingReceipt.setEmbosingLower(rs.getString("embosingLower"));
				toolingReceipt.setLaserMarking(rs.getString("lasermarking"));
				toolingReceipt.setHardCromePlating(rs.getString("hardcromeplating"));
				toolingReceipt.setDustCapGroove(rs.getString("dustcapgroove"));
				toolingReceipt.setPoQuantity(rs.getLong("poquantity"));
				toolingReceipt.setReceivedQuantity(rs.getLong("receivedquantity"));
				toolingReceipt.setToolingLotNumber(rs.getString("toolinglotnumber"));			
				toolingReceipt.setUom(rs.getString("uom"));
				toolingReceipt.setMocNumber(rs.getString("mocNumber"));
				toolingReceipt.setDqDocument(rs.getString("dqDocument"));
				toolingReceipt.setInspectionReportNumber(rs.getString("inspectionReportNumber"));
				//toolingReceipt.setExpiryDate(rs.getDate("expiryDate"));
				toolingReceipt.setManDate(rs.getDate("manDate"));
				toolingReceipt.setPunchSetNo(rs.getString("punchSetNo"));
				toolingReceipt.setCompForce(rs.getInt("compForce"));
				lstToolingReceiptNote.add(toolingReceipt);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolingReceiptNoteDetails in tooling_receipt_product table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getToolingReceiptNoteDetails details in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return lstToolingReceiptNote;	
	}
	
	public ToolingReceiptNote getToolingReceiptNote(int toolingReceiptId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt= new ToolingReceiptNote();
		try
		{
			con = datasource.getConnection();
			
			pstmt = con.prepareStatement("Select a.toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, b.minacceptedQty from tooling_receipt_note a "
					+ " left join tooling_receipt_product b on b.toolingreceiptid = a.toolingreceiptid "
					+ " where a.toolingreceiptid = ? order by grnno");
			pstmt.setInt(1, toolingReceiptId);

			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setGrnNo(rs.getInt("grnno"));
				toolingReceipt.setGrnDate(rs.getString("grndate"));
				toolingReceipt.setPo(rs.getString("po"));
				toolingReceipt.setSupplierCode(rs.getString("suppliercode"));
				toolingReceipt.setSupplierName(rs.getString("suppliername"));
				toolingReceipt.setProductSerialNo(rs.getString("productserno"));
				toolingReceipt.setMinAcceptedQty(rs.getInt("minacceptedQty"));
				toolingReceipt.setIsActive(0);
			}
			getReceiptProductDetails(toolingReceipt);
		}
		catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolingReceiptNote in tooling_receipt_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getToolingReceiptNote details in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return toolingReceipt;	
	
	}
	
	public ToolingReceiptNote getToolingReceiptProduct(int toolingProductId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt = new ToolingReceiptNote();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingreceiptid, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber, toolingcodenumber, expiryDate "
			    		+ " , storagelocation, cleaningsop, dquploadimage, mocuploadimage, inspectionimageupload, uppperQty, lowerQty, dieQty, minacceptedQty,"
			    		+ " mandate, punchSetNo, compForce "
			    		+ " from tooling_receipt_product Where toolingproductid = ?  order by productname");
			pstmt.setInt(1, toolingProductId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setToolingProductId(rs.getInt("toolingproductid"));
				toolingReceipt.setProductName(TiimUtil.ValidateNull(rs.getString("productname")));
				toolingReceipt.setDrawingNo(TiimUtil.ValidateNull(rs.getString("drawingno")));
				toolingReceipt.setStrength(TiimUtil.ValidateNull(rs.getString("strength")));
				toolingReceipt.setMachineType(TiimUtil.ValidateNull(rs.getString("machinetype")));
				toolingReceipt.setTypeOfTool(TiimUtil.ValidateNull(rs.getString("typeoftool")));
				toolingReceipt.setMocPunches(TiimUtil.ValidateNull(rs.getString("mocpunches")));
				toolingReceipt.setMocDies(TiimUtil.ValidateNull(rs.getString("mocdies")));
				toolingReceipt.setShape(TiimUtil.ValidateNull(rs.getString("shape")));
				toolingReceipt.setDimensions(TiimUtil.ValidateNull(rs.getString("dimensions")));
				toolingReceipt.setBreakLineUpper(TiimUtil.ValidateNull(rs.getString("breaklineupper")));
				toolingReceipt.setBreakLineLower(TiimUtil.ValidateNull(rs.getString("breaklinelower")));
				toolingReceipt.setEmbosingUpper(TiimUtil.ValidateNull(rs.getString("embosingUpper")));
				toolingReceipt.setEmbosingLower(TiimUtil.ValidateNull(rs.getString("embosingLower")));
				toolingReceipt.setLaserMarking(TiimUtil.ValidateNull(rs.getString("lasermarking")));
				toolingReceipt.setHardCromePlating(TiimUtil.ValidateNull(rs.getString("hardcromeplating")));
				toolingReceipt.setDustCapGroove(TiimUtil.ValidateNull(rs.getString("dustcapgroove")));
				toolingReceipt.setPoQuantity(rs.getLong("poquantity"));
				toolingReceipt.setReceivedQuantity(rs.getLong("receivedquantity"));
				toolingReceipt.setToolingLotNumber(rs.getString("toolinglotnumber"));			
				toolingReceipt.setUom(rs.getString("uom"));
				toolingReceipt.setMocNumber(rs.getString("mocNumber"));
				toolingReceipt.setDqDocument(rs.getString("dqDocument"));
				toolingReceipt.setInspectionReportNumber(rs.getString("inspectionReportNumber"));
				toolingReceipt.setToolingCodeNumber(rs.getString("toolingcodenumber"));
				//toolingReceipt.setExpiryDate(rs.getDate("expiryDate"));
				//toolingReceipt.setExpiryDates(rs.getDate("expiryDate").toString());
				toolingReceipt.setStorageLocation(rs.getString("storagelocation"));
				toolingReceipt.setMocUploadImage(rs.getString("mocuploadimage"));
				toolingReceipt.setDqUploadImage(rs.getString("dquploadimage"));
				toolingReceipt.setInspectionUploadImage(rs.getString("inspectionimageupload"));
				toolingReceipt.setUpperQnty(rs.getInt("uppperQty"));
				toolingReceipt.setLowerQnty(rs.getInt("lowerQty"));
				toolingReceipt.setDieQnty(rs.getInt("dieQty"));
				toolingReceipt.setMinAcceptedQty(rs.getInt("minacceptedQty"));
				toolingReceipt.setCleaningSOP(rs.getString("cleaningsop"));
				toolingReceipt.setManDate(rs.getDate("manDate"));
				toolingReceipt.setPunchSetNo(rs.getString("punchSetNo"));
				toolingReceipt.setCompForce(rs.getInt("compForce"));
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolingReceiptProduct in tooling_receipt_product table 1 : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getToolingReceiptProduct details in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return toolingReceipt;	
	}
	
	public ToolingReceiptNote getToolingReceiptProduct1(int toolingProductId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt = new ToolingReceiptNote();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingreceiptid, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber,"
			    		+ " expiryDate, punchSetNo, compForce  "
			    		+ " from tooling_receipt_product1 Where toolingproductid = ?  order by productname");
			pstmt.setInt(1, toolingProductId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setToolingProductId(rs.getInt("toolingproductid"));
				toolingReceipt.setProductName(TiimUtil.ValidateNull(rs.getString("productname")));
				toolingReceipt.setDrawingNo(TiimUtil.ValidateNull(rs.getString("drawingno")));
				toolingReceipt.setStrength(TiimUtil.ValidateNull(rs.getString("strength")));
				toolingReceipt.setMachineType(TiimUtil.ValidateNull(rs.getString("machinetype")));
				toolingReceipt.setTypeOfTool(TiimUtil.ValidateNull(rs.getString("typeoftool")));
				toolingReceipt.setMocPunches(TiimUtil.ValidateNull(rs.getString("mocpunches")));
				toolingReceipt.setMocDies(TiimUtil.ValidateNull(rs.getString("mocdies")));
				toolingReceipt.setShape(TiimUtil.ValidateNull(rs.getString("shape")));
				toolingReceipt.setDimensions(TiimUtil.ValidateNull(rs.getString("dimensions")));
				toolingReceipt.setBreakLineUpper(TiimUtil.ValidateNull(rs.getString("breaklineupper")));
				toolingReceipt.setBreakLineLower(TiimUtil.ValidateNull(rs.getString("breaklinelower")));
				toolingReceipt.setEmbosingUpper(TiimUtil.ValidateNull(rs.getString("embosingUpper")));
				toolingReceipt.setEmbosingLower(TiimUtil.ValidateNull(rs.getString("embosingLower")));
				toolingReceipt.setLaserMarking(TiimUtil.ValidateNull(rs.getString("lasermarking")));
				toolingReceipt.setHardCromePlating(TiimUtil.ValidateNull(rs.getString("hardcromeplating")));
				toolingReceipt.setDustCapGroove(TiimUtil.ValidateNull(rs.getString("dustcapgroove")));
				toolingReceipt.setPoQuantity(rs.getLong("poquantity"));
				toolingReceipt.setReceivedQuantity(rs.getLong("receivedquantity"));
				toolingReceipt.setToolingLotNumber(rs.getString("toolinglotnumber"));			
				toolingReceipt.setUom(rs.getString("uom"));
				toolingReceipt.setMocNumber(rs.getString("mocNumber"));
				toolingReceipt.setDqDocument(rs.getString("dqDocument"));
				toolingReceipt.setInspectionReportNumber(rs.getString("inspectionReportNumber"));
				toolingReceipt.setPunchSetNo(rs.getString("punchSetNo"));
				toolingReceipt.setCompForce(rs.getInt("compForce"));
				//toolingReceipt.setExpiryDate(rs.getDate("expiryDate"));
				
				
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolingReceiptProduct in tooling_receipt_product table 2: "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getToolingReceiptProduct details in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return toolingReceipt;	
	}
	
	public List<ToolingReceiptNote> getReceiptProductDetails(int toolingReceiptId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt = new ToolingReceiptNote();
		List<ToolingReceiptNote> lstToolingProduct = new ArrayList<ToolingReceiptNote>();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingreceiptid, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber, toolingcodenumber, expiryDate "
			    		+ " , storagelocation, cleaningsop, dquploadimage, mocuploadimage, inspectionimageupload, uppperQty, lowerQty, dieQty, minacceptedQty,"
			    		+ " manDate, punchSetNo, compForce "
			    		+ " from tooling_receipt_product Where toolingreceiptid = ?  order by productname");
			pstmt.setInt(1, toolingReceiptId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setToolingProductId(rs.getInt("toolingproductid"));
				toolingReceipt.setProductName(rs.getString("productname"));
				toolingReceipt.setDrawingNo(rs.getString("drawingno"));
				toolingReceipt.setStrength(rs.getString("strength"));
				toolingReceipt.setMachineType(rs.getString("machinetype"));
				toolingReceipt.setTypeOfTool(rs.getString("typeoftool"));
				toolingReceipt.setMocPunches(rs.getString("mocpunches"));
				toolingReceipt.setMocDies(rs.getString("mocdies"));
				toolingReceipt.setShape(rs.getString("shape"));
				toolingReceipt.setDimensions(rs.getString("dimensions"));
				toolingReceipt.setBreakLineUpper(rs.getString("breaklineupper"));
				toolingReceipt.setBreakLineLower(rs.getString("breaklinelower"));
				toolingReceipt.setEmbosingUpper(rs.getString("embosingUpper"));
				toolingReceipt.setEmbosingLower(rs.getString("embosingLower"));
				toolingReceipt.setLaserMarking(rs.getString("lasermarking"));
				toolingReceipt.setHardCromePlating(rs.getString("hardcromeplating"));
				toolingReceipt.setDustCapGroove(rs.getString("dustcapgroove"));
				toolingReceipt.setPoQuantity(rs.getLong("poquantity"));
				toolingReceipt.setReceivedQuantity(rs.getLong("receivedquantity"));
				toolingReceipt.setToolingLotNumber(rs.getString("toolinglotnumber"));		
				toolingReceipt.setUom(rs.getString("uom"));
				toolingReceipt.setMocNumber(rs.getString("mocNumber"));
				toolingReceipt.setDqDocument(rs.getString("dqDocument"));
				toolingReceipt.setInspectionReportNumber(rs.getString("inspectionReportNumber"));
				toolingReceipt.setToolingCodeNumber(rs.getString("toolingcodenumber"));
				//toolingReceipt.setExpiryDate(rs.getDate("expiryDate"));
				toolingReceipt.setStorageLocation(rs.getString("storagelocation"));
				toolingReceipt.setMocUploadImage(rs.getString("mocuploadimage"));
				toolingReceipt.setDqUploadImage(rs.getString("dquploadimage"));
				toolingReceipt.setInspectionUploadImage(rs.getString("inspectionimageupload"));
				toolingReceipt.setUpperQnty(rs.getInt("uppperQty"));
				toolingReceipt.setLowerQnty(rs.getInt("lowerQty"));
				toolingReceipt.setDieQnty(rs.getInt("dieQty"));
				toolingReceipt.setCleaningSOP(rs.getString("cleaningsop"));
				toolingReceipt.setMinAcceptedQty(rs.getInt("minacceptedQty"));
				toolingReceipt.setManDate(rs.getDate("manDate"));
				toolingReceipt.setPunchSetNo(rs.getString("punchSetNo"));
				toolingReceipt.setCompForce(rs.getInt("compForce"));
				lstToolingProduct.add(toolingReceipt);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolingReceiptProduct in tooling_receipt_product table3 : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getToolingReceiptProduct details in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return lstToolingProduct;	
	}
	
	public void getReceiptProductDetails(ToolingReceiptNote toolingReceipt)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			System.out.println("getReceiptProductDetails....");
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingreceiptid, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber, toolingcodenumber, expiryDate "
			    		+ " , storagelocation, cleaningsop, dquploadimage, mocuploadimage, inspectionimageupload, uppperQty, lowerQty, dieQty, minacceptedQty,"
			    		+ " manDate, punchSetNo, compForce "
			    		+ " from tooling_receipt_product Where toolingreceiptid = ?  order by productname");
			pstmt.setInt(1, toolingReceipt.getToolingReceiptId());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				System.out.println("getReceiptProductDetails: Mandate...."+rs.getDate("manDate"));
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setToolingProductId(rs.getInt("toolingproductid"));
				toolingReceipt.setProductName(rs.getString("productname"));
				toolingReceipt.setDrawingNo(rs.getString("drawingno"));
				toolingReceipt.setStrength(rs.getString("strength"));
				toolingReceipt.setMachineType(rs.getString("machinetype"));
				toolingReceipt.setTypeOfTool(rs.getString("typeoftool"));
				toolingReceipt.setMocPunches(rs.getString("mocpunches"));
				toolingReceipt.setMocDies(rs.getString("mocdies"));
				toolingReceipt.setShape(rs.getString("shape"));
				toolingReceipt.setDimensions(rs.getString("dimensions"));
				toolingReceipt.setBreakLineUpper(rs.getString("breaklineupper"));
				toolingReceipt.setBreakLineLower(rs.getString("breaklinelower"));
				toolingReceipt.setEmbosingUpper(rs.getString("embosingUpper"));
				toolingReceipt.setEmbosingLower(rs.getString("embosingLower"));
				toolingReceipt.setLaserMarking(rs.getString("lasermarking"));
				toolingReceipt.setHardCromePlating(rs.getString("hardcromeplating"));
				toolingReceipt.setDustCapGroove(rs.getString("dustcapgroove"));
				toolingReceipt.setPoQuantity(rs.getLong("poquantity"));
				toolingReceipt.setReceivedQuantity(rs.getLong("receivedquantity"));
				toolingReceipt.setToolingLotNumber(rs.getString("toolinglotnumber"));		
				toolingReceipt.setUom(rs.getString("uom"));
				toolingReceipt.setMocNumber(rs.getString("mocNumber"));
				toolingReceipt.setDqDocument(rs.getString("dqDocument"));
				toolingReceipt.setInspectionReportNumber(rs.getString("inspectionReportNumber"));
				toolingReceipt.setToolingCodeNumber(rs.getString("toolingcodenumber"));
				toolingReceipt.setStorageLocation(rs.getString("storagelocation"));
				toolingReceipt.setMocUploadImage(rs.getString("mocuploadimage"));
				toolingReceipt.setDqUploadImage(rs.getString("dquploadimage"));
				toolingReceipt.setInspectionUploadImage(rs.getString("inspectionimageupload"));
				toolingReceipt.setUpperQnty(rs.getInt("uppperQty"));
				toolingReceipt.setLowerQnty(rs.getInt("lowerQty"));
				toolingReceipt.setDieQnty(rs.getInt("dieQty"));
				toolingReceipt.setCleaningSOP(rs.getString("cleaningsop"));
				toolingReceipt.setMinAcceptedQty(rs.getInt("minacceptedQty"));
				toolingReceipt.setManDate(rs.getDate("manDate"));
				toolingReceipt.setPunchSetNo(rs.getString("punchSetNo"));
				toolingReceipt.setCompForce(rs.getInt("compForce"));
				/*
				 * if(rs.getDate("expiryDate") != null) { Date date = rs.getDate("expiryDate");
				 * System.out.println("before exipry date...."+date);
				 * toolingReceipt.setExpiryDate(date); if(date != null) {
				 * System.out.println("before exipry date...."+date.toString());
				 * SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); SimpleDateFormat
				 * sdfDB = new SimpleDateFormat("yyyy-MM-dd");
				 * 
				 * toolingReceipt.setExpiryDates(sdf.format(sdfDB.parse(rs.getString(
				 * "expiryDate")))); } }
				 */
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolingReceiptProduct in edit mode tooling_receipt_product table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getToolingReceiptProduct details in tooling_receipt_note table : "+ex.getMessage());
			}
		}
	}
	
	public List<ToolingReceiptNote> getReceiptProductDetails1(int toolingReceiptId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt = new ToolingReceiptNote();
		List<ToolingReceiptNote> lstToolingProduct = new ArrayList<ToolingReceiptNote>();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingreceiptid, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber,"
			    		+ " expiryDate, punchSetNo, compForce "
			    		+ " from tooling_receipt_product1 Where toolingreceiptid = ?  order by productname");
			pstmt.setInt(1, toolingReceiptId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setToolingProductId(rs.getInt("toolingproductid"));
				toolingReceipt.setProductName(rs.getString("productname"));
				toolingReceipt.setDrawingNo(rs.getString("drawingno"));
				toolingReceipt.setStrength(rs.getString("strength"));
				toolingReceipt.setMachineType(rs.getString("machinetype"));
				toolingReceipt.setTypeOfTool(rs.getString("typeoftool"));
				toolingReceipt.setMocPunches(rs.getString("mocpunches"));
				toolingReceipt.setMocDies(rs.getString("mocdies"));
				toolingReceipt.setShape(rs.getString("shape"));
				toolingReceipt.setDimensions(rs.getString("dimensions"));
				toolingReceipt.setBreakLineUpper(rs.getString("breaklineupper"));
				toolingReceipt.setBreakLineLower(rs.getString("breaklinelower"));
				toolingReceipt.setEmbosingUpper(rs.getString("embosingUpper"));
				toolingReceipt.setEmbosingLower(rs.getString("embosingLower"));
				toolingReceipt.setLaserMarking(rs.getString("lasermarking"));
				toolingReceipt.setHardCromePlating(rs.getString("hardcromeplating"));
				toolingReceipt.setDustCapGroove(rs.getString("dustcapgroove"));
				toolingReceipt.setPoQuantity(rs.getLong("poquantity"));
				toolingReceipt.setReceivedQuantity(rs.getLong("receivedquantity"));
				toolingReceipt.setToolingLotNumber(rs.getString("toolinglotnumber"));		
				toolingReceipt.setUom(rs.getString("uom"));
				toolingReceipt.setMocNumber(rs.getString("mocNumber"));
				toolingReceipt.setDqDocument(rs.getString("dqDocument"));
				toolingReceipt.setInspectionReportNumber(rs.getString("inspectionReportNumber"));
				toolingReceipt.setPunchSetNo(rs.getString("punchSetNo"));
				toolingReceipt.setCompForce(rs.getInt("compForce"));
				//toolingReceipt.setExpiryDate(rs.getDate("expiryDate"));
				
				lstToolingProduct.add(toolingReceipt);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolingReceiptProduct in tooling_receipt_product table 4 : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getToolingReceiptProduct details in tooling_receipt_note table : "+ex.getMessage());
			}
		}
		return lstToolingProduct;	
	}
	
	public String deleteReceiptNote(int toolingreceiptid, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			
			pstmt = con.prepareStatement("delete from tooling_receipt_product where toolingreceiptid = ?");
			pstmt.setInt(1, toolingreceiptid);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement("delete from tooling_receipt_product1 where toolingreceiptid = ?");
			pstmt.setInt(1, toolingreceiptid);
			pstmt.executeUpdate(); 
			
			pstmt = con.prepareStatement("delete from tooling_receipt_note where toolingreceiptid = ?");
			pstmt.setInt(1, toolingreceiptid);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("receipt.page", null,null));
			history.setDescription(messageSource.getMessage("receipt.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
			
			
		}catch(Exception ex)
		{
			msg = "Can not be Deleted";
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
	
	public List getGRNNo(String grn, int status, String approvalFlag)
	{
		List<ToolingReceiptNote> grnList = new ArrayList<ToolingReceiptNote>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt = new ToolingReceiptNote();
		try
		{
			con = datasource.getConnection();
			
			//pstmt = con.prepareStatement("Select toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno from tooling_receipt_note where grnno LIKE '"+grn+"%' order by grnno");
			if("1".equalsIgnoreCase(approvalFlag))
			{
				pstmt = con.prepareStatement("Select a.toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, b.toolinglotnumber toolinglotnumber "
						+ "  from tooling_receipt_note a,tooling_receipt_product b where grnno LIKE '"+grn+"%' and a.approvalflag = ? and a.toolingreceiptid = b.toolingreceiptid and b.receiptStatus = ?"
								+ " and grnno not in (select grnno from tooling_receiving_request)");
				pstmt.setInt(1, 1);
				pstmt.setInt(2, status);
			}else
			{
				pstmt = con.prepareStatement("Select a.toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, b.toolinglotnumber toolinglotnumber "
						+ "  from tooling_receipt_note a,tooling_receipt_product b where grnno LIKE '"+grn+"%' and  a.toolingreceiptid = b.toolingreceiptid and b.receiptStatus = ?"
								+ " and grnno not in (select grnno from tooling_receiving_request)");
				pstmt.setInt(1, status);
			
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setGrnNo(rs.getInt("grnno"));
				toolingReceipt.setGrnDate(rs.getString("grndate"));
				toolingReceipt.setPo(rs.getString("po"));
				toolingReceipt.setSupplierCode(rs.getString("suppliercode"));
				toolingReceipt.setSupplierName(rs.getString("suppliername"));
				toolingReceipt.setProductSerialNo(rs.getString("productserno"));
				toolingReceipt.setToolingLotNumber(rs.getString("toolinglotnumber"));
				toolingReceipt.setIsActive(0);
				boolean isExpiry = expiryDao.isProductExpired(rs.getString("toolinglotnumber"));
				boolean isRejected = expiryDao.isProductRejected(rs.getString("toolinglotnumber"));
				if(!isExpiry && !isRejected)
				{
					grnList.add(toolingReceipt);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting the GRN No Auto Complete List : "+e.getMessage());
		}
		return grnList;
	}
	
	public List getDrawingNo(String drawingNo, int status)
	{
		List<ToolingReceiptNote> grnList = new ArrayList<ToolingReceiptNote>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt = new ToolingReceiptNote();
		try
		{
			con = datasource.getConnection();
			
			//pstmt = con.prepareStatement("Select toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno from tooling_receipt_note where grnno LIKE '"+grn+"%' order by grnno");
			pstmt = con.prepareStatement("Select a.toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, drawingno "
					+ "  from tooling_receipt_note a,tooling_receipt_product b where drawingno LIKE '"+drawingNo+"%' and a.toolingreceiptid = b.toolingreceiptid and b.receiptStatus = ?"
							+ " and grnno not in (select grnno from tooling_receiving_request)");
			pstmt.setInt(1, status);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setGrnNo(rs.getInt("grnno"));
				toolingReceipt.setGrnDate(rs.getString("grndate"));
				toolingReceipt.setPo(rs.getString("po"));
				toolingReceipt.setSupplierCode(rs.getString("suppliercode"));
				toolingReceipt.setSupplierName(rs.getString("suppliername"));
				toolingReceipt.setProductSerialNo(rs.getString("productserno"));
				toolingReceipt.setDrawingNo(rs.getString("drawingno"));
				toolingReceipt.setIsActive(0);
				grnList.add(toolingReceipt);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting the GRN No Auto Complete List : "+e.getMessage());
		}
		return grnList;
	}
	
	public List<ToolingReceiptNote> getGRNProductDetail(String drawingNumber, String approvalFlag)
	{
		List<ToolingReceiptNote> grnList = new ArrayList<ToolingReceiptNote>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt = new ToolingReceiptNote();
		try
		{
			con = datasource.getConnection();
			
			//pstmt = con.prepareStatement("Select toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno from tooling_receipt_note where grnno LIKE '"+grn+"%' order by grnno");
			if("1".equalsIgnoreCase(approvalFlag))
			{
				if(drawingNumber == null || !"".equalsIgnoreCase(drawingNumber))
				{
					pstmt = con.prepareStatement("Select a.toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, b.productName productName, "
							+ " b.drawingno drawingno, b.machinetype machinetype, b.typeoftool typeoftool, expiryDate, b.minacceptedQty "
							+ " from tooling_receipt_note a,tooling_receipt_product b where a.approvalflag = ? and  a.toolingreceiptid = b.toolingreceiptid and b.receiptStatus = ? "
									+ " and grnno not in (select grnno from tooling_receiving_request)");
					pstmt.setInt(1, 1);
					pstmt.setInt(2, 1);
				}else
				{
					pstmt = con.prepareStatement("Select a.toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, b.productName productName, "
							+ " b.drawingno drawingno, b.machinetype machinetype, b.typeoftool typeoftool, expiryDate, b.minacceptedQty  "
							+ " from tooling_receipt_note a,tooling_receipt_product b where  a.toolingreceiptid = b.toolingreceiptid and b.approvalflag = ? and drawingno = ? and b.receiptStatus = ? "
									+ " and grnno not in (select grnno from tooling_receiving_request)");
					pstmt.setInt(1, 1);
					pstmt.setString(2, drawingNumber);
					pstmt.setInt(3, 1);
				}
			}else
			{
				if(drawingNumber == null || "".equalsIgnoreCase(drawingNumber))
				{
					pstmt = con.prepareStatement("Select a.toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, b.productName productName, "
							+ " b.drawingno drawingno, b.machinetype machinetype, b.typeoftool typeoftool, expiryDate, b.minacceptedQty "
							+ " from tooling_receipt_note a,tooling_receipt_product b where  a.toolingreceiptid = b.toolingreceiptid and b.receiptStatus = ? "
									+ " and grnno not in (select grnno from tooling_receiving_request)");
					pstmt.setInt(1, 1);
				}else
				{
					pstmt = con.prepareStatement("Select a.toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, b.productName productName, "
							+ " b.drawingno drawingno, b.machinetype machinetype, b.typeoftool typeoftool, expiryDate, b.minacceptedQty  "
							+ " from tooling_receipt_note a,tooling_receipt_product b where  a.toolingreceiptid = b.toolingreceiptid and b.receiptStatus = ? and drawingno = ? and b.receiptStatus = ? "
									+ " and grnno not in (select grnno from tooling_receiving_request)");
					pstmt.setInt(1, 1);
					pstmt.setString(2, drawingNumber);
					pstmt.setInt(3, 1);
				}
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setGrnNo(rs.getInt("grnno"));
				//System.out.println(rs.getInt("grnno"));
				toolingReceipt.setGrnDate(rs.getString("grndate"));
				toolingReceipt.setPo(rs.getString("po"));
				toolingReceipt.setSupplierCode(rs.getString("suppliercode"));
				toolingReceipt.setSupplierName(rs.getString("suppliername"));
				toolingReceipt.setProductSerialNo(rs.getString("productserno"));
				toolingReceipt.setIsActive(0);
				toolingReceipt.setProductName(rs.getString("productName"));
				toolingReceipt.setDrawingNo(rs.getString("drawingno"));
				toolingReceipt.setMachineType(rs.getString("machinetype"));
				toolingReceipt.setTypeOfTool(rs.getString("typeoftool"));
				//toolingReceipt.setExpiryDate(rs.getDate("expiryDate"));
				toolingReceipt.setMinAcceptedQty(rs.getInt("minacceptedQty"));
				toolingReceipt.setUploadPath(productDao.getProductUploadedPath(rs.getString("productName"),rs.getString("drawingno"),rs.getString("machinetype"),rs.getString("typeoftool")));
				grnList.add(toolingReceipt);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting the GRN No Auto Complete List : "+e.getMessage());
		}
		return grnList;
	}
	
	public List<ToolingReceiptNote> getExpiryProductDetail()
	{
		List<ToolingReceiptNote> grnList = new ArrayList<ToolingReceiptNote>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt = new ToolingReceiptNote();
		try
		{
			con = datasource.getConnection();
			
				pstmt = con.prepareStatement("SELECT toolingreceiptid, productname, drawingno, machinetype, typeoftool, toolinglotnumber, expirydate FROM tooling_receipt_product ");

			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setProductName(rs.getString("productName"));
				toolingReceipt.setDrawingNo(rs.getString("drawingno"));
				toolingReceipt.setMachineType(rs.getString("machinetype"));
				toolingReceipt.setTypeOfTool(rs.getString("typeoftool"));
				//toolingReceipt.setExpiryDates(rs.getString("expiryDate"));
				toolingReceipt.setToolingLotNumber(rs.getString("toolinglotnumber"));
				grnList.add(toolingReceipt);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting the getExpiryProductDetail List : "+e.getMessage());
		}
		return grnList;
	}
	

	public void updateReceiptIntervalQnty(String lotNmber, long lifeTime, long intervalQnty)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			con = datasource.getConnection();
			
				pstmt = con.prepareStatement("update tooling_receipt_product set toolinglife = ?, serviceintervalqty = ? where toolinglotnumber = ?");
				pstmt.setLong(1, lifeTime);
				pstmt.setLong(2, intervalQnty);
				pstmt.setString(3, lotNmber);
				pstmt.executeUpdate();
			
		}
		catch(Exception e)
		{
			System.out.println("Exception when update the receipt interval quantity : "+e.getMessage());
		}
	
	}
	
	public void updateReceiptIntervalQnty(String lotNmber, long intervalQnty)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			con = datasource.getConnection();
			
				pstmt = con.prepareStatement("update tooling_receipt_product set  serviceintervalqty = ? where toolinglotnumber = ?");
				pstmt.setLong(1, intervalQnty);
				pstmt.setString(2, lotNmber);
				pstmt.executeUpdate();
			
		}
		catch(Exception e)
		{
			System.out.println("Exception when update the receipt interval quantity : "+e.getMessage());
		}
	
	}
	
	public ToolingReceiptNote getReceiptIntervalQnty(String lotNmber)
	{
		ToolingReceiptNote receipt = new ToolingReceiptNote();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		try
		{
			con = datasource.getConnection();
			
			
				pstmt = con.prepareStatement("select toolinglife,serviceintervalqty from tooling_receipt_product where toolinglotnumber = ?");
				pstmt.setString(1, lotNmber);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					//System.out.println("tooing life: "+rs.getLong("toolinglife")+", "+lotNmber);
					receipt.setToolingLife(rs.getLong("toolinglife"));
					receipt.setServiceIntervalQnty(rs.getLong("serviceintervalqty"));
				}
				return receipt;
			
		}
		catch(Exception e)
		{
			System.out.println("Exception while getting the getReceiptIntervalQnty by lot number : "+e.getMessage());
		}
		return receipt;
	
	}
	
}
