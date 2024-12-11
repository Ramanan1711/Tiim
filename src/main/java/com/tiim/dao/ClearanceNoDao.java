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
import com.tiim.model.ClearanceNo;
import com.tiim.model.ClearanceReport;
import com.tiim.model.ToolingReceiptNote;
import com.tiim.util.TiimUtil;

@Repository
public class ClearanceNoDao {

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
	
	@Autowired
	ToolingReceiptNoteDao receiptDao;
	
	@Autowired
	ClearanceClosingDao clearanceClosingDao;
	
	public int getIntialValue()
	{
		int clearanceNo = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(clearanceNo) clearanceNo FROM mst_clearance_no ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				clearanceNo = rs.getInt("clearanceNo");
			}
			clearanceNo++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in ClearanceDao  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when  the connectin in ClearanceDao : "+ex.getMessage());
			}
		}
		
		return clearanceNo;	
	}
	public String addClearance(ClearanceNo clearacne, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isClearanceExists(clearacne.getClearanceNo()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into mst_clearance_no(clearanceNo,lotNumber,clearanceDate,productName,"
						+ "batchNumber,batchQty,issueId) "
						+ "values(?,?,?,?,?,?,?)");
				pstmt.setInt(1, clearacne.getClearanceNo());
				pstmt.setString(2, clearacne.getLotNumber());
				java.sql.Date date = new java.sql.Date(clearacne.getClearanceDate().getTime());
				pstmt.setDate(3, date);
				pstmt.setString(4, TiimUtil.ValidateNull(clearacne.getProductName()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(clearacne.getBatchNumber()).trim());
				pstmt.setInt(6, clearacne.getBatchQty());
				pstmt.setLong(7, clearacne.getIssueId());
				pstmt.executeUpdate();
				msg = "Saved Successfully";
				//Product product = productDao.getProductIntervalQnty(clearacne.getProductName(), clearanceClosingDao.getDrawingNumber(clearacne.getLotNumber()));
				/*
				 * ToolingReceiptNote receiptNote =
				 * receiptDao.getReceiptIntervalQnty("clearacne.getLotNumber()"); long toolLife
				 * = receiptNote.getToolingLife() - clearacne.getBatchQty(); long intervalQnty =
				 * receiptNote.getServiceIntervalQnty() - clearacne.getBatchQty();
				 */
				//long toolLife = clearacne.getToolingLife() - clearacne.getBatchQty();
				long toolLife = 0;
				long intervalQnty = clearacne.getNextDueQty() - clearacne.getBatchQty();
				receiptDao.updateReceiptIntervalQnty(clearacne.getLotNumber(), toolLife, intervalQnty);
				
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("clearance.page", null,null));
				history.setDescription(messageSource.getMessage("clearance.insert", null,null));
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
			System.out.println("Exception when adding the detail in mst_clearance_no table : "+ex.getMessage());
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
				System.out.println("Exception when  the connection in master detail in mst_clearance_no table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateClearance(ClearanceNo clearance, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update mst_clearance_no set clearanceNo= ?,lotNumber= ?,clearanceDate= ?,productName= ?,batchNumber= ?,batchQty= ?"
						+ " where clearanceNo = ?");
				pstmt.setInt(1, clearance.getClearanceNo());
				pstmt.setString(2, clearance.getLotNumber());
				java.sql.Date date = new java.sql.Date(clearance.getClearanceDate().getTime());
				pstmt.setDate(3, date);
				pstmt.setString(4, TiimUtil.ValidateNull(clearance.getProductName()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(clearance.getBatchNumber()).trim());
				pstmt.setInt(6, clearance.getBatchQty());
				pstmt.setInt(7, clearance.getClearanceNo());

				pstmt.executeUpdate();
				msg = "Updated Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("clearance.page", null,null));
				history.setDescription(messageSource.getMessage("clearance.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the detail in mst_clearance_no table : "+ex.getMessage());
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
				System.out.println("Exception when  the connection in detail in mst_clearance_no table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteClearance(int clearanceNo, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from mst_clearance_no where clearanceNo = ?");
			pstmt.setInt(1, clearanceNo);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("clearance.page", null,null));
			history.setDescription(messageSource.getMessage("clearance.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the sop detail in mst_clearance_no table : "+ex.getMessage());
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
				System.out.println("Exception when  the connection in delete the sop detail in mst_clearance_no table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	public List<ClearanceNo> getClearanceDetails(int serialNo)
	{
		List<ClearanceNo> lstSop = new ArrayList<ClearanceNo>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ClearanceNo clearance;
		try
		{
			con = datasource.getConnection();
			if(serialNo != 0)
			{
			    pstmt = con.prepareStatement("Select clearanceNo,lotNumber,clearanceDate,productName,batchNumber,batchQty"
                                  +" from mst_clearance_no Where clearanceNo like '%"+serialNo+"%' order by clearanceNo");
			}
			else
			{
				pstmt = con.prepareStatement("Select clearanceNo,lotNumber,clearanceDate,productName,batchNumber,batchQty from mst_clearance_no order by clearanceNo");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				clearance = new ClearanceNo();
				clearance.setClearanceNo(rs.getInt("clearanceNo"));
				clearance.setLotNumber(rs.getString("lotNumber"));
				clearance.setClearanceDate(rs.getDate("clearanceDate"));
				clearance.setProductName(TiimUtil.ValidateNull(rs.getString("productName")).trim());
				clearance.setBatchNumber(TiimUtil.ValidateNull(rs.getString("batchNumber")).trim());
				clearance.setBatchQty(rs.getInt("batchQty"));
				lstSop.add(clearance);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire sop in mst_clearance_no table : "+ex.getMessage());
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
				System.out.println("Exception when  the connection in entire sop details in mst_clearance_no table : "+ex.getMessage());
			}
		}
		return lstSop;	
	}
	
	public List<ClearanceNo> getAutoClearanceDetails(int serialNo)
	{
		List<ClearanceNo> lstSop = new ArrayList<ClearanceNo>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ClearanceNo clearance;
		try
		{
			con = datasource.getConnection();
			if(serialNo != 0)
			{
			    pstmt = con.prepareStatement("Select clearanceNo,lotNumber,clearanceDate,productName,batchNumber,batchQty"
                                  +" from mst_clearance_no Where clearanceNo like '%"+serialNo+"%' and clearanceNo not in(select clearanceNo from mst_clearance_Closing) order by clearanceNo");
			}
			else
			{
				pstmt = con.prepareStatement("Select clearanceNo,lotNumber,clearanceDate,productName,batchNumber,batchQty from mst_clearance_no where clearanceNo not in(select clearanceNo from mst_clearance_Closing) "
								+ " order by clearanceNo");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				clearance = new ClearanceNo();
				clearance.setClearanceNo(rs.getInt("clearanceNo"));
				clearance.setLotNumber(rs.getString("lotNumber"));
				clearance.setClearanceDate(rs.getDate("clearanceDate"));
				clearance.setProductName(TiimUtil.ValidateNull(rs.getString("productName")).trim());
				clearance.setBatchNumber(TiimUtil.ValidateNull(rs.getString("batchNumber")).trim());
				clearance.setBatchQty(rs.getInt("batchQty"));
				lstSop.add(clearance);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire sop in mst_clearance_no table : "+ex.getMessage());
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
				System.out.println("Exception when  the connection in entire sop details in mst_clearance_no table : "+ex.getMessage());
			}
		}
		return lstSop;	
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
				System.out.println("Exception when  the connection in  particular branch details in branch table by using departmentid : "+ex.getMessage());
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
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_clearance_no WHERE clearanceNo = ?");
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
				System.out.println("Exception when  the connection in isBranchExists : "+ex.getMessage());
			}
		}
		return isExists;
	}
	
	public List<String> getToolingLotNo(String productName, String lotNumber)
	{
		List<String>  lstLotNumber = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT lotnumber FROM mst_clearance_no where lotnumber like '%"+lotNumber+"%' and productname = ?");
			pstmt.setString(1, productName.trim());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				boolean isExpired = expiryDao.isProductExpired(rs.getString("lotnumber"));
				boolean isRejected = expiryDao.isProductRejected(rs.getString("lotnumber"));
				if(!isExpired && !isRejected)
				{
					lstLotNumber.add(rs.getString("lotnumber"));
				}
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getToolingLotNo in clearance number: "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getToolingLotNo in clearance number : "+ex.getMessage());
			}
		}
		return lstLotNumber;
	}
	
	public List<ClearanceReport> getClearanceReport(String lotNumber)
	{
		List<ClearanceReport> lstSop = new ArrayList<ClearanceReport>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		ClearanceReport clearance;
		try
		{
			con = datasource.getConnection();
			if(lotNumber != null)
			{
			    pstmt = con.prepareStatement("SELECT m.lotnumber lotnumber, m.clearancedate clearancedate,  m.productName productName, n.clearancedate closingdate,"
			    		+ " m.batchqty batchqty, m.batchnumber batchnumber FROM mst_clearance_no m left join mst_clearance_closing n on m.clearanceNo = n.clearanceNo where  m.lotNumber = ?");
			    pstmt.setString(1, lotNumber);
			}
			else
			{
				pstmt = con.prepareStatement("SELECT m.lotnumber lotnumber, m.clearancedate clearancedate,  m.productName productName, n.clearancedate closingdate,"
						+ " m.batchqty batchqty, m.batchnumber batchnumber FROM mst_clearance_no m left join mst_clearance_closing n on m.clearanceNo = n.clearanceNo ");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				clearance = new ClearanceReport();
				clearance.setLotNumber(rs.getString("lotNumber"));
				clearance.setClearanceDate(rs.getDate("clearanceDate"));
				clearance.setProductName(TiimUtil.ValidateNull(rs.getString("productName")).trim());
				clearance.setBatchNumber(TiimUtil.ValidateNull(rs.getString("batchNumber")).trim());
				clearance.setBatchQty(rs.getInt("batchQty"));
				clearance.setClosingDate(rs.getDate("closingdate"));
				lstSop.add(clearance);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getClearanceReport : "+ex.getMessage());
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
				System.out.println("Exception when the connection in getClearanceReport : "+ex.getMessage());
			}
		}
		return lstSop;	
	}
	
	public ClearanceNo getClearanceNumberCount(String lotNumber, Long issueId)
	{
		ClearanceNo clearance = new ClearanceNo();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select count(*) counter, sum(batchqty) totalbatchqty from mst_clearance_no where lotNumber = ? and isMoved = ? and issueId = ? ");
			pstmt.setString(1, lotNumber.trim());
			//pstmt.setInt(2, 1);
			pstmt.setInt(2, 0);
			pstmt.setLong(3, issueId);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				clearance.setBatchNumber(rs.getString("counter"));
				clearance.setBatchQty(rs.getInt("totalbatchqty"));
			}else
			{
				clearance.setBatchNumber("0");
				clearance.setBatchQty(0);
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getClearanceNumberCount in clearance number: "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in getClearanceNumberCount in clearance number : "+ex.getMessage());
			}
		}
		return clearance;
	}
}
