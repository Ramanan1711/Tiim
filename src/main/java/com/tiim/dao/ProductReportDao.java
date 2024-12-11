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

import com.tiim.model.ProductReport;
import com.tiim.model.ToolTracker;
import com.tiim.model.ToolingInspection;
import com.tiim.model.ToolingIssueNote;
import com.tiim.model.ToolingReceiptNote;
import com.tiim.model.ToolingRequest;
import com.tiim.model.ToolingRequestNote;
import com.tiim.model.ToolingReturnNote;
import com.tiim.util.TiimUtil;

@Repository
public class ProductReportDao {
	
	@Autowired
	DataSource datasource;
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfDBFull = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
	java.util.Date cDate=new java.util.Date();
	
	public List<ProductReport> getProductReport(ProductReport productReport)
	{
		List<ProductReport> lstProductReports = new ArrayList<ProductReport>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			/*String query = "SELECT distinct toolinglotnumber, a.productname productname, a.machinetype machinetype, a.drawingno drawingno,"
					+ "  b.toolinglife toolinglife, b.typeoftool typeoftool, b.serviceintervalqty serviceintervalqty FROM "
					+ " tooling_production_return_note_detail a, mst_product b where b.isActive = 1";*/
			String query = "SELECT distinct a.toolinglotnumber toolinglotnumber, a.productname productname, a.machinetype machinetype, a.drawingno drawingno,"
					+ "  b.toolinglife toolinglife, b.typeoftool typeoftool, b.serviceintervalqty serviceintervalqty FROM "
					+ " tooling_production_return_note_detail a, tooling_receipt_product b where b.isActive = 1";
			
			if(productReport.getDrawingNumber() != null && !productReport.getDrawingNumber().equals(""))
			{
				query = query + " and a.drawingno = '"+productReport.getDrawingNumber()+"' and a.drawingno = b.drawingno ";
			}
			
			if(productReport.getProductName() != null && !productReport.getProductName().equals(""))
			{
				query = query + " and a.productName = '"+productReport.getProductName()+"' and a.productname = b.productname";
			}
			if(productReport.getMachine() != null && !productReport.getMachine().equals("") )
			{
				query = query + " and a.machinetype = '"+productReport.getMachine()+"' and a.machinetype = b.machinetype";
			}
			if(productReport.getTypeOfTool() != null && !productReport.getTypeOfTool().equals(""))
			{
				query = query + " and a.typeoftool = '"+productReport.getTypeOfTool()+"' and a.typeoftool = b.typeoftool";
			}
			if(productReport.getLotNumber() != null && !productReport.getLotNumber().equals(""))
			{
				query = query + " and a.toolinglotnumber = '"+productReport.getLotNumber() +"' and a.productname = b.productname and a.typeoftool = b.typeoftool and a.machinetype = b.machinetype";
			}
			System.out.println("getProductReport query viewProductReport1: "+query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ProductReport obj = new ProductReport();
				obj.setLotNumber(rs.getString("toolinglotnumber"));
				obj.setProductName(rs.getString("productname"));
				obj.setMachine(rs.getString("machinetype"));
				obj.setDrawingNumber(rs.getString("drawingno"));
				obj.setToolingLife(rs.getInt("toolinglife"));
				obj.setTypeOfTool(rs.getString("typeoftool"));
				obj.setServiceIntervalQty(rs.getInt("serviceintervalqty"));
				lstProductReports.add(obj);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductReportDao getProductReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductReportDao getProductReport  : "+ex.getMessage());
			}
		}
	
		return lstProductReports;
	}
	
	public List<ProductReport> getProductIntervalReport(ProductReport productReport , int percentage)
	{
		List<ProductReport> lstProductReports = new ArrayList<ProductReport>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			String query = "select originalid, toolinglotnumber, productname, drawingno,  machinetype, typeoftool,  sum(producedqty) producedqty,   serviceintervalqty,"
					+ " toolinglife from vw_product_interval  where originalid != 0 ";
					
			
			if(productReport.getProductName()!= null && !productReport.getProductName().equals(""))
			{
				query = query + " and productname = '"+productReport.getProductName()+"' ";
			}
			if(productReport.getDrawingNumber()!= null && !productReport.getDrawingNumber().equals(""))
			{
				query = query + " and drawingno = '"+productReport.getDrawingNumber()+"' ";
			}
			if(productReport.getMachine()!= null && !productReport.getMachine().equals(""))
			{
				query = query + " and machinetype = '"+productReport.getMachine()+"' ";
			}
			if(productReport.getTypeOfTool()!= null && !productReport.getTypeOfTool().equals(""))
			{
				query = query + " and typeoftool = '"+productReport.getTypeOfTool()+"' ";
			}
			if(productReport.getLotNumber()!= null && !productReport.getLotNumber().equals(""))
			{
				query = query + " and toolinglotnumber = '"+productReport.getLotNumber()+"' ";
			}
			query = query + " group by toolinglotnumber";
			
			System.out.println("query: "+query);
			
			pstmt = con.prepareStatement(query);

			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ProductReport obj = new ProductReport();
				int noOfLot = getLotNumberCount(rs.getString("toolinglotnumber"));
				obj.setLotNumber(rs.getString("toolinglotnumber"));
				obj.setProductName(rs.getString("productname"));
				obj.setMachine(rs.getString("machinetype"));
				obj.setDrawingNumber(rs.getString("drawingno"));
				obj.setToolingLife(rs.getInt("toolinglife"));
				obj.setTypeOfTool(rs.getString("typeoftool"));
				obj.setServiceIntervalQty(rs.getInt("serviceintervalqty"));
				obj.setProducedQty(rs.getInt("producedqty"));
				
				long totalIntervalQuantity =  noOfLot * rs.getInt("serviceintervalqty");
				long balance = rs.getInt("producedqty") - totalIntervalQuantity;
				float serviceAvg = (float)balance / (float)obj.getServiceIntervalQty(); 
				float productServiceIntervalQty = serviceAvg * 100;
				long balanceProducedQty = obj.getServiceIntervalQty() - balance;
				
				obj.setBalanceProducedQty(balanceProducedQty);
				obj.setProducedQtyPercentage(productServiceIntervalQty);
				//if(productServiceIntervalQty <= obj.getProducedQty())
				lstProductReports.add(obj);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductReportDao getProductReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductReportDao getProductReport  : "+ex.getMessage());
			}
		}
	
		return lstProductReports;
	}
	
	public List<ProductReport> getProductLifeReport(ProductReport productReport , int percentage)
	{
		List<ProductReport> lstProductReports = new ArrayList<ProductReport>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			/*pstmt = con.prepareStatement("select toolinglotnumber, a.productname productname, a.drawingno drawingno, a.machinetype machinetype, b.typeoftool typeoftool, "
					+ " sum(b.producedqty) producedqty, a.serviceintervalqty serviceintervalqty,  a.toolinglife toolinglife"
					+ " from mst_product a, tooling_production_return_note_detail b"
					+ " where a.productname = b.productname and a.drawingno = b.drawingno "
					+ " group by toolinglotnumber");*/
			pstmt = con.prepareStatement("select originalid, toolinglotnumber, productname, drawingno,  machinetype, typeoftool,  sum(producedqty) producedqty,  "
					+ " serviceintervalqty, toolinglife from vw_product_interval group by toolinglotnumber");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ProductReport obj = new ProductReport();
				obj.setLotNumber(rs.getString("toolinglotnumber"));
				obj.setProductName(rs.getString("productname"));
				obj.setMachine(rs.getString("machinetype"));
				obj.setDrawingNumber(rs.getString("drawingno"));
				obj.setToolingLife(rs.getInt("toolinglife"));
				obj.setTypeOfTool(rs.getString("typeoftool"));
				obj.setServiceIntervalQty(rs.getInt("serviceintervalqty"));
				obj.setProducedQty(rs.getInt("producedqty"));
				int lifeQuantity = (obj.getToolingLife() * percentage) / 100;
				float lifeAvg = ((float)rs.getInt("producedqty") / (float)obj.getToolingLife());
				System.out.println("lifeAvg: "+lifeAvg);
				float lifePercentage = lifeAvg * 100;
				obj.setLifePercentage(lifePercentage);
				System.out.println(obj.getToolingLife()+", "+percentage+", "+rs.getInt("producedqty"));
				if(lifeQuantity <= obj.getProducedQty())
					lstProductReports.add(obj);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductReportDao getProductReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductReportDao getProductReport  : "+ex.getMessage());
			}
		}
	
		return lstProductReports;
	}

	public List<String> getMachineType(String machineType)
	{
		List<String> lstMachineType = new ArrayList<String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select distinct machinetype from tooling_production_return_note_detail where  machinetype like '"+machineType+"%' order by machinetype;");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				
				lstMachineType.add(rs.getString("machinetype"));
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductReportDao getMachineType : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductReportDao getMachineType  : "+ex.getMessage());
			}
		}
	
		return lstMachineType;
	}
	
	public List<String> getTypeOfTool(String typeOfTool)
	{
		List<String> lstTypeOfTool = new ArrayList<String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select distinct typeoftool from tooling_production_return_note_detail where  typeoftool like '"+typeOfTool+"%' order by typeoftool");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				
				lstTypeOfTool.add(rs.getString("typeoftool"));
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductReportDao getTypeOfTool : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductReportDao getTypeOfTool  : "+ex.getMessage());
			}
		}
	
		return lstTypeOfTool;
	}
	
	public List<String> getDrawingNumber(String drawingNumber)
	{
		List<String> lstTypeOfTool = new ArrayList<String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select distinct drawingNo from tooling_production_return_note_detail where  drawingNo like '"+drawingNumber+"%' order by drawingNo");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				
				lstTypeOfTool.add(rs.getString("drawingNo"));
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductReportDao drawingNo : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductReportDao getDrawingNumber  : "+ex.getMessage());
			}
		}
	
		return lstTypeOfTool;
	}
	
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
	
	public List<String> getToolLotNumber(String lotNumber)
	{
		List<String> lstlotNumber = new ArrayList<String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT distinct toolinglotnumber FROM tooling_receipt_product where toolinglotnumber like '"+lotNumber+"%' order by toolinglotnumber");
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
	
	public List<String> getMachineName(String machineName)
	{
		List<String> lstMachineType = new ArrayList<String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select distinct machinename from mst_machine where  machinename like '"+machineName+"%' order by machinename");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				
				lstMachineType.add(rs.getString("machinename"));
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductReportDao getMachineName : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductReportDao getMachineName  : "+ex.getMessage());
			}
		}
	
		return lstMachineType;
	}
	
	
	public int getLotNumberCount(String lotNumber)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int lotCount = 0;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT count(toolinglotnumber) noOfLotNumber FROM periodic_inspection_report_detail where toolinglotnumber = ?");
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{				
				lotCount = rs.getInt("noOfLotNumber");
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in getLotNumberCount : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getLotNumberCount: "+ex.getMessage());
			}
		}
	
		return lotCount;
	}
	
	public List<ToolingReturnNote> getClearingSOPReport(String lotNumber)
	{
		List<ToolingReturnNote> lstCleaningSOP = new ArrayList<ToolingReturnNote>();
		ToolingReturnNote objReturnNote;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			if(lotNumber == null || lotNumber.trim().equals(""))
			{
				pstmt = con.prepareStatement("SELECT a.toolinglotnumber, a.productname, a.drawingno, a.machinetype,a.typeoftool, "
						+ " (select returndate from tooling_production_return_note where returnnoteid = b.returnnoteid) returndate FROM tooling_receipt_product a "
						+ " left join tooling_production_return_note_detail b on a.toolinglotnumber = b.toolinglotnumber "
						+ " where cleaningsop!='null'");
			}else
			{
				pstmt = con.prepareStatement("SELECT a.toolinglotnumber, a.productname, a.drawingno, a.machinetype,a.typeoftool, "
						+ " (select returndate from tooling_production_return_note where returnnoteid = b.returnnoteid) FROM tooling_receipt_product a "
						+ " left join tooling_production_return_note_detail b on a.toolinglotnumber = b.toolinglotnumber "
						+ " where cleaningsop!='null' and a.toolinglotnumber = ?");
				pstmt.setString(1, lotNumber);
			}
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objReturnNote = new ToolingReturnNote();
				System.out.println(rs.getString("toolinglotnumber"));
				objReturnNote.setToolingLotNumber1(rs.getString("toolinglotnumber"));
				objReturnNote.setProductName1(rs.getString("productname"));
				objReturnNote.setDrawingNo1(rs.getString("drawingno"));
				objReturnNote.setMachineName1(rs.getString("machinetype"));
				objReturnNote.setTypeOfTooling1(rs.getString("typeoftool"));
				String returnDate = rs.getString("returndate");
				if(returnDate != null && !returnDate.equalsIgnoreCase("null"))
				{
					objReturnNote.setLastInspectionDate1(returnDate);
				}
				else
					objReturnNote.setLastInspectionDate1("N/A");
				lstCleaningSOP.add(objReturnNote);
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ProductReportDao getClearingSOPReport : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductReportDao getClearingSOPReport  : "+ex.getMessage());
			}
		}
	
		return lstCleaningSOP;
	}
	
	public ToolTracker getToolTracker(ProductReport productReport) {
		ToolTracker toolTracker = new ToolTracker();
		toolTracker.setToolingReceiptNotes(getToolingReceipt(productReport));
		toolTracker.setToolingRequests(getToolingReceivingRequest(productReport));
		toolTracker.setToolingInspections(getToolingReceivingInspectionDetails(productReport));
		toolTracker.setToolingIssueNotes(getToolingIssueNote(productReport));
		toolTracker.setToolingRequestNotes(getToolingRequest(productReport));
		return toolTracker;
	}
	
	public List<ToolingReceiptNote> getToolingReceipt(ProductReport productReport){
		List<ToolingReceiptNote> lstToolingReceiptNote = new ArrayList<ToolingReceiptNote>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ToolingReceiptNote toolingReceipt;
		try
		{
			con = datasource.getConnection();
						
			String query = "Select toolingreceiptid, toolingproductid, productname, drawingno, strength, machinetype, typeoftool,"
		    		+ " mocpunches, mocdies, shape, dimensions, breaklineupper, breaklinelower, embosingUpper, embosingLower, lasermarking, hardcromeplating,"
		    		+ " dustcapgroove, poquantity, receivedquantity, toolinglotnumber, uom, receiptStatus, mocNumber, dqDocument, inspectionReportNumber,toolinglife,serviceintervalqty, "
		    		+ "  toolingcodenumber, expiryDate, storagelocation, cleaningsop, dquploadimage, mocuploadimage, inspectionimageupload, uppperQty, lowerQty, dieQty, minacceptedQty, mandate  "
		    		+ " from tooling_receipt_product Where toolingreceiptid > 0  ";
			
			if(productReport.getProductName()!= null && !productReport.getProductName().equals(""))
			{
				query = query + " and productname = '"+productReport.getProductName()+"' ";
			}
			if(productReport.getDrawingNumber()!= null && !productReport.getDrawingNumber().equals(""))
			{
				query = query + " and drawingno = '"+productReport.getDrawingNumber()+"' ";
			}
			if(productReport.getMachine()!= null && !productReport.getMachine().equals(""))
			{
				query = query + " and machinetype = '"+productReport.getMachine()+"' ";
			}
			if(productReport.getTypeOfTool()!= null && !productReport.getTypeOfTool().equals(""))
			{
				query = query + " and typeoftool = '"+productReport.getTypeOfTool()+"' ";
			}
			if(productReport.getLotNumber()!= null && !productReport.getLotNumber().equals(""))
			{
				query = query + " and toolinglotnumber = '"+productReport.getLotNumber()+"' ";
			}
			pstmt = con.prepareStatement(query);
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
				lstToolingReceiptNote.add(toolingReceipt);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getToolingReceipt for getting history report : "+ex.getMessage());
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
				System.out.println("Exception when getting the entire getToolingReceipt for getting history report : "+ex.getMessage());
			}
		}
		return lstToolingReceiptNote;	
	}
	
	public List<ToolingRequest> getToolingReceivingRequest(ProductReport productReport){

		List<ToolingRequest> lstToolingInspection = new ArrayList<ToolingRequest>();

		ToolingRequest objInspection = new ToolingRequest();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
						
			String query = "Select toolingname, ponumber, podate, drawingno, lotnumber, receivedquantity, UOM, remarks, "
					+ " toolingrequestid, toolingRequestdetailid, toolingProductId, typeoftool, machinetype from tooling_receiving_request_details "
					+ "where toolingrequestid > 0 ";
			
			if(productReport.getDrawingNumber()!= null && !productReport.getDrawingNumber().equals(""))
			{
				query = query + " and drawingno = '"+productReport.getDrawingNumber()+"' ";
			}
			if(productReport.getMachine()!= null && !productReport.getMachine().equals(""))
			{
				query = query + " and machinetype = '"+productReport.getMachine()+"' ";
			}
			if(productReport.getTypeOfTool()!= null && !productReport.getTypeOfTool().equals(""))
			{
				query = query + " and typeoftool = '"+productReport.getTypeOfTool()+"' ";
			}
			if(productReport.getLotNumber()!= null && !productReport.getLotNumber().equals(""))
			{
				query = query + " and lotnumber = '"+productReport.getLotNumber()+"' ";
			}
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objInspection = new ToolingRequest();
				objInspection.setToolingname(rs.getString("toolingname"));
				objInspection.setPoNumber(rs.getString("ponumber"));
				objInspection.setPoDate(sdf.format(sdfDB.parse(rs.getString("podate"))));
				objInspection.setDrawingNo(rs.getString("drawingno"));
				objInspection.setLotNumber(rs.getString("lotnumber"));				
				objInspection.setReceivedQuantity(rs.getLong("receivedquantity"));
				objInspection.setUom(rs.getString("UOM"));
				//objInspection.setInspectionStatus1(rs.getString("inspectionstatus"));
				objInspection.setRemarks1(rs.getString("remarks"));
				objInspection.setToolingRequestId(rs.getInt("toolingrequestid"));
				objInspection.setToolingRequestDetailId1(rs.getInt("toolingRequestdetailid"));
				objInspection.setToolingProductId1(rs.getInt("toolingProductId"));
				objInspection.setTypeOfTool(rs.getString("typeoftool"));
				objInspection.setMachineType(rs.getString("machinetype"));
				lstToolingInspection.add(objInspection);				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getToolingReceivingRequest : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getToolingReceivingRequest : "+ex.getMessage());
			}
		}
	
		return lstToolingInspection;
	
	}
	
	public List<ToolingInspection> getToolingReceivingInspectionDetails(ProductReport productReport)
	{
		List<ToolingInspection> lstToolingInspection = new ArrayList<ToolingInspection>();

		ToolingInspection objInspection = new ToolingInspection();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			String query = "Select toolingname, drawingno, lotnumber, receivedquantity, UOM, inspectionstatus, remarks, "
					+ " toolingInspectionid, toolingInspectiondetailid, toolingrequestdetailId, typeoftool, machinetype,acceptedQty,"
					+ " rejectedQty,rejectedserialnumber from tooling_receiving_inspection_details where toolingInspectionid > 0 ";
			
			if(productReport.getDrawingNumber()!= null && !productReport.getDrawingNumber().equals(""))
			{
				query = query + " and drawingno = '"+productReport.getDrawingNumber()+"' ";
			}
			if(productReport.getMachine()!= null && !productReport.getMachine().equals(""))
			{
				query = query + " and machinetype = '"+productReport.getMachine()+"' ";
			}
			if(productReport.getTypeOfTool()!= null && !productReport.getTypeOfTool().equals(""))
			{
				query = query + " and typeoftool = '"+productReport.getTypeOfTool()+"' ";
			}
			if(productReport.getLotNumber()!= null && !productReport.getLotNumber().equals(""))
			{
				query = query + " and lotnumber = '"+productReport.getLotNumber()+"' ";
			}
			pstmt = con.prepareStatement(query);
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
	
	public List<ToolingRequestNote> getToolingRequest(ProductReport productReport)
	{
		List<ToolingRequestNote> lstRequestNote = new ArrayList<ToolingRequestNote>();
		ToolingRequestNote requestNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		String batchQty1;
		String requestQty1;
		String inStock1;
		String underInspection1;
		try
		{
			con = datasource.getConnection();

			String query = "Select originalid, requestdetailId, requestId, productName, machintype, drawingno, UOM, batchqty, requestqty, instock,"
					+ " underInspection, typeoftool,toolingLotNumber,batchnos,batchprod,market,dustcup "
					+ "  from tooling_request_note_detail"
					+ " where requestId > 0";
			
			if(productReport.getProductName()!= null && !productReport.getProductName().equals(""))
			{
				query = query + " and productname = '"+productReport.getProductName()+"' ";
			}
			if(productReport.getDrawingNumber()!= null && !productReport.getDrawingNumber().equals(""))
			{
				query = query + " and drawingno = '"+productReport.getDrawingNumber()+"' ";
			}
			if(productReport.getMachine()!= null && !productReport.getMachine().equals(""))
			{
				query = query + " and machinetype = '"+productReport.getMachine()+"' ";
			}
			if(productReport.getTypeOfTool()!= null && !productReport.getTypeOfTool().equals(""))
			{
				query = query + " and typeoftool = '"+productReport.getTypeOfTool()+"' ";
			}
			if(productReport.getLotNumber()!= null && !productReport.getLotNumber().equals(""))
			{
				query = query + " and toolinglotnumber = '"+productReport.getLotNumber()+"' ";
			}
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				requestNote = new ToolingRequestNote();
				
				requestNote.setRequestDetailId1(rs.getInt("requestdetailId"));
				requestNote.setRequestId(rs.getInt("originalid"));
				//requestNote.setRequestId(requestId);
				requestNote.setProductName1(TiimUtil.ValidateNull(rs.getString("productName")).trim());
				requestNote.setMachingType1(TiimUtil.ValidateNull(rs.getString("machintype")).trim());
				requestNote.setDrawingNo1(TiimUtil.ValidateNull(rs.getString("drawingno")).trim());
				requestNote.setUOM1(TiimUtil.ValidateNull(rs.getString("UOM")).trim());
				requestNote.setToolingLotNumber1(TiimUtil.ValidateNull(rs.getString("toolingLotNumber")).trim());
				
				requestNote.setBatchnos1(TiimUtil.ValidateNull(rs.getString("batchnos")).trim());
				requestNote.setBatchProd1(TiimUtil.ValidateNull(rs.getString("batchprod")).trim());
				
				requestNote.setMarket1(TiimUtil.ValidateNull(rs.getString("market")).trim());
				requestNote.setDustCup1(TiimUtil.ValidateNull(rs.getString("dustCup")).trim());

				
					if(rs.getInt("batchqty") == 0)
					{
						batchQty1 = "";
					}
					else
					{
						batchQty1 = rs.getInt("batchqty")+"";
					}
				requestNote.setBatchQty1(batchQty1);
					if(rs.getInt("requestqty") == 0)
					{
						requestQty1 = "";
					}
					else
					{
						requestQty1 = rs.getInt("requestqty")+"";
					}
				requestNote.setRequestQty1(requestQty1);
					if(rs.getInt("instock") == 0)
					{
						inStock1 = "";
					}
					else
					{
						inStock1 = rs.getInt("instock")+"";
					}
				requestNote.setInStock1(inStock1);
					if(rs.getInt("underInspection") == 0)
					{
						underInspection1 = "";
					}
					else
					{
						underInspection1 = rs.getInt("underInspection")+"";
					}
				requestNote.setUnderInspection1(underInspection1);
				requestNote.setTypeOfTool1(rs.getString("typeoftool"));
				
				lstRequestNote.add(requestNote);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the tooling_request_note list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the tooling_request_note list : "+ex.getMessage());
			}
		}
		return lstRequestNote;	
	}
	
	public List<ToolingIssueNote> getToolingIssueNote(ProductReport productReport){

		List<ToolingIssueNote> lstIssueNote = new ArrayList<ToolingIssueNote>();

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String inspDate = "";
		String lotNo = "";
		long nextDueQty;
		long issuedQty;
		try {
			con = datasource.getConnection();
			
			
			String query = "Select max(toolingIssueDetailId) as toolingIssueDetailId, issueId, typeoftool, productname, machineName, drawingNo, batchQty, originalid, "
					+ " requestQty, toolinglotnumber, lastInspectionDate, nextDueQty, issuedQty, UOM, serialNumber, noofdays, requestdetailId from tooling_issue_note_detail"
					+ " where originalId > 0";
			
			if(productReport.getProductName()!= null && !productReport.getProductName().equals(""))
			{
				query = query + " and productname = '"+productReport.getProductName()+"' ";
			}
			if(productReport.getDrawingNumber()!= null && !productReport.getDrawingNumber().equals(""))
			{
				query = query + " and drawingno = '"+productReport.getDrawingNumber()+"' ";
			}
			
			if(productReport.getTypeOfTool()!= null && !productReport.getTypeOfTool().equals(""))
			{
				query = query + " and typeoftool = '"+productReport.getTypeOfTool()+"' ";
			}
			if(productReport.getLotNumber()!= null && !productReport.getLotNumber().equals(""))
			{
				query = query + " and toolinglotnumber = '"+productReport.getLotNumber()+"' ";
			}
			
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setIssueDetailId1(rs.getInt("toolingIssueDetailId"));
				issueNote.setTypeOfTooling1(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				issueNote.setProductName1(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				issueNote.setMachineName1(TiimUtil.ValidateNull(rs.getString("machineName")).trim());
				issueNote.setDrawingNo1(TiimUtil.ValidateNull(rs.getString("drawingNo")).trim());
				issueNote.setBatchQty1(rs.getLong("batchQty"));
				issueNote.setRequestQty1(rs.getLong("requestQty"));
				lotNo = TiimUtil.ValidateNull(rs.getString("toolinglotnumber"));
				if ("0".equals(lotNo)) {
					lotNo = "";
				}
				issueNote.setToolingLotNumber1(lotNo);
				inspDate = TiimUtil.ValidateNull(rs.getString("lastInspectionDate"));
				if (!"".equals(inspDate)) {
					inspDate = sdf.format(sdfDB.parse(inspDate));
				} else {
					inspDate = "";
				}
				issueNote.setLastInspectionDate1(inspDate);
				nextDueQty = rs.getLong("nextDueQty");

				issueNote.setNextDueQty1(nextDueQty);
				issuedQty = (rs.getLong("issuedQty"));

				issueNote.setIssuedQty1(issuedQty);
				issueNote.setUOM1(rs.getString("UOM"));
				issueNote.setSerialNumber(rs.getString("serialNumber"));
				issueNote.setNoOfDays(rs.getInt("noofdays"));
				issueNote.setRequestDetailId1(rs.getInt("requestdetailId"));
				lstIssueNote.add(issueNote);
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the tooling_issue_note list : " + ex.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connection in the tooling_issue_note list : " + ex.getMessage());
			}
		}
		return lstIssueNote;

	
	}
	
}
