package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.tiim.model.Branch;
import com.tiim.model.ClearanceClosing;
import com.tiim.model.Product;
import com.tiim.model.ToolingReceiptNote;
import com.tiim.util.TiimUtil;

@Repository
public class ClearanceClosingDao {

	@Autowired
	DataSource datasource;
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	ToolingReceiptNoteDao receiptDao;

	
	public int getIntialValue()
	{
		int closingNo = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(closingNo) closingNo FROM mst_clearance_Closing ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				closingNo = rs.getInt("closingNo");
			}
			closingNo++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ClearanceClosingDao  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in ClearanceClosingDao : "+ex.getMessage());
			}
		}
		
		return closingNo;	
	}
	public String addClearanceClosing(ClearanceClosing clearacne, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isClearanceExists(clearacne.getClosingNo()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into mst_clearance_closing(clearanceNo,lotNumber,clearanceDate,productName,"
						+ "batchNumber,batchQty,closingNo) "
						+ "values(?,?,?,?,?,?,?)");
				pstmt.setInt(1, clearacne.getClearanceNo());
				pstmt.setString(2, clearacne.getLotNumber());
				java.sql.Date date = new java.sql.Date(clearacne.getClearanceDate().getTime());
				pstmt.setDate(3, date);
				pstmt.setString(4, TiimUtil.ValidateNull(clearacne.getProductName()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(clearacne.getBatchNumber()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(clearacne.getBatchQty()).trim());
				pstmt.setInt(7, clearacne.getClosingNo());

				pstmt.executeUpdate();
				closeClearance(clearacne.getClearanceNo());
				msg = "Saved Successfully";
				
				Product product = productDao.getProductIntervalQnty(clearacne.getProductName(), getDrawingNumber(clearacne.getLotNumber()));
				ToolingReceiptNote receiptNote = receiptDao.getReceiptIntervalQnty(clearacne.getLotNumber()); 
				
				/*
				 * long toolLife = receiptNote.getToolingLife() *
				 * Integer.parseInt(clearacne.getBatchQty()); long intervalQnty =
				 * receiptNote.getServiceIntervalQnty() *
				 * Integer.parseInt(clearacne.getBatchQty());
				 */
			//	System.out.println("batch aty: "+clearacne.getBatchQty()+", "+clearacne.getCurrentBatchQty());
				long batchQuantity = Long.parseLong(clearacne.getBatchQty());
				if(batchQuantity < clearacne.getCurrentBatchQty()) {
					//long toolLife = receiptNote.getToolingLife() + (clearacne.getCurrentBatchQty() - batchQuantity);
					long toolLife = 0;
					long intervalQnty = receiptNote.getServiceIntervalQnty() - (clearacne.getCurrentBatchQty() - batchQuantity);
					
					receiptDao.updateReceiptIntervalQnty(clearacne.getLotNumber(), toolLife, intervalQnty);
					
				}
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("clearanceClosing.page", null,null));
				history.setDescription(messageSource.getMessage("clearanceClosing.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			}
			else
			{
				msg = "Already Exists";
			}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the detail in mst_clearance_closing table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in master detail in mst_clearance_closing table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateClearanceClosing(ClearanceClosing clearance, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update mst_clearance_closing set clearanceNo= ?,lotNumber= ?,clearanceDate= ?,productName= ?,batchNumber= ?,batchQty= ?"
						+ ",closingNo = ? where closingNo = ?");
				pstmt.setInt(1, clearance.getClearanceNo());
				pstmt.setString(2, clearance.getLotNumber());
				java.sql.Date date = new java.sql.Date(clearance.getClearanceDate().getTime());
				pstmt.setDate(3, date);
				pstmt.setString(4, TiimUtil.ValidateNull(clearance.getProductName()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(clearance.getBatchNumber()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(clearance.getBatchQty()).trim());
				pstmt.setInt(7, clearance.getClosingNo());
				pstmt.setInt(8, clearance.getClosingNo());

				pstmt.executeUpdate();
				
				Product product = productDao.getProductIntervalQnty(clearance.getProductName(), getDrawingNumber(clearance.getLotNumber()));
				//long toolLife = Integer.parseInt(product.getToolingLife()) - Integer.parseInt(clearance.getBatchQty());
				long toolLife = 0;
				long intervalQnty = Integer.parseInt(product.getServiceIntervalQty()) - Integer.parseInt(clearance.getBatchQty());
				receiptDao.updateReceiptIntervalQnty(clearance.getLotNumber(), toolLife, intervalQnty);
				
				msg = "Updated Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("clearanceClosing.page", null,null));
				history.setDescription(messageSource.getMessage("clearanceClosing.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the detail in mst_clearance_closing table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in detail in mst_clearance_closing table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteClearanceClosing(int closingNo, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from mst_clearance_closing where closingNo = ?");
			pstmt.setInt(1, closingNo);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("clearanceClosing.page", null,null));
			history.setDescription(messageSource.getMessage("clearanceClosing.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the sop detail in mst_clearance_closing table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the sop detail in mst_clearance_closing table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	public List<ClearanceClosing> getClearanceClosingDetails(int serialNo)
	{
		List<ClearanceClosing> lstSop = new ArrayList<ClearanceClosing>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ClearanceClosing clearance;
		try
		{
			con = datasource.getConnection();
			if(serialNo != 0)
			{
			    pstmt = con.prepareStatement("Select clearanceNo,lotNumber,clearanceDate,productName,batchNumber,batchQty,closingNo"
                                  +" from mst_clearance_Closing Where closingNo like '%"+serialNo+"%' order by closingNo desc");
			}
			else
			{
				pstmt = con.prepareStatement("Select clearanceNo,lotNumber,clearanceDate,productName,batchNumber,batchQty,closingNo from mst_clearance_Closing order by closingNo desc");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				clearance = new ClearanceClosing();
				clearance.setClearanceNo(rs.getInt("clearanceNo"));
				clearance.setLotNumber(rs.getString("lotNumber"));
				clearance.setClearanceDate(rs.getDate("clearanceDate"));
				clearance.setProductName(TiimUtil.ValidateNull(rs.getString("productName")).trim());
				clearance.setBatchNumber(TiimUtil.ValidateNull(rs.getString("batchNumber")).trim());
				clearance.setBatchQty(TiimUtil.ValidateNull(rs.getString("batchQty")).trim());
				clearance.setClosingNo(rs.getInt("closingNo"));

				lstSop.add(clearance);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire sop in mst_clearance_closing table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire sop details in mst_clearance_closing table : "+ex.getMessage());
			}
		}
		return lstSop;	
	}
	
	public ClearanceClosing getClearanceClosing(int serialNo)
	{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ClearanceClosing clearance = new ClearanceClosing();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select clearanceNo,lotNumber,clearanceDate,productName,batchNumber,batchQty,closingNo"
                                  +" from mst_clearance_Closing Where closingNo = ? order by closingNo");
			pstmt.setInt(1, serialNo);
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				clearance.setClearanceNo(rs.getInt("clearanceNo"));
				clearance.setLotNumber(rs.getString("lotNumber"));
				clearance.setClearanceDate(rs.getDate("clearanceDate"));
				clearance.setProductName(TiimUtil.ValidateNull(rs.getString("productName")).trim());
				clearance.setBatchNumber(TiimUtil.ValidateNull(rs.getString("batchNumber")).trim());
				clearance.setBatchQty(TiimUtil.ValidateNull(rs.getString("batchQty")).trim());
				clearance.setClosingNo(rs.getInt("closingNo"));

			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire sop in mst_clearance_closing table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire sop details in mst_clearance_closing table : "+ex.getMessage());
			}
		}
		return clearance;	
	}
	
	public Branch getBranchDetail(int branchId)
	{
		Branch branch = new Branch();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select branchId, branchname, branchcode,  isActive from branch Where branchId = ?");
			pstmt.setInt(1, branchId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				branch.setBranchId(rs.getInt("branchId"));
				branch.setBranchCode(TiimUtil.ValidateNull(rs.getString("branchcode")).trim());
				branch.setBranchName(TiimUtil.ValidateNull(rs.getString("branchname")).trim());
				branch.setIsActive(rs.getInt("isactive"));
				
				branch.setAction("");
				branch.setSearchBranch("");
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the particular branch details in branch table by using departmentid : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  particular branch details in branch table by using departmentid : "+ex.getMessage());
			}
		}
		return branch;	
	}
	
	private boolean isClearanceExists(int sopId)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_clearance_closing WHERE closingNo = ?");
			 pstmt.setInt(1, sopId);
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
			System.out.println("Exception while checking the isBranchExists : "+e.getMessage());
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
				System.out.println("Exception when closing the connection in isBranchExists : "+ex.getMessage());
			}
		}
		return isExists;
	}
	
	public void closeClearance(int clearanceId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("update mst_clearance_no set isclosed = 1 where clearanceNo = ?");
			 pstmt.setInt(1, clearanceId);
			 pstmt.executeUpdate();			 
		}
		catch(Exception e)
		{
			System.out.println("Exception while closeClearance : "+e.getMessage());
		}
		finally
		{
			try
			{

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
				System.out.println("Exception when closing the connection in closeClearance : "+ex.getMessage());
			}
		}
	
	}
	
	public String getDrawingNumber(String lotNumber)
	{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		String drawingNumber = new String();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select drawingno from tooling_receipt_product where toolinglotnumber = ?");
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				drawingNumber = rs.getString("drawingno");
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getDrawingNumber : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getDrawingNumber : "+ex.getMessage());
			}
		}
		return drawingNumber;	
	}
	
	public ClearanceClosing getTotalBatchQnty(String lotNumber)
	{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		int batchNumber = 0;
		int batchQty = 0;
		ClearanceClosing closing = new ClearanceClosing();
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT batchNumber, batchQty FROM mst_clearance_closing where lotnumber = ?");
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				batchNumber = batchNumber + rs.getInt("batchNumber");
				batchQty = batchQty + rs.getInt("batchQty");
			}
			closing.setBatchNumber(batchNumber+"");
			closing.setBatchQty(batchQty+"");
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getDrawingNumber : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getDrawingNumber : "+ex.getMessage());
			}
		}
		return closing;	
	}
	
}
