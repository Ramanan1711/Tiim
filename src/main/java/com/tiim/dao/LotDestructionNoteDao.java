package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.tiim.model.LotToolDestruction;

@Repository
public class LotDestructionNoteDao {

	@Autowired
	DataSource datasource;
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	
	public String addLotToolDestruction(LotToolDestruction toolDestruction, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isLotToolDestructionExists(toolDestruction.getDestructionNo()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into tool_lot_destruction(lotdestruction,lotdestructiondate,destructionno,destroyedby,lotno,punch,serialno,rejectedat,remarks)"
						+ "values(?,?,?,?,?,?,?,?,?)");
				pstmt.setInt(1, toolDestruction.getLotDestruction());
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(2, date);
				pstmt.setInt(3, toolDestruction.getDestructionNo());
				pstmt.setString(4, toolDestruction.getDestroyedBy());
				pstmt.setString(5, toolDestruction.getLotNo());
				pstmt.setString(6, toolDestruction.getPunch());
				pstmt.setString(7, toolDestruction.getSerailNo());
				pstmt.setString(8, toolDestruction.getRejectedAt());
				pstmt.setString(9, toolDestruction.getRemarks());
				
				pstmt.executeUpdate();
				msg = "Saved Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("lotdestruction.page", null,null));
				history.setDescription(messageSource.getMessage("lotdestruction.insert", null,null));
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
			System.out.println("Exception when adding the toolDestruction detail in tool_lot_destruction table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in toolDestruction master detail in tool_lot_destruction table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateLotToolDestruction(LotToolDestruction toolDestruction, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update tool_lot_destruction set lotdestruction=?,lotdestructiondate=?,destructionno=?,destroyedby=?,lotno=?,punch=?,serialno=?,rejectedat=?,remarks=?"
						+ " where lotdestruction = ?");
				pstmt.setInt(1, toolDestruction.getLotDestruction());
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(2, date);
				pstmt.setInt(3, toolDestruction.getDestructionNo());
				pstmt.setString(4, toolDestruction.getDestroyedBy());
				pstmt.setString(5, toolDestruction.getLotNo());
				pstmt.setString(6, toolDestruction.getPunch());
				pstmt.setString(7, toolDestruction.getSerailNo());
				pstmt.setString(8, toolDestruction.getRejectedAt());
				pstmt.setString(9,toolDestruction.getRemarks());
				pstmt.setInt(10, toolDestruction.getLotDestruction());
				


				pstmt.executeUpdate();
				msg = "Updated Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("lotdestruction.page", null,null));
				history.setDescription(messageSource.getMessage("lotdestruction.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the toolDestruction detail in tool_lot_destruction table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in toolDestruction detail in tool_lot_destruction table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteLotToolDestruction(int lotdestruction, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from tool_lot_destruction where lotdestruction = ?");
			pstmt.setInt(1, lotdestruction);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("lotdestruction.page", null,null));
			history.setDescription(messageSource.getMessage("lotdestruction.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the toolDestruction detail in tool_lot_destruction table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the toolDestruction detail in tool_lot_destruction table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	public List<LotToolDestruction> getLotToolDestructionDetails(int lotdestruction)
	{
		List<LotToolDestruction> lstSop = new ArrayList<LotToolDestruction>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		LotToolDestruction toolDestruction;
		try
		{
			con = datasource.getConnection();
			if(lotdestruction != 0)
			{
			    pstmt = con.prepareStatement("Select lotdestruction,lotdestructiondate,destructionno,destroyedby,lotno,punch,serialno,rejectedat,remarks "
			    		+ " FROM tool_lot_destruction Where lotdestruction like '%"+lotdestruction+"%' order by lotdestruction");
			}
			else
			{
				pstmt = con.prepareStatement("Select lotdestruction,lotdestructiondate,destructionno,destroyedby,lotno,punch,serialno,rejectedat,remarks"
						+ " from tool_lot_destruction order by lotdestruction");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				toolDestruction = new LotToolDestruction();
				toolDestruction.setLotDestruction(rs.getInt("lotdestruction"));
				toolDestruction.setLotDestructionDate(rs.getString("lotdestructiondate"));
				toolDestruction.setDestructionNo(rs.getInt("destructionno"));
				toolDestruction.setDestroyedBy(rs.getString("destroyedby"));
				toolDestruction.setLotNo(rs.getString("lotno"));
				toolDestruction.setPunch(rs.getString("punch"));
				toolDestruction.setSerailNo(rs.getString("serialno"));
				toolDestruction.setRejectedAt(rs.getString("rejectedat"));
				toolDestruction.setRemarks(rs.getString("remarks"));

				lstSop.add(toolDestruction);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire toolDestruction in tool_lot_destruction table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire toolDestruction details in tool_lot_destruction table : "+ex.getMessage());
			}
		}
		return lstSop;	
	}
	
	
	private boolean isLotToolDestructionExists(int lotdestruction)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM tool_lot_destruction WHERE lotdestruction = ?");
			 pstmt.setInt(1, lotdestruction);
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
		int lotdestruction = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(lotdestruction) lotdestruction FROM tool_lot_destruction ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				lotdestruction = rs.getInt("lotdestruction");
			}
			lotdestruction++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in tool_lot_destruction  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in tool_lot_destruction : "+ex.getMessage());
			}
		}
		
		return lotdestruction;	
	}
}
