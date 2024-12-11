package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

//import org.apache.tomcat.util.digester.SetRootRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.tiim.model.SupplierReturn;
import com.tiim.model.ToolingReturnNote;
import com.tiim.util.TiimUtil;

@Repository
public class SupplierReturnNoteDao {
	
	@Autowired
	DataSource datasource;
	
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfDBFull = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
	java.util.Date cDate = new java.util.Date();
	

	public List<SupplierReturn> getReceiptProductDetails(int supplierReturnId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		SupplierReturn supplierReturn = new SupplierReturn();
		List<SupplierReturn> lstSupplierReturn = new ArrayList<SupplierReturn>();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingreceiptid, toolingproductid, productname, drawingno, machinetype, typeoftool,"
			    		+ " receivedquantity, toolinglotnumber, uom "
			    		+ " from tooling_receipt_product Where toolingreceiptid = ? and receiptStatus = ?  order by productname");
			pstmt.setInt(1, supplierReturnId);
			pstmt.setInt(2, 0);//Reject status
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				supplierReturn = new SupplierReturn();
				supplierReturn.setTransactionId(rs.getInt("toolingreceiptid"));
				supplierReturn.setTransactionDetailId(rs.getInt("toolingproductid"));
				supplierReturn.setProductName(rs.getString("productname"));
				supplierReturn.setDrawingNo(rs.getString("drawingno"));
				supplierReturn.setMachineType(rs.getString("machinetype"));
				supplierReturn.setTypeOfTool(rs.getString("typeoftool"));
				supplierReturn.setReceivedQuantity(rs.getInt("receivedquantity"));
				supplierReturn.setToolingLotNumber(rs.getString("toolinglotnumber"));		
				supplierReturn.setUom(rs.getString("uom"));
				lstSupplierReturn.add(supplierReturn);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getsupplierReturnProduct in tooling_receipt_product table SupplierReturnNoteDao : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire getsupplierReturnProduct details in tooling_receipt_note table SupplierReturnNoteDao : "+ex.getMessage());
			}
		}
		return lstSupplierReturn;	
	}
	
	public List<SupplierReturn> getToolingInspectionDetails(int toolingInspectionId)
	{
		SupplierReturn supplierReturn = new SupplierReturn();
		List<SupplierReturn> lstSupplierReturn = new ArrayList<SupplierReturn>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select toolingname, drawingno, lotnumber, receivedquantity, UOM,   "
					+ " toolingInspectionid, toolingInspectiondetailid, typeoftool, machinetype from tooling_receiving_inspection_details where toolingInspectionid = ? and inspectionstatus='Rejected'");
			pstmt.setInt(1, toolingInspectionId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				supplierReturn = new SupplierReturn();
				supplierReturn.setTransactionId(rs.getInt("toolingInspectionid"));
				supplierReturn.setTransactionDetailId(rs.getInt("toolingInspectiondetailid"));
				supplierReturn.setProductName(rs.getString("toolingname"));
				supplierReturn.setDrawingNo(rs.getString("drawingno"));
				supplierReturn.setMachineType(rs.getString("machinetype"));
				supplierReturn.setTypeOfTool(rs.getString("typeoftool"));
				supplierReturn.setReceivedQuantity(rs.getInt("receivedquantity"));
				supplierReturn.setToolingLotNumber(rs.getString("lotnumber"));		
				supplierReturn.setUom(rs.getString("UOM"));
				lstSupplierReturn.add(supplierReturn);
			
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
	
		return lstSupplierReturn;
	
	}
	
	public List<SupplierReturn> getPeriodicInspectionDetail(int requestNo)
	{
		SupplierReturn supplierReturn = new SupplierReturn();
		List<SupplierReturn> lstSupplierReturn = new ArrayList<SupplierReturn>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select Period.requestdetailid, Period.requestno, Period.toolinglotnumber, Period.typeoftool, Period.productname, Period.drawingno," +
					"Period.UOM, Period.tabletproducedqty, Period.toolingstatus, Period.toolingdueqty, Period.toolinghistory, Period.machinetype " +
					"from tooling_periodical_inspection_request_detail Period " +
					"where requestno = ? and toolingstatus = 'Damaged'");
			pstmt.setInt(1, requestNo);		
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				supplierReturn = new SupplierReturn();
				supplierReturn.setTransactionId(rs.getInt("requestno"));
				supplierReturn.setTransactionDetailId(rs.getInt("requestdetailid"));
				supplierReturn.setProductName(rs.getString("productname"));
				supplierReturn.setDrawingNo(rs.getString("drawingno"));
				supplierReturn.setMachineType(rs.getString("machinetype"));
				supplierReturn.setTypeOfTool(rs.getString("typeoftool"));
				supplierReturn.setReceivedQuantity(rs.getInt("tabletproducedqty"));
				supplierReturn.setToolingLotNumber(rs.getString("toolinglotnumber"));		
				supplierReturn.setUom(rs.getString("UOM"));
				lstSupplierReturn.add(supplierReturn);
				
			
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
		return lstSupplierReturn;
	
	}
	
	public String addSupplierReturn(SupplierReturn supplierReturn, int userId)
	{
		String message = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into suplier_return(supplierreturndate,requestno,requestdate, branch, source) "
						+ "values(?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
				//,Statement.RETURN_GENERATED_KEYS
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(1, date);
				pstmt.setInt(2, supplierReturn.getRequestId());
				//pstmt.setString(3, sdfDB.format(sdf.parse(supplierReturn.getRequestDate())));
				pstmt.setString(3, supplierReturn.getRequestDate());
				pstmt.setString(4, TiimUtil.ValidateNull(supplierReturn.getBranch()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(supplierReturn.getReturnType()).trim());
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next())
				{
					supplierReturn.setReturnId(rs.getInt(1));
				}
				message = addReturnDetail(supplierReturn);
				message = "Saved Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("supplierReturn.page", null,null));
				history.setDescription(messageSource.getMessage("supplierReturn.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding Supplier return initial value in addSupplierReturn table : "+ex.getMessage());
			ex.getStackTrace();
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
				System.out.println("Exception when closing the connection in Supplier return initial value in addSupplierReturn : "+ex.getMessage());
			}
		}
		return message;
	}
	
	public String addReturnDetail(SupplierReturn supplierReturn)
	{

		String message = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into suplier_return_detail(toolinglotno, productname, machinename, drawingno, typeoftooling, uom, receivedqty, supplierreturnid) "
						+ "values(?, ?, ?, ?, ?, ?, ?, ?)");
				System.out.println("supplierReturn.getToolingLotNumber(): "+supplierReturn.getToolingLotNumber());
				pstmt.setString(1, supplierReturn.getToolingLotNumber());
				pstmt.setString(2, supplierReturn.getProductName());
				pstmt.setString(3, supplierReturn.getMachineType());
				pstmt.setString(4, TiimUtil.ValidateNull(supplierReturn.getDrawingNo()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(supplierReturn.getTypeOfTool()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(supplierReturn.getUom()).trim());
				pstmt.setInt(7, supplierReturn.getReceivedQuantity());
				pstmt.setInt(8, supplierReturn.getReturnId());
				
				pstmt.executeUpdate();
				message = "Saved Successfully";
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding Supplier return initial value in addSupplierReturnDetail table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in Supplier return initial value in addSupplierReturnDetail : "+ex.getMessage());
			}
		}
		return message;
	
	}
	
	public String updateSupplierReturn(SupplierReturn supplierReturn, int userId)
	{
		String message = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update suplier_return supplierreturndate = ?,requestno = ?,requestdate = ?, branch = ? "
						+ " where supplierreturnid = ?");
				//,Statement.RETURN_GENERATED_KEYS
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(1, date);
				pstmt.setInt(2, supplierReturn.getRequestId());
				pstmt.setString(3, sdfDB.format(sdf.parse(supplierReturn.getRequestDate())));
				pstmt.setString(4, TiimUtil.ValidateNull(supplierReturn.getBranch()).trim());
				
				pstmt.executeUpdate();
				
				message = addReturnDetail(supplierReturn);
				message = "Updated Successfully";
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("supplierReturn.page", null,null));
				history.setDescription(messageSource.getMessage("supplierReturn.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding Supplier return initial value in updateSupplierReturn table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in Supplier return initial value in updateSupplierReturn : "+ex.getMessage());
			}
		}
		return message;
	}
	
	public String updateReturnDetail(SupplierReturn supplierReturn)
	{


		String message = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("update suplier_return_detail toolinglotno = ?, productname = ?, machinename = ?, drawingno = ?, typeoftooling = ?,"
						+ " uom = ?, receivedqty = ?, supplierreturnid = ? where supplierreturndetailid = ?");

				pstmt.setString(1, supplierReturn.getToolingLotNumber());
				pstmt.setString(2, supplierReturn.getProductName());
				pstmt.setString(3, supplierReturn.getMachineType());
				pstmt.setString(4, TiimUtil.ValidateNull(supplierReturn.getDrawingNo()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(supplierReturn.getTypeOfTool()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(supplierReturn.getUom()).trim());
				pstmt.setInt(7, supplierReturn.getReceivedQuantity());
				pstmt.setInt(8, supplierReturn.getReturnId());
				pstmt.setInt(9, supplierReturn.getReturnDetailId());
				
				pstmt.executeUpdate();
				message = "Updated Successfully";
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding Supplier return initial value in addSupplierReturnDetail table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in Supplier return initial value in addSupplierReturnDetail : "+ex.getMessage());
			}
		}
		return message;
	
	}
	
	public List<SupplierReturn> getSupplierReturn(int returnId)
	{

		List<SupplierReturn> lstReturnNote = new ArrayList<SupplierReturn>();
		SupplierReturn returnNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			if(returnId == 0)
			{
				pstmt = con.prepareStatement("select supplierreturnid, supplierreturndate,requestno,requestdate, branch from suplier_return");
			}else
			{
				pstmt = con.prepareStatement("select supplierreturnid, supplierreturndate,requestno,requestdate, branch from suplier_return where supplierreturnid = ?");
				pstmt.setInt(1, returnId);
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				returnNote = new SupplierReturn();
				System.out.println(rs.getInt("supplierreturnid"));
				returnNote.setReturnId(rs.getInt("supplierreturnid"));
				returnNote.setReturnDate(sdf.format(sdfDB.parse(rs.getString("supplierreturndate"))));
				returnNote.setRequestId(rs.getInt("requestno"));
				returnNote.setRequestDate(sdf.format(sdfDB.parse(rs.getString("requestdate"))));
				returnNote.setBranch(rs.getString("branch"));
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
	
	public SupplierReturn getSupplierReturnById(int returnId)
	{

		SupplierReturn returnNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select supplierreturnid, supplierreturndate,requestno,requestdate, branch from suplier_return where supplierreturnid = ?");
			pstmt.setInt(1, returnId);
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				returnNote = new SupplierReturn();
				returnNote.setReturnId(rs.getInt("supplierreturnid"));
				returnNote.setReturnDate(sdf.format(sdfDB.parse(rs.getString("supplierreturndate"))));
				returnNote.setRequestId(rs.getInt("requestno"));
				returnNote.setRequestDate(sdf.format(sdfDB.parse(rs.getString("requestdate"))));
				returnNote.setBranch(rs.getString("branch"));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getSupplierReturnById list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the getSupplierReturnById list : "+ex.getMessage());
			}
		}
	
		return returnNote;
	
	}
	
	public SupplierReturn getSupplierReturnDetail(int returnId)
	{
		
		SupplierReturn returnNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select toolinglotno, productname, machinename, drawingno, typeoftooling, uom, receivedqty, supplierreturnid, supplierreturndetailid from "
						+ " suplier_return_detail where supplierreturnid = ?");
			
			pstmt.setInt(1, returnId);
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				returnNote = new SupplierReturn();
				returnNote.setToolingLotNumber(rs.getString("toolinglotno"));
				returnNote.setProductName(rs.getString("productname"));
				returnNote.setMachineType(rs.getString("machinename"));
				returnNote.setDrawingNo(rs.getString("drawingno"));
				returnNote.setTypeOfTool(rs.getString("typeoftooling"));
				returnNote.setUom(rs.getString("uom"));
				returnNote.setReceivedQuantity(rs.getInt("receivedqty"));
				returnNote.setReturnId(rs.getInt("supplierreturnid"));
				returnNote.setReturnDetailId(rs.getInt("supplierreturndetailid"));

			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getSupplierReturnDetail list : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in the getSupplierReturnDetail list : "+ex.getMessage());
			}
		}
	
		return returnNote;
	
	}
	
	public void deleteSupplierReturn(int returnId, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from suplier_return_detail where supplierreturnid = ?");
			pstmt.setInt(1, returnId);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement("delete from suplier_return where supplierreturnid = ?");
			pstmt.setInt(1, returnId);
			pstmt.executeUpdate();
			
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("supplierReturn.page", null,null));
			history.setDescription(messageSource.getMessage("supplierReturn.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
			msg = "Deleted Successfully";
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the detail in deleteSupplierReturn table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in delete the detail in deleteSupplierReturn table : "+ex.getMessage());
			}
		}
	}
}
