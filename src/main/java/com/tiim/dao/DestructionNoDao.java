package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.tiim.model.DestructionNote;
import com.tiim.util.TiimUtil;

@Repository
public class DestructionNoDao {

	@Autowired
	DataSource datasource;
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	
	public String addDestructionNo(DestructionNote destructionNote, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isDestructionNoteExists(destructionNote.getDestructionNo()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into tool_destruction_note(destructionno,destructiondate,uploadedpath) "
						+ "values(?,?,?)");
				pstmt.setInt(1, destructionNote.getDestructionNo());
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(2, date);
				pstmt.setString(3, destructionNote.getUploadedPath());

				pstmt.executeUpdate();
				msg = "Saved Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("destructionNote.page", null,null));
				history.setDescription(messageSource.getMessage("destructionNote.insert", null,null));
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
			System.out.println("Exception when adding the destructionNote detail in tool_destruction_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in destructionNote master detail in tool_destruction_note table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateDestructionNote(DestructionNote destructionNote, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update tool_destruction_note set destructionno= ?,destructiondate= ?,uploadedpath= ?"
						+ " where destructionno = ?");
				pstmt.setInt(1, destructionNote.getDestructionNo());
				Calendar calendar = Calendar.getInstance();
			    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
				pstmt.setDate(2, date);
				pstmt.setString(3, destructionNote.getUploadedPath());

				pstmt.executeUpdate();
				msg = "Updated Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("destructionNote.page", null,null));
				history.setDescription(messageSource.getMessage("destructionNote.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the destructionNote detail in tool_destruction_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in destructionNote detail in tool_destruction_note table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteDestructionNote(int destructionno, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from tool_destruction_note where destructionno = ?");
			pstmt.setInt(1, destructionno);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("destructionNote.page", null,null));
			history.setDescription(messageSource.getMessage("destructionNote.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the destructionNote detail in tool_destruction_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in delete the destructionNote detail in tool_destruction_note table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	public List<DestructionNote> getDestructionNoteDetails(int destructionno)
	{
		List<DestructionNote> lstSop = new ArrayList<DestructionNote>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		DestructionNote destructionNote;
		try
		{
			con = datasource.getConnection();
			if(destructionno != 0)
			{
			    pstmt = con.prepareStatement("Select destructionno, destructiondate, uploadedpath from tool_destruction_note Where destructionno "
			    		+ "like '%"+destructionno+"%' order by destructionno");
			}
			else
			{
				pstmt = con.prepareStatement("Select destructionno, destructiondate, uploadedpath from tool_destruction_note order by destructionno");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				destructionNote = new DestructionNote();
				destructionNote.setDestructionNo(rs.getInt("destructionno"));
				destructionNote.setDestructionDate(rs.getString("destructiondate"));
				destructionNote.setUploadedPath(rs.getString("uploadedpath"));
				lstSop.add(destructionNote);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire destructionNote in tool_destruction_note table : "+ex.getMessage());
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
				System.out.println("Exception when closing the connection in entire destructionNote details in tool_destruction_note table : "+ex.getMessage());
			}
		}
		return lstSop;	
	}
	
	
	private boolean isDestructionNoteExists(int destructionno)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM tool_destruction_note WHERE destructionno = ?");
			 pstmt.setInt(1, destructionno);
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
		int destructionno = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(destructionno) destructionno FROM tool_destruction_note ");
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				destructionno = rs.getInt("destructionno");
			}
			destructionno++;
			
		}catch(Exception ex)
		{
			System.out.println("Exception in tool_destruction_note  getIntialValue  : "+ex.getMessage());
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
				System.out.println("Exception when closing the connectin in tool_destruction_note : "+ex.getMessage());
			}
		}
		
		return destructionno;	
	}
}
