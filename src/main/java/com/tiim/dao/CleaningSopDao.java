package com.tiim.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.tiim.model.Branch;
import com.tiim.model.CleaningSop;
import com.tiim.model.Department;
import com.tiim.util.TiimUtil;

@Repository
public class CleaningSopDao {

	@Autowired
	DataSource datasource;
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	
	public String addsop(CleaningSop sop, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isBranchExists(sop.getSerialNo()))
			{
				String typeExist = getInsertedTypeValue(TiimUtil.ValidateNull(sop.getCleaningtype()).trim());
				if(typeExist.equalsIgnoreCase(TiimUtil.ValidateNull(sop.getCleaningtype()).trim())){
					msg = "Selected Type Is Already Exists";
					return msg;
				}
				if(sop.getCleaningprocess() == null){
					msg = "Atleast One Process Should Be Added";
					return msg;
				}
				for(int i = 0; i< sop.getCleaningprocess().length;i++)
				{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into mst_cleaning_sop(serialNo,cleaningtype,description,cleaningprocess,approvalflag,approvedby,approveddate,alertWeeks) "
						+ "values(?,?,?,?,?,?,?,?)");
				pstmt.setInt(1, sop.getSerialNo());
				pstmt.setString(2, TiimUtil.ValidateNull(sop.getCleaningtype()).trim());
				pstmt.setString(3, TiimUtil.ValidateNull(sop.getDescription()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(sop.getCleaningprocess()[i]).trim());
				pstmt.setInt(5, sop.getApprovalflag());
				pstmt.setString(6, TiimUtil.ValidateNull(sop.getApprovedby()).trim());
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(7, date);
				pstmt.setInt(8, sop.getAlertWeeks());

				pstmt.executeUpdate();
				msg = "Saved Successfully";
			}
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("sop.page", null,null));
				history.setDescription(messageSource.getMessage("sop.insert", null,null));
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
			System.out.println("Exception when adding the sop detail in mst_cleaning_sop table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in sop master detail in mst_cleaning_sop table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateSop(CleaningSop sop, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection(); 
				for(int i = 0; i< sop.getCleaningprocess().length;i++)
				{
				pstmt = con.prepareStatement("Update mst_cleaning_sop set serialNo= ?,cleaningtype= ?,description= ?,cleaningprocess= ?,approvalflag= ?,approvedby= ?,approveddate= ?,alertWeeks= ? where serialNo = ? and cleaningId =? ");
				pstmt.setInt(1, sop.getSerialNo());
				pstmt.setString(2, TiimUtil.ValidateNull(sop.getCleaningtype()).trim());
				pstmt.setString(3, TiimUtil.ValidateNull(sop.getDescription()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(sop.getCleaningprocess()[i]).trim());
				pstmt.setInt(5, sop.getApprovalflag());
				pstmt.setString(6, TiimUtil.ValidateNull(sop.getApprovedby()).trim());
				pstmt.setDate(7, (Date) sop.getApproveddate());
				pstmt.setInt(8, sop.getSerialNo());
				pstmt.setInt(9, sop.getCleaningId()[i]);
				pstmt.setInt(10,sop.getAlertWeeks());

				pstmt.executeUpdate();
				msg = "Updated Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("sop.page", null,null));
				history.setDescription(messageSource.getMessage("sop.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
				}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the sop detail in mst_cleaning_sop table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in sop detail in mst_cleaning_sop table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteSop(int serialNo, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from mst_cleaning_sop where serialNo = ?");
			pstmt.setInt(1, serialNo);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("sop.page", null,null));
			history.setDescription(messageSource.getMessage("sop.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the sop detail in mst_cleaning_sop table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the sop detail in mst_cleaning_sop table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteProcess(int cleaningId, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from mst_cleaning_sop where cleaningId = ?");
			pstmt.setInt(1, cleaningId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("sop.page", null,null));
			history.setDescription(messageSource.getMessage("sop.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the sop detail in mst_cleaning_sop table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the sop detail in mst_cleaning_sop table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	public List<CleaningSop> getSopDetails(int serialNo)
	{
		List<CleaningSop> lstSop = new ArrayList<CleaningSop>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		CleaningSop sop;
		try
		{
			con = datasource.getConnection();
			if(serialNo != 0)
			{
			    pstmt = con.prepareStatement("Select cleaningId,serialNo, cleaningtype, description,cleaningprocess,alertWeeks from mst_cleaning_sop Where serialNo like '%"+serialNo+"%' order by cleaningId");
			}
			else
			{
				pstmt = con.prepareStatement("Select cleaningId,serialNo, cleaningtype, description,cleaningprocess,alertWeeks from mst_cleaning_sop order by cleaningId");
			}
			rs = pstmt.executeQuery();
			rs.last();
			String clean[] = new String[rs.getRow()];
			int cleanId[] = new int[rs.getRow()];
			rs.beforeFirst();
			int count = 0; 
			while(rs.next())
			{
				sop = new CleaningSop();
				cleanId[count] =rs.getInt("cleaningId");
				sop.setCleaningId(cleanId);
				sop.setSerialNo(rs.getInt("serialNo"));
				sop.setCleaningtype(TiimUtil.ValidateNull(rs.getString("cleaningtype")).trim());
				sop.setDescription(TiimUtil.ValidateNull(rs.getString("description")).trim());
				clean[count] = TiimUtil.ValidateNull(rs.getString("cleaningprocess")).trim();
				sop.setCleaningprocess(clean);
				sop.setAlertWeeks(rs.getInt("alertWeeks"));
				lstSop.add(sop);
				count++;
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire sop in mst_cleaning_sop table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire sop details in mst_cleaning_sop table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in  particular branch details in branch table by using departmentid : "+ex.getMessage());
			}
		}
		return branch;	
	}
	
	private boolean isBranchExists(int sopId)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_cleaning_sop WHERE serialNo = ?");
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
	public int getIntialValue()
	{
		int serialNo = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(serialNo) serialNo FROM mst_cleaning_sop ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				serialNo = rs.getInt("serialNo");
			}
			serialNo++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in mst_cleaning_sop  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in mst_cleaning_sop : "+ex.getMessage());
			}
		}
		
		return serialNo;	
	}
	public List<CleaningSop> getSopListDetails(int serialNo)
	{
		List<CleaningSop> lstSop = new ArrayList<CleaningSop>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		CleaningSop sop;
		try
		{
			con = datasource.getConnection();
			if(serialNo != 0)
			{
			    pstmt = con.prepareStatement("select distinct cleaningId,serialNo, cleaningtype, description,cleaningprocess from mst_cleaning_sop wh"
			    		+ "ere row(serialNo, cleaningId) in (select serialNo, max(cleaningId) from mst_cleaning_sop where serialNo like '%"+serialNo+"%' group by serialNo)"
			    		+ " order by serialNo");
			}
			else
			{
				pstmt = con.prepareStatement("select distinct cleaningId,serialNo, cleaningtype, description,cleaningprocess from mst_cleaning_sop "
						+ "where row(serialNo, cleaningId) in (select serialNo, max(cleaningId) from mst_cleaning_sop group by serialNo)"
						+ " order by serialNo");
			}
			rs = pstmt.executeQuery();
			rs.last();
			String clean[] = new String[rs.getRow()];
			int cleanId[] = new int[rs.getRow()];
			rs.beforeFirst();
			int count = 0; 
			while(rs.next())
			{
				sop = new CleaningSop();
				cleanId[count] =rs.getInt("cleaningId");
				sop.setCleaningId(cleanId);
				sop.setSerialNo(rs.getInt("serialNo"));
				sop.setCleaningtype(TiimUtil.ValidateNull(rs.getString("cleaningtype")).trim());
				sop.setDescription(TiimUtil.ValidateNull(rs.getString("description")).trim());
				clean[count] = TiimUtil.ValidateNull(rs.getString("cleaningprocess")).trim();
				sop.setCleaningprocess(clean);
				lstSop.add(sop);
				count++;
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire sop in mst_cleaning_sop table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire sop details in mst_cleaning_sop table : "+ex.getMessage());
			}
		}
		return lstSop;	
	}
	
	public List<String> getSop(String cleaningType)
	{
		List<String> lstSop = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			
			pstmt = con.prepareStatement("Select cleaningId,serialNo, cleaningtype, description,cleaningprocess,alertWeeks from mst_cleaning_sop"
					+ " where  cleaningtype = ? order by cleaningId");
			pstmt.setString(1, cleaningType);			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				lstSop.add(rs.getString("cleaningprocess"));
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the getSop in mst_cleaning_sop table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire sop details in mst_cleaning_sop table : "+ex.getMessage());
			}
		}
		return lstSop;	
	}
	
	public String getInsertedTypeValue(String type)
	{
		String cleaningtype = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT cleaningtype FROM mst_cleaning_sop where cleaningtype =? group by serialNo");
			pstmt.setString(1, type);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				cleaningtype = rs.getString("cleaningtype");
			}
			
		}catch(Exception ex)
		{
			System.out.println("Exception in mst_cleaning_sop  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in mst_cleaning_sop : "+ex.getMessage());
			}
		}
		
		return cleaningtype;	
	}
}
