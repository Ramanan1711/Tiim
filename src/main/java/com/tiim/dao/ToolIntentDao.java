package com.tiim.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.tiim.model.ToolIndent;
import com.tiim.model.ToolingReceiptNote;
import com.tiim.util.TiimUtil;

@Repository
public class ToolIntentDao {
	
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
			pstmt = con.prepareStatement("SELECT max(intentId) intentId FROM tooling_intent_note ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				grnNo = rs.getInt("intentId");
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
	
	public String getIntialLotNumberValue(String productName,String drawingNo)
	{
		int lotNumber = 0;
		/*Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT productid FROM mst_product where productname = ?");
			pstmt.setString(1, productName);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				lotNumber = rs.getInt("productid");
			}
			
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
		
		String autoToolingLotNumber = lotNumber +"";
		if(autoToolingLotNumber.length() == 1)
		{
			autoToolingLotNumber = "00000"+autoToolingLotNumber;
		}else if(autoToolingLotNumber.length() == 2)
		{
			autoToolingLotNumber = "0000"+autoToolingLotNumber;
		}else if(autoToolingLotNumber.length() == 3)
		{
			autoToolingLotNumber = "000"+autoToolingLotNumber;
		}else if(autoToolingLotNumber.length() == 4)
		{
			autoToolingLotNumber = "00"+autoToolingLotNumber;
		}else if(autoToolingLotNumber.length() == 5)
		{
			autoToolingLotNumber = "0"+autoToolingLotNumber;
		}
		autoToolingLotNumber = messageSource.getMessage("lotNumberprefix", null,null)+autoToolingLotNumber+messageSource.getMessage("lotNumbersuffix", null,null)+maxNoByProductName(productName);
		*/
		return (messageSource.getMessage("lotNumbersuffix", null,null)+maxNoByProductName(productName,drawingNo));	
	}
	
	public String addToolingIntent(ToolIndent toolIndent, int userId)
	{
		System.out.println("--Start--");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int indentNo = 0;
		try
		{
			con = datasource.getConnection();
			if(isToolReceiptExists(toolIndent.getIndentNo()))
			{
				pstmt = con.prepareStatement("insert into tooling_intent_note(intentId, intentdate, po, suppliercode, suppliername, productname,"
						+ "	drawingno, machinetype, typeoftool, mocpunches, mocdies, shape, dimensions, embosingLower,branchname, hardcromeplating,"
						+ " dustcapgroove, poquantity, toolinglotnumber, uom, expiryDate, embosingupper,minquantity, punchSetNo, compForce"
						+ " )"
						+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

				pstmt.setInt(1, toolIndent.getIndentNo());
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(2, date);
				pstmt.setString(3, TiimUtil.ValidateNull(toolIndent.getPo()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(toolIndent.getSupplierCode()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(toolIndent.getSupplierName()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(toolIndent.getProductNamePO()).trim());
				pstmt.setString(7, TiimUtil.ValidateNull(toolIndent.getDrawingNo()).trim());
				pstmt.setString(8, TiimUtil.ValidateNull(toolIndent.getMachineTypePO()).trim());
				pstmt.setString(9, TiimUtil.ValidateNull(toolIndent.getTypeOfTool()).trim());
				pstmt.setString(10, TiimUtil.ValidateNull(toolIndent.getMocPunches()).trim());
				pstmt.setString(11, TiimUtil.ValidateNull(toolIndent.getMocDies()).trim());
				pstmt.setString(12, TiimUtil.ValidateNull(toolIndent.getShape()).trim());
				pstmt.setString(13, TiimUtil.ValidateNull(toolIndent.getDimensionsPO()).trim());
				pstmt.setString(14, TiimUtil.ValidateNull(toolIndent.getEmbosingLower()).trim());
				pstmt.setString(15, TiimUtil.ValidateNull(toolIndent.getBranchName()).trim());
				pstmt.setString(16, TiimUtil.ValidateNull(toolIndent.getHardCromePlating()).trim());
				pstmt.setString(17, TiimUtil.ValidateNull(toolIndent.getDustCapGroove()).trim());
				pstmt.setLong(18, toolIndent.getPoQuantity());
				pstmt.setString(19, TiimUtil.ValidateNull(toolIndent.getToolingLotNumberPO()).trim());
				pstmt.setString(20, TiimUtil.ValidateNull(toolIndent.getUomPO()).trim());
				//pstmt.setDate(21, getDbFormatDate(toolIndent.getExpiryDates()));
				pstmt.setInt(21, toolIndent.getExpiryDates());
				pstmt.setString(22,TiimUtil.ValidateNull(toolIndent.getEmbosingUpper()).trim());
				pstmt.setLong(23, toolIndent.getMinQuantity());
				pstmt.setString(24, toolIndent.getPunchSetNo());
				pstmt.setInt(25, toolIndent.getCompForce());

				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					indentNo = rs.getInt(1);
				}
				toolIndent.setIndentNo(indentNo);
				msg = "Saved Successfully</>";

				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("intent.page", null,null));
				history.setDescription(messageSource.getMessage("intent.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			}
			else
			{
				 pstmt = con.prepareStatement("SELECT intentId FROM tooling_intent_note WHERE intentId = ?");
				 pstmt.setInt(1, toolIndent.getIndentNo());
				 rs = pstmt.executeQuery();
				 if(rs.next())
				 {
					 toolIndent.setIndentNo(rs.getInt("intentId"));
				 }
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
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM tooling_intent_note WHERE intentId = ?");
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
	

	public String updateToolIndent(ToolIndent toolIndent, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update tooling_intent_note set intentdate = ?, po = ?, suppliercode = ?, suppliername = ?, productname = ?, drawingno = ?,"
						+ "	machinetype = ?, typeoftool = ?, mocpunches = ?, mocdies = ?, shape = ?, dimensions = ?, embosingLower = ?, branchname = ?, hardcromeplating = ?,"
						+ " dustcapgroove = ?, poquantity = ?, toolinglotnumber = ?, uom = ?, expiryDate = ?, embosingupper =?, minquantity = ?,"
						+ "  punchSetNo = ?, compForce = ?"
						+ " Where intentId = ?");

				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(1, date);
				pstmt.setString(2, TiimUtil.ValidateNull(toolIndent.getPo()).trim());
				pstmt.setString(3, TiimUtil.ValidateNull(toolIndent.getSupplierCode()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(toolIndent.getSupplierName()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(toolIndent.getProductNamePO()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(toolIndent.getDrawingNo()).trim());
				pstmt.setString(7, TiimUtil.ValidateNull(toolIndent.getMachineTypePO()).trim());
				pstmt.setString(8, TiimUtil.ValidateNull(toolIndent.getTypeOfTool()).trim());
				pstmt.setString(9, TiimUtil.ValidateNull(toolIndent.getMocPunches()).trim());
				pstmt.setString(10, TiimUtil.ValidateNull(toolIndent.getMocDies()).trim());
				pstmt.setString(11, TiimUtil.ValidateNull(toolIndent.getShape()).trim());
				pstmt.setString(12, TiimUtil.ValidateNull(toolIndent.getDimensionsPO()).trim());
				pstmt.setString(13, TiimUtil.ValidateNull(toolIndent.getEmbosingLower()).trim());
				pstmt.setString(14, TiimUtil.ValidateNull(toolIndent.getBranchName()).trim());
				pstmt.setString(15, TiimUtil.ValidateNull(toolIndent.getHardCromePlating()).trim());
				pstmt.setString(16, TiimUtil.ValidateNull(toolIndent.getDustCapGroove()).trim());
				pstmt.setLong(17, toolIndent.getPoQuantity());
				pstmt.setString(18, TiimUtil.ValidateNull(toolIndent.getToolingLotNumberPO()).trim());
				pstmt.setString(19, TiimUtil.ValidateNull(toolIndent.getUomPO()).trim());
				pstmt.setInt(20, (toolIndent.getExpiryDates()));
				pstmt.setString(21,TiimUtil.ValidateNull(toolIndent.getEmbosingUpper()).trim());
				pstmt.setLong(22,toolIndent.getMinQuantity());
				pstmt.setString(23, toolIndent.getPunchSetNo());
				pstmt.setInt(24, toolIndent.getCompForce());
				pstmt.setInt(25,toolIndent.getIndentNo());
				pstmt.executeUpdate();
				
				msg = "updated Successfully";

				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("intent.page", null,null));
				history.setDescription(messageSource.getMessage("intent.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				
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
	
	public List<ToolIndent> getlstIntentDetail(String searchToolIndent)
	{
		List<ToolIndent> lstToolIndent = new ArrayList<ToolIndent>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolIndent toolIndent;
		try
		{
			con = datasource.getConnection();
			if(searchToolIndent != null && !"".equals(searchToolIndent))
			{
			    pstmt = con.prepareStatement("Select intentId, DATE_FORMAT(intentdate,'%m/%d/%Y') AS intentdate, suppliername, toolinglotnumber, productname, drawingno, machinetype, typeoftool from tooling_intent_note Where intentId like '%"+searchToolIndent+"%' order by intentId desc");
			}
			else
			{
				pstmt = con.prepareStatement("Select intentId, DATE_FORMAT(intentdate,'%m/%d/%Y') AS intentdate, suppliername, toolinglotnumber, productname, drawingno, machinetype, typeoftool from tooling_intent_note order by intentId desc");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolIndent = new ToolIndent();
				toolIndent.setIndentNo(rs.getInt("intentId"));
				toolIndent.setIndentDate(rs.getString("intentdate"));
				toolIndent.setSupplierName(rs.getString("suppliername"));
				toolIndent.setToolingLotNumber(rs.getString("toolinglotnumber"));
				toolIndent.setProductName(rs.getString("productname"));
				toolIndent.setDrawingNo(rs.getString("drawingno"));
				toolIndent.setMachineType(rs.getString("machinetype"));
				toolIndent.setTypeOfTool(rs.getString("typeoftool"));
				//toolIndent.setCompForce(getIntialValue());
				lstToolIndent.add(toolIndent);
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
		return lstToolIndent;	
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
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber, "
			    		+ " toolingcodenumber, expiryDate "
			    		+ " from tooling_receipt_product Where toolingreceiptid = ?"
			    		+ " and productname like '%"+searchToolingProduct+"%' order by productname");
			}
			else
			{
				pstmt = con.prepareStatement("Select toolingreceiptid, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber,"
			    		+ "  toolingcodenumber, expiryDate  "
			    		+ " from tooling_receipt_product Where toolingreceiptid = ?  order by productname");
			}
			pstmt.setInt(1, toolingReceiptId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setToolingProductId(rs.getInt("toolingproductid"));
				toolingReceipt.setProductNamePO(rs.getString("productname"));
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
			    		+ " from tooling_receipt_product a , tooling_receipt_note b Where a.toolingreceiptid = b.toolingreceiptid and receiptStatus = ?"
			    		+ " and drawingno like '%"+drawingNumber+"%'  and b.toolingreceiptid not in (select requestno from suplier_return)  order by drawingno");
			}
			else
			{
				pstmt = con.prepareStatement("Select a.toolingreceiptid toolingreceiptid, grndate, branchname, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber, toolingcodenumber, expiryDate  "
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
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber, expiryDate "
			    		+ " from tooling_receipt_product1 Where toolingreceiptid = ?"
			    		+ " and productname like '%"+searchToolingProduct+"%' order by productname");
			}
			else
			{
				pstmt = con.prepareStatement("Select toolingreceiptid, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
			    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
			    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber, expiryDate  "
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
	
	public ToolIndent getToolIndent(int toolingProductId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolIndent toolIndent = new ToolIndent();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select intentId, intentdate, po, suppliercode, suppliername, productname,"
						+ "	drawingno, machinetype, typeoftool, mocpunches, mocdies, shape, dimensions, embosingLower,branchname, hardcromeplating,"
						+ " dustcapgroove, poquantity, toolinglotnumber, uom, expiryDate, approvalflag, approveddate, approvedby, embosingupper, poquantity, minquantity,"
						+ " punchSetNo,compForce "
			    		+ " from tooling_intent_note Where intentId = ?  order by intentId");
			pstmt.setInt(1, toolingProductId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolIndent.setIndentNo(rs.getInt("intentId"));
				toolIndent.setIndentDate(rs.getString("intentdate"));
				toolIndent.setPo(TiimUtil.ValidateNull(rs.getString("po")));
				toolIndent.setSupplierCode(TiimUtil.ValidateNull(rs.getString("suppliercode")));
				toolIndent.setSupplierName(TiimUtil.ValidateNull(rs.getString("suppliername")));
				toolIndent.setProductSerialNo(TiimUtil.ValidateNull(rs.getString("productname")));
				toolIndent.setDrawingNo(TiimUtil.ValidateNull(rs.getString("drawingno")));
				toolIndent.setMachineTypePO(TiimUtil.ValidateNull(rs.getString("machinetype")));
				toolIndent.setTypeOfTool(TiimUtil.ValidateNull(rs.getString("typeoftool")));
				toolIndent.setMocPunches(TiimUtil.ValidateNull(rs.getString("mocpunches")));
				toolIndent.setMocDies(TiimUtil.ValidateNull(rs.getString("mocdies")));
				toolIndent.setShape(TiimUtil.ValidateNull(rs.getString("shape")));
				toolIndent.setDimensionsPO(TiimUtil.ValidateNull(rs.getString("dimensions")));
				toolIndent.setEmbosingLower(TiimUtil.ValidateNull(rs.getString("embosingLower")));
				toolIndent.setBranchName(TiimUtil.ValidateNull(rs.getString("branchname")));
				toolIndent.setHardCromePlating(TiimUtil.ValidateNull(rs.getString("hardcromeplating")));
				toolIndent.setDustCapGroove(TiimUtil.ValidateNull(rs.getString("dustcapgroove")));
				toolIndent.setPoQuantity(rs.getLong("poquantity"));
				toolIndent.setToolingLotNumberPO(rs.getString("toolinglotnumber"));			
				toolIndent.setUomPO(rs.getString("uom"));
				toolIndent.setExpiryDates(rs.getInt("expiryDate"));
				toolIndent.setMinQuantity(rs.getLong("minquantity"));
				/*if(rs.getDate("expiryDate") != null)
				{
				Date date = rs.getDate("expiryDate");
				System.out.println("before exipry date...."+date);
				if(date != null)
				{
					System.out.println("before exipry date...."+date.toString());
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
					
					toolIndent.setExpiryDates(sdf.format(sdfDB.parse(rs.getString("expiryDate"))));
				}
				}*/
				toolIndent.setApprovalflag(rs.getString("approvalflag"));
				toolIndent.setApproveddate(rs.getString("approveddate"));
				toolIndent.setApprovedby(rs.getString("approvedby"));
				toolIndent.setProductNamePO(TiimUtil.ValidateNull(rs.getString("productname")));
				toolIndent.setEmbosingUpper(TiimUtil.ValidateNull(rs.getString("embosingupper")));
				toolIndent.setPoQuantity(rs.getLong("poquantity"));
				toolIndent.setPunchSetNo(rs.getString("punchSetNo"));
				toolIndent.setCompForce(rs.getInt("compForce"));

			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolingReceiptProduct in tooling_receipt_product table : "+ex.getMessage());
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
		return toolIndent;	
	}
	
	public String deleteIndentNote(int tooIndentId, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			
			pstmt = con.prepareStatement("delete from tooling_intent_note where intentId = ?");
			pstmt.setInt(1, tooIndentId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("intent.page", null,null));
			history.setDescription(messageSource.getMessage("intent.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
			
			
		}catch(Exception ex)
		{
			msg = "Can not be Deleted";
			System.out.println("Exception when delete the detail in tooling_intent_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in delete the detail in tooling_intent_note table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<ToolIndent> getPendingIndent(String approvalFlag)
	{
		List<ToolIndent> lstIndent = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolIndent toolIndent = new ToolIndent();
		try
		{
			con = datasource.getConnection();
			if("1".equalsIgnoreCase(approvalFlag))
			{
				pstmt = con.prepareStatement("SELECT intentId, DATE_FORMAT(intentdate,'%m/%d/%Y') AS intentdate, suppliername, toolinglotnumber, productname, drawingno, machinetype, typeoftool  FROM "
						+ " tooling_intent_note where approvalflag = ? and toolinglotnumber not in (select toolinglotnumber from tooling_receipt_product)");
				pstmt.setInt(1, 1);
			}else
			{
				pstmt = con.prepareStatement("SELECT intentId, DATE_FORMAT(intentdate,'%m/%d/%Y') AS intentdate, suppliername, toolinglotnumber, productname, drawingno, machinetype, typeoftool  FROM "
						+ " tooling_intent_note where toolinglotnumber not in (select toolinglotnumber from tooling_receipt_product)");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolIndent = new ToolIndent();
				toolIndent.setIndentNo(rs.getInt("intentId"));
				toolIndent.setIndentDate(rs.getString("intentdate"));
				toolIndent.setSupplierName(rs.getString("suppliername"));
				toolIndent.setToolingLotNumber(rs.getString("toolinglotnumber"));
				toolIndent.setProductName(TiimUtil.ValidateNull(rs.getString("productname")));
				toolIndent.setDrawingNo(TiimUtil.ValidateNull(rs.getString("drawingno")));
				toolIndent.setMachineTypePO(TiimUtil.ValidateNull(rs.getString("machinetype")));
				toolIndent.setTypeOfTool(TiimUtil.ValidateNull(rs.getString("typeoftool")));
				lstIndent.add(toolIndent);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getPendingIndent : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getPendingIndent : "+ex.getMessage());
			}
		}
		return lstIndent;	
	
	}
	
	public List getGRNNo(String grn, int status)
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
			pstmt = con.prepareStatement("Select a.toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, b.toolinglotnumber toolinglotnumber "
					+ "  from tooling_receipt_note a,tooling_receipt_product b where grnno LIKE '"+grn+"%' and a.toolingreceiptid = b.toolingreceiptid and b.receiptStatus = ?"
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
	
	public List<ToolingReceiptNote> getGRNProductDetail(String drawingNumber)
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
			if(drawingNumber == null || !"".equalsIgnoreCase(drawingNumber))
			{
				pstmt = con.prepareStatement("Select a.toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, b.productName productName, "
						+ " b.drawingno drawingno, b.machinetype machinetype, b.typeoftool typeoftool, expiryDate "
						+ " from tooling_receipt_note a,tooling_receipt_product b where CURDATE() <= expiryDate and  a.toolingreceiptid = b.toolingreceiptid and b.receiptStatus = ? "
								+ " and grnno not in (select grnno from tooling_receiving_request)");
				pstmt.setInt(1, 1);
			}else
			{
				pstmt = con.prepareStatement("Select a.toolingreceiptid, grnno, grndate, po, suppliercode, suppliername, productserno, b.productName productName, "
						+ " b.drawingno drawingno, b.machinetype machinetype, b.typeoftool typeoftool, expiryDate  "
						+ " from tooling_receipt_note a,tooling_receipt_product b where CURDATE() <= expiryDate and  a.toolingreceiptid = b.toolingreceiptid and b.receiptStatus = ? and drawingno = ? "
								+ " and grnno not in (select grnno from tooling_receiving_request)");
				pstmt.setInt(1, 1);
				pstmt.setString(2, drawingNumber);
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolingReceipt = new ToolingReceiptNote();
				toolingReceipt.setToolingReceiptId(rs.getInt("toolingreceiptid"));
				toolingReceipt.setGrnNo(rs.getInt("grnno"));
				System.out.println(rs.getInt("grnno"));
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
			//	toolingReceipt.setExpiryDate(rs.getDate("expiryDate"));
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
			
				pstmt = con.prepareStatement("SELECT toolingreceiptid, productname, drawingno, machinetype, typeoftool, toolinglotnumber, expirydate FROM tooling_receipt_product where CURDATE() > expiryDate");

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

	private int maxNoByProductName(String productName,String drawingNo)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM tooling_intent_note WHERE productname = ? and drawingno = ?");
			 pstmt.setString(1, productName);
			 pstmt.setString(2, drawingNo);
			 rs = pstmt.executeQuery();
			 if(rs.next())
			 {
				 count = rs.getInt("Is_Exists");
			 }
			 count++;
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
		return count;
	
	}
	public Date getDbFormatDate(String date) {
		Date dbDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		java.util.Date dtDob = new java.util.Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			java.util.Date utilDate  = formatter.parse(sdf.format(dtDob));
			dbDate = new Date(utilDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		return dbDate;
	}	
}
